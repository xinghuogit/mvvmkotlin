package com.spark.myapplication.kotlin

import android.annotation.SuppressLint
import android.widget.TextView
import com.google.gson.Gson
import com.spark.myapplication.model.WeChatSubscription
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

/*************************************************************************************************
 * 日期：2019/12/13 17:23
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 */
class RetrofitToKotlin {
    /**
     * 1）通过Observable.create()方法，调用OkHttp网络请求；
     * 2）通过map操作符集合gson，将Response转换为bean类；
     * 3）通过doOnNext()方法，解析bean中的数据，并进行数据库存储等操作；
     * 4）调度线程，在子线程中进行耗时操作任务，在主线程中更新UI；
     * 5）通过subscribe()，根据请求成功或者失败来更新UI。
     */
    @SuppressLint("CheckResult")
    fun simple(stringBuffer: StringBuffer, tv: TextView) {
        val url = "https://wanandroid.com/wxarticle/chapters/json"
//        val url = "http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512"
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin create")
        stringBuffer.append("\n")
        Observable.create(object : ObservableOnSubscribe<Response> {
            override fun subscribe(emitter: ObservableEmitter<Response>) {
                val builder = Request.Builder()
                    .url(url)
                val request = builder.build()
                val call = OkHttpClient().newCall(request)
                val response = call.execute()
                emitter.onNext(response)
            }
        })
            .map(object : Function<Response, WeChatSubscription> {
                override fun apply(t: Response): WeChatSubscription? {
                    println("map 线程：${Thread.currentThread().name}")
                    stringBuffer.append("map 线程：${Thread.currentThread().name}")
                    if (t.isSuccessful) {
                        val body = t.body
                        if (body != null) {
                            val string = body.string()
                            println("map 转前：${string}")
                            stringBuffer.append("map 转前：${string}")
                            return Gson().fromJson(string, WeChatSubscription::class.java)
                        }
                    }
                    return null;
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(object : Consumer<WeChatSubscription> {
                override fun accept(t: WeChatSubscription?) {
                    println("doOnNext() 保存数据 ${t.toString()}")
                    stringBuffer.append("doOnNext() 保存数据")
//                    if (tv != null) tv.text = "doOnNext() 保存数据"
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<WeChatSubscription> {
                override fun accept(t: WeChatSubscription?) {
                    println("成功 ${t.toString()}")
                    stringBuffer.append("成功 ${t.toString()}")
                    tv.text = stringBuffer.toString()
                }
            }, object : Consumer<Throwable> {
                override fun accept(t: Throwable?) {
                    if (t != null) {
                        println("失败 ${t.message}")
                        stringBuffer.append("失败 ${t.message}")
                    } else {
                        println("失败")
                        stringBuffer.append("失败")
                    }
                    tv.text = stringBuffer.toString()
                }
            })
    }
}
