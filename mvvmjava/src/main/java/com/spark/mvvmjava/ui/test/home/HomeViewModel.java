package com.spark.mvvmjava.ui.test.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.spark.mvvmjava.base.mvvm.BaseViewModel;
import com.spark.mvvmjava.base.mvvm.RepositoryImpl;
import com.spark.mvvmjava.bean.Advert;
import com.spark.mvvmjava.bean.HomeBean;
import com.spark.mvvmjava.bean.HomeFatherBean;
import com.spark.mvvmjava.network.ParamsBuilder;
import com.spark.mvvmjava.network.Resource;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*************************************************************************************************
 * 日期：2020/1/15 10:21
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class HomeViewModel extends BaseViewModel<RepositoryImpl> {
    private static final String TAG = "HomeViewModel";

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Resource<List<Advert>>> getBannerList() {
        return getRepository().getBannerList();
    }

    /**
     * @param curPage
     * @param paramsBuilder
     * @return 获取首页文章
     */
    public LiveData<Resource<HomeFatherBean>> getHomeArticles(int curPage, ParamsBuilder paramsBuilder) {
        return getRepository().getHomeArticles(curPage, paramsBuilder);
    }

    //收藏文章
    public LiveData<Resource<String>> collectArticle(HomeBean data) {
        int id = data.getId();
        //收藏站内文章
        if (id > 0) {
            return collectArticle(id);
        }
        //收藏站外文章
        return getRepository().collectOutArticle(data.getTitle(), data.getAuthor(), data.getLink());
    }

    public LiveData<Resource<String>> unCollectByHome(int id) {
        return getRepository().unCollectByHome(id);
    }


    //收藏站内文章
    public LiveData<Resource<String>> collectArticle(int id) {
        return getRepository().collectArticle(id);
    }

    public LiveData<Resource<String>> upload(HashMap<String, String> map, Map<String, File> files) {
        return getRepository().upLoad(map, files);
    }

//    public MutableLiveData<Resource<List<Advert>>> getAdverts() {
//        /**
//         *因为用到LiveData，我觉得都不需要切换到主线程了。LiveData可以帮我们做
//         *调用接口，返回我们的MutableLiveData<List<BannerBean>>
//         */
//        final MutableLiveData<Resource<List<Advert>>> liveData = new MutableLiveData<>();
//        RetrofitManager.getInstance().getApiService().getAdverts()
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        liveData.postValue(Resource.<List<Advert>>loading(""));
//                    }
//                })
//                .subscribe(new Consumer<ResponseModel<List<Advert>>>() {
//                    @Override
//                    public void accept(ResponseModel<List<Advert>> listResponseModel) throws Exception {
//                        liveData.postValue(Resource.success(listResponseModel.getData()));
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        liveData.postValue(Resource.<List<Advert>>error(throwable));
//                    }
//                });
//        return liveData;
//    }
}
