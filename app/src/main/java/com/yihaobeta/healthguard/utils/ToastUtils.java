package com.yihaobeta.healthguard.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * description: Toast帮助类，避免同样的信息多次触发重复弹出的问题
 * author: yihaoBeta
 * date: 2017/4/24 0024 12:45
 * update: 2017/4/24 0024
 * version: v1.0
 */
public class ToastUtils {

    protected static Toast toast = null;
    private static Context sContext;
    private static String oldMsg;
    private static long oneTime = 0;
    private static long twoTime = 0;

    private ToastUtils() {
        throw new RuntimeException("ToastUtils cannot be initialized!");
    }

    public static void init(Context context) {
        sContext = context;
    }

    public static void showToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(sContext, s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
            oneTime = twoTime;
        }
    }

    public static void showToast(int resId) {
        showToast(sContext.getString(resId));
    }
}
