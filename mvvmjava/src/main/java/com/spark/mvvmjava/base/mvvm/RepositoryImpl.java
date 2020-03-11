package com.spark.mvvmjava.base.mvvm;

import androidx.lifecycle.MutableLiveData;

import com.spark.mvvmjava.network.Resource;
import com.youth.banner.Banner;

import java.util.List;

/*************************************************************************************************
 * 日期：2020/3/11 17:56
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class RepositoryImpl extends BaseModel {

    public MutableLiveData<Resource<List<Banner>>> getBannerList() {
        MutableLiveData<Resource<List<Banner>>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getAdverts(), liveData);
    }
}
