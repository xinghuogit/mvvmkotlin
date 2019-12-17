package com.library.common.utils

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

/*************************************************************************************************
 * 日期：2019/12/17 14:47
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
object DateUtils {
    const val dateTime_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss"
    const val dateTime_yyyyMMdd = "yyyy-MM-dd"
    const val dateTime_yyyyMM = "yyyy-MM"
    const val dateTime_MMdd = "MM-dd"
    const val dateTime_HHmmss = "HH:mm:ss"
    const val dateTime_HHmm = "HH:mm"
    const val dateTime_mmss = "mm:ss"

    /**
     *@strDateTime 时间戳转换成为时间
     *@time 时间戳 dateTime_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss"
     */
    fun getDateTimeToStr(timeStamp: Long): String {
        return getDateTimeToStr(timeStamp, dateTime_yyyyMMddHHmmss)
    }

    /**
     *@strDateTime 时间戳转换成为时间
     *@time 时间戳
     *@str  转换成为时间的格式 "yyyy-MM-dd HH:mm:ss" yyyy年MM月dd日 yyyyMMddHHmmss
     */
    fun getDateTimeToStr(timeStamp: Long, str: String): String {
        var format = SimpleDateFormat(str, Locale.getDefault());
        var date = Date(isMS(timeStamp));
        var dateTimeToStr = format.format(date);
        return dateTimeToStr
    }

    /**
     * @time 一般13位整数位毫秒，如果不是乘以1000 1秒=1000毫秒
     */
    fun isMS(time: Long): Long {
        return if (time.toString().length == 13) time else time * 1000
    }
}