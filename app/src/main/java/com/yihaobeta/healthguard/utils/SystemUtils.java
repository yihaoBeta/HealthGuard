package com.yihaobeta.healthguard.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.yihaobeta.healthguard.base.BaseApplication;

/**
 * Created by yihaobeta on 2017/4/9.
 */

public class SystemUtils {
    public static String getVersion(Context context) {
        if (context == null) {
            context = BaseApplication.getContext();
        }
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
