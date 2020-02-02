package com.library.common.base;

import android.app.Application;

import com.facebook.stetho.Stetho;

/*************************************************************************************************
 * 日期：2020/1/13 14:04
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class CommonBaseApplication extends Application {
    public static CommonBaseApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Stetho.initializeWithDefaults(this);
    }

    public static CommonBaseApplication getInstance() {
        if (context == null) context = new CommonBaseApplication();
        return context;
    }
}
