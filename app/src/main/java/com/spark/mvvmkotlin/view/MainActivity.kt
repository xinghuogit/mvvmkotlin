package com.spark.mvvmkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.spark.mvvmkotlin.R
import com.spark.mvvmkotlin.databinding.ActivityMainBinding
import com.spark.mvvmkotlin.model.Weather
import com.spark.mvvmkotlin.viewmodel.MainViewModel

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
    }
}
