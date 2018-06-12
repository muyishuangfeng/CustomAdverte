package com.yk.adverte.common;

import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Silence on 2018/3/20.
 */

public class Constants {

    /**
     * 异常日志路径
     */
    public static final String LOG_PATH = Environment
            .getExternalStorageDirectory().getPath() + File.separator + "Adverte" + File.separator
            + "log" + File.separator;

    /**
     * 图片路径
     */
    public static final String IMAGE_PATH = Environment
            .getExternalStorageDirectory().getPath() + File.separator + "Adverte" + File.separator
            + "Image" + File.separator;
    /**
     * 路径
     */
    public static final String SDCARD_PATH = Environment
            .getExternalStorageDirectory().getPath();
    /**
     * 下载路径
     */
    public static final String DOWNLOAD_PATH = "/Adverte/download/";
    /**
     * 头像路径
     */
    public static final String HEAD_PIC_PATH = Environment
            .getExternalStorageDirectory().getPath() + File.separator + "Adverte" + File.separator
            + "user" + File.separator;
    /**
     * 频道路径
     */
    public static final String CHANNEL_PIC_PATH = Environment
            .getExternalStorageDirectory().getPath() + File.separator + "Adverte" + File.separator
            + "channel" + File.separator;
    /**
     * 用户头像名称
     */
    public static final String USER_PIC_NAME = Constants.HEAD_PIC_PATH
            + "head.png";

    /**
     * 第一次进入应用
     */
    public static final String FIRST_INTO_APP = "FIRST";

    /**
     * 更新照片标记
     */
    public static final String UPDATE_PHOTO_FLAG = "UPDATE_PHOTO_FLAG";
    /**
     * 手机号码
     */
    public static final String USER_TELL = "USER_TELL";
    /**
     * 密码
     */
    public static final String USER_PASS = "USER_PASS";
    /**
     * 用户名
     */
    public static final String USER_NAME = "USER_NAME";
    /**
     * 昵称
     */
    public static final String NICK_NAME = "NICK_NAME";
    /**
     * 地址
     */
    public static final String HTTP_SERVER = "http://192.168.0.197";


    /**城市名*/
    public static final String CITY_NAME="CITY_NAME";
    /**登录状态*/
    public static final String USER_FLAG="USER_FLAG";
    /**经度*/
    public static final String LONGITUDE="LONGITUDE";
    /**纬度*/
    public static final String LATITUDE="LATITUDE";
    /**详细地址*/
    public static final String ADDRESS_DETAIL="ADDRESS_DETAIL";

    public static final String NOTIFICATION_CHANNEL_NAME = "BackgroundLocation";
}
