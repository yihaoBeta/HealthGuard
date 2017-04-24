package com.yihaobeta.healthguard.base;

import com.yihaobeta.healthguard.database.entity.UniversalDetailEntity;

import java.util.List;

/**
 * description: 本地操作Model接口
 * author: yihaoBeta
 * date: 2017/4/13 0013 12:59
 * update: 2017/4/13 0013
 * version: v1.0
 */

public interface ILocalModel {
    void getDataFromDatabase(int type, int id);

    void saveData2Database(UniversalDetailEntity data);

    void deleteDataFromDatabase(UniversalDetailEntity data);

    void deleteDataFromDataBase(List<UniversalDetailEntity> list);

    void deleteAllDataFromDataBase();

    void updateData2Database(UniversalDetailEntity data);

    void getFavoritesFromDataBase(int type);

    void getHistoriesFromDataBase();
}
