package com.spark.mvvmjavastudy.ui.lifecycle;



import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.library.common.utils.LogUtils;

/*************************************************************************************************
 * 日期：2020/1/13 9:59
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class MyObserver implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        LogUtils.INSTANCE.i("MyObserver", "onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        LogUtils.INSTANCE.i("MyObserver", "onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        LogUtils.INSTANCE.i("MyObserver", "onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        LogUtils.INSTANCE.i("MyObserver", "onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        LogUtils.INSTANCE.i("MyObserver", "onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestory() {
        LogUtils.INSTANCE.i("MyObserver", "onDestory");
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
//    public void onAny() {
//        LogUtils.INSTANCE.i("MyObserver","onAny");
//    }
}
