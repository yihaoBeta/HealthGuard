package com.yihaobeta.healthguard.base;

import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;

import java.util.List;

/**
 * description: 本地操作Presenter接口
 * author: yihaoBeta
 * date: 2017/4/13 0013 12:59
 * update: 2017/4/13 0013
 * version: v1.0
 */

public interface ILocalPresenter {
    void getDataFromDateBase(int type);

    void removeFavorites(List<UniversalDetailEntity> list, ILocalCallBack.ViewCallBack callBack);

    void clearHistories(ILocalCallBack.ViewCallBack callBack);
}
