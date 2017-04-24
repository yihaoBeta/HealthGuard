package com.yihaobeta.healthguard.base;

/**
 * description: Presenter接口
 * author: yihaoBeta
 * date: 2017/4/13 0013 13:00
 * update: 2017/4/13 0013
 * version: v1.0
 */
public interface IPresenter<T, M> {
    void loadData(T t, M m);

    void refreshData(T t, M m);
}
