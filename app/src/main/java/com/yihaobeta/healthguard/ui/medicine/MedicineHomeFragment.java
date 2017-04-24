package com.yihaobeta.healthguard.ui.medicine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.orhanobut.logger.Logger;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.adapter.MedicineClassifyAdapter;
import com.yihaobeta.healthguard.adapter.MedicineListAdapter;
import com.yihaobeta.healthguard.base.BaseFragment;
import com.yihaobeta.healthguard.base.IView;
import com.yihaobeta.healthguard.beans.MedicineClassify;
import com.yihaobeta.healthguard.beans.MedicineList;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.common.SettingChangeBroadcastReceiver;
import com.yihaobeta.healthguard.dagger.component.DaggerMedicineHomeComponent;
import com.yihaobeta.healthguard.dagger.modules.MedicineHomeModule;
import com.yihaobeta.healthguard.ui.medicine.medicinedetail.MedicineDetailActivity;
import com.yihaobeta.healthguard.utils.ToastUtils;
import com.yihaobeta.healthguard.view.EmptyView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yihaobeta on 2017/3/10.
 */

public class MedicineHomeFragment extends BaseFragment implements IView<MedicineList> {

    private static final int REQUEST_CODE = 0x11;
    @Inject
    public MedicineClassifyAdapter mClassifyAdapter;
    @Inject
    public MedicineListAdapter mMedicineListAdapter;
    @Inject
    public MedicineHomePresenter mPresenter;
    @Inject
    public SettingChangeBroadcastReceiver mReceiver;
    @Inject
    public EmptyView emptyView;
    @BindView(R.id.rv_medicine_classify)
    RecyclerView mRvClassify;
    @BindView(R.id.rv_medicine_list)
    RecyclerView mRvMedicineList;
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.ed_search)
    EditText ed_search;
    private int mCurId = 17;//分类列表第一个的id（家庭常备）
    private int mCurPage = 1;
    private int mTotal = 0;

    @Override
    protected void setListener() {
        mClassifyAdapter.setOnItemClickListener(new MedicineClassifyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, MedicineClassify item) {
                mCurId = item.getId();
                mCurPage = 1;
                mMedicineListAdapter.getData().clear();
                mPresenter.loadData(mCurId, mCurPage);
            }
        });

        mMedicineListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (mMedicineListAdapter.getData().size() >= mTotal) {
                    mMedicineListAdapter.loadMoreEnd();
                } else {
                    mPresenter.loadData(mCurId, mCurPage);
                }
            }
        });

        mRvMedicineList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                MedicineList.MedicineSummary summary = (MedicineList.MedicineSummary) adapter.getData().get(position);
                if (summary != null) {
                    MedicineDetailActivity.start(getContext(), MedicineDetailActivity.SEARCH_BY_ID, String.valueOf(summary.getId()));
                } else {
                    Logger.e("error--->item data = null");
                }
            }
        });
    }

    @Override
    protected void initViews() {
        handleToolBar(mToolbar, false, getString(R.string.tab_medicine), null);
        emptyView.setEmptyStatus(EmptyView.STATUS_LOADING);
        mMedicineListAdapter.setEmptyView(emptyView);
        mMedicineListAdapter.setEnableLoadMore(true);
        mRvClassify.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvClassify.setItemAnimator(new DefaultItemAnimator());
        mRvClassify.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRvClassify.setAdapter(mClassifyAdapter);
        mRvMedicineList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvMedicineList.setItemAnimator(new DefaultItemAnimator());
        mRvMedicineList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRvMedicineList.setAdapter(mMedicineListAdapter);
    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_fragment_medicine_home;
    }

    @Override
    protected void handleLogic() {
        mPresenter.loadData(mCurId, mCurPage);
    }

    @Override
    protected void initDagger() {
        DaggerMedicineHomeComponent.builder()
                .appComponent(getAppComponent())
                .medicineHomeModule(new MedicineHomeModule(getContext(), this))
                .build()
                .inject(this);
    }

    @Override
    public void showToast(String info) {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void onDataLoaded(MedicineList data) {
        if (mMedicineListAdapter.isLoading())
            mMedicineListAdapter.loadMoreComplete();
        mTotal = data.getTotal();
        Logger.d(data.getTngou().size());
        Logger.d(data.getTngou().get(0).getName());
        mMedicineListAdapter.addData(data.getTngou());
        mCurPage++;
    }

    @Override
    public void onDataLoadFail(ResponseState state) {
        if (mMedicineListAdapter.getData().size() <= 0) {
            emptyView.setEmptyStatus(EmptyView.STATUS_NO_DATA);
            emptyView.setRetryListener(new EmptyView.OnRetryListener() {
                @Override
                public void onRetry() {
                    emptyView.setEmptyStatus(EmptyView.STATUS_LOADING);
                    mPresenter.loadData(mCurId, mCurPage);
                }
            });
        }
        if (mMedicineListAdapter.isLoading())
            mMedicineListAdapter.loadMoreFail();
    }

    @OnClick(R.id.ib_scan)
    public void onScanBtnClick() {
        Logger.d("click scan btn");
        Intent intent = new Intent(getContext(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @OnClick(R.id.btn_search)
    public void onSearchBtnClick() {
        String name = ed_search.getText().toString();
        if (name == null || TextUtils.isEmpty(name)) {
            ToastUtils.showToast("请输入正确的药名");
            ed_search.setText("");
        } else {
            MedicineDetailActivity.start(getContext(), MedicineDetailActivity.SEARCH_BY_NAME, name);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ToastUtils.showToast("解析结果:" + result);
                    MedicineDetailActivity.start(getContext(), MedicineDetailActivity.SEARCH_BY_CODE, result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtils.showToast("解析二维码失败");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        getActivity().registerReceiver(mReceiver, SettingChangeBroadcastReceiver.getIntentFilter());
        super.onResume();
    }

    @Override
    public void onStop() {
        getActivity().unregisterReceiver(mReceiver);
        super.onStop();
    }
}
