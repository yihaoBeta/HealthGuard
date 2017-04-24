package com.yihaobeta.healthguard.ui.medicine.medicinedetail;

import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.base.AbsDetailModel;
import com.yihaobeta.healthguard.base.ILocalCallBack;
import com.yihaobeta.healthguard.beans.MedicineDetail;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.common.RetrofitUtils;
import com.yihaobeta.healthguard.common.RxJavaUtils;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;
import com.yihaobeta.healthguard.utils.NetWorkUtils;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by yihaobeta on 2017/3/12.
 */

public class MedicineDetailModel extends AbsDetailModel<MedicineDetail> {
    Observable<MedicineDetail> observable = null;
    private Subscription mSubscription;

    public MedicineDetailModel(ILocalCallBack.PresenterCallBack callback) {
        super(callback);
    }

    private void unSubscripte() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    protected Observable<MedicineDetail> newRetrofitObservable(int id) {
        return RetrofitUtils.getApiService().getMedicineDetailByID(id);
    }

    /**
     * 为了兼容药品信息查询，除了ID查询外，还可以通过关键字和二维码查询
     * 此处可优化，基类去做兼容
     *
     * @param param
     * @param type
     */
    public Subscription getDataFromServer(String param, Integer type) {
        Logger.e(param + "," + type);
        switch (type.intValue()) {
            case MedicineDetailActivity.SEARCH_BY_ID:
                observable = RetrofitUtils.getApiService().getMedicineDetailByID(Integer.valueOf(param));
                return super.getData(ConstantValue.TYPE_MEDICINE, Integer.valueOf(param));
            case MedicineDetailActivity.SEARCH_BY_NAME:
                observable = RetrofitUtils.getApiService().getMedicineDetailByName(param);
                return getDataByTitle(ConstantValue.TYPE_MEDICINE, param);
            case MedicineDetailActivity.SEARCH_BY_CODE:
                observable = RetrofitUtils.getApiService().getMedicineDetailByCode(param);
                return getDataFromServer()
                        .filter(new Func1<UniversalDetailEntity, Boolean>() {
                            @Override
                            public Boolean call(UniversalDetailEntity universalDetailEntity) {
                                return universalDetailEntity != null;
                            }
                        })

                        .compose(RxJavaUtils.<UniversalDetailEntity>applySchedulers())
                        .subscribe(new Subscriber<UniversalDetailEntity>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                mCallBack.onError(NetWorkUtils.getExceptionState(e));
                            }

                            @Override
                            public void onNext(UniversalDetailEntity universalDetailEntity) {
                                Logger.d(universalDetailEntity.toString());
                                mCallBack.onDataLoadComplete(universalDetailEntity);
                            }
                        });
            default:
                mCallBack.onError(null);
                return null;
        }

    }


    private Observable<UniversalDetailEntity> getDataFromServer() {
        return observable.compose(RxJavaUtils.<MedicineDetail>convert2UniversalDetailEntity())
                .flatMap(new Func1<UniversalDetailEntity, Observable<UniversalDetailEntity>>() {
                    @Override
                    public Observable<UniversalDetailEntity> call(UniversalDetailEntity universalDetailEntity) {
                        UniversalDetailEntity entity = isLocaled(universalDetailEntity);
                        if (entity != null) {
                            return Observable.just(entity);
                        } else {
                            saveData2Database(universalDetailEntity);
                            return Observable.just(universalDetailEntity);
                        }
                    }
                });
    }

    public Subscription getDataByTitle(final int type, final String title) {

        return super.isLocaled(type, title)
                .flatMap(new Func1<UniversalDetailEntity, Observable<UniversalDetailEntity>>() {
                    @Override
                    public Observable<UniversalDetailEntity> call(UniversalDetailEntity universalDetailEntity) {
                        if (universalDetailEntity == null) {
                            return getDataFromServer();
                        } else {
                            return Observable.just(universalDetailEntity);
                        }
                    }
                })
                .filter(new Func1<UniversalDetailEntity, Boolean>() {
                    @Override
                    public Boolean call(UniversalDetailEntity universalDetailEntity) {
                        return universalDetailEntity != null;
                    }
                })
                .compose(RxJavaUtils.<UniversalDetailEntity>applySchedulers())
                .subscribe(new Subscriber<UniversalDetailEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        mCallBack.onError(NetWorkUtils.getExceptionState(e));
                    }

                    @Override
                    public void onNext(UniversalDetailEntity universalDetailEntity) {
                        mCallBack.onDataLoadComplete(universalDetailEntity);
                    }
                });

    }
}
