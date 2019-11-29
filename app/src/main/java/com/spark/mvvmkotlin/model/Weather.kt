package com.spark.mvvmkotlin.model

/*************************************************************************************************
 * 日期：2019/11/29 15:47
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
class Weather() {
    var title = ""
    var dateTime = ""
    var number = 0

    constructor(title: String, dateTime: String, number: Int) : this() {
        this.title = title;
        this.dateTime = dateTime;
        this.number = number;
    }
}