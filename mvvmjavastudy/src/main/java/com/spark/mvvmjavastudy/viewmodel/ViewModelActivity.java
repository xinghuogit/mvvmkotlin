package com.spark.mvvmjavastudy.viewmodel;



import androidx.lifecycle.ViewModel;

import com.library.common.utils.LogUtils;

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
        LogUtils.INSTANCE.i(TAG, "onCleared: ");
    }
}
