package com.yihaobeta.healthguard.base;

/**
 * description: 详情presenter借口继承自Presenter接口
 * author: yihaoBeta
 * date: 2017/4/13 0013 12:57
 * update: 2017/4/13 0013
 * version: v1.0
 */
public interface IDetailPresenter<T, M> extends IPresenter<T, M> {
    /**
     * 改变收藏的状态
     */
    void changeFavorite();
}
