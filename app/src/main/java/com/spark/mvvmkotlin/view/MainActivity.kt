package com.spark.mvvmkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.spark.mvvmkotlin.R
import com.spark.mvvmkotlin.databinding.ActivityMainBinding
import com.spark.mvvmkotlin.model.Weather
import com.spark.mvvmkotlin.viewmodel.MainViewModel
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
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
        test2()
    }

    private fun test2() {
        println("test2：" + Thread.currentThread().name)
        Observable.create(ObservableOnSubscribe<String> {
            println(Thread.currentThread().name)
            it.onNext("线程")
            it.onNext("线程d")
            it.onComplete()
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()) //
            .doOnNext {
                //                for (s in it)
                println("doOnNext$it")
                println(Thread.currentThread().name)
            }
            .observeOn(Schedulers.io())
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
}
