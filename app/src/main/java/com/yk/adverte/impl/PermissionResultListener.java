package com.yk.adverte.impl;

/**
 * Created by Silence on 2017/8/15.
 */

public interface PermissionResultListener {

    //获得权限
    void onPermissionGranted();

    //拒绝权限
    void onPermissionDenied();

}
