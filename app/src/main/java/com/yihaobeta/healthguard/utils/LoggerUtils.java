package com.yihaobeta.healthguard.utils;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.BuildConfig;
import com.yihaobeta.healthguard.common.ConstantValue;

/**
 * Created by yihaobeta on 2017/4/2.
 * Logger工具类，提供全局的logger配置
 */

public class LoggerUtils {

    public static void init() {
        Logger.init(ConstantValue.LOG_TAG)
                .methodCount(3)
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);
    }
}
