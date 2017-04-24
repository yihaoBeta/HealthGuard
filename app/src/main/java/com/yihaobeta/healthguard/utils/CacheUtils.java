package com.yihaobeta.healthguard.utils;

import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.base.BaseApplication;
import com.yihaobeta.healthguard.common.RetrofitUtils;

import java.math.BigDecimal;

/**
 * description: 缓存工具类，用于提供缓存的获取和清除
 * author: yihaoBeta
 * date: 2017/4/13 0013 13:06
 * update: 2017/4/13 0013
 * version: v1.0
 */
public class CacheUtils {
    public static String getCacheSize() {
        String result = null;
        try {
            long totalCacheSize = RetrofitUtils.getCacheSize() + GlideUtils.getCacheSize();
            result = getFormatSize(totalCacheSize);
        } catch (Exception e) {
            result = BaseApplication.getContext().getString(R.string.calc_cache_fail);
        }
        return result;
    }

    public static boolean clearCache() {
        return GlideUtils.clearAllCache() && RetrofitUtils.clearCache();
    }

    // 格式化单位
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

}
