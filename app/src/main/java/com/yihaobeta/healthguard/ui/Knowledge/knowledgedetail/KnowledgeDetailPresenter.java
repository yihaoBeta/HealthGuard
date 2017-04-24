package com.yihaobeta.healthguard.ui.Knowledge.knowledgedetail;

import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.base.BaseDetailPresenter;
import com.yihaobeta.healthguard.base.IDetailView;
import com.yihaobeta.healthguard.base.ILocalCallBack;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;

import java.util.List;

/**
 * Created by yihaobeta on 2017/3/11.
 */

public class KnowledgeDetailPresenter extends BaseDetailPresenter<Integer, Integer> implements ILocalCallBack.PresenterCallBack {
    private KnowledgeDetailModel mModel;

    public KnowledgeDetailPresenter(IDetailView view) {
        super(view);
        this.mModel = new KnowledgeDetailModel(this);
    }

    @Override
    protected void updateData2DB(UniversalDetailEntity data) {
        mModel.updateData2Database(data);
    }

    @Override
    public void loadData(Integer id, Integer integer) {
        mModel.getData(ConstantValue.TYPE_KNOWLEDGE, id);
    }

    @Override
    public void refreshData(Integer integer, Integer integer2) {

    }


    @Override
    public void onDataLoadComplete(UniversalDetailEntity data) {
        Logger.d(data.getTitle());
        mView.onDataLoaded(data);
        setData(data);
    }

    @Override
    public void onUpdateComplete(UniversalDetailEntity data) {
        setData(data);
    }

    @Override
    public void onQueryComplete(List<UniversalDetailEntity> list) {

    }

    @Override
    public void onDeleteComplete() {

    }

    @Override
    public void onError(ResponseState state) {
        Logger.d(state.getStateDescription());
        mView.onDataLoadFail(state);
    }


}
