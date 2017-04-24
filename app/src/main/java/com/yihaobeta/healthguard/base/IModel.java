package com.yihaobeta.healthguard.base;

import rx.Subscription;

/**
 * description: Model接口
 * author: yihaoBeta
 * date: 2017/4/13 0013 12:59
 * update: 2017/4/13 0013
 * version: v1.0
 */
public interface IModel<T, M, P> {
    Subscription getDataFromServer(T t, M m, IBaseCallBack<P> callBack);
}
