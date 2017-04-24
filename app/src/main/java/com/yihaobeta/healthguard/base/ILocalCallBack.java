package com.yihaobeta.healthguard.base;

import com.yihaobeta.healthguard.common.ResponseState;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;

import java.util.List;

/**
 * description: 本地操作的CallBack接口类
 * author: yihaoBeta
 * date: 2017/4/13 0013 12:58
 * update: 2017/4/13 0013
 * version: v1.0
 */
public class ILocalCallBack {
    public interface PresenterCallBack {
        void onDataLoadComplete(UniversalDetailEntity data);

        void onUpdateComplete(UniversalDetailEntity data);

        void onQueryComplete(List<UniversalDetailEntity> list);

        void onDeleteComplete();

        void onError(ResponseState state);
    }

    public interface ViewCallBack {
        void onComplete();

        void onError(ResponseState state);
    }

}
