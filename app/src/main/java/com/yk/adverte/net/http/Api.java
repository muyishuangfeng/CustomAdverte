package com.yk.adverte.net.http;


import com.yk.adverte.net.retrofit.impl.BaseApiImpl;

/**
 * Created by Silence on 2018/1/4.
 */

public class Api extends BaseApiImpl {

    //private static Api api=new Api() ;
    private static Api api = new Api(RetrofitService.BASE_URL);

    /**
     * 初始化
     *
     * @param baseUrl
     */
    public Api(String baseUrl) {
        super(baseUrl);
    }

    public static RetrofitService getInstance() {
        return api.getRetrofit()
                .create(RetrofitService.class);
    }
}
