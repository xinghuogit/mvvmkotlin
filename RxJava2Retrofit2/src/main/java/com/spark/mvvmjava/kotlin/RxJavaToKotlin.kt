package com.spark.mvvmjava.kotlin

import com.library.common.utils.DateUtils
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.io.BufferedReader
import java.io.FileReader
import java.io.Serializable
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


/*************************************************************************************************
 * 日期：2019/12/13 17:23
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 */
class RxJavaToKotlin {
    fun flowable() {
        Flowable.create(object : FlowableOnSubscribe<String> {
            override fun subscribe(emitter: FlowableEmitter<String>) {
                try {
                    var fileReader = FileReader("E:\\work\\沁园春·长沙.txt");
                    var br = BufferedReader(fileReader)
//                    var str = ""
                    br.use {
                        while (!emitter.isCancelled) {
                            var str = br.readText().toString()
                            while (emitter.requested() == 0L) {
                                if (emitter.isCancelled) break
                            }
                            emitter.onNext(str)
                        }
                    }
                    br.close()
                    fileReader.close()
                    emitter.onComplete()
                } catch (e: Exception) {
                    emitter.onError(e)
                    e.printStackTrace()
                }
            }
        }, BackpressureStrategy.ERROR)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribe(object : Subscriber<String> {
                override fun onComplete() {
                    println("onComplete()");
                }

                override fun onSubscribe(s: Subscription) {
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

    fun request() {
        mSubscription!!.request(96)
    }

    /**
     * Flowable专用于解决背压问题
     */
    fun flowable(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin flowable 查看log")
        stringBuffer.append("\n")
        Flowable.create(object : FlowableOnSubscribe<Int> {
            override fun subscribe(emitter: FlowableEmitter<Int>) {
//                emitter.onNext(1)
//                emitter.onNext(2)
//                emitter.onNext(23)
//                var i = 0
//                while (i < Long.MAX_VALUE) {
//                    println("emitter$i")
//                    emitter.onNext(i)
//                    i++
//                }
                var flag = false
                var i = 0
                while (i < Long.MAX_VALUE) {
                    flag = false
                    while (emitter.requested() == 0L) {
                        if (!flag) {
                            flag = true
                            println("没有数据了")
                        }
                    }
                    println("emitter:${i}       emitter.requested():${emitter.requested()}")
                    emitter.onNext(i)
                    i++
                }

//                println(emitter.requested())
//                println("Long.MAX_VALUE${Long.MAX_VALUE}")
            }
        }, BackpressureStrategy.ERROR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<Int> {
                override fun onComplete() {
                    stringBuffer.append("onComplete()")
                    stringBuffer.append("\n")
                }

                override fun onSubscribe(s: Subscription) {
                    mSubscription = s;
                    stringBuffer.append("onSubscribe()${s != null}")
                    stringBuffer.append("\n")
//                    s.request(1)
//                    s.request(2)
//                    s!!.request(Long.MAX_VALUE)
                }

                override fun onNext(t: Int?) {
                    println("onNext$t");
                    stringBuffer.append("onNext()$t")
                    stringBuffer.append("\n")
                }

                override fun onError(t: Throwable?) {
                    println(t);
                    stringBuffer.append("onError()${t!!.message}")
                    stringBuffer.append("\n")
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
        return stringBuffer.toString()
    }

    /**
     * Completable只关心结果，也就是说Completable是没有onNext的，要么成功要么出错，不关心过程，在subscribe后的某个时间点返回结果。
     */
    fun completable(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin completable")
        stringBuffer.append("\n")
        Completable.timer(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    stringBuffer.append("onComplete()")
                    stringBuffer.append("\n")
                }

                override fun onSubscribe(d: Disposable) {
                    stringBuffer.append("onSubscribe()${d.isDisposed}")
                    stringBuffer.append("\n")
                }

                override fun onError(e: Throwable) {
                    stringBuffer.append("onSubscribe()${e.message}")
                    stringBuffer.append("\n")
                }
            })
        return stringBuffer.toString()
    }

    /**
     * BehaviorSubject的最后一次onNext()操作会被缓存，然后在subscribe()后立刻推给新注册的Observer。
     */
    fun behaviorSubject(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin behaviorSubject")
        stringBuffer.append("\n")
        var behaviorSubject = BehaviorSubject.create<Int>();
        behaviorSubject.subscribe(object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                stringBuffer.append("First onSubscribe()${d.isDisposed}")
                stringBuffer.append("\n")
            }

            override fun onNext(t: Int) {
                stringBuffer.append("First onNext()$t")
                stringBuffer.append("\n")
            }

            override fun onError(e: Throwable) {
                stringBuffer.append("First onError()${e.message}")
                stringBuffer.append("\n")
            }

            override fun onComplete() {
                stringBuffer.append("First onComplete()")
                stringBuffer.append("\n")
            }
        })
        behaviorSubject.onNext(1)
        behaviorSubject.onNext(4)

        behaviorSubject.subscribe(object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                stringBuffer.append("Second onSubscribe()${d.isDisposed}")
                stringBuffer.append("\n")
            }

            override fun onNext(t: Int) {
                stringBuffer.append("Second onNext()$t")
                stringBuffer.append("\n")
            }

            override fun onError(e: Throwable) {
                stringBuffer.append("Second onError()${e.message}")
                stringBuffer.append("\n")
            }

            override fun onComplete() {
                stringBuffer.append("Second onComplete()")
                stringBuffer.append("\n")
            }
        })
        behaviorSubject.onNext(5)
        behaviorSubject.onNext(7)
        behaviorSubject.onComplete()
        return stringBuffer.toString()
    }

    /**
     * 在调用onComplete()之前，除了subscribe()其它的操作都会被缓存，
     * 在调用onComplete()之后只有最后一个onNext()会生效。
     */
    fun asyncSubject(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin asyncSubject")
        stringBuffer.append("\n")
        var asyncSubject = AsyncSubject.create<Int>();
        asyncSubject.subscribe(object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                stringBuffer.append("First onSubscribe()${d.isDisposed}")
                stringBuffer.append("\n")
            }

            override fun onNext(t: Int) {
                stringBuffer.append("First onNext()$t")
                stringBuffer.append("\n")
            }

            override fun onError(e: Throwable) {
                stringBuffer.append("First onError()${e.message}")
                stringBuffer.append("\n")
            }

            override fun onComplete() {
                stringBuffer.append("First onComplete()")
                stringBuffer.append("\n")
            }
        })
        asyncSubject.onNext(1)
        asyncSubject.onNext(4)

