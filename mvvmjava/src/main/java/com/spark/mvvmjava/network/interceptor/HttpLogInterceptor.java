package com.spark.mvvmjava.network.interceptor;

import android.text.TextUtils;

import com.library.common.utils.DateUtils;
import com.library.common.utils.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/*************************************************************************************************
 * 日期：2020/1/13 14:21
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：拦截网络数据并且打印网络日志
 ************************************************************************************************/
public class HttpLogInterceptor implements Interceptor {
    private static final String TAG = "HttpLogInterceptor";

    private final Charset UTF8 = Charset.forName("UTF-8");

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        Request request = chain.request();
        RequestBody requestBody = request.body();
        String body = "";
        try {
            if (requestBody != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                body = buffer.clone().readString(charset);
                if (!TextUtils.isEmpty(body)) {
                    //如果是图片上传调用URLDecoder会报错，即使tryCache都没用，what!!!
                    String netUrl = request.url().toString();
                    body = URLDecoder.decode(body, "utf-8");
                }
            }
        } catch (IOException e) {
            LogUtils.INSTANCE.i(TAG, "上传文件或者，下载文件");
        }
        stringBuffer.append("请求时间：");
        stringBuffer.append(DateUtils.INSTANCE.getTimeStampToDateTime(System.currentTimeMillis()) + "  System.currentTimeMillis()：" + System.currentTimeMillis());
        stringBuffer.append("\n请求方式：");
        stringBuffer.append(request.method());
        stringBuffer.append("\n请求Urls：");
        stringBuffer.append(request.url());
        stringBuffer.append("\n请求头部：");
        stringBuffer.append(request.headers());
        stringBuffer.append("\n请求参数：");
        stringBuffer.append(body);

        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        String rBody;

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.getBuffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }
        rBody = buffer.clone().readString(charset);
        try {
            if (!TextUtils.isEmpty(rBody)) {
                rBody = decodeUnicode(rBody);
            }
        } catch (Exception e) {
            rBody = "";
            LogUtils.INSTANCE.i(TAG, "上传文件或者，下载文件");
        }
        stringBuffer.append("\n响应时间：");
        stringBuffer.append(DateUtils.INSTANCE.getTimeStampToDateTime(System.currentTimeMillis()) + "  System.currentTimeMillis()：" + System.currentTimeMillis());
        stringBuffer.append("\n收到响应：");
        stringBuffer.append(response.code());
        stringBuffer.append("\nResponse：");
        stringBuffer.append(rBody);
        LogUtils.INSTANCE.i(TAG, stringBuffer.toString());
        return response;
    }

    /**
     * http请求数据返回json中,中文字符为unicode编码转汉字转码
     *
     * @param str
     * @return 转化后的结果.
     */
    public static String decodeUnicode(String str) {
        char aChar;
        int len = str.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int i = 0; i < len; ) {
            aChar = str.charAt(i++);
            if (aChar == '\\') {
                aChar = str.charAt(i++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int j = 0; j < 4; j++) {
                        aChar = str.charAt(i++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }
}
