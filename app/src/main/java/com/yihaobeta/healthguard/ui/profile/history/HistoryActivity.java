package com.yihaobeta.healthguard.ui.profile.history;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.adapter.HistoryListAdapter;
import com.yihaobeta.healthguard.base.BaseActivity;
import com.yihaobeta.healthguard.base.BaseApplication;
import com.yihaobeta.healthguard.base.ILocalCallBack;
import com.yihaobeta.healthguard.base.IView;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.dagger.component.DaggerHistoryComponent;
import com.yihaobeta.healthguard.dagger.modules.HistoryModule;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;
import com.yihaobeta.healthguard.ui.Knowledge.knowledgedetail.KnowledgeDetailActivity;
import com.yihaobeta.healthguard.ui.cookbook.cookbookdetail.CookBookDetailActivity;
import com.yihaobeta.healthguard.ui.medicine.medicinedetail.MedicineDetailActivity;
import com.yihaobeta.healthguard.ui.profile.LocalPresenter;
import com.yihaobeta.healthguard.utils.ToastUtils;
import com.yihaobeta.healthguard.view.EmptyView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by yihaobeta on 2017/3/26.
 */

public class HistoryActivity extends BaseActivity implements IView<List<UniversalDetailEntity>> {
    @BindView(R.id.rv_history_list)
    RecyclerView mRecyclerView;
    @Inject
    HistoryListAdapter mAdapter;
    @Inject
    EmptyView mEmptyView;
    @Inject
    LocalPresenter mPresenter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private UniversalDetailEntity curHistory;

    public static void start(Context context) {
        Intent intent = new Intent(context, HistoryActivity.class);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.zoom_out_entry, R.anim.hold);
    }

    @Override
    public void showToast(String info) {
        ToastUtils.showToast(info);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void onDataLoaded(List<UniversalDetailEntity> data) {
        mEmptyView.setEmptyStatus(EmptyView.STATUS_NO_DATA);
        mEmptyView.setEmptyMessage(getString(R.string.has_no_history));
        mAdapter.addData(data);
    }


    @Override
    public void onDataLoadFail(ResponseState state) {
        mEmptyView.setEmptyStatus(EmptyView.STATUS_NO_DATA);
        mEmptyView.setEmptyMessage(getString(R.string.has_no_history));
    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_activity_history;
    }

    @Override
    protected void initDagger() {
        DaggerHistoryComponent.builder()
                .appComponent(BaseApplication.getAppComponent())
                .historyModule(new HistoryModule(this, this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        handleToolBar(mToolbar, true, getString(R.string.read_history), null);
        mEmptyView.setEmptyStatus(EmptyView.STATUS_LOADING);
        mAdapter.setEmptyView(mEmptyView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void setListener() {
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                UniversalDetailEntity data = (UniversalDetailEntity) adapter.getData().get(position);
                curHistory = data;
                jump2Detail(data.getType(), data.getId(), data.getTitle());
            }
        });
    }

    @Override
    protected void handleLogic() {
        mPresenter.getDataFromDateBase(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }

            case R.id.clear: {
                mPresenter.clearHistories(new ILocalCallBack.ViewCallBack() {
                    @Override
                    public void onComplete() {
                        mAdapter.getData().clear();
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ResponseState state) {
                        Logger.d(state.getStateDescription());
                    }
                });
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }


    private void jump2Detail(int type, int requestId, String title) {
        switch (type) {
            case ConstantValue.TYPE_COOKBOOK:
                CookBookDetailActivity.start(this, requestId);
                break;
            case ConstantValue.TYPE_KNOWLEDGE:
                KnowledgeDetailActivity.start(this, requestId, title);
                break;
            case ConstantValue.TYPE_MEDICINE:
                MedicineDetailActivity.start(this, MedicineDetailActivity.SEARCH_BY_ID, String.valueOf(requestId));
                break;
        }
    }

    @Override
    protected void onResume() {
        if (mAdapter != null && mPresenter != null && curHistory != null) {
            mAdapter.getData().clear();
            mPresenter.getDataFromDateBase(0);
        }
        super.onResume();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.zoom_out_exit);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}


