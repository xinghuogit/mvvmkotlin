package com.library.common.utils

/*************************************************************************************************
 * 日期：2020/3/14 10:24
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：单位工具类
 ************************************************************************************************/
object StringUtils {
    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return `true`: 空<br></br> `false`: 不为空
     */
    fun isEmpty(s: CharSequence?): Boolean {
        return s == null || s.trim().isEmpty() || s == "null"
    }
}
