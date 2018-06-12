package com.yk.adverte.net.retrofit;

import com.google.gson.JsonParseException;
import com.yk.adverte.util.LogUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;



/**
 * Created by Silence on 2018/1/4.
 */

public class ExceptionHelper {

    public static String handleException(Throwable e) {
        e.printStackTrace();
        String error;
        if (e instanceof SocketTimeoutException) {//网络超时
            LogUtils.e("网络连接异常: " + e.getMessage());
            error = "网络连接异常";
        } else if (e instanceof ConnectException) {//均视为网络错误
            LogUtils.e("网络连接异常: " + e.getMessage());
            error = "网络连接异常";
        } else if (e instanceof JSONException
                || e instanceof JsonParseException
                || e instanceof ParseException) {//均视为解析错误
            LogUtils.e("数据解析异常: " + e.getMessage());
            error = "数据解析异常";
        } else if (e instanceof ApiException) {//服务器返回的错误信息
            error = e.getCause().getMessage();
        } else if (e instanceof UnknownHostException) {
            LogUtils.e("网络连接异常: " + e.getMessage());
            error = "网络连接异常";
        } else if (e instanceof IllegalArgumentException) {
            LogUtils.e("下载文件已存在: " + e.getMessage());
            error = "下载文件已存在";
        } else {
            try {
                LogUtils.e("错误: " + e.getMessage());
            } catch (Exception ex) {
                LogUtils.e("未知错误Debug调试");
            }
            error = "错误";
        }
        return error;
    }
}
