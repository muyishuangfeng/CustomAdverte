package com.yk.adverte.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;


import com.google.gson.Gson;
import com.yk.adverte.model.bean.JsonBean;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JsonParseUtils {

    /**
     * 解析
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 解析
     * @param result
     * @return
     */
    public static ArrayList<JsonBean> parseData(String result,Handler handler, int msg) {
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendEmptyMessage(msg);
        }
        return detail;
    }


}
