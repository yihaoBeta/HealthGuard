package com.yihaobeta.healthguard.base;

import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.common.GreenDaoUtils;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.common.RxJavaUtils;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntityDao;
import com.yihaobeta.healthguard.utils.NetWorkUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by yihaobeta on 2017/3/27.
 * 详情页面Model的抽象类
 */

public abstract class AbsDetailModel<T extends BaseDetailBean> implements IRemoteModel, ILocalModel {

    protected ILocalCallBack.PresenterCallBack mCallBack;

    public AbsDetailModel(ILocalCallBack.PresenterCallBack callback) {
        this.mCallBack = callback;
    }

    @Override
    public void getDataFromDatabase(int type, int id) {
        GreenDaoUtils.getUniversalDetailEntityDao()
                .queryBuilder()
                .where(UniversalDetailEntityDao.Properties.Id.eq(id),
                        UniversalDetailEntityDao.Properties.Type.eq(type))
                .unique();
    }

    protected Subscription getData(final int type, final int id) {
        return isLocaled(type, id)
                //flatmap变换，判断数据源的获取方式
                .flatMap(new Func1<UniversalDetailEntity, Observable<UniversalDetailEntity>>() {
                    @Override
                    public Observable<UniversalDetailEntity> call(UniversalDetailEntity universalDetailEntity) {
                        if (universalDetailEntity == null) {
                            Logger.d("server");
                            return getDataFromServer(id);
                        } else {
                            Logger.d("local");
                            return Observable.just(universalDetailEntity);
                        }
                    }
                })
                //统一更改访问时间戳,设置浏览纪录标志
                .doOnNext(new Action1<UniversalDetailEntity>() {
                    @Override
                    public void call(UniversalDetailEntity universalDetailEntity) {
                        universalDetailEntity.setTimeStamp(System.currentTimeMillis());
                        universalDetailEntity.setHistory(true);
                        updateData2Database(universalDetailEntity);
                    }
                })
                .compose(RxJavaUtils.<UniversalDetailEntity>applySchedulers())
                .subscribe(new Subscriber<UniversalDetailEntity>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("err");
                        if (null == e || e.getCause() == null) {
                            mCallBack.onError(new ResponseState(ResponseState.State.STATE_COMMON_FAIL));
                        } else {
                            mCallBack.onError(NetWorkUtils.getExceptionState(e));
                        }
                    }

                    @Override
                    public void onNext(UniversalDetailEntity universalDetailEntity) {
                        Logger.d(universalDetailEntity.getTitle());
                        mCallBack.onDataLoadComplete(universalDetailEntity);
                    }
                });

    }

    public UniversalDetailEntity isLocaled(UniversalDetailEntity data) {
        return GreenDaoUtils.getUniversalDetailEntityDao().queryBuilder()
                .where(UniversalDetailEntityDao.Properties.Key.eq(data.getKey()))
                .unique();
    }

    private Observable<UniversalDetailEntity> isLocaled(int type, int id) {
        return GreenDaoUtils.getUniversalDetailEntityDao()
                .queryBuilder()
                .where(UniversalDetailEntityDao.Properties.Type.eq(type), UniversalDetailEntityDao.Properties.Id.eq(id))
                .rx()
                .unique();
    }

    protected Observable<UniversalDetailEntity> isLocaled(int type, String title) {
        return GreenDaoUtils.getUniversalDetailEntityDao()
                .queryBuilder()
                .where(UniversalDetailEntityDao.Properties.Type.eq(type), UniversalDetailEntityDao.Properties.Title.eq(title))
                .rx()
                .unique();
    }

    @Override
    public void saveData2Database(UniversalDetailEntity data) {
        GreenDaoUtils.getUniversalDetailEntityDao().insertOrReplace(data);
    }


    @Override
    public void deleteDataFromDatabase(UniversalDetailEntity data) {

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

                    }

                    @Override
                    public void onError(Throwable e) {
                        mCallBack.onError(new ResponseState(ResponseState.State.DB_STATE_DELETE_FAIL));
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        mCallBack.onDeleteComplete();
                    }
                });
    }

    @Override
    public void updateData2Database(UniversalDetailEntity data) {

        GreenDaoUtils.getUniversalDetailEntityDao()
                .rx()
                .update(data)
                .compose(RxJavaUtils.<UniversalDetailEntity>applySchedulers())
                .subscribe(new Subscriber<UniversalDetailEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        mCallBack.onError(new ResponseState(ResponseState.State.DB_STATE_UPDATE_FAIL));
                    }

                    @Override
                    public void onNext(UniversalDetailEntity universalDetailEntity) {
                        mCallBack.onUpdateComplete(universalDetailEntity);
                    }
                });
    }

    @Override
    public void getFavoritesFromDataBase(int type) {

    }

    @Override
    public void getHistoriesFromDataBase() {

    }

    protected abstract Observable<T> newRetrofitObservable(int id);

    @Override
    public Observable<UniversalDetailEntity> getDataFromServer(int id) {
        return newRetrofitObservable(id)
                .compose(RxJavaUtils.<T>convert2UniversalDetailEntity())
                .doOnNext(new Action1<UniversalDetailEntity>() {
                    @Override
                    public void call(UniversalDetailEntity universalDetailEntity) {
                        saveData2Database(universalDetailEntity);
                    }
                });

    }
}
