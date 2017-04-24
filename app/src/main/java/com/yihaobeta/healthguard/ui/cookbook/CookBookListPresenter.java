package com.yihaobeta.healthguard.ui.cookbook;

import com.yihaobeta.healthguard.base.IBaseCallBack;
import com.yihaobeta.healthguard.base.IModel;
import com.yihaobeta.healthguard.base.IPresenter;
import com.yihaobeta.healthguard.beans.CookBookList;
import com.yihaobeta.healthguard.common.ResponseState;

import rx.Subscription;

/**
 * Created by yihaobeta on 2017/3/14.
 */

public class CookBookListPresenter implements IPresenter<Integer, Integer> {

    private CookBookListFragment mView;
    private IModel mModel;
    private int mCurPage = 1;
    private int mTotal = 0;

    public CookBookListPresenter(CookBookListFragment view) {
        mView = view;
        mModel = new CookBookListModel();
    }

    @Override
    public void loadData(Integer id, Integer page) {

        Subscription subscription = mModel.getDataFromServer(id, mCurPage, new IBaseCallBack<CookBookList>() {
            @Override
            public void onSuccess(CookBookList data) {
                if (data != null && data.getTngou().size() > 0) {
                    mTotal = data.getTotal();
                    mCurPage++;
                    mView.onDataLoaded(data.getTngou());
                } else if (data == null) {
                    mView.onDataLoadFail(new ResponseState(ResponseState.State.NT_STATE_SERVER_ERROR));
                } else if (!data.isStatus()) {
                    mView.onDataLoadFail(new ResponseState(ResponseState.State.NT_STATE_NO_MORE_DATA));
                }
            }

            @Override
            public void onFail(ResponseState state) {
                mView.onDataLoadFail(state);
            }
        });
        mView.setSubscription(subscription);
    }

    @Override
    public void refreshData(Integer id, Integer curSize) {

    }

}
