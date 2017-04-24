package com.yihaobeta.healthguard.ui.medicine.medicinedetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Created by yihaobeta on 2017/3/12.
 */

public class MedicineDetailActivity extends BaseDetailView<UniversalDetailEntity, MedicineDetailPresenter> {
    public static final int SEARCH_BY_ID = 0x12;
    public static final int SEARCH_BY_CODE = 0x13;
    public static final int SEARCH_BY_NAME = 0x14;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.backdrop)
    ImageView mImageView;
    @BindView(R.id.tv_medicine_type)
    TextView tv_price;
    @BindView(R.id.tv_medicine_price)
    TextView tv_type;
    @BindView(R.id.tv_medicine_message)
    TextView tv_message;
    @BindView(R.id.empty_layout)
    EmptyView empty_layout;
    private String mParamForSearch;
    private int mCurType = SEARCH_BY_ID;

    public static void start(Context context, int type, String data) {
        Intent intent = new Intent(context, MedicineDetailActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("type", type);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    public static void startForResult(Fragment fragment, int position, int id) {
        Intent intent = new Intent(fragment.getContext(), MedicineDetailActivity.class);
        intent.putExtra(ConstantValue.INTENT_ID, id);
        intent.putExtra(ConstantValue.INTENT_POSITION, position);
        intent.putExtra(ConstantValue.INTENT_IS_FROM_FAVORITE, true);
        fragment.startActivityForResult(intent, ConstantValue.REQUEST_CODE_FAVORITE);
        fragment.getActivity().overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_activity_medicine_detail;
    }

    @Override
    protected void initDagger() {

    }

    @Override
    protected void init() {
        empty_layout.setEmptyStatus(EmptyView.STATUS_LOADING);
        mCurType = getIntent().getIntExtra("type", SEARCH_BY_ID);
        mParamForSearch = getIntent().getStringExtra("data");
        if (isFromFavorite) {
            mCurType = SEARCH_BY_ID;
            mParamForSearch = String.valueOf(getIntent().getIntExtra(ConstantValue.INTENT_ID, 0));
        }
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void handleLogic() {
        mPresenter.loadData(mParamForSearch, mCurType);
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
        empty_layout.hide();
        showFavoriteButton();
        GlideUtils.showPicture(ConstantValue.BASE_URL_IMG + data.getImgUrl()
                , mImageView);
        tv_type.setText(getString(R.string.medicine_type) + data.getMedicineType());
        tv_price.setText(getString(R.string.price) + data.getPrice());
        RichTextUtils.setText(data.getMessage(), tv_message);
        handleToolBar(mToolbar, true, data.getTitle(), null);
    }

    @Override
    public void onDataLoadFail(ResponseState state) {
        if (state.getState().equals(ResponseState.State.NT_STATE_NO_SUCH_DATA)) {
            ToastUtils.showToast(state.getStateDescription());
            finish();
        }

        empty_layout.setEmptyStatus(EmptyView.STATUS_NO_NET);
        empty_layout.setRetryListener(new EmptyView.OnRetryListener() {
            @Override
            public void onRetry() {
                empty_layout.setEmptyStatus(EmptyView.STATUS_LOADING);
                mPresenter.loadData(mParamForSearch, mCurType);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
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
    protected MedicineDetailPresenter initPresenter() {
        return new MedicineDetailPresenter(this);
    }
}
