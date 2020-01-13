package com.library.common.utils

import android.util.Log

/*************************************************************************************************
 * 日期：2020/1/13 10:28
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
object LogUtils {
    var isLogEnabled = true;

    fun v(tag: String, msg: String) {
        log('v', tag, msg)
    }

    fun d(tag: String, msg: String) {
        log('d', tag, msg)
    }

    fun i(tag: String, msg: String) {
        log('i', tag, msg)
    }

    fun w(tag: String, msg: String) {
        log('w', tag, msg)
    }

    fun e(tag: String, msg: String) {
        log('e', tag, msg)
    }

    private fun log(type: Char, tag: String, msg: String) {
        var message = createLog(msg)
        if (isLogEnabled) {
            if ('v' == type) {
                Log.v(tag, message)
            } else if ('d' == type) {
                Log.d(tag, message)
            } else if ('i' == type) {
                Log.i(tag, message)
            } else if ('w' == type) {
                Log.w(tag, message)
            } else if ('e' == type) {
                Log.e(tag, message)
            }
        }
    }

    private fun createLog(msg: String): String {
        var str = msg
        val stackTrace = Thread.currentThread().stackTrace
        if (stackTrace != null && stackTrace.isNotEmpty() && stackTrace.size >= 5) {
            val traceElement = stackTrace[5]
            var stackStr = StringBuilder()
            stackStr.append(traceElement.methodName)
            stackStr.append("(")
            stackStr.append(traceElement.fileName)
            stackStr.append(":")
            stackStr.append(traceElement.lineNumber)
            stackStr.append(")")
            stackStr.append("\n")
            stackStr.append("========================================")
            stackStr.append("\n")
            stackStr.append(msg)
            stackStr.append("\n")
            stackStr.append("========================================")
            stackStr.append("\n")
            str = stackStr.toString();
        }
        return str
    }
}