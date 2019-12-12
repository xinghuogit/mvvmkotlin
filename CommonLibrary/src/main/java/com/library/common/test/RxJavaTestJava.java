package com.library.common.test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*************************************************************************************************
 * 日期：2019/12/11 19:50
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class RxJavaTestJava {
    private static final String TAG = "RxJavaTestJava";

    public static void main(String[] args) {
        test1();
    }


    /**
     * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
     * Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
     * Schedulers.newThread() 代表一个常规的新线程
     * AndroidSchedulers.mainThread()  代表Android的主线程
     * <p>
     * 出现这个问题的原因是：AndroidSchedulers.mainThread返回的是LooperScheduler的一个实例，
     * 但是LooperScheduler依赖于Android，我们在纯Java的单元测试中是无法使用它的。
     * 找到了问题的原因解决起来就容易多了。我们需要在单元测试运行之前，
     * 初始化RxAndroidPlugins让它返回一个我们指定的不依赖于Android的Scheduler。下面给出两种解决方案。
     */
    private static void test2() {
        System.out.println("test2：" + Thread.currentThread().getName());
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                System.out.println(Thread.currentThread().getName());
                emitter.onNext("线程");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())//上游发送事件的线程 指定第一个有效
                .observeOn(AndroidSchedulers.mainThread())//下游接收事件的线程  下游可以改变线程
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("doOnNext" + s);
                        System.out.println(Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())//下游接收事件的线程  下游可以改变线程
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(Thread.currentThread().getName());
                        System.out.println("accept:" + s);
                    }
                });
    }

    /**
     * 简单订阅
     */
    private static void test1() {
        //创建一个上游 Observable：
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        Observer<Integer> observer = new Observer<Integer>() { //创建一个下游 Observer
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("常规" + "subscribe");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("常规" + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("常规" + "error");
            }

            @Override
            public void onComplete() {
                System.out.println("常规" + "complete");
            }
        };
        observable.subscribe(observer);//建立连接

        //链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("链式" + "subscribe");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("链式" + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("链式" + "error");
            }

            @Override
            public void onComplete() {
                System.out.println("链式" + "complete");
            }
        });

        //链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

            }
        });
    }
}
