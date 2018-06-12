package com.yk.adverte.net.impl;



/**
 * Created by jingbin on 2017/1/17.
 * 用于数据请求的回调
 */

public interface RequestImpl {
    void loadSuccess(Object object);

    void loadFailed();

}
