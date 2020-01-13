package com.spark.mvvmjava.model

import java.io.Serializable

class data<T> : Serializable {
    var data: T? = null
    var errorCode: Int = 0
    var errorMsg: String = ""
    override fun toString(): String {
        return "data(data=$data, errorCode=$errorCode, errorMsg='$errorMsg')"
    }
}