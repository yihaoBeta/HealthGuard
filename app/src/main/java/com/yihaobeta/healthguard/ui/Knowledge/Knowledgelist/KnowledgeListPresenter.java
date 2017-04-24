package com.yihaobeta.healthguard.ui.Knowledge.Knowledgelist;

import com.yihaobeta.healthguard.base.IBaseCallBack;
import com.yihaobeta.healthguard.base.IModel;
import com.yihaobeta.healthguard.base.IPresenter;
import com.yihaobeta.healthguard.beans.KnowledgeList;
import com.yihaobeta.healthguard.common.ResponseState;

/**
 * Created by yihaobeta on 2017/3/10.
 */

public class KnowledgeListPresenter implements IPresenter<Integer, Integer> {

    private KnowledgeListFragment mView;
    private IModel mModel;


    public KnowledgeListPresenter(KnowledgeListFragment view) {
        mView = view;
        mModel = new KnowledgeListModel();
    }


    @Override
    public void loadData(Integer id, Integer page) {

        mModel.getDataFromServer(id, page, new IBaseCallBack<KnowledgeList>() {
            @Override
            public void onSuccess(KnowledgeList data) {
                mView.onDataLoaded(data);
            }

            @Override
            public void onFail(ResponseState state) {
                mView.onDataLoadFail(state);
            }
        });
    }

    @Override
    public void refreshData(Integer id, Integer page) {

        mModel.getDataFromServer(id, page, new IBaseCallBack<KnowledgeList>() {
            @Override
            public void onSuccess(KnowledgeList data) {
                if (data.getTngou().get(0).getTitle().equals(mView.mAdapter.getData().get(0).getTitle())) {
                    mView.onDataLoadFail(new ResponseState(ResponseState.State.NT_STATE_NO_MORE_DATA));
                } else {
                    mView.onDataLoaded(data);
                }
            }


            @Override
            public void onFail(ResponseState state) {
                mView.onDataLoadFail(state);
            }
        });
    }
}
