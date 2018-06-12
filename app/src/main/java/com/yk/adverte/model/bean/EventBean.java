package com.yk.adverte.model.bean;

/**
 * Created by Silence on 2018/4/28.
 */

public class EventBean {

    private int code;
    private String result;

    public EventBean(int code) {
        this.code = code;
    }

    public EventBean(int code, String result) {
        this.code = code;
        this.result = result;
    }

    public EventBean(String result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
