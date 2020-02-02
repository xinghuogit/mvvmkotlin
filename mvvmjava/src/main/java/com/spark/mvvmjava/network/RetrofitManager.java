package com.spark.mvvmjava.network;

import android.os.Environment;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.library.common.api.Urls;
import com.library.common.base.CommonBaseApplication;
import com.spark.mvvmjava.network.interceptor.HttpLogInterceptor;
import com.spark.mvvmjava.network.interceptor.NetCacheInterceptor;
import com.spark.mvvmjava.network.interceptor.OfflineCacheInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*************************************************************************************************
 * 日期：2020/1/14 16:53
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：网络操作者
 ************************************************************************************************/
public class RetrofitManager {
    private static RetrofitManager retrofitManager;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private PersistentCookieJar persistentCookieJar;//缓存
    private RetrofitApiService retrofitApiService;

    private RetrofitManager() {
        persistentCookieJar = new PersistentCookieJar(new SetCookieCache(),
                new SharedPrefsCookiePersistor(CommonBaseApplication.getInstance()));
        ////如果后端没有提供退出登录接口，可以通过以下主动清理
//        persistentCookieJar.clear();
//        persistentCookieJar.clearSession();
        initOkHttpClient();
        initRetrofit();
    }

    public static RetrofitManager getInstance() {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (retrofitManager == null) {
                    retrofitManager = new RetrofitManager();
                }
            }
        }
        return retrofitManager;
    }

    private void initOkHttpClient() {
        okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(new File(
                        Environment.getExternalStorageDirectory() + "/okhttp_cache/")
                        , 5 * 1024 * 1024))
                .connectTimeout(10 * 2, TimeUnit.SECONDS)
                .readTimeout(10 * 2, TimeUnit.SECONDS)
                .writeTimeout(10 * 2, TimeUnit.SECONDS)
                .addInterceptor(new HttpLogInterceptor())
                .addInterceptor(OfflineCacheInterceptor.getInstance())//设置离线缓存
                .addNetworkInterceptor(NetCacheInterceptor.getInstance())//设置在线缓存
                .cookieJar(persistentCookieJar)
                .build();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create()) //
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//
                .client(okHttpClient)
                .build();
        retrofitApiService = retrofit.create(RetrofitApiService.class);
    }

    public RetrofitApiService getApiService() {
        if (retrofitManager == null) {
            retrofitManager = getInstance();
        }
        return retrofitManager.retrofitApiService;
    }
}
