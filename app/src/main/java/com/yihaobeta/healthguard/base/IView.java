package com.yihaobeta.healthguard.base;

import com.yihaobeta.healthguard.common.ResponseState;

/**
 * description: view接口
 * author: yihaoBeta
 * date: 2017/4/13 0013 13:01
 * update: 2017/4/13 0013
 * version: v1.0
 */

public interface IView<T> {

    void showToast(String info);

    void showDialog();

    void onDataLoaded(T data);

    void onDataLoadFail(ResponseState state);
}
