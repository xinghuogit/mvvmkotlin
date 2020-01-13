package com.spark.mvvmjavastudy.ui.lifecycle;

import android.app.Activity;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.library.common.utils.LogUtils;
import com.spark.mvvmjavastudy.R;

/*************************************************************************************************
 * 日期：2020/1/13 9:48
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class LifecycleActivitiy extends Activity implements LifecycleOwner {
    private static final String TAG = "LifecycleActivitiy";

    private MutableLiveData<String> liveData = new MutableLiveData<>();
    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                LogUtils.INSTANCE.i(TAG, "onChanged: s" + s);
            }
        });
        getLifecycle().addObserver(new MyObserver());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    protected void onStop() {
        super.onStop();
        liveData.postValue("测试123");
    }
}
