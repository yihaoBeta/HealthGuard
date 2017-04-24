package com.yihaobeta.healthguard.utils;

import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

/**
 * Created by yihaobeta on 2017/4/4.
 * RichText工具类，提供RichText的全局配置
 */

public class RichTextUtils {
    public static void setText(String text, TextView textView) {
        RichText.fromHtml(HtmlUtils.removeImgTag(text))
                .autoFix(true)
                .noImage(true)
                .clickable(PreferencesUtils.isBrowserEnable())
                .into(textView);
    }
}