        asyncSubject.subscribe(object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                stringBuffer.append("Second onSubscribe()${d.isDisposed}")
                stringBuffer.append("\n")
            }

            override fun onNext(t: Int) {
                stringBuffer.append("Second onNext()$t")
                stringBuffer.append("\n")
            }

            override fun onError(e: Throwable) {
                stringBuffer.append("Second onError()${e.message}")
                stringBuffer.append("\n")
            }

            override fun onComplete() {
                stringBuffer.append("Second onComplete()")
                stringBuffer.append("\n")
            }
        })
        asyncSubject.onNext(5)
        asyncSubject.onNext(7)
        asyncSubject.onComplete()
        return stringBuffer.toString()
    }

    /**
     * onNext() 会通知每个观察者，仅此而已
     */
    fun publishSubject(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin publishSubject")
        stringBuffer.append("\n")
        var publishSubject = PublishSubject.create<Int>();
        publishSubject.subscribe(object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                stringBuffer.append("First onSubscribe()${d.isDisposed}")
                stringBuffer.append("\n")
            }

            override fun onNext(t: Int) {
                stringBuffer.append("First onNext()$t")
                stringBuffer.append("\n")
            }

            override fun onError(e: Throwable) {
                stringBuffer.append("First onError()${e.message}")
                stringBuffer.append("\n")
            }

            override fun onComplete() {
                stringBuffer.append("First onComplete()")
                stringBuffer.append("\n")
            }
        })
        publishSubject.onNext(1)
        publishSubject.onNext(4)

        publishSubject.subscribe(object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                stringBuffer.append("Second onSubscribe()${d.isDisposed}")
                stringBuffer.append("\n")
            }

            override fun onNext(t: Int) {
                stringBuffer.append("Second onNext()$t")
                stringBuffer.append("\n")
            }

            override fun onError(e: Throwable) {
                stringBuffer.append("Second onError()${e.message}")
                stringBuffer.append("\n")
            }

            override fun onComplete() {
                stringBuffer.append("Second onComplete()")
                stringBuffer.append("\n")
            }
        })
        publishSubject.onNext(5)
        publishSubject.onNext(7)
        publishSubject.onComplete()
        return stringBuffer.toString()
    }


    /**
     * 按照实际划分窗口，将数据发送给不同的Observable。
     * 每个数据间隔2秒，最多接收5个数据，每个window间隔3秒
     */
    fun window(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin window 查看log")
        stringBuffer.append("\n")
        Observable.interval(2, TimeUnit.SECONDS)//间隔2秒
            .take(5) // 最多5个
            .window(3, TimeUnit.SECONDS)//每个间隔3秒
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<Observable<Long>> {
                override fun accept(t: Observable<Long>?) {
                    println("window")
                    t!!.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Consumer<Long> {
                            override fun accept(t: Long?) {
                                println("accept()：$t")
                            }
                        })
                }
            })
        return stringBuffer.toString()
    }

    /**
     * scan操作符作用和上面的reduce一致，唯一区别是reduce是个只追求结果的坏人，而scan会始终如一地把每一个步骤都输出。
     * scan操作符每次用一个方法处理一个值，可以有一个seed作为初始值。
     * 可以看到，代码中，我们中间采用scan，支持一个 function 为两数值相加，
     * 所以应该最后的值是：1 = 1，1 + 3 = 4 ，4 + 4 = 8 而Log 日志完美解决了我们的问题。
     */
    fun scan(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin scan")
        stringBuffer.append("\n")
        Observable.just(1, 3, 4)
            .scan(object : BiFunction<Int, Int, Int> {
                override fun apply(t1: Int, t2: Int): Int {
                    return t1 + t2
                }
            })
            .subscribe(object : Consumer<Int> {
                override fun accept(t: Int?) {
                    stringBuffer.append("accept()$t")
                    stringBuffer.append("\n")
                }
            })
        return stringBuffer.toString()
    }

    /**
     * reduce操作符每次用一个方法处理一个值，可以有一个seed作为初始值。
     * 可以看到，代码中，我们中间采用 reduce ，支持一个 function 为两数值相加，
     * 所以应该最后的值是：1 + 3 = 4 + 4 = 8 ， 而Log 日志完美解决了我们的问题。
     */
    fun reduce(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin reduce")
        stringBuffer.append("\n")
        Observable.just(1, 3, 4)
            .reduce(object : BiFunction<Int, Int, Int> {
                override fun apply(t1: Int, t2: Int): Int {
                    return t1 + t2
                }
            }).subscribe(object : Consumer<Int> {
                override fun accept(t: Int?) {
                    stringBuffer.append("accept()$t")
                    stringBuffer.append("\n")
                }
            })
        return stringBuffer.toString()
    }

    /**
     * merge顾名思义，熟悉版本控制工具的你一定不会不知道merge命令，
     * 而在Rx操作符中，merge的作用是把多个Observable结合起来，接受可变参数，也支持迭代器集合。
     * 注意它和concat的区别在于，不用等到发射器A发送完所有的事件再进行发射器B的发送。
     */
    fun merge(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin merge")
        stringBuffer.append("\n")
        Observable.merge(Observable.just(1, 3), Observable.just(6, 8))
            .subscribe(object : Consumer<Int> {
                override fun accept(t: Int?) {
                    stringBuffer.append("accept()$t")
                    stringBuffer.append("\n")
                }
            })
        return stringBuffer.toString()
    }

    /**
     * last操作符仅取出可观察到的最后一个值，或者是满足某些条件的最后一项。
     */
    fun last(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin last")
        stringBuffer.append("\n")
        Observable.just(1, 3, 7)
            .last(4)
            .subscribe(object : Consumer<Int> {
                override fun accept(t: Int?) {
                    stringBuffer.append("accept()$t")
                    stringBuffer.append("\n")
                }
            })
        return stringBuffer.toString()
    }

    /**
     * 简单地时候就是每次订阅都会创建一个新的Observable，并且如果没有被订阅，就不会产生新的Observable。
     */
    fun defer(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin defer")
        stringBuffer.append("\n")
        var observable = Observable.defer(object : Callable<ObservableSource<Int>> {
            override fun call(): ObservableSource<Int> {
                return Observable.just(1, 3, 4)
            }
        })
        observable.subscribe(object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                stringBuffer.append("onSubscribe()${d.isDisposed}")
                stringBuffer.append("\n")
            }

            override fun onNext(t: Int) {
                stringBuffer.append("onNext()$t")
                stringBuffer.append("\n")
            }

            override fun onError(e: Throwable) {
                stringBuffer.append("onError()${e.message}")
                stringBuffer.append("\n")
            }

            override fun onComplete() {
                stringBuffer.append("onComplete()")
                stringBuffer.append("\n")
            }
        })
        return stringBuffer.toString()
    }

    /**
     * debounce去除发送频率过快的项，看起来好像没啥用处，但你信我，后面绝对有地方很有用武之地。过滤了350秒的，查看log
     */
    fun debounce(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin debounce")
        stringBuffer.append("\n")
        stringBuffer.append("除发送间隔时间小于350毫秒的发射事件，所以2/3/4被去掉了，查看log")
        stringBuffer.append("\n")
        Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
            Thread.sleep(400)
            it.onNext(2)
            Thread.sleep(300)
            it.onNext(3)
            Thread.sleep(100)
            it.onNext(4)
            Thread.sleep(350)
            it.onNext(5)
            Thread.sleep(410)
//            it.onComplete()
        }).debounce(350, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<Int> {
                override fun accept(t: Int?) {
                    println(t)
                }
            })
        return stringBuffer.toString()
    }

    /**
     * 顾名思义，Single只会接收一个参数，而SingleObserver只会调用onError()或者onSuccess()。
     */
    fun single(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin single")
        stringBuffer.append("\n")
        Single.just(Random().nextInt()).subscribe(object : SingleObserver<Int> {
            override fun onSubscribe(d: Disposable) {
                stringBuffer.append("onSubscribe()${d.isDisposed}")
                stringBuffer.append("\n")
            }

            override fun onSuccess(t: Int) {
                stringBuffer.append("onSuccess()${t}")
                stringBuffer.append("\n")
            }

            override fun onError(e: Throwable) {
                stringBuffer.append("onError()${e.message}")
                stringBuffer.append("\n")
            }
        })
        return stringBuffer.toString()
    }

    /**
     * just，没什么好说的，其实在前面各种例子都说明了，就是一个简单的发射器依次调用onNext()方法。
     */
    fun just(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin just")
        stringBuffer.append("\n")
        Observable.just("1", "2", "3")
            .subscribe(object : Consumer<String> {
                override fun accept(t: String?) {
                    stringBuffer.append("accept()$t")
                    stringBuffer.append("\n")
                }
            })
        return stringBuffer.toString()
    }

    /**
     * take，接受一个long型参数count，代表至多接收count个数据。
     */
    fun take(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin take 显示前面2个")
        stringBuffer.append("\n")
        Observable.just(1, 2, 3, 4, 5)
            .take(2)
            .subscribe(object : Consumer<Int> {
                override fun accept(t: Int?) {
                    stringBuffer.append("accept()$t")
                    stringBuffer.append("\n")
                }
            })
        return stringBuffer.toString()
    }

    /**
     * skip很有意思，其实作用就和字面意思一样，接受一个long型参数count，代表跳过count个数目开始接收。
     */
    fun skip(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin skip 跳过4个 直接显示5")
        stringBuffer.append("\n")
        Observable.just(1, 2, 3, 4, 5)
            .skip(4)//跳过4个 直接显示5
            .subscribe(object : Consumer<Int> {
                override fun accept(t: Int?) {
                    stringBuffer.append("accept()$t")
                    stringBuffer.append("\n")
                }
            })
        return stringBuffer.toString()
    }

    /**
     * 其实觉得 doOnNext 应该不算一个操作符，但考虑到其常用性，我们还是咬咬牙将它放在了这里。
     * 它的作用是让订阅者在接收到数据之前干点有意思的事情。假如我们在获取到数据之前想先保存一下它，无疑我们可以这样实现。
     */
    fun doOnNext(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin doOnNext")
        stringBuffer.append("\n")
        Observable.just(1, 2, 3)
            .doOnNext(object : Consumer<Int> {
                override fun accept(t: Int?) {
                    stringBuffer.append("保存成功i$t")
                    stringBuffer.append("\n")
                }
            }).subscribe(object : Consumer<Int> {
                override fun accept(t: Int?) {
                    stringBuffer.append("accept()$t")
                    stringBuffer.append("\n")
                }
            })
        return stringBuffer.toString()
    }

    /**
     * 如同我们上面可说，interval操作符用于间隔时间执行某个操作，其接受三个参数，分别是第一次发送延迟，间隔时间，时间单位。
     * 如同Log日志一样，第一次延迟了2秒后接收到，后面每次间隔了3秒。
     * 然而，心细的小伙伴可能会发现，由于我们这个是间隔执行，所以当我们的Activity都销毁的时候，
     * 实际上这个操作还依然在进行，所以，我们得花点小心思让我们在不需要它的时候干掉它。
     * 查看源码发现，我们subscribe(Cousumer<?superT>onNext)返回的是Disposable，我们可以在这上面做文章。
     */
    var disposableInterval: Disposable? = null;

    fun interval(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin interval")
        stringBuffer.append("2秒后见log日志,当前：${DateUtils.getTimeStampToDateTime(System.currentTimeMillis())}")
        stringBuffer.append("\n")
        var i: Int = 0
        disposableInterval = Observable.interval(2, 3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<Long> {
                override fun accept(t: Long?) {
                    i++
                    println("3秒后：${DateUtils.getTimeStampToDateTime(System.currentTimeMillis())}")
                    if (i == 2 && disposableInterval != null) {
                        disposableInterval!!.dispose()
                    }
                    println("mDisposable!!.isDisposed：${disposableInterval!!.isDisposed}")
                }
            })
        return stringBuffer.toString()
    }

    /**
     * timer很有意思，相当于一个定时任务。在1.x中它还可以执行间隔逻辑，但在2.x中此功能被交给了interval，下一个会介绍。
     * 但需要注意的是，timer和interval均默认在新线程。
     */
    fun timer(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin timer")
        stringBuffer.append("\n")
        stringBuffer.append("两秒后见log日志,当前：${DateUtils.getTimeStampToDateTime(System.currentTimeMillis())}")
        stringBuffer.append("\n")
        Observable.timer(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<Long> {
                override fun accept(t: Long?) {
                    println("两秒后：${DateUtils.getTimeStampToDateTime(System.currentTimeMillis())}")
                }
            })
        return stringBuffer.toString()
    }

    /**
     * @buffer 如图，我们把1,2,3,4,5依次发射出来，经过buffer操作符，
     * 其中参数skip为4，count为4，而我们的输出依次是1234，5。显而易见，
     * 我们buffer的第一个参数是count，代表最大取值，在事件足够的时候，一般都是取count个值，
     * 然后每次跳过skip个事件。其实看Log日志，我相信大家都明白了。
     */
    fun buffer(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin buffer")
        stringBuffer.append("\n")
        Observable.just(1, 2, 3, 4, 5)
            .buffer(4, 4) //skip每次跳过几个步长  count每次最多几个
            .subscribe(object : Consumer<List<Int>> {
                override fun accept(t: List<Int>?) {
                    if (t != null) {
                        stringBuffer.append("accept()t.size${t.size}")
                        stringBuffer.append("\n")
                        for (i in t) {
                            stringBuffer.append("accept()i$i")
                            stringBuffer.append("\n")
                        }
                    }
                }
            })
        return stringBuffer.toString()
    }


    /**
     * @filter 可以看到，我们过滤器舍去了小于 10 的值，所以最好的输出只有 10, 25。
     */
    fun filter(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin filter")
        stringBuffer.append("\n")
        Observable.just(1, 10, -65, 25)
            .filter(object : Predicate<Int> {
                override fun test(t: Int): Boolean {
                    return t >= 10//过滤小于10的
                }
            }).subscribe(object : Consumer<Int> {
                override fun accept(t: Int?) {
                    stringBuffer.append("accept()$t")
                    stringBuffer.append("\n")
                }
            })
        return stringBuffer.toString()
    }

    /**
     * @distinct Log 日志显而易见，我们在经过 dinstinct() 后接收器接收到的事件只有1,2,3了。
     */
    fun distinct(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin distinct")
        stringBuffer.append("\n")
        Observable.just(1, 1, 2, 3, 3)
            .distinct()
            .subscribe(object : Consumer<Int> {
                override fun accept(t: Int?) {
                    stringBuffer.append("accept()$t")
                    stringBuffer.append("\n")
                }
            })
        return stringBuffer.toString()
    }

    /**
     * @concatMap 上面其实就说了，concatMap与FlatMap的唯一区别就是concatMap保证了顺序，
     * 所以，我们就直接把flatMap替换为concatMap验证吧。
     */
    fun concatMap(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin concatMap")
        stringBuffer.append("\n")

        Observable.create(ObservableOnSubscribe<Int> {
            stringBuffer.append("emitter 1")
            stringBuffer.append("\n")
            it.onNext(1)
            stringBuffer.append("emitter 2")
            stringBuffer.append("\n")
            it.onNext(2)
            stringBuffer.append("emitter 3")
            stringBuffer.append("\n")
            it.onNext(3)
            stringBuffer.append("emitter onComplete()")
            stringBuffer.append("\n")
        }).concatMap(object : Function<Int, ObservableSource<String>> {
            override fun apply(t: Int): ObservableSource<String> {
                var list = ArrayList<String>()
                for (i in 0..2) {
                    stringBuffer.append("this is result $t")
                    stringBuffer.append("\n")
                    list.add("this is result $t")
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS)
            }
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<String> {
                override fun accept(t: String?) {
                    stringBuffer.append("accept()$t")
                    stringBuffer.append("\n")
                }
            })
        return stringBuffer.toString()
    }


    /**
     * @flatMap FlatMap是一个很有趣的东西，我坚信你在实际开发中会经常用到。
     * 它可以把一个发射器Observable通过某种方法转换为多个Observables，
     * 然后再把这些分散的Observables装进一个单一的发射器Observable。
     * 但有个需要注意的是，flatMap并不能保证事件的顺序，如果需要保证，需要用到我们下面要讲的ConcatMap。
     * <p>
     * 一切都如我们预期中的有意思，为了区分concatMap（下一个会讲），我在代码中特意动了一点小手脚，我采用一个随机数，
     * 生成一个时间，然后通过delay（后面会讲）操作符，做一个小延时操作，而查看Log日志也确认验证了我们上面的说法，它是无序的。
     */
    fun flatMap(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin flatMap")
        stringBuffer.append("\n")
        Observable.create(ObservableOnSubscribe<Int> {
            stringBuffer.append("emitter 1")
            stringBuffer.append("\n")
            it.onNext(1)
            stringBuffer.append("emitter 2")
            stringBuffer.append("\n")
            it.onNext(2)
            stringBuffer.append("emitter 3")
            stringBuffer.append("\n")
            it.onNext(3)
//            stringBuffer.append("emitter onComplete()")
//            stringBuffer.append("\n")

            var i = 4
            while (true) {   //无限循环发事件
                it.onNext(i)
                i++
                println(i)
            }
        }).flatMap(object : Function<Int, ObservableSource<String>> {
            override fun apply(t: Int): ObservableSource<String> {
                var list = ArrayList<String>();
                for (i in 0..2) {
                    stringBuffer.append("this is result $t")
                    stringBuffer.append("\n")
                    list.add("this is result $t")
                }
                var delayTime: Long = (1 + Math.random() * 10).toLong()
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS)
            }
        }).subscribeOn(Schedulers.newThread())//上游发送事件的线程 指定第一个有效,多次无效
            .observeOn(AndroidSchedulers.mainThread())//下游接收事件的线程 下游可以改变线程
            .subscribe(object : Consumer<String> {
                override fun accept(t: String?) {
                    stringBuffer.append("accept()$t")
                    stringBuffer.append("\n")
                    println("accept():$t");
                }
            })
        return stringBuffer.toString()
    }

    /**
     * @concat 对于单一的把两个发射器连接成一个发射器，虽然zip不能完成，但我们还是可以自力更生，
     * 官方提供的concat让我们的问题得到了完美解决。
     * 如图，可以看到。发射器B把自己的三个孩子送给了发射器A，让他们组合成了一个新的发射器，非常懂事的孩子，有条不紊的排序接收。
     */
    fun concat(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin concat")
        stringBuffer.append("\n")

        Observable.concat<Serializable>(
            Observable.just(1, 2, 3)
            , Observable.just(4, 5, 6, 7)
            , Observable.just("8", "9")
        )
            .subscribe(object : Consumer<Serializable> {
                override fun accept(t: Serializable?) {
                    stringBuffer.append("accept()$t")
                    stringBuffer.append("\n")
                }
            })
        return stringBuffer.toString()
    }

    /**
     * @zip zip专用于合并事件，该合并不是连接（连接操作符后面会说），而是两两配对，也就意味着，
     * 最终配对出的Observable发射事件数目只和少的那个相同。
     * <p>
     * zip组合事件的过程就是分别从发射器A和发射器B各取出一个事件来组合，并且一个事件只能被使用一次，
     * 组合的顺序是严格按照事件发送的顺序来进行的，所以上面截图中，可以看到，1永远是和A结合的，2永远是和B结合的。
     * <p>
     * 最终接收器收到的事件数量是和发送器发送事件最少的那个发送器的发送事件数目相同，所以如截图中，
     * 3很孤单，没有人愿意和它交往，孤独终老的单身狗。
     */
    fun zip(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin zip")
        stringBuffer.append("\n")

        var observableStr = Observable.create(ObservableOnSubscribe<String> {
            if (!it.isDisposed) {
                stringBuffer.append("emitter a")
                stringBuffer.append("\n")
                it.onNext("a")
                stringBuffer.append("emitter b")
                stringBuffer.append("\n")
                it.onNext("b")
//                stringBuffer.append("emitter c")
//                stringBuffer.append("\n")
//                it.onNext("c")
                stringBuffer.append("emitter onComplete()")
                stringBuffer.append("\n")
                it.onComplete()
            }
        }).subscribeOn(Schedulers.io())

        var observableInt = Observable.create(ObservableOnSubscribe<Int> {
            if (!it.isDisposed) {
                stringBuffer.append("emitter 1")
                stringBuffer.append("\n")
                it.onNext(1)
                stringBuffer.append("emitter 2")
                stringBuffer.append("\n")
                it.onNext(2)
                stringBuffer.append("emitter 3")
                stringBuffer.append("\n")
                it.onNext(3)
//                stringBuffer.append("emitter onComplete()")
//                stringBuffer.append("\n")
                var i = 0
                while (true) {   //无限循环发事件
                    it.onNext(i)
                    i++
                    println(i)
                }
//                it.onComplete()
            }
        }).subscribeOn(Schedulers.io())


        Observable.zip(observableStr, observableInt, object : BiFunction<String, Int, String> {
            override fun apply(t1: String, t2: Int): String {
                stringBuffer.append(t1 + t2)
                stringBuffer.append("\n")
                return t1 + t2
            }
        }).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<String> {
                override fun accept(t: String?) {
                    stringBuffer.append("accept()$t")
                    stringBuffer.append("\n")
                }
            })

        return stringBuffer.toString()
    }


    /**
     * @Map Map基本算是RxJava中一个最简单的操作符了，熟悉RxJava1.x的知道，
     * 它的作用是对发射时间发送的每一个事件应用一个函数，使得每一个事件都按照指定的函数去变化，而在2.x中它的作用几乎一致。
     * 是的，map基本作用就是将一个Observable通过某种函数关系，转换为另一种Observable，
     * 上面例子中就是把我们的Integer数据变成了String类型。从Log日志显而易见。
     */
    fun map(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin map")
        stringBuffer.append("\n")
        Observable.create(ObservableOnSubscribe<Int> {
            stringBuffer.append("emitter 1")
            stringBuffer.append("\n")
            it.onNext(1)
            stringBuffer.append("emitter 2")
            stringBuffer.append("\n")
            it.onNext(2)
//            stringBuffer.append("emitter 3")
//            stringBuffer.append("\n")
//            it.onNext(3)
            stringBuffer.append("emitter onComplete()")
            stringBuffer.append("\n")
            it.onComplete()
//            it.onError(Throwable())
        }).map(object : Function<Int, String> {
            override fun apply(t: Int): String {
                stringBuffer.append("this is result $t")
                stringBuffer.append("\n")
                return "this is result $t"
            }
        }).subscribe(object : Consumer<String> {
            override fun accept(t: String?) {
                stringBuffer.append("accept() $t")
                stringBuffer.append("\n")
            }
        })
        return stringBuffer.toString()
    }

    /**
     * 同线程 简单订阅
     * @ObservableEmitter：发射器
     * @Disposable：解除订阅关系
     * @create create操作符应该是最常见的操作符了，主要用于产生一个Obserable被观察者对象，为了方便大家的认知，
     * 以后的教程中统一把被观察者Observable称为发射器（上游事件），观察者Observer称为接收器（下游事件）。
     * @a 在发射事件中，我们在发射了数值3之后，直接调用了e.onComlete()，虽然无法接收事件，但发送事件还是继续的。
     * @b 另外一个值得注意的点是，在RxJava2.x中，可以看到发射事件方法相比1.x多了一个throws Excetion，意味着我们做一些特定操作再也不用try-catch了。
     * @Disposable 并且2.x中有一个Disposable概念，这个东西可以直接调用切断，可以看到，当它的isDisposed()返回为false的时候，
     * 接收器能正常接收事件，但当其为true的时候，接收器停止了接收。所以可以通过此参数动态控制接收事件了。
     */
    fun create(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin create")
        stringBuffer.append("\n")
        // 第一步：初始化Observable
        Observable.create(ObservableOnSubscribe<Int> {
            stringBuffer.append("emitter 1")
            stringBuffer.append("\n")
            it.onNext(1)
            stringBuffer.append("emitter 2")
            stringBuffer.append("\n")
            it.onNext(2)
            stringBuffer.append("emitter 3")
            stringBuffer.append("\n")
            it.onNext(3)
            stringBuffer.append("emitter onComplete")
            stringBuffer.append("\n")
            it.onComplete()
        }).subscribe(object : Observer<Int> { // 第三步：订阅
            // 第二步：初始化Observer
            lateinit var mDisposable: Disposable

            override fun onComplete() {
                stringBuffer.append("onComplete():完成")
                stringBuffer.append("\n")
            }

            override fun onSubscribe(d: Disposable) {
                stringBuffer.append("onSubscribe():建立连接")
                stringBuffer.append("\n")
                mDisposable = d;
            }

            override fun onNext(t: Int) {
                stringBuffer.append("onNext():$t")
                stringBuffer.append("\n")
                if (t == 1) {
                    stringBuffer.append("1mDisposable.isDisposed:${mDisposable.isDisposed}")
                    stringBuffer.append("\n")
                    mDisposable.dispose()
                    stringBuffer.append("2mDisposable.isDisposed:${mDisposable.isDisposed}")
                    stringBuffer.append("\n")
                }
            }

            override fun onError(e: Throwable) {
                stringBuffer.append("onError():${e.message}")
                stringBuffer.append("\n")
            }
        })
        return stringBuffer.toString();
    }
}
