package com.yihaobeta.healthguard.ui.profile;

import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.base.ILocalCallBack;
import com.yihaobeta.healthguard.base.ILocalPresenter;
import com.yihaobeta.healthguard.base.IView;
import com.yihaobeta.healthguard.common.ConstantValue;
import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;

import java.util.List;

/**
 * Created by yihaobeta on 2017/3/24.
 */

public class LocalPresenter implements ILocalPresenter, ILocalCallBack.PresenterCallBack {
    IView mView;
    LocalModel mModel;
    private int mFavOrHistory;
    private List<UniversalDetailEntity> mHistoryList;

    public LocalPresenter(IView view, int favoriteOrHistory) {
        this.mFavOrHistory = favoriteOrHistory;
        mView = view;
        mModel = new LocalModel(this);
    }


    @Override
    public void getDataFromDateBase(int type) {
        if (mFavOrHistory == ConstantValue.TYPE_FAVORITE) {
            mModel.getFavoritesFromDataBase(type);
        } else {
            mModel.getHistoriesFromDataBase();
        }
    }

    @Override
    public void removeFavorites(List<UniversalDetailEntity> list, ILocalCallBack.ViewCallBack callBack) {
        mModel.removeFavorites(list, callBack);
    }

    @Override
    public void clearHistories(ILocalCallBack.ViewCallBack callBack) {
        mModel.clearHistories(mHistoryList, callBack);
    }

    @Override
    public void onDataLoadComplete(UniversalDetailEntity data) {

    }

    @Override
    public void onUpdateComplete(UniversalDetailEntity data) {

    }

    @Override
    public void onQueryComplete(List<UniversalDetailEntity> list) {
        if (this.mFavOrHistory == ConstantValue.TYPE_HISTORY) {
            this.mHistoryList = list;
        }
        mView.onDataLoaded(list);
    }

    @Override
    public void onDeleteComplete() {

    }

    @Override
    public void onError(ResponseState state) {
        Logger.d(state.getStateDescription());
    }


}
