package com.library.common.utils

import android.widget.Toast
import com.library.common.base.CommonBaseApplication

/*************************************************************************************************
 * 日期：2020/1/15 18:22
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
object ToastUtils {
    private var toast: Toast? = null
    private var oldMsg: String? = null
    private var lastToastTime: Long = 0

    public fun showToast(message: String) {
        if (toast == null) {
            toast =
                Toast.makeText(CommonBaseApplication.getInstance(), message, Toast.LENGTH_SHORT)
            toast!!.show()
            lastToastTime = System.currentTimeMillis()
        } else {
            var currentTime = System.currentTimeMillis()
            if (message == oldMsg) {//Kotlin可以==  Java必须equals
                if (currentTime - lastToastTime > Toast.LENGTH_SHORT) {
                    toast!!.show()
                }
            } else {
                oldMsg = message;
                toast!!.setText(oldMsg)
                toast!!.show()
            }
            lastToastTime = System.currentTimeMillis()
        }
    }
}