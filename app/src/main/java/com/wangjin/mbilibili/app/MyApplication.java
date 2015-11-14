package com.wangjin.mbilibili.app;

import android.app.Application;

/**
 * Created by wangjin on 15/11/10.
 */
public class MyApplication extends Application {

    private static MyApplication app;

    public static MyApplication getInstance(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
