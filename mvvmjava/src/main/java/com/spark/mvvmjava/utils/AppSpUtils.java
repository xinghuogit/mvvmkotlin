package com.spark.mvvmjava.utils;

import com.library.common.utils.SPUtils;
import com.spark.mvvmjava.base.AppConstant;
import com.spark.mvvmjava.bean.UserInfo;

/*************************************************************************************************
 * 日期：2020/3/14 11:26
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class AppSpUtils {

    private static final String userInfoStr = "userInfo";//用户信息

    public static UserInfo getLoginUserInfo() {
        return SPUtils.getInstance().getByClass(userInfoStr, UserInfo.class);
    }


    public static void updateUserInfo(UserInfo userInfo) {
        SPUtils.getInstance().putStringByClass(userInfoStr, userInfo);
    }

    public static void logOut() {
        SPUtils.getInstance().removeByKey(userInfoStr);
    }

}
