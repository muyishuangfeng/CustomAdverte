package com.yk.adverte.net.http;


import com.yk.adverte.model.bean.CarBean;
import com.yk.adverte.model.bean.UserBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

/**
 * Created by Silence on 2018/1/4.
 */

public interface RetrofitService {

    String BASE_URL = "http://192.168.0.197:8080/";

    @FormUrlEncoded
    @POST("CustomPostManger/insertBannerInfo.action?")
    Observable<String> uploadData(@Field("method") String method,
                                  @Field("banner_content") String content);

    @FormUrlEncoded
    @POST
    Observable<String> speechDetection(@Header("header") String header,
                                       @Field("app_id") int app_id,
                                       @Field("time_stamp") int time_stamp,
                                       @Field("nonce_str") String nonce_str,
                                       @Field("format") int format,
                                       @Field("callback_url") String callback_url,
                                       @Field("speech") String speech,
                                       @Field("sign") String sign);

    @FormUrlEncoded
    @POST
    Observable<String> speechDetections(@Url() String url, @Header("header") String header,
                                        @Field("app_id") int app_id,
                                        @Field("time_stamp") int time_stamp,
                                        @Field("nonce_str") String nonce_str,
                                        @Field("format") int format,
                                        @Field("callback_url") String callback_url,
                                        @Field("speech") String speech,
                                        @Field("sign") String sign);

    @FormUrlEncoded
    @POST
    Observable<CarBean> getCarData(@Field("method") String method, @Field("page") int page);

    @POST
    Observable<ResponseBody> uploadFile(@Url String url,@PartMap
                                        @Body MultipartBody body);

    /**
     * 登录
     *
     * @param method
     * @param user_name
     * @param user_pass
     * @return
     */
    @FormUrlEncoded
    @POST("/")
    Observable<UserBean> login(@Field("method") String method,
                               @Field("user_name") String user_name,
                               @Field("user_pass") String user_pass);
    /**
     * 注册
     *
     * @param method
     * @param user_name
     * @param user_pass
     * @return
     */
    @FormUrlEncoded
    @POST("CustomPostManger/registerUser.action?")
    Observable<UserBean> register(@Field("method") String method,
                               @Field("user_name") String user_name,
                               @Field("user_code") String user_code,
                               @Field("user_pass") String user_pass);

    /**
     * 获取验证码
     * @param method
     * @param user_phone
     * @return
     */
    @FormUrlEncoded
    @POST("/")
    Observable<String> getCode(@Field("method") String method,
                               @Field("user_phone") String user_phone);
}
