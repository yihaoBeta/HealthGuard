package com.yihaobeta.healthguard.common;

import com.yihaobeta.healthguard.base.BaseDetailBean;
import com.yihaobeta.healthguard.beans.CookBookDetail;
import com.yihaobeta.healthguard.beans.KnowledgeDetail;
import com.yihaobeta.healthguard.beans.MedicineDetail;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yihaobeta on 2017/4/13 0013.
 */

public class RxJavaUtils {

    /**
     * 提供统一的进程调度管理，避免代码重复
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io());
            }
        };
    }


    /**
     * 提供统一的数据类型变换操作
     *
     * @param <T>
     * @return
     */
    public static <T extends BaseDetailBean> Observable.Transformer<T, UniversalDetailEntity> convert2UniversalDetailEntity() {
        return new Observable.Transformer<T, UniversalDetailEntity>() {
            @Override
            public Observable<UniversalDetailEntity> call(Observable<T> tObservable) {
                return tObservable.flatMap(new Func1<T, Observable<UniversalDetailEntity>>() {
                    UniversalDetailEntity mEntity = new UniversalDetailEntity();

                    @Override
                    public Observable<UniversalDetailEntity> call(T t) {
                        if (t == null || !t.isStatus())
                            return Observable.error(new NoSuchDataThrowable("data is null"));
                        if (t instanceof CookBookDetail) {
                            CookBookDetail detail = (CookBookDetail) t;
                            int type = ConstantValue.TYPE_COOKBOOK;
                            mEntity.setKey(detail.getImg() + detail.getId() + type);
                            mEntity.setDescription(detail.getDescription());
                            mEntity.setHistory(true);
                            mEntity.setType(type);
                            mEntity.setFavorite(false);
                            mEntity.setId(detail.getId());
                            mEntity.setImgUrl(detail.getImg());
                            mEntity.setKeywords(detail.getKeywords());
                            mEntity.setMessage(detail.getMessage());
                            mEntity.setTitle(detail.getName());
                        } else if (t instanceof KnowledgeDetail) {
                            KnowledgeDetail detail = (KnowledgeDetail) t;
                            int type = ConstantValue.TYPE_KNOWLEDGE;
                            mEntity.setKey(detail.getImg() + detail.getId() + type);
                            mEntity.setDescription(detail.getDescription());
                            mEntity.setHistory(true);
                            mEntity.setType(type);
                            mEntity.setFavorite(false);
                            mEntity.setId(detail.getId());
                            mEntity.setImgUrl(detail.getImg());
                            mEntity.setKeywords(detail.getKeywords());
                            mEntity.setMessage(detail.getMessage());
                            mEntity.setTitle(detail.getTitle());
                        } else if (t instanceof MedicineDetail) {
                            MedicineDetail detail = (MedicineDetail) t;
                            int type = ConstantValue.TYPE_MEDICINE;
                            mEntity.setKey(detail.getImg() + detail.getId() + type);
                            mEntity.setDescription(detail.getDescription());
                            mEntity.setHistory(true);
                            mEntity.setType(type);
                            mEntity.setFavorite(false);
                            mEntity.setId(detail.getId());
                            mEntity.setImgUrl(detail.getImg());
                            mEntity.setKeywords(detail.getKeywords());
                            mEntity.setMessage(detail.getMessage());
                            mEntity.setTitle(detail.getName());
                            mEntity.setPrice(detail.getPrice());
                            mEntity.setMedicineType(detail.getType());
                        }
                        mEntity.setTimeStamp(System.currentTimeMillis());
                        return Observable.just(mEntity);
                    }
                });
            }
        };
    }
}
