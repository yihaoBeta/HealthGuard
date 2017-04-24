package com.yihaobeta.healthguard.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.yihaobeta.healthguard.base.BaseApplication;
import com.yihaobeta.healthguard.common.ConstantValue;

/**
 * Created by yihaobeta on 2017/4/4.
 */

public class PreferencesUtils {
    public static boolean isMobileNetEnable() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getContext());
        return preferences.getBoolean(ConstantValue.PREFERENCE_KEY_USE_MOBILE, false);
    }

    public static boolean isNoPicMode() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getContext());
        return preferences.getBoolean(ConstantValue.PREFERENCE_KEY_NO_PICTURE, false);
    }

    public static boolean isAdEnable() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getContext());
        return preferences.getBoolean(ConstantValue.PREFERENCE_KEY_ENABLE_ADS, false);
    }

    public static boolean isBrowserEnable() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getContext());
        return preferences.getBoolean(ConstantValue.PREFERENCE_KEY_ENABLE_BROWSER, false);
    }
}
