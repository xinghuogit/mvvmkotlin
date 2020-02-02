package com.spark.mvvmjava.base.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/*************************************************************************************************
 * 日期：2020/1/13 13:05
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：基础ViewModel //继承AndroidViewModel，是因为里面要用context时候直接可以getApplication()
 ************************************************************************************************/
public class BaseViewModel extends AndroidViewModel {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
