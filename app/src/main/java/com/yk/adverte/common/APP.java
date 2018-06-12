package com.yk.adverte.common;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;


/**
 * Created by Silence on 2017/8/15.
 */

public class APP extends Application {

    private static APP mApp = null;
    protected static int mainThreadId;
    protected static Handler handler;
    private static int count = 0;

    public static APP getInstance() {
        return mApp;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        // 在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常
        CrashHandler crashHandler = CrashHandler.getInstance();
        mainThreadId = android.os.Process.myTid();
        mApp = this;
        crashHandler.init(this);
        registerActivityLifecycleCallbacks(mCallBack);
    }


    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    public static Handler getHandler() {
        return handler;
    }


    /**
     * 获取主线程id
     *
     * @return 主线程id
     */
    public static int getMainThreadId() {
        return mainThreadId;
    }

    /**
     * 使其系统更改字体大小无效
     */
    private void initTextSize() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    /**
     * 获取Application的Context
     **/
    public static Context getAppContext() {
        if (mApp == null)
            return null;
        return mApp.getApplicationContext();
    }

    /**
     * 生命周期
     */
    ActivityLifecycleCallbacks mCallBack = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (count > 0) {
                count--;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    /**
     * 判断app是否在后台
     *
     * @return
     */
    public static boolean isBackground() {
        if (count <= 0) {
            return true;
        } else {
            return false;
        }
    }
}

