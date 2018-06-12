package com.yk.adverte.common;

public class AppStatusManager {


    public static AppStatusManager appStatusManager;
    //APP状态初始值为没启动不在前台状态
    public int appStatus = AppStatusConstant.STATUS_FORCE_KILLED;

    private AppStatusManager() {

    }

    public static AppStatusManager getInstance() {
        if (appStatusManager == null) {
            appStatusManager = new AppStatusManager();
        }
        return appStatusManager;
    }

    public int getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
    }


}
