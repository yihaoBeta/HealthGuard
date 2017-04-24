package com.yihaobeta.healthguard.base;

import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;

import rx.Subscription;

/**
 * description: 详情presenter抽象基类
 * author: yihaoBeta
 * date: 2017/3/13 0013 12:52
 * update: 2017/3/13 0013
 * version: v1.0
 */

public abstract class BaseDetailPresenter<T, M> implements IDetailPresenter<T, M> {
    public IDetailView mView;
    private boolean isFav = false;
    private UniversalDetailEntity mData = null;
    private Subscription mSubscription;

    public BaseDetailPresenter(IDetailView view) {
        this.mView = view;
    }

    public void setSubscription(Subscription subscription) {
        mSubscription = subscription;
    }

    /**
     * 缓存当前UniversalDetailEntity数据
     *
     * @param data
     */
    protected void setData(UniversalDetailEntity data) {
        mData = data;
        Logger.d(data.getFavorite());
        setFavoriteState(mData.getFavorite());
    }

    /**
     * 获取是否已收藏
     *
     * @return true:已收藏;false:未收藏
     */
    protected boolean getFavoriteState() {
        return this.isFav;
    }

    /**
     * 设置收藏状态
     *
     * @param fav
     */
    protected void setFavoriteState(boolean fav) {
        this.isFav = fav;
        mView.onFavoriteStateChange(fav);
    }

    @Override
    public void changeFavorite() {
        mData.setFavorite(!isFav);
        updateData2DB(mData);
    }

    public void unsubscribe() {

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            Logger.d("unsubscribe");
            mSubscription.unsubscribe();
        }
    }


    /**
     * 数据库更新
     *
     * @param data
     */
    protected abstract void updateData2DB(UniversalDetailEntity data);
}
