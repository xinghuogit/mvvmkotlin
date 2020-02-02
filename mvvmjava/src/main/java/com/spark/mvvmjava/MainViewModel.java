package com.spark.mvvmjava;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.spark.mvvmjava.base.mvvm.BaseViewModel;
import com.library.common.utils.LogUtils;
import com.spark.mvvmjava.bean.Advert;
import com.spark.mvvmjava.network.Resource;
import com.spark.mvvmjava.network.ResponseModel;
import com.spark.mvvmjava.network.RetrofitManager;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*************************************************************************************************
 * 日期：2020/1/15 10:21
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class MainViewModel extends BaseViewModel {
    private static final String TAG = "MainViewModel";

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Resource<List<Advert>>> getAdverts() {
        /**
         *因为用到LiveData，我觉得都不需要切换到主线程了。LiveData可以帮我们做
         *调用接口，返回我们的MutableLiveData<List<BannerBean>>
         */
        final MutableLiveData<Resource<List<Advert>>> liveData = new MutableLiveData<>();
        RetrofitManager.getInstance().getApiService().getAdverts()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        liveData.postValue(Resource.<List<Advert>>loading(""));
                    }
                })
                .subscribe(new Consumer<ResponseModel<List<Advert>>>() {
                    @Override
                    public void accept(ResponseModel<List<Advert>> listResponseModel) throws Exception {
                        liveData.postValue(Resource.success(listResponseModel.getData()));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        liveData.postValue(Resource.<List<Advert>>error(throwable));
                    }
                });
        return liveData;
    }
}
