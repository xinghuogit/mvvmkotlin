package com.spark.mvvmjava.base.mvvm;

import androidx.lifecycle.MutableLiveData;

import com.spark.mvvmjava.bean.Advert;
import com.spark.mvvmjava.network.Resource;

import java.util.List;

/*************************************************************************************************
 * 日期：2020/3/11 17:56
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：所有的网络请求所在的位置
 ************************************************************************************************/
public class RepositoryImpl extends BaseModel {

    /**
     * 获取 banner列表
     */
    public MutableLiveData<Resource<List<Advert>>> getBannerList() {
        MutableLiveData<Resource<List<Advert>>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getAdverts(), liveData);
    }
}
