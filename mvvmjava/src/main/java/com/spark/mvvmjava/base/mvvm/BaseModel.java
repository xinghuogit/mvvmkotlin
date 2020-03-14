package com.spark.mvvmjava.base.mvvm;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.spark.mvvmjava.network.ParamsBuilder;
import com.spark.mvvmjava.network.Resource;
import com.spark.mvvmjava.network.ResponseModel;
import com.spark.mvvmjava.network.RetrofitApiService;
import com.spark.mvvmjava.network.RetrofitManager;
import com.spark.mvvmjava.network.interceptor.NetCacheInterceptor;
import com.spark.mvvmjava.network.interceptor.OfflineCacheInterceptor;
import com.trello.rxlifecycle3.LifecycleTransformer;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/*************************************************************************************************
 * 日期：2020/3/11 14:57
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public abstract class BaseModel {
    public LifecycleTransformer lifecycleTransformer;   //解决RxJava可能存在的内存泄漏
    public ArrayList<String> onNetTags; // 如果开启，同一url还在请求网络时，不会

    public RetrofitApiService getApiService() {
        return RetrofitManager.getInstance().getApiService();
    }

    public void setLifecycleTransformer(LifecycleTransformer lifecycleTransformer) {
        this.lifecycleTransformer = lifecycleTransformer;
    }

    public void setOnNetTags(ArrayList<String> onNetTags) {
        this.onNetTags = onNetTags;
    }

    //把统一操作全部放在这，ParamsBuilder是我定义的一个参数，需不需要Loading，loadingmessage，需不需要重连都在这里。
    //不传的话，都是默认值。封装好后，子类只要传Retrofit的网络请求返回值，和LiveData返回值就Ok了
    public <T> MutableLiveData<T> observeGo(Observable observable, final MutableLiveData<T> liveData) {
        return observe(observable, liveData, null);
    }

    public <T> MutableLiveData<T> observeGo(Observable observable, final MutableLiveData<T> liveData, ParamsBuilder paramsBuilder) {
        if (paramsBuilder == null) paramsBuilder = ParamsBuilder.build();
        int retryCount = paramsBuilder.getRetryCount();
        if (retryCount > 0) {
            return observeRetry(observable, liveData, paramsBuilder);
        } else {
            return observe(observable, liveData, paramsBuilder);
        }
    }

    //把统一操作全部放在这，不会重连
    public <T> MutableLiveData<T> observe(Observable observable, final MutableLiveData<T> liveData, ParamsBuilder paramsBuilder) {
        if (paramsBuilder == null) paramsBuilder = ParamsBuilder.build();
        boolean showLoading = paramsBuilder.isShowLoading();
        String loadingMessage = paramsBuilder.getLoadingMessage();
        int onlineCacheTime = paramsBuilder.getOnlineCacheTime();
        int offlineCacheTime = paramsBuilder.getOfflineCacheTime();

        if (onlineCacheTime > 0) setOnlineCacheTime(onlineCacheTime);
        if (offlineCacheTime > 0) setOfflineCacheTime(onlineCacheTime);

        String oneTag = paramsBuilder.getOneTag();
        if (!TextUtils.isEmpty(oneTag) && onNetTags != null) {
            if (onNetTags.contains(oneTag)) {
                return liveData;
            }
        }
        observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (!TextUtils.isEmpty(oneTag) && onNetTags != null) {
                            onNetTags.add(oneTag);
                        }
                        if (showLoading) {
                            liveData.postValue((T) Resource.loading(loadingMessage));
                        }
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (!TextUtils.isEmpty(oneTag) && onNetTags != null) {
                            onNetTags.remove(oneTag);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(lifecycleTransformer)
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        liveData.postValue((T) Resource.response((ResponseModel<Object>) o));
                    }
                }, throwable -> {
                    liveData.postValue((T) Resource.error((Throwable) throwable));
                });
        return liveData;
    }

    //把统一操作全部放在这，不会重连
    public <T> MutableLiveData<T> observeRetry(Observable observable, final MutableLiveData<T> liveData, ParamsBuilder paramsBuilder) {
        if (paramsBuilder == null) paramsBuilder = ParamsBuilder.build();
        boolean showLoading = paramsBuilder.isShowLoading();
        String loadingMessage = paramsBuilder.getLoadingMessage();
        int onlineCacheTime = paramsBuilder.getOnlineCacheTime();
        int offlineCacheTime = paramsBuilder.getOfflineCacheTime();

        if (onlineCacheTime > 0) setOnlineCacheTime(onlineCacheTime);
        if (offlineCacheTime > 0) setOfflineCacheTime(onlineCacheTime);

        String oneTag = paramsBuilder.getOneTag();
        if (!TextUtils.isEmpty(oneTag) && onNetTags != null) {
            if (onNetTags.contains(oneTag)) {
                return liveData;
            }
        }

        final int maxCount = paramsBuilder.getRetryCount();
        final int[] currentCount = {0};

        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        if (currentCount[0] <= maxCount) {    //如果还没到次数，就延迟5秒发起重连
                            currentCount[0]++;
                            return Observable.just(1).delay(5000, TimeUnit.MILLISECONDS);
                        } else {//到达次数，抛出异常
                            return Observable.error(new Throwable("重连次数已达最高,请求超时"));
                        }
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (!TextUtils.isEmpty(oneTag) && onNetTags != null) {
                            onNetTags.add(oneTag);
                        }
                        if (showLoading) {
                            liveData.postValue((T) Resource.loading(loadingMessage));
                        }
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (!TextUtils.isEmpty(oneTag) && onNetTags != null) {
                            onNetTags.remove(oneTag);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(lifecycleTransformer)
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        liveData.postValue((T) Resource.response((ResponseModel<Object>) o));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        liveData.postValue((T) Resource.error(throwable));
                    }
                });
        return liveData;
    }


    //设置在线网络缓存
    public void setOnlineCacheTime(int time) {
        NetCacheInterceptor.getInstance().setOnlineCacheTime(time);
    }

    //设置离线网络缓存
    public void setOfflineCacheTime(int time) {
        OfflineCacheInterceptor.getInstance().setOfflineCacheTime(time);
    }

    //上传文件只有2个参数，showDialog：是否显示showLoading；loadingMessage：showLoading显示的文字
    public <T> MutableLiveData<T> uploadFile(Observable observable, MutableLiveData<T> liveData) {
        return uploadFile(observable, liveData, null);
    }
    
    //上传文件只有2个参数，showDialog：是否显示showLoading；loadingMessage：showLoading显示的文字
    private <T> MutableLiveData<T> uploadFile(Observable observable, MutableLiveData<T> liveData, ParamsBuilder paramsBuilder) {
        if (paramsBuilder == null) paramsBuilder = ParamsBuilder.build();
        boolean showLoading = paramsBuilder.isShowLoading();
        String loadingMessage = paramsBuilder.getLoadingMessage();
        observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (showLoading) {
                            liveData.postValue((T) Resource.loading(loadingMessage));
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        liveData.postValue((T) Resource.success("上传成功"));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        liveData.postValue((T) Resource.error(throwable));
                    }
                });
        return liveData;
    }

}
