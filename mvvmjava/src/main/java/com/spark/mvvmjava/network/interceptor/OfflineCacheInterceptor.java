package com.spark.mvvmjava.network.interceptor;

import com.library.common.utils.NetWorkUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/*************************************************************************************************
 * 日期：2020/1/13 14:21
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：* 在没有网络连接的时候，会取的缓存
 * 这个会比网络拦截器先运行
 * 重点 : 一般OkHttp只缓存不大改变的数据适合get。（个人理解，无网络的时候可以将无网络有效期改长点）
 * 这里和前面的不同，立即设置，立即生效。例，你一个接口设置1个小时的离线缓存有效期，立即设置0.下次进入后，则无效
 *  ************************************************************************************************/
public class OfflineCacheInterceptor implements Interceptor {
    private static final String TAG = "OfflineCacheInterceptor";

    private static OfflineCacheInterceptor offlineCacheInterceptor;

    //离线的时候的缓存的过期时间
    private int offlineCacheTime;

    private OfflineCacheInterceptor() {
    }

    public static OfflineCacheInterceptor getInstance() {
        if (offlineCacheInterceptor == null) {
            synchronized (OfflineCacheInterceptor.class) {
                if (offlineCacheInterceptor == null) {
                    offlineCacheInterceptor = new OfflineCacheInterceptor();
                }
            }
        }
        return offlineCacheInterceptor;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetWorkUtils.isConnected()) {
            if (offlineCacheTime != 0) {
                int temp = offlineCacheTime;
                request = request.newBuilder()
//                        .cacheControl(new CacheControl
//                                .Builder()
//                                .maxStale(60, TimeUnit.SECONDS)
//                                .onlyIfCached()
//                                .build())
//                        .build();//两种方式结果是一样的，写法不同
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + temp)
                        .build();
            } else {
                request = request.newBuilder()
                        .cacheControl(new CacheControl
                                .Builder()
                                .maxStale(60, TimeUnit.SECONDS)
                                .onlyIfCached()
                                .build())
                        .build();//两种方式结果是一样的，写法不同
//                        .header("Cache-Control", "no-cache")
//                        .build();
            }
        }
        return chain.proceed(request);
    }

    /**
     * @param offlineCacheTime 单位秒 离线的时候的缓存的过期时间
     */
    public void setOfflineCacheTime(int offlineCacheTime) {
        this.offlineCacheTime = offlineCacheTime;
    }
}
