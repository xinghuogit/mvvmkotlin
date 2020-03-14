package com.spark.mvvmjava.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.spark.mvvmjava.base.BaseApplication;

/*************************************************************************************************
 * 日期：2020/3/14 12:50
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class ActivityUtils {

    public static void startActivity(Context context, Class<? extends Activity> clz) {
        Intent intent = new Intent(context, clz);
        context.startActivity(intent);
    }

    public static void startHome() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.getInstance().startActivity(homeIntent);
    }
}
