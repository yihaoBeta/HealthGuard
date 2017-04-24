package com.yihaobeta.healthguard.ui.cookbook.cookbookdetail;

import com.yihaobeta.healthguard.base.AbsDetailModel;
import com.yihaobeta.healthguard.base.ILocalCallBack;
import com.yihaobeta.healthguard.beans.CookBookDetail;
import com.yihaobeta.healthguard.common.RetrofitUtils;

import rx.Observable;

/**
 * Created by yihaobeta on 2017/3/27.
 */

public class CookBookDetailModel extends AbsDetailModel<CookBookDetail> {


    public CookBookDetailModel(ILocalCallBack.PresenterCallBack callback) {
        super(callback);
    }

    public void getData(int type, int id) {
        super.getData(type, id);
    }

    @Override
    protected Observable<CookBookDetail> newRetrofitObservable(int id) {
        return RetrofitUtils.getApiService().getCookBookDetail(id);
    }
}
