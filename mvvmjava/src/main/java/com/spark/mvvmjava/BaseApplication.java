package com.spark.mvvmjava;

import android.app.Application;

import com.facebook.stetho.Stetho;

/*************************************************************************************************
 * 日期：2020/1/13 14:04
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class BaseApplication extends Application {
    public static BaseApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Stetho.initializeWithDefaults(this);
    }

    public static BaseApplication getInstance() {
        if (context == null) context = new BaseApplication();
        return context;
    }
}
