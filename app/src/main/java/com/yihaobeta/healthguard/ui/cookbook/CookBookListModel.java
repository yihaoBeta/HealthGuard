package com.yihaobeta.healthguard.ui.cookbook;

import com.yihaobeta.healthguard.base.IBaseCallBack;
import com.yihaobeta.healthguard.base.IModel;
import com.yihaobeta.healthguard.beans.CookBookList;
import com.yihaobeta.healthguard.common.RetrofitUtils;
import com.yihaobeta.healthguard.common.RxJavaUtils;
import com.yihaobeta.healthguard.utils.NetWorkUtils;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by yihaobeta on 2017/3/14.
 */

public class CookBookListModel implements IModel<Integer, Integer, CookBookList> {

    @Override
    public Subscription getDataFromServer(Integer id, Integer page, final IBaseCallBack<CookBookList> callBack) {

        return RetrofitUtils.getApiService().getCookListById(id.intValue(), page.intValue(), 20)
                .compose(RxJavaUtils.<CookBookList>applySchedulers())
                .subscribe(new Subscriber<CookBookList>() {
                    @Override
                    public void onNext(CookBookList cookBookList) {
                        callBack.onSuccess(cookBookList);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFail(NetWorkUtils.getExceptionState(e));
                    }
                });
    }
}
