package com.yk.adverte.model.bean;

import java.io.Serializable;
import java.util.List;

public class BaseEntity<T> implements Serializable {

    private int errCode;
    private String errDesc;
    private List<T> rsp;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrDesc() {
        return errDesc;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

    public List<T> getRsp() {
        return rsp;
    }

    public void setRsp(List<T> rsp) {
        this.rsp = rsp;
    }
}
