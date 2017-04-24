package com.yihaobeta.healthguard.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yihaobeta.healthguard.adapter.KnowledgeListAdapter;
import com.yihaobeta.healthguard.base.BaseApplication;
import com.yihaobeta.healthguard.utils.SliderHelper;

/**
 * Created by yihaobeta on 2017/4/4.
 * 广播接收器，用于接受setting中的选项改变
 */

public class SettingChangeBroadcastReceiver extends BroadcastReceiver {
    private BaseQuickAdapter mAdapter;
    private Context mContext;

    public SettingChangeBroadcastReceiver(Context context, BaseQuickAdapter adapter) {
        this.mAdapter = adapter;
        this.mContext = context;
    }

    public static IntentFilter getIntentFilter() {
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(ConstantValue.ACTION_NO_PIC_SETTING_CHANGED);
        return mIntentFilter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConstantValue.ACTION_NO_PIC_SETTING_CHANGED)) {
            mAdapter.notifyDataSetChanged();
            if (mAdapter instanceof KnowledgeListAdapter) {
                mAdapter.removeHeaderView(SliderHelper.getInstance(BaseApplication.getContext()).getHeaderView());
                mAdapter.addHeaderView(SliderHelper.getInstance(mContext).createHeaderView((KnowledgeListAdapter) mAdapter));
            }
        }
    }
}
