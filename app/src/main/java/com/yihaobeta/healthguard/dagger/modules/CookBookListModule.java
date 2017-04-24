package com.yihaobeta.healthguard.dagger.modules;

import android.content.Context;

import com.yihaobeta.healthguard.adapter.CookBookListAdapter;
import com.yihaobeta.healthguard.beans.CookBookList;
import com.yihaobeta.healthguard.common.SettingChangeBroadcastReceiver;
import com.yihaobeta.healthguard.dagger.scope.FragmentScope;
import com.yihaobeta.healthguard.ui.cookbook.CookBookListFragment;
import com.yihaobeta.healthguard.ui.cookbook.CookBookListPresenter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yihaobeta on 2017/4/22 0022.
 */
@Module
public class CookBookListModule {
    private CookBookListFragment mView;
    private Context mContext;
    private CookBookListAdapter mAdapter;

    public CookBookListModule(Context context, CookBookListFragment view) {
        mView = view;
        mContext = context;
        mAdapter = new CookBookListAdapter(new ArrayList<CookBookList.CookBookSummary>());
    }

    @Provides
    @FragmentScope
    public SettingChangeBroadcastReceiver provideBroadcastReceiver() {
        return new SettingChangeBroadcastReceiver(mContext, mAdapter);
    }

    @Provides
    @FragmentScope
    public CookBookListAdapter provideAdapter() {
        return mAdapter;
    }

    @Provides
    @FragmentScope
    public CookBookListPresenter providePresenter() {
        return new CookBookListPresenter(mView);
    }
}
