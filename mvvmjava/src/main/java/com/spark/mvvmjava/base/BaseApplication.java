package com.spark.mvvmjava.base;

import com.library.common.base.CommonBaseApplication;

/*************************************************************************************************
 * 日期：2020/1/13 14:04
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class BaseApplication extends CommonBaseApplication {
    public static BaseApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        AppConfig.initFilesDir();
        AppConfig.initSmartRefreshLayout();
        AppConstant.getUserInfo();
    }

    public static BaseApplication getInstance() {
        if (context == null) context = new BaseApplication();
        return context;
    }
}
