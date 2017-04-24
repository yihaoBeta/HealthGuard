package com.yihaobeta.healthguard.ui.profile.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.adapter.FavoriteListAdapter;
import com.yihaobeta.healthguard.base.BaseFragment;
import com.yihaobeta.healthguard.base.ILocalCallBack;
import com.yihaobeta.healthguard.base.IView;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.dagger.component.DaggerFavoriteListFragmentComponent;
import com.yihaobeta.healthguard.dagger.modules.FavoriteListFragmentModule;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;
import com.yihaobeta.healthguard.ui.Knowledge.knowledgedetail.KnowledgeDetailActivity;
import com.yihaobeta.healthguard.ui.cookbook.cookbookdetail.CookBookDetailActivity;
import com.yihaobeta.healthguard.ui.medicine.medicinedetail.MedicineDetailActivity;
import com.yihaobeta.healthguard.ui.profile.LocalPresenter;
import com.yihaobeta.healthguard.view.EmptyView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yihaobeta on 2017/3/24.
 */

public class FavoriteListFragment extends BaseFragment implements IView<List<UniversalDetailEntity>> {

    @BindView(R.id.rv_cookbook_list)
    RecyclerView mRvList;
    @Inject
    FavoriteListAdapter mAdapter;
    @Inject
    LocalPresenter mPresenter;
    @Inject
    EmptyView emptyView;
    private int type;
    private boolean isActionMode = false;
    ActionMode.Callback mCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.action_mode_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            isActionMode = true;
            mAdapter.enableMultiSelectMode(true);
            return false;
        }

        @Override
        public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_all:
                    mAdapter.selectAll();
                    break;
                case R.id.menu_none:
                    mAdapter.selectNone();
                    break;
                case R.id.menu_del:
                    mPresenter.removeFavorites(mAdapter.getSelectedList(), new ILocalCallBack.ViewCallBack() {
                        @Override
                        public void onComplete() {
                            mAdapter.confirmSelectedListRemove();
                            mode.finish();
                        }

                        @Override
                        public void onError(ResponseState state) {
                            mode.finish();
                        }
                    });
                    break;

            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            isActionMode = false;
            mAdapter.enableMultiSelectMode(false);
        }
    };

    public static FavoriteListFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        FavoriteListFragment fragment = new FavoriteListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setListener() {
        mRvList.addOnItemTouchListener(new OnItemLongClickListener() {
            @Override
            public void onSimpleItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                startActionMode();
                mAdapter.setSelectItem(position);
            }
        });

        mRvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mAdapter.isMultiSelectMode()) {
                    mAdapter.setSelectItem(position);
                } else {
                    jump2Detail(type, position, ((UniversalDetailEntity) adapter.getData().get(position)).getId());
                }
            }
        });
    }

    private void jump2Detail(int type, int position, int requestId) {
        switch (type) {
            case ConstantValue.TYPE_COOKBOOK:
                CookBookDetailActivity.startForResult(this, position, requestId);
                break;
            case ConstantValue.TYPE_KNOWLEDGE:
                KnowledgeDetailActivity.startForResult(this, position, requestId);
                break;
            case ConstantValue.TYPE_MEDICINE:
                MedicineDetailActivity.startForResult(this, position, requestId);
                break;
        }
    }

    @Override
    protected void initViews() {
        type = getArguments().getInt("type", ConstantValue.TYPE_KNOWLEDGE);
        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvList.setItemAnimator(new DefaultItemAnimator());
        mRvList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRvList.setAdapter(mAdapter);
        emptyView.setEmptyStatus(EmptyView.STATUS_LOADING);
        mAdapter.setEmptyView(emptyView);
        setHasOptionsMenu(true);
    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_fragment_cookbook_list;
    }

    @Override
    protected void handleLogic() {

        mPresenter.getDataFromDateBase(type);
    }

    @Override
    protected void initDagger() {
        DaggerFavoriteListFragmentComponent.builder()
                .appComponent(getAppComponent())
                .favoriteListFragmentModule(new FavoriteListFragmentModule(getContext(), this))
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
    public void onDataLoaded(List<UniversalDetailEntity> data) {
        emptyView.setEmptyStatus(EmptyView.STATUS_NO_DATA);
        emptyView.setEmptyMessage("暂无收藏");
        mAdapter.addData(data);
    }

    @Override
    public void onDataLoadFail(ResponseState state) {
        if (state.getState().equals(ResponseState.State.NT_STATE_NO_MORE_DATA)
                && mAdapter.getData().size() <= 0) {
            emptyView.setEmptyStatus(EmptyView.STATUS_NO_DATA);
            emptyView.setEmptyMessage("暂无收藏");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_edit:
                startActionMode();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startActionMode() {
        if (!isActionMode) {
            ((AppCompatActivity) getActivity()).startSupportActionMode(mCallback);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.d("onActResult");
        if (requestCode == ConstantValue.REQUEST_CODE_FAVORITE && resultCode == RESULT_OK) {
            boolean isDel = data.getBooleanExtra(ConstantValue.INTENT_ISDELETE, false);
            int pos = data.getIntExtra(ConstantValue.INTENT_POSITION, 0);
            Logger.d("isDel = " + isDel + ",pos = " + pos);
            if (isDel) {
                mAdapter.remove(pos);
            }
        }
    }
}
