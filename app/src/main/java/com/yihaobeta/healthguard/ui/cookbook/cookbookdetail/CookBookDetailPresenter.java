package com.yihaobeta.healthguard.ui.cookbook.cookbookdetail;

import com.yihaobeta.healthguard.base.BaseDetailPresenter;
import com.yihaobeta.healthguard.base.IDetailView;
import com.yihaobeta.healthguard.base.ILocalCallBack;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;

import java.util.List;

/**
 * Created by yihaobeta on 2017/3/17.
 */

public class CookBookDetailPresenter extends BaseDetailPresenter<Integer, Integer> implements ILocalCallBack.PresenterCallBack {

    private CookBookDetailModel mModel;

    public CookBookDetailPresenter(IDetailView view) {
        super(view);
        this.mModel = new CookBookDetailModel(this);
    }

    @Override
    protected void updateData2DB(UniversalDetailEntity data) {
        mModel.updateData2Database(data);
    }

    @Override
    public void loadData(Integer id, Integer integer2) {
        setSubscription(mModel.getData(ConstantValue.TYPE_COOKBOOK, id));
    }

    @Override
    public void refreshData(Integer id, Integer integer2) {

    }

    @Override
    public void onDataLoadComplete(UniversalDetailEntity data) {
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
        mView.onDataLoadFail(new ResponseState(ResponseState.State.NT_STATE_NO_MORE_DATA));
    }
}
