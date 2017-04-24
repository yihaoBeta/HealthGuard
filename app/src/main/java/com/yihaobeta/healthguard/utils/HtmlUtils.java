package com.yihaobeta.healthguard.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yihaobeta on 2017/4/14 0014.
 */

public class HtmlUtils {

    /**
     * 利用正则表达式匹配替换<img .....>标签
     * 不过server端返回的数据格式不规范，可能会出现个别匹配不到的情况
     *
     * @param htmlSource
     * @return
     */
    public static String removeImgTag(String htmlSource) {
        Pattern pattern = Pattern.compile("<img(.*?)\">");
        Matcher matcher = pattern.matcher(htmlSource);
        if (matcher.find()) {
            return matcher.replaceAll("");
        }
        return htmlSource;
    }
}
