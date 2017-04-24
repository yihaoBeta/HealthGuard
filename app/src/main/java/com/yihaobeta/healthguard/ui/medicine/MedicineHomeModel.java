package com.yihaobeta.healthguard.ui.medicine;

import com.yihaobeta.healthguard.base.IBaseCallBack;
import com.yihaobeta.healthguard.base.IModel;
import com.yihaobeta.healthguard.beans.MedicineList;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.common.RetrofitUtils;
import com.yihaobeta.healthguard.common.RxJavaUtils;
import com.yihaobeta.healthguard.utils.NetWorkUtils;

import rx.Subscriber;
import rx.Subscription;


/**
 * Created by yihaobeta on 2017/3/12.
 */

public class MedicineHomeModel implements IModel<Integer, Integer, MedicineList> {

    @Override
    public Subscription getDataFromServer(Integer id, Integer page, final IBaseCallBack<MedicineList> callBack) {

        return RetrofitUtils.getApiService().getMedicineList(id.intValue(), page.intValue(), 20)
                .compose(RxJavaUtils.<MedicineList>applySchedulers())
                .subscribe(new Subscriber<MedicineList>() {


                    @Override
                    public void onNext(MedicineList medicineList) {
                        if (medicineList != null) {
                            callBack.onSuccess(medicineList);
                        } else
                            callBack.onFail(new ResponseState(ResponseState.State.NT_STATE_NO_MORE_DATA));
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFail(NetWorkUtils.getExceptionState(e));
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }
}
