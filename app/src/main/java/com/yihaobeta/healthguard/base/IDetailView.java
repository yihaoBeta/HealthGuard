package com.yihaobeta.healthguard.base;


/**
 * description: 详情view接口
 * author: yihaoBeta
 * date: 2017/4/13 0013 12:58
 * update: 2017/4/13 0013
 * version: v1.0
 */
public interface IDetailView<T> extends IView<T> {
    void onFavoriteStateChange(boolean isFav);
}
