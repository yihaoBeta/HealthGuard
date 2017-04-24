package com.yihaobeta.healthguard.common;

/**
 * description: 自定义Throwable，用于没有查询到相关数据时的返回提示
 * author: yihaoBeta
 * date: 2017/4/2 0022 12:30
 * update: 2017/4/22 0022
 * version: v1.0
 */
public class NoSuchDataThrowable extends Throwable {
    public NoSuchDataThrowable(String message) {
        super(message);
    }
}
