package com.yihaobeta.healthguard.common;

/**
 * Created by yihaobeta on 2017/3/10.
 * 全局常量类
 */

public class ConstantValue {
    public static final String LOG_TAG = "MY_TAG";
    public static final String BASE_URL = "http://www.tngou.net/api/";
    public static final int READ_TIMEOUT = 10;//读取超时时间,单位  秒
    public static final int CONN_TIMEOUT = 10;//连接超时时间,单位  秒
    public static final String BASE_URL_IMG = "http://tnfs.tngou.net/image";

    //设缓存有效期为1天
    public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    public static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";
    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
    public static final String AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

    public static final String KNOWLEDGE_ID = "knowledge_id";
    public static final String COOKBOOK_ID = "cookbook_id";
    public static final String DB_NAME = "health-guard-db";

    /**
     * 用于数据库（收藏和历史纪录）中区分不同的类别
     */
    public static final int TYPE_COOKBOOK = 0x01;
    public static final int TYPE_KNOWLEDGE = 0x02;
    public static final int TYPE_MEDICINE = 0x03;
    //Activity请求码
    public static final int REQUEST_CODE_FAVORITE = 0x04;
    public static final String INTENT_IS_FROM_FAVORITE = "isFromFavorite";
    public static final String INTENT_ID = "id";
    public static final String INTENT_POSITION = "position";
    public static final String INTENT_ISDELETE = "isdelete";
    public static final String INTENT_TITLE = "title";
    public static final int TYPE_FAVORITE = 0x05;
    public static final int TYPE_HISTORY = 0x06;

    /**
     * 用于设置界面
     */
    public static final String PREFERENCE_KEY_USE_MOBILE = "use_mobile_net_preference";
    public static final String PREFERENCE_KEY_AUTO_UPDATE = "auto_update_on_wifi_preference";
    public static final String PREFERENCE_KEY_ENABLE_ADS = "ads_preference";
    public static final String PREFERENCE_KEY_ENABLE_BROWSER = "enable_browser_preference";
    public static final String PREFERENCE_KEY_NO_PICTURE = "no_picture_preference";
    public static final String ACTION_NO_PIC_SETTING_CHANGED = "ACTION_NO_PIC_SETTING_CHANGED";
}
