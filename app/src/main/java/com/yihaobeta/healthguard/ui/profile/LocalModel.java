package com.yihaobeta.healthguard.ui.profile;

import com.yihaobeta.healthguard.base.BaseLocalModel;
import com.yihaobeta.healthguard.base.ILocalCallBack;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.common.RxJavaUtils;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by yihaobeta on 2017/3/28.
 */

public class LocalModel extends BaseLocalModel {
    ILocalCallBack.PresenterCallBack callBack;


    public LocalModel(ILocalCallBack.PresenterCallBack callBack) {
        super(callBack);
        this.callBack = callBack;
    }


    @Override
    public void clearHistories(List<UniversalDetailEntity> historyList, final ILocalCallBack.ViewCallBack callBack) {
        Observable.from(historyList)
                .doOnNext(new Action1<UniversalDetailEntity>() {
                    @Override
                    public void call(UniversalDetailEntity universalDetailEntity) {
                        universalDetailEntity.setHistory(false);
                        updateData2Database(universalDetailEntity);
                    }
                })
                .filter(new Func1<UniversalDetailEntity, Boolean>() {
                    @Override
                    public Boolean call(UniversalDetailEntity universalDetailEntity) {
                        return universalDetailEntity.getFavorite();
                    }
                })
                .compose(RxJavaUtils.<UniversalDetailEntity>applySchedulers())
                .subscribe(new Subscriber<UniversalDetailEntity>() {
                    @Override
                    public void onCompleted() {
                        callBack.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(new ResponseState(ResponseState.State.DB_STATE_DELETE_FAIL));
                    }

                    @Override
                    public void onNext(UniversalDetailEntity universalDetailEntity) {
                        deleteDataFromDatabase(universalDetailEntity);
                    }
                });

    }

    @Override
    public void removeFavorites(List<UniversalDetailEntity> list, final ILocalCallBack.ViewCallBack callBack) {
        Observable.from(list)
                .map(new Func1<UniversalDetailEntity, UniversalDetailEntity>() {
                    @Override
                    public UniversalDetailEntity call(UniversalDetailEntity universalDetailEntity) {
                        universalDetailEntity.setFavorite(false);
                        return universalDetailEntity;
                    }
                })
                .filter(new Func1<UniversalDetailEntity, Boolean>() {
                    @Override
                    public Boolean call(UniversalDetailEntity universalDetailEntity) {
                        if (!universalDetailEntity.getFavorite() && !universalDetailEntity.getHistory()) {
                            deleteDataFromDatabase(universalDetailEntity);
                            return true;
                        } else {
                            updateData2Database(universalDetailEntity);
                            return false;
                        }
                    }
                })
                .compose(RxJavaUtils.<UniversalDetailEntity>applySchedulers())
                .subscribe(new Subscriber<UniversalDetailEntity>() {
                    @Override
                    public void onCompleted() {
                        callBack.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(new ResponseState(ResponseState.State.DB_STATE_DELETE_FAIL));
                    }

                    @Override
                    public void onNext(UniversalDetailEntity universalDetailEntity) {
                    }
                });
    }
}
