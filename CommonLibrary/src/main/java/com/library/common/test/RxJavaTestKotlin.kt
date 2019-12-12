package com.library.common.test

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/*************************************************************************************************
 * 日期：2019/12/11 16:17
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
object RxJavaTestKotlin {
    private val TAG = "RxJavaTestKotlin"


    @JvmStatic
    fun main(args: Array<String>) {
        println(Thread.currentThread().name)
//        test1()
//        test2()
    }

    /**
     * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
     * Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
     * Schedulers.newThread() 代表一个常规的新线程
     * AndroidSchedulers.mainThread()  代表Android的主线程
     *
     * 出现这个问题的原因是：AndroidSchedulers.mainThread返回的是LooperScheduler的一个实例，
     * 但是LooperScheduler依赖于Android，我们在纯Java的单元测试中是无法使用它的。
     * 找到了问题的原因解决起来就容易多了。我们需要在单元测试运行之前，
     * 初始化RxAndroidPlugins让它返回一个我们指定的不依赖于Android的Scheduler。下面给出两种解决方案。
     */
    private fun test2() {
        println("test2：" + Thread.currentThread().name)
        Observable.create(ObservableOnSubscribe<String> {
            println(Thread.currentThread().name)
            it.onNext("线程")
            it.onComplete()
        }).subscribeOn(Schedulers.newThread())//上游发送事件的线程 指定第一个有效
            .observeOn(AndroidSchedulers.mainThread())//下游接收事件的线程  下游可以改变线程
            .doOnNext {
                println("doOnNext$it")
                println(Thread.currentThread().name)
            }
            .observeOn(Schedulers.io())//下游接收事件的线程  下游可以改变线程
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
                    println("onSubscribe")
                }

                override fun onNext(t: String) {
                    println("onNext$t")
                    println(Thread.currentThread().name)
                }

                override fun onError(e: Throwable) {
                    println("onError")
                }

                override fun onComplete() {
                    println("onComplete")
                }
            })
    }

    /**
     * 同线程 简单订阅   ObservableEmitter：发射器   Disposable：开关
     */
    private fun test1() {
        //创建一个上游 Observable：
        val observable = Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
            it.onComplete()
        })
        val observer = object : Observer<Int> {
            //创建一个下游 Observer
            override fun onSubscribe(d: Disposable) {
                println("常规:onSubscribe")
//                d.dispose()
            }

            override fun onNext(t: Int) {
                println("常规:$t")
            }

            override fun onError(e: Throwable) {
                println("常规:onError")
            }

            override fun onComplete() {
                println("常规:onComplete")
            }
        }
//        observable.subscribe(observer)//建立连接

        //链式操作
        Observable.create(ObservableOnSubscribe<Int> {
            println("it 4")
            it.onNext(4)
            println("it 5")
            it.onNext(5)
            println("it 6")
            it.onNext(6)
            println("it 7")
            it.onNext(7)
            println("it 8")
            it.onNext(8)
            println("it onComplete()")
            it.onComplete()
        }).subscribe(object : Observer<Int> {
            lateinit var disposable: Disposable
            var i: Int = 0
            override fun onSubscribe(d: Disposable) {
                disposable = d;
                println("链式:onSubscribe")
            }

            override fun onNext(t: Int) {
                println("链式onNext:$t")
//                if (t == 6 && disposable != null) {
//                    println("链式disposable.isDisposed:" + disposable.isDisposed)
//                    disposable.dispose()
//                    println("链式disposable.isDisposed:" + disposable.isDisposed)
//                }
            }

            override fun onError(e: Throwable) {
                println("链式:onError")
            }

            override fun onComplete() {
                println("链式:onComplete")
                println("链式:onComplete:" + (disposable.isDisposed))
            }
        })
    }
}