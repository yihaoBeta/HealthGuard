package com.yihaobeta.healthguard.ui.Knowledge.knowledgedetail;

import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.base.AbsDetailModel;
import com.yihaobeta.healthguard.base.ILocalCallBack;
import com.yihaobeta.healthguard.beans.KnowledgeDetail;
import com.yihaobeta.healthguard.common.RetrofitUtils;

import rx.Observable;


/**
 * Created by yihaobeta on 2017/3/11.
 */

public class KnowledgeDetailModel extends AbsDetailModel<KnowledgeDetail> {

    public KnowledgeDetailModel(ILocalCallBack.PresenterCallBack callback) {
        super(callback);
    }

    public void getData(int type, int id) {
        Logger.d(type + "," + id);
        super.getData(type, id);
    }

    @Override
    protected Observable<KnowledgeDetail> newRetrofitObservable(int id) {
        return RetrofitUtils.getApiService().getKnowledgeDetail(id);
    }
}
