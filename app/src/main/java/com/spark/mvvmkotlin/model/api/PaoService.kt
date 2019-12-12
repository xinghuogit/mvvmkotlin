package com.spark.mvvmkotlin.model.api

import com.spark.mvvmkotlin.model.Weather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/*************************************************************************************************
 * 日期：2019/12/12 16:58
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
interface PaoService {
    @GET("wxarticle/list/408/1/json")
    fun getGongZongHaoList(@Query("id") id: Int): Single<Weather>;
}