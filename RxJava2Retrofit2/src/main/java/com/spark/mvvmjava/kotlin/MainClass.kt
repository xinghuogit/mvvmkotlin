package com.spark.mvvmjava.kotlin

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.io.BufferedReader
import java.io.FileReader

/*************************************************************************************************
 * 日期：2019/12/19 16:10
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
object MainClass {

    @JvmStatic
    fun main(args: Array<String>) {
        flowable()
        Thread.sleep(10000000)
//        test1()
//        test2()
//        testMap()
//        testFlatMap()
//        testZip()
    }

    fun flowable() {
        Flowable.create(object : FlowableOnSubscribe<String> {
            override fun subscribe(emitter: FlowableEmitter<String>) {
//                println("1")
                var fileReader = FileReader("E:\\work\\沁园春·长沙.txt");
                var br = BufferedReader(fileReader)
                var line: String
                println("2")
                while (true) {
                    line = br.readLine() ?: break
//                    println("br.readLine()${line}")
                    println("emitter.requested()${emitter.requested()}")
//                    while (!emitter.isCancelled) {
//                        while (emitter.requested() == 0L) {
//                            if (emitter.isCancelled) break
//                        }
//                        println("emitter$line")
//                        line = br.readLine()
                    println("str:$line")
                    emitter.onNext(line)
//                    }
                }
//                br.readLine().forEach { it ->
//                    while (!emitter.isCancelled) {
//                        while (emitter.requested() == 0L) {
//                            if (emitter.isCancelled) break
//                        }
//                        emitter.onNext(it)
//                    }
//                }
                br.close()
                fileReader.close()
                emitter.onComplete()
            }
        }, BackpressureStrategy.ERROR)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribe(object : Subscriber<String> {
                override fun onComplete() {
                    println("onComplete()");
                }

                override fun onSubscribe(s: Subscription) {
                    println("onSubscribe()");
                    mSubscription = s;
                    s.request(1)
                }

                override fun onNext(t: String) {
                    println("$t");
                    Thread.sleep(2000)
                    if (mSubscription != null) mSubscription!!.request(1)
                }

                override fun onError(t: Throwable?) {
                    println(t);
                }
            })
//        Flowable.just(1, 2, 3, 4)
//            .reduce(100, object : BiFunction<Int, Int, Int> {
//                override fun apply(t1: Int, t2: Int): Int {
//                    return (t1 + t2)
//                }
//            }).subscribe(object : Consumer<Int> {
//                override fun accept(t: Int) {
//                    println("accept()$t")
//                }
//            })
    }

    var mSubscription: Subscription? = null
}