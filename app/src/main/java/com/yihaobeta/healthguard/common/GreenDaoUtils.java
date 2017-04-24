package com.yihaobeta.healthguard.common;


import android.content.Context;

import com.yihaobeta.healthguard.database.entity.DaoMaster;
import com.yihaobeta.healthguard.database.entity.DaoSession;
import com.yihaobeta.healthguard.database.entity.UniversalDetailEntityDao;

/**
 * description: GreenDao工具类
 * author: yihaoBeta
 * date: 2017/4/13 0013 13:04
 * update: 2017/4/13 0013
 * version: v1.0
 */

public class GreenDaoUtils {
    private static GreenDaoUtils sInstance;
    private static DaoSession session;
    private Context mContext;

    private GreenDaoUtils(Context context) {
        this.mContext = context;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, ConstantValue.DB_NAME);
        DaoMaster master = new DaoMaster(helper.getWritableDb());
        session = master.newSession();
    }

    public static GreenDaoUtils init(Context context) {
        if (sInstance == null) {
            synchronized (GreenDaoUtils.class) {
                if (sInstance == null) {
                    sInstance = new GreenDaoUtils(context);
                }
            }
        }
        return sInstance;
    }


    public static UniversalDetailEntityDao getUniversalDetailEntityDao() {
        if (session == null) return null;
        return session.getUniversalDetailEntityDao();
    }

}
