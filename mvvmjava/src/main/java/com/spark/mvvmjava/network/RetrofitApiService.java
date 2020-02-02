package com.spark.mvvmjava.network;

import com.spark.mvvmjava.bean.Advert;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/*************************************************************************************************
 * 日期：2020/1/14 17:10
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：接口请求配置都在这
 ************************************************************************************************/
public interface RetrofitApiService {

    /**
     *
     */
    @GET("banner/json")
    Observable<ResponseModel<List<Advert>>> getAdverts();
}
