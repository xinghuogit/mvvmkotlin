package com.spark.mvvmjava.base;

import com.library.common.utils.SPUtils;
import com.spark.mvvmjava.bean.UserInfo;
import com.spark.mvvmjava.utils.AppSpUtils;

/*************************************************************************************************
 * 日期：2020/3/14 10:24
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：全局配置信息声明
 ************************************************************************************************/
public class AppConstant {
    public static UserInfo userInfo;   //用户信息
    public static long userId;   //用户id
    public static boolean isLogin;   //是否登录

    public static void updateUserInfo(UserInfo userInfo) {
        AppSpUtils.updateUserInfo(userInfo);
        AppConstant.isLogin = true;
        AppConstant.userInfo = userInfo;
        AppConstant.userId = userInfo.getId();
    }

    public static void logOut() {
        AppSpUtils.logOut();
        AppConstant.isLogin = false;
        AppConstant.userInfo = null;
        AppConstant.userId = -1;
    }

    public static void getUserInfo() {
        AppConstant.userInfo = AppSpUtils.getLoginUserInfo();
        if (AppConstant.userInfo != null && AppConstant.userInfo.getId() >= 1) {
            AppConstant.isLogin = true;
            AppConstant.userId = userInfo.getId();
        } else {
            logOut();
        }
    }
}
