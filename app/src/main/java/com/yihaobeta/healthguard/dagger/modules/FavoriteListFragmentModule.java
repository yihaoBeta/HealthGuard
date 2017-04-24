package com.yihaobeta.healthguard.dagger.modules;

import android.content.Context;

import com.yihaobeta.healthguard.adapter.FavoriteListAdapter;
import com.yihaobeta.healthguard.base.IView;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.dagger.scope.FragmentScope;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;
import com.yihaobeta.healthguard.ui.profile.LocalPresenter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yihaobeta on 2017/4/22 0022.
 */
@Module
public class FavoriteListFragmentModule {
    private Context mContext;
    private IView mView;

    public FavoriteListFragmentModule(Context context, IView view) {
        mContext = context;
        mView = view;
    }

    @Provides
    @FragmentScope
    public FavoriteListAdapter provideAdapter() {
        return new FavoriteListAdapter(new ArrayList<UniversalDetailEntity>(), mContext);
    }

    @Provides
    @FragmentScope
    public LocalPresenter providePresenter() {
        return new LocalPresenter(mView, ConstantValue.TYPE_FAVORITE);
    }
}
