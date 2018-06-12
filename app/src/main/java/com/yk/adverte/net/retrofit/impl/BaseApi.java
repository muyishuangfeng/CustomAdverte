package com.yk.adverte.net.retrofit.impl;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Silence on 2018/1/4.
 */

public interface BaseApi {


    //获取实例
    Retrofit getRetrofit();

    //过滤器
    OkHttpClient.Builder setInterceptor(Interceptor interceptor);

    //转换
    Retrofit.Builder setConverterFactory(Converter.Factory factory);
}
