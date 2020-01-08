package com.spark.mvvmjava.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.spark.mvvmjava.R;
import com.spark.mvvmjava.bean.Dog;
import com.spark.mvvmjava.bean.YellowDog;
import com.spark.mvvmjava.databinding.ActivityLivedataBinding;
import com.spark.mvvmjava.viewmodel.LiveDataViewModel;

/**
 * 首先我们把简单介绍下LiveData极其作用和特点。后面用例子来证明。
 * 简介：LiveData 是一个有生命周期感知 & 可观察的数据持有者类
 * 作用：
 * 持久化的观察数据的更改与变化
 * 特点：
 * 1、感知对应Activity的生命周期，只有生命周期处于onStart与onResume时，LiveData处于活动状态，才会把更新的数据通知至对应的Activity
 * 2、当生命周期处于onStop或者onPause时，不回调数据更新，直至生命周期为onResume时，立即回调
 * 3、当生命周期处于onDestory时，观察者会自动删除，防止内存溢出
 * 4、共享资源。可以使用单例模式扩展LiveData对象以包装系统服务，以便可以在应用程序中共享它们，同时有遵守了以上生命周期
 * LiveData有2个方法通知数据改变：
 * <p>
 * 同步：.setValue（value）接收端数据回调与发送端同一个线程
 * 异步：.postValue（value）接收端在主线程回调数据
 */
public class LiveDataActivity extends AppCompatActivity {
    private static final String TAG = "LiveDataActivity";

    private ActivityLivedataBinding binding;

    private MutableLiveData<String> liveData = new MutableLiveData<>();
    private YellowDog yellowDog = new YellowDog();

    private LiveDataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_model);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_livedata);
        binding.setOnlyLive(liveData.getValue());
        binding.setYellowDog(yellowDog);

        binding.setLifecycleOwner(this); //本节重点是下面2句代码哦
        viewModel = ViewModelProviders.of(this).get(LiveDataViewModel.class);//本节重点是下面2句代码哦
        binding.setLiveDataViewModel(viewModel);

        addLiveObserve();
        addTextViewChange();
    }

    private void addTextViewChange() {
        binding.tvLiveData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "TextView的变化 单独使用LiveData ==> " + s);
            }
        });
        binding.tvDataBinding.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "TextView的变化 DataBinding双向绑定 ==> " + s);
            }
        });
        binding.tvLiveDataViewModel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "TextView的变化 ViewModel配合LiveData设置数据 ==> " + s);
            }
        });
    }

    private void addLiveObserve() {
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.setOnlyLive(s);
                Log.i(TAG, "单独使用LiveData设置数据onChanged: " + s);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        liveData.postValue("单独LiveData使用111");//使用setValue屏幕闪烁
        yellowDog.name.set("我是DataBinding双向绑定");//使用DataBinding双向绑定 在按home键就设置了  所有要用 LiveData和ViewModel
        viewModel.getLiveDataName().postValue("ViewModel配合LiveData设置数据");//binding.setLifecycleOwner(this); DataBinding双向绑定 也跟随生命周期
        viewModel.getDogMutableLiveData().postValue(new Dog("花花", "红色"));
    }
}
