package com.yihaobeta.healthguard.dagger.modules;

import android.support.v4.app.FragmentActivity;

import com.yihaobeta.healthguard.adapter.ViewPagerAdapter;
import com.yihaobeta.healthguard.dagger.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yihaobeta on 2017/4/22 0022.
 */

@Module
public class FavoriteListActivityModule {
    private FragmentActivity mFragmentActivity;

    public FavoriteListActivityModule(FragmentActivity context) {
        mFragmentActivity = context;
    }

    @Provides
    @ActivityScope
    public ViewPagerAdapter provideViewPagerAdapter() {
        return new ViewPagerAdapter(mFragmentActivity.getSupportFragmentManager());
    }
}
