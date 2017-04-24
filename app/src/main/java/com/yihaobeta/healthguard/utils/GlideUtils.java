package com.yihaobeta.healthguard.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.base.BaseApplication;

/**
 * Created by yihaobeta on 2017/3/22.
 * Glide工具类，用于提供全局的Glide配置
 */

public class GlideUtils {

    static Context mContext;
    private static DrawableRequestBuilder<Integer> thumbnailRequest;
    private static GlideUtils sInstance;

    private GlideUtils() {
        mContext = BaseApplication.getContext();
        thumbnailRequest = Glide
                .with(mContext)
                .load(R.drawable.ic_default);
    }

    /**
     * 初始化Glide
     */
    public static void init() {
        if (sInstance == null) {
            synchronized (GlideUtils.class) {
                if (sInstance == null) {
                    sInstance = new GlideUtils();
                }
            }
        }
    }

    public static void showPicture(String url, ImageView imageView) {
        if (sInstance == null)
            throw new RuntimeException("GlideUtils is not init");
        if (!NetWorkUtils.isNetworkReachable(mContext))
            return;
        if (!NetWorkUtils.isWifiConnected(mContext) && PreferencesUtils.isNoPicMode()) {
            Glide.with(mContext)
                    .load(R.drawable.ic_default)
                    .thumbnail(thumbnailRequest)
                    .into(imageView);
            return;
        }
        Glide.with(mContext)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(thumbnailRequest)
                .into(imageView);

    }

    public static long getCacheSize() {
        long result = 0;
        try {
            result = FileUtils.getFolderSize(Glide.getPhotoCacheDir(BaseApplication.getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 清除图片磁盘缓存，调用Glide自带方法
     *
     * @return
     */
    public static boolean clearCacheDiskSelf() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(BaseApplication.getContext()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(BaseApplication.getContext()).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 清除Glide内存缓存
     *
     * @return
     */
    public static boolean clearCacheMemory() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(BaseApplication.getContext()).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 清除所有Glide缓存
     *
     * @return
     */
    public static boolean clearAllCache() {
        return clearCacheDiskSelf() && clearCacheMemory();
    }
}
