package com.yihaobeta.healthguard.base;

import android.app.Application;
import android.content.Context;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.yihaobeta.healthguard.common.GreenDaoUtils;
import com.yihaobeta.healthguard.common.RetrofitUtils;
import com.yihaobeta.healthguard.dagger.component.AppComponent;
import com.yihaobeta.healthguard.dagger.component.DaggerAppComponent;
import com.yihaobeta.healthguard.dagger.modules.AppModule;
import com.yihaobeta.healthguard.utils.GlideUtils;
import com.yihaobeta.healthguard.utils.LoggerUtils;
import com.yihaobeta.healthguard.utils.ToastUtils;

/**
 * description:BaseApplication
 * author: yihaoBeta
 * date: 2017/3/13 0013 12:47
 * update: 2017/4/13 0013
 * version: v1.0
 */
public class BaseApplication extends Application {
    private static AppComponent sAppComponent;
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        initDataBase();
        initInjector();
        initOthers();
    }

    /**
     * 初始化操作
     */
    private void initOthers() {
        RetrofitUtils.init(sContext);
        ToastUtils.init(this);
        ZXingLibrary.initDisplayOpinion(this);
        LoggerUtils.init();
        GlideUtils.init();
        /*RxJavaPlugins.getInstance().registerErrorHandler(new RxJavaErrorHandler() {
            @Override
            public void handleError(Throwable e) {
                Logger.e(e.getMessage());
            }
        });*/
    }

    /**
     * dagger全局初始化
     */
    private void initInjector() {
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    /**
     * 数据库全局初始化
     */
    private void initDataBase() {
        GreenDaoUtils.init(this);
    }

}
