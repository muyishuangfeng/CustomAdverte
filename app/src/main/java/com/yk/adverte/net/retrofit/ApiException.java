package com.yk.adverte.net.retrofit;

/**
 * Created by Silence on 2018/1/4.
 * 自定义exception 用于显示
 */

public class ApiException extends Exception {

    private int code;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ApiException(String message) {
        super(new Throwable(message));
    }
}
