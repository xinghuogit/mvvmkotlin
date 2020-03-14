package com.spark.mvvmjava.base.mvvm;

import androidx.lifecycle.MutableLiveData;

import com.spark.mvvmjava.bean.Advert;
import com.spark.mvvmjava.bean.HomeFatherBean;
import com.spark.mvvmjava.bean.UserInfo;
import com.spark.mvvmjava.network.MultipartBodyUtils;
import com.spark.mvvmjava.network.ParamsBuilder;
import com.spark.mvvmjava.network.Resource;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 获取 banner列表
     */
    public MutableLiveData<Resource<HomeFatherBean>> getHomeArticles(int curPage, ParamsBuilder paramsBuilder) {
        MutableLiveData<Resource<HomeFatherBean>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getHomeArticles(curPage), liveData, paramsBuilder);
    }

    //登录
    public MutableLiveData<Resource<UserInfo>> login(HashMap<String, Object> map, ParamsBuilder paramsBuilder) {
        MutableLiveData<Resource<UserInfo>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().login(map), liveData, paramsBuilder);
    }

    //站内收藏文章
    public MutableLiveData<Resource<String>> collectArticle(int id) {
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().collectArticle(id), liveData, ParamsBuilder.build().isShowLoading(false));//不显示加载logo
    }

    //站外收藏文章
    public MutableLiveData<Resource<String>> collectOutArticle(String title, String author, String link) {
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().collectOutArticle(title, author, link), liveData, ParamsBuilder.build().isShowLoading(false));
    }

    //取消收藏 -- 首页文章列表
    public MutableLiveData<Resource<String>> unCollectByHome(int id) {
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().unCollectByHome(id), liveData, ParamsBuilder.build().isShowLoading(false));
    }

    //取消收藏 -- 我的收藏列表
    public MutableLiveData<Resource<String>> unCollectByMe(int id, int originId) {
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().unCollectByMe(id, originId), liveData, null);
    }

    //上传文件
    public MutableLiveData<Resource<String>> upLoad(HashMap<String, String> map, Map<String, File> files) {
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
        return uploadFile(getApiService().upload(map, MultipartBodyUtils.getBody(liveData, files)), liveData);
    }
}
