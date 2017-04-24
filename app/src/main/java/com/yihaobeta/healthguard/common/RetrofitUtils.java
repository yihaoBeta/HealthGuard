package com.yihaobeta.healthguard.common;


import android.content.Context;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.api.ApiService;
import com.yihaobeta.healthguard.base.BaseApplication;
import com.yihaobeta.healthguard.utils.FileUtils;
import com.yihaobeta.healthguard.utils.NetWorkUtils;
import com.yihaobeta.healthguard.utils.PreferencesUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit网络请求工具类
 * Created by HDL on 2016/7/29.
 */
public class RetrofitUtils {

    public static File mHttpCacheFile = new File(BaseApplication.getContext().getCacheDir(), "http-cache");
    private static Retrofit mRetrofit;
    private static Context mContext;
    private static long mCacheSize = 20 * 1024 * 1024;

    private RetrofitUtils(Context context) {
        mContext = context;
    }

    public static void init(Context context) {
        if (mContext == null) {
            synchronized (RetrofitUtils.class) {
                if (mContext == null) {
                    new RetrofitUtils(context);
                }
            }
        }
    }

    public static ApiService getApiService() {
        if (mRetrofit == null) {
            synchronized (RetrofitUtils.class) {
                if (mRetrofit == null) {
                    //初始化一个client,不然retrofit会自己默认添加一个
                    mRetrofit = new Retrofit.Builder()
                            .client(provideOkHttpClient())//添加一个client,不然retrofit会自己默认添加一个
                            .baseUrl(ConstantValue.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(new Gson()))
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }

        return mRetrofit.create(ApiService.class);
    }

    /**
     * 设置返回数据的  Interceptor
     * 判断网络   没网或者当前非WIFI网络且不许允使用移动数据时读取缓存
     */
    private static Interceptor getOfflineInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetWorkUtils.isNetworkReachable(mContext)
                        || (NetWorkUtils.NET_TYPE_WIFI != NetWorkUtils.getNetType(mContext)
                        && !PreferencesUtils.isMobileNetEnable())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }

    /**
     * 设置连接器  设置缓存
     */
    private static Interceptor getNetWorkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                if (NetWorkUtils.isNetworkReachable(mContext)) {
                    int maxAge = 3 * 60;
                    // 有网络时 设置缓存超时时间
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时时间
                    int maxStale = 60 * 60 * 24 * 7;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
    }

    /**
     * 设置缓存路径和缓存大小
     *
     * @return
     */
    private static Cache provideCache() {
        Cache cache = null;

        try {
            cache = new Cache(mHttpCacheFile, mCacheSize);
        } catch (Exception e) {
            Logger.e("cache can not be provided");
            e.printStackTrace();
        }
        return cache;
    }

    /**
     * 生成自定义HttpClient
     *
     * @return
     */
    private static OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        OkHttpClient client = builder.readTimeout(ConstantValue.READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(ConstantValue.CONN_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(getOfflineInterceptor())
                .addNetworkInterceptor(getNetWorkInterceptor())
                .writeTimeout(ConstantValue.CONN_TIMEOUT, TimeUnit.SECONDS)
                .cache(provideCache())
                .build();
        return client;
    }

    /**
     * 获取当前http缓存大小
     *
     * @return
     */
    public static long getCacheSize() {
        long result = 0;
        try {
            result = FileUtils.getFolderSize(mHttpCacheFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 清除已有的http缓存数据
     *
     * @return
     */
    public static boolean clearCache() {
        return FileUtils.delTree(mHttpCacheFile);
    }
}
