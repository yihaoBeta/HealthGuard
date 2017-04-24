package com.yihaobeta.healthguard.base;

import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;

import rx.Observable;

/**
 * description: 远程访问Model接口
 * author: yihaoBeta
 * date: 2017/4/13 0013 13:00
 * update: 2017/4/13 0013
 * version: v1.0
 */
public interface IRemoteModel {
    Observable<UniversalDetailEntity> getDataFromServer(int id);
}
