package com.yihaobeta.healthguard.dagger.modules;

import android.content.Context;

import com.yihaobeta.healthguard.base.BaseApplication;
import com.yihaobeta.healthguard.view.EmptyView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yihaobeta on 2017/3/10.
 */

@Module
public class AppModule {
    private final BaseApplication mApplication;

    public AppModule(BaseApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    EmptyView provideEmptyView() {
        return new EmptyView(mApplication);
    }
}
