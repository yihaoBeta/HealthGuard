package com.yihaobeta.healthguard.ui.cookbook.cookbookdetail;

import com.yihaobeta.healthguard.base.AbsDetailModel;
import com.yihaobeta.healthguard.base.ILocalCallBack;
import com.yihaobeta.healthguard.beans.CookBookDetail;
import com.yihaobeta.healthguard.common.RetrofitUtils;

import rx.Observable;
import rx.Subscription;

/**
 * Created by yihaobeta on 2017/3/27.
 */

public class CookBookDetailModel extends AbsDetailModel<CookBookDetail> {


    public CookBookDetailModel(ILocalCallBack.PresenterCallBack callback) {
        super(callback);
    }

    public Subscription getData(int type, int id) {
        return super.getData(type, id);
    }

    @Override
    protected Observable<CookBookDetail> newRetrofitObservable(int id) {
        return RetrofitUtils.getApiService().getCookBookDetail(id);
    }
}
