package com.yk.adverte.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SPUtil {

    private static Editor saveEditor;
    private static SharedPreferences saveInfo;

    public SPUtil(Context context) {
        if (saveInfo == null && context != null) {
            saveInfo = context.getSharedPreferences("cmcc_omp",
                    Context.MODE_PRIVATE);
            saveEditor = saveInfo.edit();
        }
    }

    /**
     * 保存密码
     */
    public void putPassWord(String key, String value) {
        saveEditor.putString(key, value);
        saveEditor.commit();
    }

    /**
     * 保存用户ID
     */
    public void putUserID(String key, String value) {
        saveEditor.putString(key, value);
        saveEditor.commit();
    }


    /**
     * 保存用户编号
     */
    public void putUserNo(String key, String value) {
        saveEditor.putString(key, value);
        saveEditor.commit();
    }
    /**
     * 保存地址
     */
    public void putAddress(String key, String value) {
        saveEditor.putString(key, value);
        saveEditor.commit();
    }
    /**
     * 保存城市
     */
    public void putCity(String key, String value) {
        saveEditor.putString(key, value);
        saveEditor.commit();
    }
    /**
     * 保存纬度
     */
    public void putLat(String key, String value) {
        saveEditor.putString(key, value);
        saveEditor.commit();
    }
    /**
     * 保存经度
     */
    public void putLong(String key, String value) {
        saveEditor.putString(key, value);
        saveEditor.commit();
    }

    /**
     * 保存用户名
     */
    public void putUserName(String key, String value) {
        saveEditor.putString(key, value);
        saveEditor.commit();
    }

    /**
     * 保存昵称
     */
    public void putNickName(String key, String value) {
        saveEditor.putString(key, value);
        saveEditor.commit();
    }

    /**
     * 保存用户电话
     */
    public void putUserTell(String key, String value) {
        saveEditor.putString(key, value);
        saveEditor.commit();
    }

    /**
     * 保存点赞状态
     */
    public void putStarStatus(String key, String value) {
        saveEditor.putString(key, value);
        saveEditor.commit();
    }

    /**
     * 保存用户登录类型
     */
    public void putUserFlag(String key, String value) {
        saveEditor.putString(key, value);
        saveEditor.commit();
    }

    /**
     * 保存用户积分
     */
    public void putUserIntegral(String key, String value) {
        saveEditor.putString(key, value);
        saveEditor.commit();
    }

    /**
     * 保存用户格言
     */
    public void putMotto(String key, String value) {
        saveEditor.putString(key, value);
        saveEditor.commit();
    }

    /**
     * 获取用户电话
     *
     * @return
     */
    public String getUserTell(String key) {
        return saveInfo.getString(key, "");
    }
    /**
     * 获取城市
     *
     * @return
     */
    public String getCity(String key) {
        return saveInfo.getString(key, "");
    }

    /**
     * 获取用户编号
     *
     * @return
     */
    public String getUserNo(String key) {
        return saveInfo.getString(key, "");
    }

    /**
     * 获取用户昵称
     *
     * @return
     */
    public String getNickName(String key) {
        return saveInfo.getString(key, "");
    }


    /**
     * 获取用户ID
     *
     * @return
     */
    public String getUserID(String key) {
        return saveInfo.getString(key, "");
    }

    /**
     * 获取用户点赞状态
     *
     * @return
     */
    public String getStarStatus(String key) {
        return saveInfo.getString(key, "");
    }


    /**
     * 获取用户密码
     *
     * @return
     */
    public String getPassWord(String key) {
        return saveInfo.getString(key, "");
    }

    /**
     * 获取用户登录类型
     *
     * @return
     */
    public String getUserFlag(String key) {
        return saveInfo.getString(key, "");
    }

    /**
     * 获取用户积分
     *
     * @return
     */
    public String getUserIntegral(String key) {
        return saveInfo.getString(key, "");
    }

    /**
     * 是否是第一次进入
     */
    public void putIsFirst(String key, String value) {
        saveEditor.putString(key, value);
        saveEditor.commit();
    }

    /**
     * 获取第一次进入的值
     *
     * @return
     */
    public String getIsFirst(String key) {
        return saveInfo.getString(key, "0");
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public String getUsername(String key) {
        return saveInfo.getString(key, "");
    }
    /**
     * 获取地址
     *
     * @return
     */
    public String getAddress(String key) {
        return saveInfo.getString(key, "");
    }
    /**
     * 获取纬度
     *
     * @return
     */
    public String getLat(String key) {
        return saveInfo.getString(key, "");
    }
    /**
     * 获取经度
     *
     * @return
     */
    public String getLong(String key) {
        return saveInfo.getString(key, "");
    }

    /**
     * 获取格言
     *
     * @return
     */
    public String getMotto(String key) {
        return saveInfo.getString(key, "");
    }



    public void putString(String key, String values) {
        saveEditor.putString(key, values);
        saveEditor.commit();
    }

    public String getString(String key) {
        return saveInfo.getString(key, "");
    }


    /**
     * 移除
     *
     * @param KEY_NAME
     */
    public void remove(String KEY_NAME) {
        Editor editor = saveEditor;
        editor.remove(KEY_NAME);
        editor.commit();
    }

    /**
     * 查询某个key是否已经存在
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences("cmcc_omp",
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }
}
