package com.yihaobeta.healthguard.base;

import com.yihaobeta.healthguard.common.GreenDaoUtils;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.common.RxJavaUtils;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntityDao;

import java.util.List;

import rx.Subscriber;

/**
 * description: 本地操作Model基类，抽象出部分数据库操作在此完成
 * author: yihaoBeta
 * date: 2017/4/13 0013 12:56
 * update: 2017/4/13 0013
 * version: v1.0
 */
public abstract class BaseLocalModel implements ILocalModel {

    ILocalCallBack.PresenterCallBack mCallBack;

    public BaseLocalModel(ILocalCallBack.PresenterCallBack callBack) {
        this.mCallBack = callBack;
    }

    public abstract void clearHistories(List<UniversalDetailEntity> historyList, ILocalCallBack.ViewCallBack callBack);

    public abstract void removeFavorites(List<UniversalDetailEntity> FavoriteList, ILocalCallBack.ViewCallBack callBack);

    @Override
    public void getDataFromDatabase(int type, int id) {

    }

    @Override
    public void saveData2Database(UniversalDetailEntity data) {

    }

    @Override
    public void deleteDataFromDatabase(UniversalDetailEntity data) {
        GreenDaoUtils.getUniversalDetailEntityDao().delete(data);
    }

    @Override
    public void deleteDataFromDataBase(List<UniversalDetailEntity> list) {

    }

    @Override
    public void deleteAllDataFromDataBase() {
        GreenDaoUtils.getUniversalDetailEntityDao()
                .rx()
                .deleteAll()
                .compose(RxJavaUtils.<Void>applySchedulers())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        mCallBack.onDeleteComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mCallBack.onError(new ResponseState(ResponseState.State.DB_STATE_DELETE_FAIL));
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
    }

    @Override
    public void updateData2Database(UniversalDetailEntity data) {
        GreenDaoUtils.getUniversalDetailEntityDao()
                .update(data);
    }

    @Override
    public void getFavoritesFromDataBase(int type) {
        GreenDaoUtils.getUniversalDetailEntityDao()
                .queryBuilder()
                .where(UniversalDetailEntityDao.Properties.Favorite.eq(true)
                        , UniversalDetailEntityDao.Properties.Type.eq(type))
                .rx()
                .list()
                .compose(RxJavaUtils.<List<UniversalDetailEntity>>applySchedulers())
                .subscribe(new Subscriber<List<UniversalDetailEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mCallBack.onError(new ResponseState(ResponseState.State.DB_STATE_QUERY_FAIL));
                    }

                    @Override
                    public void onNext(List<UniversalDetailEntity> universalDetailEntities) {
                        mCallBack.onQueryComplete(universalDetailEntities);
                    }
                });
    }

    @Override
    public void getHistoriesFromDataBase() {
        GreenDaoUtils.getUniversalDetailEntityDao()
                .queryBuilder()
                .where(UniversalDetailEntityDao.Properties.History.eq(true))
                .orderDesc(UniversalDetailEntityDao.Properties.TimeStamp)
                .rx()
                .list()
                .compose(RxJavaUtils.<List<UniversalDetailEntity>>applySchedulers())
                .subscribe(new Subscriber<List<UniversalDetailEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mCallBack.onError(new ResponseState(ResponseState.State.DB_STATE_QUERY_FAIL));
                    }

                    @Override
                    public void onNext(List<UniversalDetailEntity> universalDetailEntities) {
                        mCallBack.onQueryComplete(universalDetailEntities);
                    }
                });
    }
}
