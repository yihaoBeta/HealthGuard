package com.yihaobeta.healthguard.dagger.modules;

import android.content.Context;

import com.yihaobeta.healthguard.adapter.HistoryListAdapter;
import com.yihaobeta.healthguard.base.IView;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.dagger.scope.ActivityScope;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;
import com.yihaobeta.healthguard.ui.profile.LocalPresenter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yihaobeta on 2017/4/22 0022.
 */
@Module
public class HistoryModule {
    private IView mView;
    private Context mContext;

    public HistoryModule(IView view, Context context) {
        mView = view;
        mContext = context;
    }

    @Provides
    @ActivityScope
    public HistoryListAdapter provideAdapter() {
        return new HistoryListAdapter(new ArrayList<UniversalDetailEntity>(), mContext);
    }

    @Provides
    @ActivityScope
    public LocalPresenter providePresenter() {
        return new LocalPresenter(mView, ConstantValue.TYPE_HISTORY);
    }
}
