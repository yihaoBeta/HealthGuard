package com.yihaobeta.healthguard.ui.medicine.medicinedetail;

import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.base.BaseDetailPresenter;
import com.yihaobeta.healthguard.base.IDetailView;
import com.yihaobeta.healthguard.base.ILocalCallBack;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;

import java.util.List;

/**
 * Created by yihaobeta on 2017/3/12.
 */

public class MedicineDetailPresenter extends BaseDetailPresenter<String, Integer> implements ILocalCallBack.PresenterCallBack {
    private MedicineDetailModel mModel;

    public MedicineDetailPresenter(IDetailView view) {
        super(view);
        this.mModel = new MedicineDetailModel(this);
    }

    @Override
    protected void updateData2DB(UniversalDetailEntity data) {
        mModel.updateData2Database(data);
    }

    @Override
    public void loadData(String param, Integer type) {
        mModel.getDataFromServer(param, type);
    }

    @Override
    public void refreshData(String s, Integer integer) {

    }


    @Override
    public void onDataLoadComplete(UniversalDetailEntity data) {
        mView.onDataLoaded(data);
        super.setData(data);
    }

    @Override
    public void onUpdateComplete(UniversalDetailEntity data) {
        super.setData(data);
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
