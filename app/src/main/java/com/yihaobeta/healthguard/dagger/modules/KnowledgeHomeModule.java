package com.yihaobeta.healthguard.dagger.modules;

import android.support.v4.app.FragmentManager;

import com.yihaobeta.healthguard.adapter.ViewPagerAdapter;
import com.yihaobeta.healthguard.dagger.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yihaobeta on 2017/3/11.
 */
@Module
public class KnowledgeHomeModule {
    private FragmentManager mManager;

    public KnowledgeHomeModule(FragmentManager manager) {
        this.mManager = manager;
    }

    @Provides
    @FragmentScope
    public ViewPagerAdapter provideViewPagerAdapter() {
        return new ViewPagerAdapter(mManager);
    }
}
