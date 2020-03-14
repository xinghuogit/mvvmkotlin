package com.spark.mvvmjava.network;

import com.spark.mvvmjava.bean.Advert;
import com.spark.mvvmjava.bean.HomeFatherBean;
import com.spark.mvvmjava.bean.UserInfo;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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

    /**
     * 首页文章,curPage拼接。从0开始
     */
    @GET("article/list/{curPage}/json")
    Observable<ResponseModel<HomeFatherBean>> getHomeArticles(@Path("curPage") int curPage);

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<ResponseModel<UserInfo>> login(@FieldMap HashMap<String, Object> map);

    //收藏站内文章
    @POST("lg/collect/{id}/json")
    Observable<ResponseModel<String>> collectArticle(@Path("id") int id);

    //收藏站外文章
    @FormUrlEncoded
    @POST("lg/collect/add/json")
    Observable<ResponseModel<String>> collectOutArticle(@Field("title") String title, @Field("author") String author, @Field("link") String link);

    //取消收藏 -- 首页文章列表
    @POST("lg/uncollect_originId/{id}/json")
    Observable<ResponseModel<String>> unCollectByHome(@Path("id") int id);

    //取消收藏 -- 我的收藏列表
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    Observable<ResponseModel<String>> unCollectByMe(@Path("id") int id, @Field("originId") int originId);

    //Retrofit 上传文件,前面的sequence是单表单@Part("sequence") RequestBody sequence
    //Observable<ResponseBody> uploadPic(@Url String url,@Part("sequence") RequestBody sequence , @Part MultipartBody.Part file);
    // 多表单 @FieldMap Map<String, String> usermaps
    @POST("upload/pic")
    @Multipart
    Observable<ResponseBody> upload(@FieldMap HashMap<String, String> map, @Part MultipartBody.Part file);
}
