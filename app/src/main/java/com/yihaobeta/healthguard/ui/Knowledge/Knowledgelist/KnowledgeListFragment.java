package com.yihaobeta.healthguard.ui.Knowledge.Knowledgelist;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.daimajia.slider.library.SliderLayout;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.adapter.KnowledgeListAdapter;
import com.yihaobeta.healthguard.base.BaseFragment;
import com.yihaobeta.healthguard.base.IView;
import com.yihaobeta.healthguard.beans.KnowledgeList;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.common.SettingChangeBroadcastReceiver;
import com.yihaobeta.healthguard.dagger.component.DaggerKnowledgeListComponent;
import com.yihaobeta.healthguard.dagger.modules.KnowledgeListModule;
import com.yihaobeta.healthguard.ui.Knowledge.knowledgedetail.KnowledgeDetailActivity;
import com.yihaobeta.healthguard.utils.SliderHelper;
import com.yihaobeta.healthguard.utils.ToastUtils;
import com.yihaobeta.healthguard.view.EmptyView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by yihaobeta on 2017/3/10.
 */

public class KnowledgeListFragment extends BaseFragment implements IView<KnowledgeList> {

    @Inject
    public KnowledgeListAdapter mAdapter;
    @Inject
    public KnowledgeListPresenter mPresenter;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_information_list)
    RecyclerView mRecyclerView;
    @Inject
    EmptyView mEmptyView;
    SliderLayout mAdSlider;
    SettingChangeBroadcastReceiver mReceiver;
    private int mId = 1;
    private int mCurPage = 1;
    private int mTotal = 0;

    public static KnowledgeListFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(ConstantValue.KNOWLEDGE_ID, id);
        KnowledgeListFragment fragment = new KnowledgeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData(mId, 1);
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (mAdapter.getData().size() >= mTotal) {
                    mAdapter.loadMoreEnd();
                    return;
                }
                mPresenter.loadData(mId, mCurPage);

            }
        });

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                KnowledgeList.KnowledgeSummary info = (KnowledgeList.KnowledgeSummary) adapter.getData().get(position);
                KnowledgeDetailActivity.start(getContext(), info.getId(), info.getTitle());
            }
        });

    }

    @Override
    protected void initViews() {
        if (getArguments() != null) {
            mId = getArguments().getInt(ConstantValue.KNOWLEDGE_ID);
        }

        mEmptyView.setEmptyStatus(EmptyView.STATUS_LOADING);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setEmptyView(mEmptyView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//设置为listview的布局
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置动画
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_fragment_knowledge_list;
    }

    @Override
    protected void handleLogic() {
        mPresenter.loadData(mId, mCurPage);
    }


    @Override
    protected void initDagger() {
        DaggerKnowledgeListComponent.builder()
                .appComponent(getAppComponent())
                .knowledgeListModule(new KnowledgeListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showToast(String info) {
        ToastUtils.showToast(info);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void onDataLoaded(KnowledgeList data) {
        if (mAdapter.isLoading())
            mAdapter.loadMoreComplete();
        if (mRefreshLayout.isRefreshing())
            mRefreshLayout.setRefreshing(false);
        mCurPage++;
        mTotal = data.getTotal();
        mAdapter.addData(data.getTngou());
        mReceiver = new SettingChangeBroadcastReceiver(getContext(), mAdapter);
        getActivity().registerReceiver(mReceiver, SettingChangeBroadcastReceiver.getIntentFilter());
        if (mId == 0 && mAdapter.getHeaderLayout() == null) {
            mAdapter.addHeaderView(SliderHelper.getInstance(getContext()).createHeaderView(mAdapter));
        }
    }

    @Override
    public void onDataLoadFail(ResponseState state) {
        if (mAdapter.getData().size() <= 0) {
            mEmptyView.setEmptyStatus(EmptyView.STATUS_NO_DATA);
            mEmptyView.setRetryListener(new EmptyView.OnRetryListener() {
                @Override
                public void onRetry() {
                    mPresenter.loadData(mId, mCurPage);
                    mEmptyView.setEmptyStatus(EmptyView.STATUS_LOADING);
                }
            });
        } else {
            if (mAdapter.isLoading())
                mAdapter.loadMoreFail();
            if (mRefreshLayout.isRefreshing())
                mRefreshLayout.setRefreshing(false);
        }
        if (state.getState().equals(ResponseState.State.NT_STATE_CONNECTION_TIME_OUT))
            showToast(state.getStateDescription());
        else
            showToast(state.getStateDescription());
    }
/*

    private void initHeader(List<KnowledgeList.KnowledgeSummary> data) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_header_knowledge_list, null);
        mAdSlider = (SliderLayout) view.findViewById(R.id.slider_ads);
        SliderHelper.initAdSlider(getContext(), mAdSlider, data);
        mAdapter.addHeaderView(view);
    }
*/

    @Override
    public void onResume() {
        super.onResume();
        if (mReceiver != null)
            getActivity().registerReceiver(mReceiver, SettingChangeBroadcastReceiver.getIntentFilter());
        if (mAdSlider != null) {
            mAdSlider.startAutoCycle();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mReceiver != null)
            getActivity().unregisterReceiver(mReceiver);
        if (mAdSlider != null) {
            mAdSlider.stopAutoCycle();
        }
    }
}
