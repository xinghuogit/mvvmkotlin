package com.spark.mvvmjava.network.interceptor;

import android.text.TextUtils;

import com.library.common.utils.SPUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/*************************************************************************************************
 * 日期：2020/1/13 14:21
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：* 在有网络的情况下
 *  * 如果还在网络有效期呢则取缓存，否则请求网络
 *  * 重点 : 一般okhttp只缓存不大改变的数据适合get。（个人理解 : 例如你设置了我的方案列表接口的缓存后，你删除了一条方案，刷新下。
 *  * 他取的是缓存，结果那条删除的数据会出来。这个时候这个接口，不适合用缓存了）
 *  * (这里注意，如果一个接口设置了缓存30秒，下次请求这个接口的30秒内都会去取缓存，即使你设置0也不起效。因为缓存文件里的标识里已经有30秒的有效期)
 ************************************************************************************************/
public class NetCacheInterceptor implements Interceptor {
    private static final String TAG = "NetCacheInterceptor";

    private static NetCacheInterceptor netCacheInterceptor;

    //30在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
    private int onlineCacheTime;

    private NetCacheInterceptor() {
    }

    public static NetCacheInterceptor getInstance() {
        if (netCacheInterceptor == null) {
            synchronized (NetCacheInterceptor.class) {
                if (netCacheInterceptor == null) {
                    netCacheInterceptor = new NetCacheInterceptor();
                }
            }
        }
        return netCacheInterceptor;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String token = SPUtils.getInstance().getString("UserToken", "");
        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("Token", token).build();
        }
        request = builder.build();
        Response response = chain.proceed(request);
        List<String> list = response.headers().values("Token");
        if (list.size() > 0) {
            SPUtils.getInstance().putString("UserToken", list.get(0));
        }
        //如果有时间就设置缓存
        if (onlineCacheTime != 0) {
            int temp = onlineCacheTime;
            Response response1 = response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + temp)
                    .removeHeader("Pragma")
                    .build();
            onlineCacheTime = 0;
            return response1;
        } else {//如果没有时间就不缓存
            Response response1 = response.newBuilder()
                    .header("Cache-Control", "no-cache")
                    .removeHeader("Pragma")
                    .build();
            return response1;
        }
    }

    /**
     * @param onlineCacheTime 单位秒 30秒在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
     */
    public void setOnlineCacheTime(int onlineCacheTime) {
        this.onlineCacheTime = onlineCacheTime;
    }
}
