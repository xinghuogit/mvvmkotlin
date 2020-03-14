package com.library.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.library.common.base.CommonBaseApplication;

import java.io.Serializable;

/*************************************************************************************************
 * 日期：2020/1/14 18:52
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class SPUtils {
    private static SPUtils spUtils;

    private static final String PreferencesNameConfig = "config";
    private static final String PreferencesNameUserInfo = "userInfo";
    private static SharedPreferences sharedPreferencesConfig;
    private static SharedPreferences sharedPreferencesUserInfo;

    private SPUtils() {
        if (sharedPreferencesConfig == null) {  //(存储节点文件名称，读写方式)
            sharedPreferencesConfig = CommonBaseApplication.getInstance()
                    .getSharedPreferences(PreferencesNameConfig, Context.MODE_PRIVATE);
        }
        if (sharedPreferencesUserInfo == null) {  //(存储节点文件名称，读写方式)
            sharedPreferencesUserInfo = CommonBaseApplication.getInstance()
                    .getSharedPreferences(PreferencesNameUserInfo, Context.MODE_PRIVATE);
        }
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
    public void putString(String key, String value) {
        sharedPreferencesConfig.edit().putString(key, value).commit();
    }

    public String getString(String key, String defValue) {
        return sharedPreferencesConfig.getString(key, defValue);
    }

    /**
     * 以下是保存类的方式，跟上面的FILE_NAME表不在一个表里
     */
    /*
     * 保存类，当前SharedPreferences以 class类名被表名
     * */
    public <T extends Serializable> boolean putStringByClass(String key, T entity) {
        String json = GsonUtils.Companion.getInstant().getObjectToJson(entity);
        return sharedPreferencesUserInfo.edit().putString(key, json).commit();
    }

    /*
     * 获取以类名为表名的，某个key值上的value
     * 第二个参数是，类名class,也就是当前的表名
     * */
    public <T extends Serializable> T getByClass(String key, Class<T> clazz) {
        String json = sharedPreferencesUserInfo.getString(key, null);
        if (json == null) return null;
        return GsonUtils.Companion.getInstant().fromJson(json, clazz);
    }

    /*
   移除 key
     * */
    public void removeByKey(String key) {
        sharedPreferencesUserInfo.edit().remove(key).commit();
    }

    /*
     * 移除 PreferencesNameConfig
     * */
    public void clearPreferencesNameConfig() {
        sharedPreferencesConfig.edit().clear().commit();
    }

    /*
     * 移除 PreferencesNameUserInfo
     * */
    public void clearPreferencesNameUserInfo() {
        sharedPreferencesUserInfo.edit().clear().commit();
    }
}
