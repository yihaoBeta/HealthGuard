package com.yihaobeta.healthguard.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.common.ConstantValue;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * description: 详情View抽象基类，抽象出收藏相关功能在此实现
 * author: yihaoBeta
 * date: 2017/3/23 0013 12:54
 * update: 2017/4/13 0013
 * version: v1.0
 */

public abstract class BaseDetailView<T, V extends IDetailPresenter> extends BaseActivity implements IDetailView<T> {

    protected boolean isFirst = true;
    protected V mPresenter;
    //记录是否从收藏界面进入的详情界面，如果是，返回时要回调，以便收藏界面更新状态
    protected boolean isFromFavorite = false;
    @BindView(R.id.fab_favorite)
    FloatingActionButton fab_favorite;
    private boolean isDelete = false;

    protected abstract V initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = initPresenter();
        isFromFavorite = getIntent().getBooleanExtra(ConstantValue.INTENT_IS_FROM_FAVORITE, false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onFavoriteStateChange(boolean isFav) {
        changeFavIcon(isFav);
        isDelete = !isFav;
    }

    @OnClick(R.id.fab_favorite)
    public void onFavorite() {
        isFirst = false;
        mPresenter.changeFavorite();
    }

    private void changeFavIcon(boolean flag) {
        fab_favorite.setImageResource(flag ? R.drawable.favorite : R.drawable.non_favorite);
        if (!isFirst)
            showToast(flag ? getString(R.string.favorite_success) : getString(R.string.cancel_favorite));
    }


    @Override
    public void finish() {
        if (isFromFavorite) {
            Logger.d(isDelete);
            Logger.d(getIntent().getIntExtra(ConstantValue.INTENT_POSITION, 4));
            Intent intent = new Intent();
            intent.putExtra(ConstantValue.INTENT_POSITION, getIntent().getIntExtra(ConstantValue.INTENT_POSITION, 0));
            intent.putExtra(ConstantValue.INTENT_ISDELETE, isDelete);
            setResult(RESULT_OK, intent);
        }
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

    protected void showFavoriteButton() {
        fab_favorite.setVisibility(View.VISIBLE);
    }
}
