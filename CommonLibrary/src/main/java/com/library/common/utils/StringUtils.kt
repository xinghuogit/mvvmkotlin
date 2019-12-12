package com.library.common.utils

/**
 * 日期：2019/3/26 17:41
 * 创建：李加蒙
 * 描述：
 */
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
