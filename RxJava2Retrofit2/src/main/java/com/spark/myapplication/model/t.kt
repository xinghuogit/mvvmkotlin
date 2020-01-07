package com.spark.myapplication.model

import com.google.gson.Gson

/*************************************************************************************************
 * 日期：2019/12/19 19:32
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 */
object t {
    @JvmStatic
    fun main(args: Array<String>) {
        Gson().fromJson("", WeChatSubscription::class.java)
    }
}