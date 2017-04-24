package com.yihaobeta.healthguard.ui.medicine;

import com.yihaobeta.healthguard.base.IBaseCallBack;
import com.yihaobeta.healthguard.base.IModel;
import com.yihaobeta.healthguard.base.IPresenter;
import com.yihaobeta.healthguard.base.IView;
import com.yihaobeta.healthguard.beans.MedicineList;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.utils.ToastUtils;

/**
 * Created by yihaobeta on 2017/3/12.
 */

public class MedicineHomePresenter implements IPresenter<Integer, Integer> {

    IView mView;
    IModel mModel;

    public MedicineHomePresenter(IView view) {
        mView = view;
        mModel = new MedicineHomeModel();
    }

    @Override
    public void loadData(Integer id, Integer page) {
        mModel.getDataFromServer(id, page, new IBaseCallBack<MedicineList>() {
            @Override
            public void onSuccess(MedicineList data) {
                mView.onDataLoaded(data);
            }

            @Override
            public void onFail(ResponseState state) {
                mView.onDataLoadFail(state);
                ToastUtils.showToast(state.getStateDescription());
            }
        });
    }

    @Override
    public void refreshData(Integer integer, Integer integer2) {

    }
}
