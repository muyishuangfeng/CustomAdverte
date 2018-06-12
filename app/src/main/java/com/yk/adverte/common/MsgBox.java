package com.yk.adverte.common;

public class MsgBox {

    private static final int BASE_MESSAGE = 0X01;

    public static final int MSG_LOCATION_SUCCESS = BASE_MESSAGE + 1;
    public static final int MSG_LOCATION_FAILED = BASE_MESSAGE + 2;
    /**
     * 运行时权限请求码
     */
    public static final int PER_REQUEST_CODE = BASE_MESSAGE + 3;
    /**
     * 保存到相册
     */
    public static final int DIALOG_SAVE_ALBUM = BASE_MESSAGE + 4;
    /**
     * 加载数据
     */
    public static final int MSG_LOAD_DATA = BASE_MESSAGE + 5;
    /**
     * 加载成功
     */
    public static final int MSG_LOAD_SUCCESS = BASE_MESSAGE + 6;
    /**
     * 加载失败
     */
    public static final int MSG_LOAD_FAILED = BASE_MESSAGE + 7;
    /**
     * GPS请求标记
     */
    public static final int GPS_REQUEST_CODE = BASE_MESSAGE + 8;
    /**
     * GPS请求标记
     */
    public static final int GPS_NOTIFICATION_START = BASE_MESSAGE + 9;
}
