package com.library.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.library.common.base.CommonBaseApplication;

/*************************************************************************************************
 * 日期：2020/1/14 18:52
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class SPUtils {
    private static SPUtils spUtils;

    private static final String PREFERENCE_NAME = "config";
    private static SharedPreferences sharedPreferences;

    private SPUtils() {
    }

    public static SPUtils getInstance() {
        if (spUtils == null) {
            synchronized (SPUtils.class) {
                if (spUtils == null) {
                    spUtils = new SPUtils();
                }
            }
        }
        return spUtils;
    }

    /**
     * 写入String变量至SharedPreferences中
     *
     * @param key   存储节点名称
     * @param value 存储节点的值String
     */
    public static void putString(String key, String value) {
        if (sharedPreferences == null) {  //(存储节点文件名称，读写方式)
            sharedPreferences = CommonBaseApplication.getInstance()
                    .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defValue) {
        if (sharedPreferences == null) {  //(存储节点文件名称，读写方式)
            sharedPreferences = CommonBaseApplication.getInstance()
                    .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, defValue);
    }
}
