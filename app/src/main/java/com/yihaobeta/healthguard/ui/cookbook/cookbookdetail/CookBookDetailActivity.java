package com.yihaobeta.healthguard.ui.cookbook.cookbookdetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.base.BaseDetailView;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;
import com.yihaobeta.healthguard.utils.GlideUtils;
import com.yihaobeta.healthguard.utils.RichTextUtils;
import com.yihaobeta.healthguard.utils.ToastUtils;
import com.yihaobeta.healthguard.view.EmptyView;

import butterknife.BindView;

/**
 * Created by yihaobeta on 2017/3/15.
 */

public class CookBookDetailActivity extends BaseDetailView<UniversalDetailEntity, CookBookDetailPresenter> {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tv_content)
    TextView mTv_content;

    @BindView(R.id.empty_layout)
    EmptyView mEmptyView;

    @BindView(R.id.backdrop)
    ImageView mImageView;

    private int id;

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, CookBookDetailActivity.class);
        intent.putExtra(ConstantValue.INTENT_ID, id);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    public static void startForResult(Fragment fragment, int position, int id) {
        Intent intent = new Intent(fragment.getContext(), CookBookDetailActivity.class);
        intent.putExtra(ConstantValue.INTENT_ID, id);
        intent.putExtra(ConstantValue.INTENT_POSITION, position);
        intent.putExtra(ConstantValue.INTENT_IS_FROM_FAVORITE, true);
        fragment.startActivityForResult(intent, ConstantValue.REQUEST_CODE_FAVORITE);
        fragment.getActivity().overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }


    @Override
    protected int attachLayout() {

        return R.layout.layout_activity_cookbook_detail;
    }

    @Override
    protected void initDagger() {
    }

    @Override
    protected void init() {
        id = getIntent().getIntExtra(ConstantValue.INTENT_ID, 0);
        mEmptyView.setEmptyStatus(EmptyView.STATUS_LOADING);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void handleLogic() {
        mPresenter.loadData(id, 0);
    }

    @Override
    public void showToast(String info) {
        ToastUtils.showToast(info);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void onDataLoaded(UniversalDetailEntity data) {
        mEmptyView.hide();
        showFavoriteButton();
        handleToolBar(mToolbar, true, data.getTitle(), null);
        RichTextUtils.setText(data.getMessage(), mTv_content);
        GlideUtils.showPicture(ConstantValue.BASE_URL_IMG + data.getImgUrl()
                , mImageView);
    }


    @Override
    public void onDataLoadFail(ResponseState state) {
        if (state.getState().equals(ResponseState.State.NT_STATE_NO_MORE_DATA)) {
            Logger.d("NT_STATE_NO_MORE_DATA");
            mEmptyView.setEmptyStatus(EmptyView.STATUS_NO_DATA);
        } else {
            mEmptyView.setEmptyStatus(EmptyView.STATUS_NO_NET);
            ToastUtils.showToast(state.getStateDescription());
        }
        mEmptyView.setRetryListener(new EmptyView.OnRetryListener() {
            @Override
            public void onRetry() {
                mEmptyView.setEmptyStatus(EmptyView.STATUS_LOADING);
                mPresenter.loadData(id, 0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    protected CookBookDetailPresenter initPresenter() {
        return new CookBookDetailPresenter(this);
    }
}
