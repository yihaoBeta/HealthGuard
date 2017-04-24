package com.yihaobeta.healthguard.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.yihaobeta.healthguard.common.NoSuchDataThrowable;
import com.yihaobeta.healthguard.common.ResponseState;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * Created by yihaobeta on 2017/3/7.
 * 网络工具类，提供网络状态信息
 */

public class NetWorkUtils {

    public static final int NET_TYPE_DISCONNECTED = -1;
    public static final int NET_TYPE_WIFI = 1;
    public static final int NET_TYPE_WAP = 2;
    public static final int NET_TYPE_CMNET = 3;

    public static ResponseState getExceptionState(Throwable e) {
        ResponseState state = new ResponseState();
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            if (code == 500 || code == 404) {
                state.setState(ResponseState.State.NT_STATE_SERVER_ERROR);
            } else if (code == 504) {
                state.setState(ResponseState.State.NT_STATE_CONNECTION_TIME_OUT);
            }
        } else if (e instanceof ConnectException) {
            state.setState(ResponseState.State.NT_STATE_NETWORK_DISCONNECTED);
        } else if (e instanceof SocketTimeoutException) {
            state.setState(ResponseState.State.NT_STATE_CONNECTION_TIME_OUT);
        } else if (e instanceof UnknownHostException) {
            state.setState(ResponseState.State.NT_STATE_NETWORK_DISCONNECTED);
        } else if (e instanceof NoSuchDataThrowable) {
            state.setState(ResponseState.State.NT_STATE_NO_SUCH_DATA);
        } else {
            state.setState(ResponseState.State.STATE_COMMON_FAIL);
            e.printStackTrace();
        }
        return state;

    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }

    /**
     * wifi 是否连接
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return manager.isWifiEnabled();
    }

    /**
     * 获取当前网络类型
     * 返回值 -1：没有网络  1：WIFI网络2：wap网络3：net网络
     *
     * @param context
     * @return
     */
    public static int getNetType(Context context) {
        int netType = NET_TYPE_DISCONNECTED;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                netType = NET_TYPE_CMNET;
            } else {
                netType = NET_TYPE_WAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NET_TYPE_WIFI;
        }
        return netType;
    }
}
