package com.library.common.test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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
//        test1();
//        test2();
//        test3();
//        testFlatMap();
        new Thread() {
            @Override
            public void run() {
                super.run();
                testZip();
            }
        }.start();
    }

    private static void testZip() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                System.out.println("emit 1");
                emitter.onNext(1);
                Thread.sleep(1000);

                System.out.println("emit 2");
                emitter.onNext(2);
                Thread.sleep(1000);

                System.out.println("emit 3");
                emitter.onNext(3);
                Thread.sleep(1000);

                System.out.println("emit 4");
                emitter.onNext(4);
                Thread.sleep(1000);

                System.out.println("emit complete1");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                System.out.println("emit A");
                emitter.onNext("A");
                Thread.sleep(1000);

                System.out.println("emit B");
                emitter.onNext("B");
                Thread.sleep(1000);

                System.out.println("emit C");
                emitter.onNext("C");
                Thread.sleep(1000);

                System.out.println("emit complete2");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).
                subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(String value) {
                        System.out.println("onNext: " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }


    /**
     *     api.register(new RegisterRequest())            //发起注册请求
     *                 .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
     *                 .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求注册结果
     *                 .doOnNext(new Consumer<RegisterResponse>() {
     *                     @Override
     *                     public void accept(RegisterResponse registerResponse) throws Exception {
     *                         //先根据注册的响应结果去做一些操作
     *                     }
     *                 })
     *                 .observeOn(Schedulers.io())                 //回到IO线程去发起登录请求
     *                 .flatMap(new Function<RegisterResponse, ObservableSource<LoginResponse>>() {
     *                     @Override
     *                     public ObservableSource<LoginResponse> apply(RegisterResponse registerResponse) throws Exception {
     *                         return api.login(new LoginRequest());
     *                     }
     *                 })
     *                 .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求登录的结果
     *                 .subscribe(new Consumer<LoginResponse>() {
     *                     @Override
     *                     public void accept(LoginResponse loginResponse) throws Exception {
     *                         Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
     *                     }
     *                 }, new Consumer<Throwable>() {
     *                     @Override
     *                     public void accept(Throwable throwable) throws Exception {
     *                         Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
     *                     }
     *                 });
     */

    /**
     * 但是测试时一致的
     * FlatMap将一个发送事件的上游Observable变换为多个发送事件的Observables，
     * 然后将它们发射的事件合并后放进一个单独的Observable里.flatMap并不保证事件的顺序
     * concatMap它和flatMap的作用几乎一模一样, 只是它的结果是严格按照上游发送的顺序来发送的
     */
    private static void testFlatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                return Observable.fromIterable(list);
            }
        })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }

    /**
     * 通过Map, 可以将上游发来的事件转换为任意的类型, 可以是一个Object, 也可以是一个集合,
     */
    private static void test3() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
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
