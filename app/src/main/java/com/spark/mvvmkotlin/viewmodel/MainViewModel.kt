package com.spark.mvvmkotlin.viewmodel

import androidx.databinding.ObservableField
import com.spark.mvvmkotlin.model.Weather

/*************************************************************************************************
 * 日期：2019/11/29 15:46
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
class MainViewModel(val mWeather: Weather) {
    val info =
        ObservableField<String>("${mWeather.dateTime} 天气 ${mWeather.title} 第 ${mWeather.number} 次刷新")

    fun addNumber() {
        mWeather.number++
        info.set("${mWeather.dateTime} 天气 ${mWeather.title} 第 ${mWeather.number} 次刷新")
    }
}