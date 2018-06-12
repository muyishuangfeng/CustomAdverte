package com.yk.adverte.net.upload;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yk.adverte.common.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    private static Retrofit retrofit;

    public static synchronized Retrofit buildRetrofit() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);
            retrofit = new Retrofit.Builder().client(OkHttpManager.getInstance())
                    .baseUrl(Constants.HTTP_SERVER)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
