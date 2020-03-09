package com.library.common.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type

/*************************************************************************************************
 * 日期：2020/3/9 12:02
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
class GsonUtils {
    private var gson: Gson? = null;

    companion object {
        private var instant: GsonUtils? = null
            get() {
                if (instant == null)
                    instant = GsonUtils()

                return instant
            }
    }

    private constructor() {
        gson = GsonBuilder().registerTypeAdapterFactory(NullStringToEmptyAdapterFactory<Any?>())
            .create()
    }

    fun get(): GsonUtils { //细心的小伙伴肯定发现了，这里不用getInstance作为为方法名，是因为在伴生对象声明时，内部已有getInstance方法，所以只能取其他名字
        return instant!!
    }

    fun <T> fromJson(text: String, type: Class<T>): T? {
        return get().parse(text, type)
    }

    fun <T> fromJson(text: String, type: Type): T? {
        return get().parse(text, type)
    }

    private fun <T> parse(text: String, type: Type): T? {
        var t: T? = null
        try {
            if (gson != null) {
                t = gson!!.fromJson(text, type)
//                if (t != null && t is BaseResponse && com.library.common.utils.ParseUtils.checkResponseCode != null) {
//                    com.library.common.utils.ParseUtils.checkResponseCode.checkCode((t as BaseResponse).code)
//                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return t
    }

    /**
     * Object转Json字符串
     */
    fun getObjectToJson(o: Any?): String? {
        try {
            return get().gson!!.toJson(o)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}