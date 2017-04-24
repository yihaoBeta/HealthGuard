package com.yihaobeta.healthguard.dagger.modules;

import android.content.Context;

import com.yihaobeta.healthguard.adapter.MedicineClassifyAdapter;
import com.yihaobeta.healthguard.adapter.MedicineListAdapter;
import com.yihaobeta.healthguard.base.IView;
import com.yihaobeta.healthguard.beans.MedicineList;
import com.yihaobeta.healthguard.common.SettingChangeBroadcastReceiver;
import com.yihaobeta.healthguard.dagger.scope.FragmentScope;
import com.yihaobeta.healthguard.ui.medicine.MedicineHomePresenter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yihaobeta on 2017/4/22 0022.
 */
@Module
public class MedicineHomeModule {
    private MedicineListAdapter mAdapter;
    private Context mContext;
    private IView mView;

    public MedicineHomeModule(Context context, IView view) {
        mContext = context;
        mView = view;
        mAdapter = new MedicineListAdapter(mContext, new ArrayList<MedicineList.MedicineSummary>());
    }

    @Provides
    @FragmentScope
    public MedicineClassifyAdapter provideClassifyAdapter() {
        return new MedicineClassifyAdapter(mContext);
    }

    @Provides
    @FragmentScope
    public MedicineListAdapter provideListAdapter() {
        return mAdapter;
    }

    @Provides
    @FragmentScope
    public MedicineHomePresenter providePresenter() {
        return new MedicineHomePresenter(mView);
    }

    @Provides
    @FragmentScope
    public SettingChangeBroadcastReceiver provideBroadcastRecevier() {
        return new SettingChangeBroadcastReceiver(mContext, mAdapter);
    }
}
