package com.library.common.test

import java.util.ArrayList

/*************************************************************************************************
 * 日期：2019/12/12 15:48
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 */
object t {
    @JvmStatic
    fun main(args: Array<String>) {
        val list = ArrayList<String>()
        for (i in 0..2) {
            list.add("I am value $i")
        }
    }
}
