package com.yihaobeta.healthguard.dagger.modules;

import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;

import com.yihaobeta.healthguard.common.FragmentController;
import com.yihaobeta.healthguard.dagger.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yihaobeta on 2017/3/11.
 */
@Module
public class MainActivityModule {
    FragmentActivity mContext;
    @LayoutRes
    int layoutId;

    public MainActivityModule(FragmentActivity context, int layoutId) {
        mContext = context;
        this.layoutId = layoutId;
    }

    @Provides
    @ActivityScope
    public FragmentController provideFragmentManager() {
        return FragmentController.getInstance(mContext, layoutId, true);
    }
}
