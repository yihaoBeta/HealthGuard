package com.yihaobeta.healthguard.ui.cookbook;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.adapter.CookBookListAdapter;
import com.yihaobeta.healthguard.base.BaseFragment;
import com.yihaobeta.healthguard.base.IView;
import com.yihaobeta.healthguard.beans.CookBookList;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.common.SettingChangeBroadcastReceiver;
import com.yihaobeta.healthguard.dagger.component.DaggerCookBookListComponent;
import com.yihaobeta.healthguard.dagger.modules.CookBookListModule;
import com.yihaobeta.healthguard.ui.cookbook.cookbookdetail.CookBookDetailActivity;
import com.yihaobeta.healthguard.utils.ToastUtils;
import com.yihaobeta.healthguard.view.EmptyView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by yihaobeta on 2017/3/14.
 */

public class CookBookListFragment extends BaseFragment implements IView<List<CookBookList.CookBookSummary>> {
    @Inject
    public CookBookListPresenter mPresenter;
    @Inject
    public CookBookListAdapter mAdapter;
    @Inject
    public EmptyView mEmptyView;
    @Inject
    public SettingChangeBroadcastReceiver mReceiver;
    @BindView(R.id.rv_cookbook_list)
    RecyclerView mRvCookList;
    private int mId = 0;

    public static CookBookListFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(ConstantValue.COOKBOOK_ID, id);
        CookBookListFragment fragment = new CookBookListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setListener() {
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadData(mId, 0);
            }
        });

        mRvCookList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                CookBookList.CookBookSummary summary = (CookBookList.CookBookSummary) adapter.getData().get(position);
                CookBookDetailActivity.start(getContext(), summary.getId());
            }
        });
    }

    @Override
    protected void initViews() {
        if (getArguments() != null) {
            mId = getArguments().getInt(ConstantValue.COOKBOOK_ID);
        }
        mEmptyView.setEmptyStatus(EmptyView.STATUS_LOADING);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setEmptyView(mEmptyView);
        mRvCookList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvCookList.setItemAnimator(new DefaultItemAnimator());
        mRvCookList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRvCookList.setAdapter(mAdapter);
    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_fragment_cookbook_list;
    }

    @Override
    protected void handleLogic() {
        mPresenter.loadData(mId, 0);
    }

    @Override
    protected void initDagger() {
        DaggerCookBookListComponent.builder()
                .appComponent(getAppComponent())
                .cookBookListModule(new CookBookListModule(getContext(), this))
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
    public void onDataLoaded(List<CookBookList.CookBookSummary> data) {
        if (mAdapter.isLoading())
            mAdapter.loadMoreComplete();

        mAdapter.addData(data);
    }

    @Override
    public void onDataLoadFail(ResponseState state) {
        switch (state.getState()) {
            case NT_STATE_NO_MORE_DATA:
                if (mAdapter.isLoading())
                    mAdapter.loadMoreEnd();
                break;
            case NT_STATE_NO_SUCH_DATA:
            case NT_STATE_SERVER_ERROR:
            case STATE_COMMON_FAIL:
                if (mAdapter.isLoading())
                    mAdapter.loadMoreFail();
                break;
            case NT_STATE_CONNECTION_TIME_OUT:
            case NT_STATE_NETWORK_DISCONNECTED:
                ToastUtils.showToast(state.getStateDescription());
                break;
        }
        if (mAdapter.getData().size() <= 0) {
            mEmptyView.setEmptyStatus(EmptyView.STATUS_NO_DATA);
            mEmptyView.setRetryListener(new EmptyView.OnRetryListener() {
                @Override
                public void onRetry() {
                    mEmptyView.setEmptyStatus(EmptyView.STATUS_LOADING);
                    mPresenter.loadData(mId, 0);
                }
            });
        }
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
