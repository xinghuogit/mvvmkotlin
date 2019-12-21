package com.spark.myapplication.kotlin

import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.spark.myapplication.model.WeChatSubscription
import com.spark.myapplication.model.data
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
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
     * 1）在实际应用中（对数据不敏感）都需要我们先读取缓存的数据，如缓存无数据，再通过网络请求获取，随后在主线程更新我们的UI。
     * 2）concat操作符简直就是为我们这种需求量身定做。
     * 3）concat可以做到不交错的发射两个甚至多个Observable的发射事件，并且只有前一个Observable终止(onComplete())后才会定义下一个Observable。
     * 4）利用这个特性，我们就可以先读取缓存数据，倘若获取到的缓存数据不是我们想要的，再调用onComplete()以执行获取网络数据的Observable，如果缓存数据能应我们所需，则直接调用onNext()，防止过度的网络请求，浪费用户的流量。
     */
    fun moreNetWorkFlatMap(stringBuffer: StringBuffer, tv: TextView) {
        val url = "https://wanandroid.com/wxarticle/chapters/json  "
//        val url = "http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512"
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin moreNetWorkFlatMap")
        stringBuffer.append("\n")
        Observable.create(object : ObservableOnSubscribe<ArrayList<WeChatSubscription>> {
            override fun subscribe(emitter: ObservableEmitter<ArrayList<WeChatSubscription>>) {
                println("moreNetWorkFlatMap 线程：${Thread.currentThread().name}")
                stringBuffer.append("moreNetWorkFlatMap 线程：${Thread.currentThread().name}")
                val builder = Request.Builder()
                    .url(url)
                val request = builder.build()
                val call = OkHttpClient().newCall(request)
                val response = call.execute()
                if (response.isSuccessful) {
                    val body = response.body
                    if (body != null) {
                        val string = body.string()
                        println("moreNetWorkFlatMap 转前：${string}")
                        stringBuffer.append("moreNetWorkFlatMap 转前：${string}")
                        var data1 =
                            Gson().fromJson<data<ArrayList<WeChatSubscription>>>(
                                string,
                                object :
                                    TypeToken<data<ArrayList<WeChatSubscription>>>() {}.type
                            )
                        emitter.onNext(data1!!.data!!)
                    }
                }
            }
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(object : Consumer<ArrayList<WeChatSubscription>> {
                override fun accept(t: ArrayList<WeChatSubscription>?) {
                    println("doOnNext() 保存数据 ${t.toString()}")
                    stringBuffer.append("doOnNext() 保存数据")
                }
            })
            .observeOn(Schedulers.io())
            .flatMap(object :
                Function<ArrayList<WeChatSubscription>, ObservableSource<String>> {
                override fun apply(t: ArrayList<WeChatSubscription>): ObservableSource<String>? {
                    if (t != null) {
                        println("flatMap 线程：${Thread.currentThread().name}")
                        stringBuffer.append("flatMap 线程：${Thread.currentThread().name}")
                        val builder = Request.Builder()
                            .url("https://wanandroid.com/wxarticle/list/${t.get(2).id}/1/json")
                        val request = builder.build()
                        val call = OkHttpClient().newCall(request)
                        val response = call.execute()
                        if (response.isSuccessful) {
                            val body = response.body
                            if (body != null) {
                                val string = body.string()
                                println("flatMap 转前：${string}")
                                stringBuffer.append("flatMap 转前：${string}")
//                                var data1 =
//                                    Gson().fromJson<data<ArrayList<WeChatSubscription>>>(
//                                        string,
//                                        object :
//                                            TypeToken<data<ArrayList<WeChatSubscription>>>() {}.type
//                                    )
                                var list = ArrayList<String>()
                                list.add(string)
                                return Observable.fromIterable(list)
                            }
                        }
                    }
                    return null;
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<String> {
                override fun accept(t: String?) {
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


    /**
     * 1）在实际应用中（对数据不敏感）都需要我们先读取缓存的数据，如缓存无数据，再通过网络请求获取，随后在主线程更新我们的UI。
     * 2）concat操作符简直就是为我们这种需求量身定做。
     * 3）concat可以做到不交错的发射两个甚至多个Observable的发射事件，并且只有前一个Observable终止(onComplete())后才会定义下一个Observable。
     * 4）利用这个特性，我们就可以先读取缓存数据，倘若获取到的缓存数据不是我们想要的，再调用onComplete()以执行获取网络数据的Observable，如果缓存数据能应我们所需，则直接调用onNext()，防止过度的网络请求，浪费用户的流量。
     */
    fun cacheNetWork(stringBuffer: StringBuffer, tv: TextView) {
        val url = "https://www.wanandroid.com/banner/json"
        var isFormNet = false;
//        val url = "http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512"
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin cacheNetWork")
        stringBuffer.append("\n")

        var chche =
            Observable.create(object : ObservableOnSubscribe<WeChatSubscription> {
                override fun subscribe(emitter: ObservableEmitter<WeChatSubscription>) {
                    println("chche 线程：${Thread.currentThread().name}")
                    stringBuffer.append("chche 线程：${Thread.currentThread().name}")
                    var mWeChatSubscriptions: WeChatSubscription? = null
//                mWeChatSubscription.name = "123"

                    if (mWeChatSubscriptions != null) {
                        isFormNet = false
                        emitter.onNext(mWeChatSubscriptions)
                        println("chche isFormNet：${isFormNet}")
                        stringBuffer.append("chche isFormNet：${isFormNet}")
                    } else {
                        isFormNet = true
                        emitter.onComplete()
                        println("chche isFormNet：${isFormNet}")
                        stringBuffer.append("chche isFormNet：${isFormNet}")
                    }
                }
            })

        var network =
            Observable.create(object : ObservableOnSubscribe<WeChatSubscription> {
                override fun subscribe(emitter: ObservableEmitter<WeChatSubscription>) {
                    println("network 线程：${Thread.currentThread().name}")
                    stringBuffer.append("network 线程：${Thread.currentThread().name}")
                    val builder = Request.Builder()
                        .url(url)
                    val request = builder.build()
                    val call = OkHttpClient().newCall(request)
                    val response = call.execute()
                    if (response.isSuccessful) {
                        val body = response.body
                        if (body != null) {
                            val string = body.string()
                            println("network 转前：${string}")
                            stringBuffer.append("network 转前：${string}")
                            emitter.onNext(
                                Gson().fromJson<WeChatSubscription>(
                                    string,
                                    WeChatSubscription::class.java
                                )
                            )
//                            var data1 = Gson().fromJson<ArrayList<data>>(
//                                string,
//                                object : TypeToken<ArrayList<data>>() {}.type
//                            )
//                            emitter.onNext(data1.data)
                        }
                    }
                }
            })
        Observable.concat(chche, network)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<WeChatSubscription> {
                override fun accept(t: WeChatSubscription?) {
                    if (isFormNet) {
                        println("network 成功 ${t.toString()}")
                        stringBuffer.append("network 成功 ${t.toString()}")
                    } else {
                        println("chche 成功 ${t.toString()}")
                        stringBuffer.append("chche 成功 ${t.toString()}")
                    }
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

    /**
     * 1）通过Observable.create()方法，调用OkHttp网络请求；
     * 2）通过map操作符集合gson，将Response转换为bean类；
     * 3）通过doOnNext()方法，解析bean中的数据，并进行数据库存储等操作；
     * 4）调度线程，在子线程中进行耗时操作任务，在主线程中更新UI；
     * 5）通过subscribe()，根据请求成功或者失败来更新UI。
     */
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
                //通过doOnNext()方法，解析bean中的数据，并进行数据库存储等操作；
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
