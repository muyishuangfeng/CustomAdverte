package com.yk.adverte.net.retrofit.cache;


import com.yk.adverte.common.APP;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by Horrarndoo on 2017/9/12.
 * <p>
 */
public class HttpCache {

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;

    public static Cache getCache() {

        return new Cache(new File(APP.getAppContext().getCacheDir()
                + File.separator + "httpCache"),
                HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
