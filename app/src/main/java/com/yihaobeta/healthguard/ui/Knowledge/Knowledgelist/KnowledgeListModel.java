package com.yihaobeta.healthguard.ui.Knowledge.Knowledgelist;

import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.base.IBaseCallBack;
import com.yihaobeta.healthguard.base.IModel;
import com.yihaobeta.healthguard.beans.KnowledgeList;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.common.RetrofitUtils;
import com.yihaobeta.healthguard.common.RxJavaUtils;
import com.yihaobeta.healthguard.utils.NetWorkUtils;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by yihaobeta on 2017/3/10.
 */

public class KnowledgeListModel implements IModel<Integer, Integer, KnowledgeList> {

    @Override
    public Subscription getDataFromServer(Integer id, Integer page, final IBaseCallBack<KnowledgeList> callBack) {
        if (id <= 0) {
            id = 10;
        }
        return RetrofitUtils.getApiService().getKnowledgeList(id.intValue(), page.intValue(), 20)
                .compose(RxJavaUtils.<KnowledgeList>applySchedulers())
                .subscribe(new Subscriber<KnowledgeList>() {

                    @Override
                    public void onNext(KnowledgeList knowledgeList) {
                        if (knowledgeList != null) {
                            callBack.onSuccess(knowledgeList);
                        } else
                            callBack.onFail(new ResponseState(ResponseState.State.NT_STATE_NO_MORE_DATA));
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("ERROR--->" + NetWorkUtils.getExceptionState(e).getStateDescription());
                        callBack.onFail(NetWorkUtils.getExceptionState(e));
                    }


                });
    }
}
