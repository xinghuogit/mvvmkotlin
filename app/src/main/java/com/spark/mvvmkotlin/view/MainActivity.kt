package com.spark.mvvmkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.spark.mvvmkotlin.R
import com.spark.mvvmkotlin.databinding.ActivityMainBinding
import com.spark.mvvmkotlin.model.Weather
import com.spark.mvvmkotlin.viewmodel.MainViewModel
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.function.Consumer

class MainActivity : AppCompatActivity() {
    lateinit var mActivityMainBinding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val weather = Weather("20191129", "阴天", 1);
        mainViewModel = MainViewModel(weather)
        mActivityMainBinding.activitymain = mainViewModel;
        testZip()
    }

    private fun testZip() {
        val observable1 = Observable.create(ObservableOnSubscribe<Int> { emitter ->
            println("emit 1")
            emitter.onNext(1)
            Thread.sleep(1000)

            println("emit 2")
            emitter.onNext(2)
            Thread.sleep(1000)

            println("emit 3")
            emitter.onNext(3)
            Thread.sleep(1000)

            println("emit 4")
            emitter.onNext(4)
            Thread.sleep(1000)

            println("emit complete1")
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())

        val observable2 = Observable.create(ObservableOnSubscribe<String> { emitter ->
            println("emit A")
            emitter.onNext("A")
            Thread.sleep(1000)

            println("emit B")
            emitter.onNext("B")
            Thread.sleep(1000)

            println("emit C")
            emitter.onNext("C")
            Thread.sleep(1000)

            println("emit complete2")
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())

        Observable.zip(observable1, observable2, object :
            BiFunction<Int, String, String> {

            override fun apply(t1: Int, t2: String): String {
                return t1.toString() + t2
            }
        }).subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                println("onSubscribe")
            }

            override fun onNext(value: String) {
                println("onNext: $value")
            }

            override fun onError(e: Throwable) {
                println("onError")
            }

            override fun onComplete() {
                println("onComplete")
            }
        })
    }

    private fun test2() {
        println("test2：" + Thread.currentThread().name)
        Observable.create(ObservableOnSubscribe<String> {
            println(Thread.currentThread().name)
            it.onNext("线程")
//            it.onNext("线程d")
            it.onComplete()
        }).subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread()) //
//            .observeOn(Schedulers.newThread()) //
            .doOnNext {
                //                for (s in it)
                print("doOnNext$it  ")
                println(Thread.currentThread().name)
            }
            .observeOn(Schedulers.io())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
                    println("onSubscribe")
                }

                override fun onNext(t: String) {
                    print("onNext$t  ")
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
}
