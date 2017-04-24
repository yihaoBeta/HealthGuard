package com.yihaobeta.healthguard.base;

import com.yihaobeta.healthguard.common.ResponseState;

/**
 * Created by yihaobeta on 2017/3/9.
 * Callback基类
 */

public interface IBaseCallBack<T> {
    void onSuccess(T data);

    void onFail(ResponseState state);
}
