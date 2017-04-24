package com.yihaobeta.healthguard.ui.Knowledge.knowledgedetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.base.BaseApplication;
import com.yihaobeta.healthguard.base.BaseDetailView;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.dagger.component.DaggerKnowledgeDetailComponent;
import com.yihaobeta.healthguard.dagger.modules.KnowledgeDetailModule;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;
import com.yihaobeta.healthguard.utils.GlideUtils;
import com.yihaobeta.healthguard.utils.RichTextUtils;
import com.yihaobeta.healthguard.utils.ToastUtils;
import com.yihaobeta.healthguard.view.EmptyView;

import butterknife.BindView;

/**
 * Created by yihaobeta on 2017/3/11.
 */

public class KnowledgeDetailActivity extends BaseDetailView<UniversalDetailEntity, KnowledgeDetailPresenter> {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.backdrop)
    ImageView imageView;

    @BindView(R.id.content)
    TextView content;

    @BindView(R.id.keyword)
    TextView keyword;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.empty_layout)
    EmptyView empty_layout;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    private int mId = 0;
    private String title = "";

    public static void start(Context context, int id, String title) {
        Intent intent = new Intent(context, KnowledgeDetailActivity.class);
        intent.putExtra(ConstantValue.INTENT_ID, id);
        intent.putExtra(ConstantValue.INTENT_TITLE, title);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    public static void startForResult(Fragment fragment, int position, int id) {
        Intent intent = new Intent(fragment.getContext(), KnowledgeDetailActivity.class);
        intent.putExtra(ConstantValue.INTENT_ID, id);
        intent.putExtra(ConstantValue.INTENT_POSITION, position);
        intent.putExtra(ConstantValue.INTENT_IS_FROM_FAVORITE, true);
        fragment.startActivityForResult(intent, ConstantValue.REQUEST_CODE_FAVORITE);
        fragment.getActivity().overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
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
        showFavoriteButton();
        Logger.d(data.getTitle());
        empty_layout.hide();
        handleToolBar(mToolBar, true, data.getTitle(), null);
        GlideUtils.showPicture(ConstantValue.BASE_URL_IMG + data.getImgUrl()
                , imageView);
        keyword.setText(getString(R.string.keywords) + data.getKeywords());
        description.setText(getString(R.string.tips) + data.getDescription());
        RichTextUtils.setText(data.getMessage(), content);
    }


    @Override
    public void onDataLoadFail(ResponseState state) {
        empty_layout.setEmptyStatus(EmptyView.STATUS_NO_NET);
        empty_layout.setRetryListener(new EmptyView.OnRetryListener() {
            @Override
            public void onRetry() {
                empty_layout.setEmptyStatus(EmptyView.STATUS_LOADING);
                mPresenter.loadData(mId, 0);
            }
        });
    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_activity_knowledgedetail;
    }

    @Override
    protected void initDagger() {
        DaggerKnowledgeDetailComponent.builder()
                .appComponent(BaseApplication.getAppComponent())
                .knowledgeDetailModule(new KnowledgeDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        mId = getIntent().getIntExtra(ConstantValue.INTENT_ID, 0);
        if (!isFromFavorite) {
            title = getIntent().getStringExtra(ConstantValue.INTENT_TITLE);
            handleToolBar(mToolBar, true, title, null);
        }
        empty_layout.setEmptyStatus(EmptyView.STATUS_LOADING);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void handleLogic() {
        mPresenter.loadData(mId, 0);

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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected KnowledgeDetailPresenter initPresenter() {
        return new KnowledgeDetailPresenter(this);
    }
}
