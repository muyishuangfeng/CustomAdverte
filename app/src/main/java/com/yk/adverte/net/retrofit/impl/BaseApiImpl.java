package com.yk.adverte.net.retrofit.impl;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yk.adverte.net.retrofit.cache.CacheInterceptor;
import com.yk.adverte.net.retrofit.cache.HttpCache;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Silence on 2018/1/4.
 */

public class BaseApiImpl implements BaseApi {

    private volatile static Retrofit retrofit = null;
    protected Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
    protected OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
    private static final int TIMEOUT_READ = 20;
    private static final int TIMEOUT_CONNECTION = 10;
    private static CacheInterceptor cacheInterceptor = new CacheInterceptor();
    private static final String BASE_URL="";


    /**
     * 初始化
     *
     * @param baseUrl
     */
    public BaseApiImpl(String baseUrl) {
        retrofitBuilder.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(baseUrl);

    }

    /**
     * okhttp
     */
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()

            //打印日志
            .addInterceptor(getInterceptor())
            //设置Cache拦截器
            .addNetworkInterceptor(cacheInterceptor)
            .addInterceptor(cacheInterceptor)
            //缓存大小
            .cache(HttpCache.getCache())
            //time out
            .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            //失败重连
            .retryOnConnectionFailure(true)
            .build();

    @Override
    public Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (BaseApiImpl.class) {
                if (retrofit == null) {
                    //创建retrofit对象
                    retrofit = retrofitBuilder.baseUrl(BASE_URL).build();
                }
            }
        }
        return retrofit;
    }

    @Override
    public OkHttpClient.Builder setInterceptor(Interceptor interceptor) {
        return httpBuilder.addInterceptor(interceptor);
    }

    @Override
    public Retrofit.Builder setConverterFactory(Converter.Factory factory) {
        return retrofitBuilder.addConverterFactory(factory);
    }

    /**
     * 自定义日志拦截器
     *
     * @return
     */
    public static HttpLoggingInterceptor getInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d("Custom", "--->" + message);
                    }
                });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }



}
