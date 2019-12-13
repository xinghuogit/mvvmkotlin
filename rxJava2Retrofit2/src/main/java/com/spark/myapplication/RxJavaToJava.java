package com.spark.myapplication;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/*************************************************************************************************
 * 日期：2019/12/13 17:23
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class RxJavaToJava {



    /**
     * @create 同线程 简单订阅
     * @ObservableEmitter：发射器
     * @Disposable：解除订阅关系
     */
    public String create(StringBuffer stringBuffer) {
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        stringBuffer.append("RxJavaToJava create");
        stringBuffer.append("\n");
        // 第一步：初始化Observable
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                stringBuffer.append("emitter 1");
                stringBuffer.append("\n");
                emitter.onNext(1);
                stringBuffer.append("emitter 2");
                stringBuffer.append("\n");
                emitter.onNext(2);
                stringBuffer.append("emitter 3");
                stringBuffer.append("\n");
                emitter.onNext(3);
                stringBuffer.append("emitter onComplete");
                stringBuffer.append("\n");
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            // 第三步：订阅
            // 第二步：初始化Observer
            Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                stringBuffer.append("onSubscribe():建立连接");
                stringBuffer.append("\n");
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                stringBuffer.append("onNext():" + integer);
                stringBuffer.append("\n");
                if (integer == 2) ;
                {
                    stringBuffer.append("1mDisposable.isDisposed:" + mDisposable.isDisposed());
                    stringBuffer.append("\n");
                    mDisposable.dispose();
                    stringBuffer.append("2mDisposable.isDisposed:" + mDisposable.isDisposed());
                    stringBuffer.append("\n");
                }
            }

            @Override
            public void onError(Throwable e) {
                stringBuffer.append("onError():" + e.getMessage());
                stringBuffer.append("\n");
            }

            @Override
            public void onComplete() {
                stringBuffer.append("onComplete():完成");
                stringBuffer.append("\n");
            }
        });
        return stringBuffer.toString();
    }

}
