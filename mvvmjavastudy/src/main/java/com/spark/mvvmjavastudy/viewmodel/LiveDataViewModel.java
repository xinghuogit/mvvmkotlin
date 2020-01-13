package com.spark.mvvmjavastudy.viewmodel;



import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.library.common.utils.LogUtils;
import com.spark.mvvmjavastudy.bean.Dog;

/*************************************************************************************************
 * 日期：2020/1/7 19:25
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class LiveDataViewModel extends ViewModel {
    private static final String TAG = "LiveDataViewModel";
    private MutableLiveData<String> liveDataName = new MutableLiveData<>();
    private MutableLiveData<Dog> dogMutableLiveData = new MutableLiveData<>();

    @Override
    protected void onCleared() {
        super.onCleared();
        LogUtils.INSTANCE.i(TAG, "onCleared: ");
    }

    public MutableLiveData<Dog> getDogMutableLiveData() {
        return dogMutableLiveData;
    }

    public MutableLiveData<String> getLiveDataName() {
        return liveDataName;
    }

}
