package com.spark.mvvmjava.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

/*************************************************************************************************
 * 日期：2020/1/7 19:25
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class ViewModelActivity extends ViewModel {
    private static final String TAG = "ViewModelActivity";

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "onCleared: ");
    }
}
