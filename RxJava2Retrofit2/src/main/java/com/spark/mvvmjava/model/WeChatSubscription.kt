package com.spark.mvvmjava.model

import java.io.Serializable

/*************************************************************************************************
 * 日期：2019/12/19 19:21
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
class WeChatSubscription : Serializable {
    var title: String = ""
    var name: String = ""
    var id: Long = 0L
    var order: Long = 0L
    var data: ArrayList<WeChatSubscription>? = null
    override fun toString(): String {
        return "WeChatSubscription(title='$title', name='$name', id=$id, order=$order, data=$data)"
    }
}