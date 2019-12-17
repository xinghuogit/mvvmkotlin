package com.spark.myapplication;

import com.library.common.utils.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/*************************************************************************************************
 * 日期：2019/12/13 17:23
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class RxJavaToJava {

    /**
     * just，没什么好说的，其实在前面各种例子都说明了，就是一个简单的发射器依次调用onNext()方法。
     */
    public String just(StringBuffer stringBuffer) {
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        stringBuffer.append("RxJavaToJava just");
        stringBuffer.append("\n");
        Observable.just(6, 2, 3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        stringBuffer.append("accept()" + integer);
                        stringBuffer.append("\n");
                    }
                });
        return stringBuffer.toString();
    }

    /**
     * take，接受一个long型参数count，代表至多接收count个数据。
     */
    public String take(StringBuffer stringBuffer) {
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        stringBuffer.append("RxJavaToJava take 显示前面1个");
        stringBuffer.append("\n");
        Observable.just(6, 2, 3, 4, 5)
                .take(1)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        stringBuffer.append("accept()" + integer);
                        stringBuffer.append("\n");
                    }
                });
        return stringBuffer.toString();
    }

    /**
     * skip很有意思，其实作用就和字面意思一样，接受一个long型参数count，代表跳过count个数目开始接收。
     */
    public String skip(StringBuffer stringBuffer) {
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        stringBuffer.append("RxJavaToJava skip 跳过3个 直接显示4，5");
        stringBuffer.append("\n");
        Observable.just(1, 2, 3, 4, 5)
                .skip(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        stringBuffer.append("accept()" + integer);
                        stringBuffer.append("\n");
                    }
                });
        return stringBuffer.toString();
    }

    /**
     * 其实觉得 doOnNext 应该不算一个操作符，但考虑到其常用性，我们还是咬咬牙将它放在了这里。
     * 它的作用是让订阅者在接收到数据之前干点有意思的事情。假如我们在获取到数据之前想先保存一下它，无疑我们可以这样实现。
     */
    public String doOnNext(StringBuffer stringBuffer) {
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        stringBuffer.append("RxJavaToJava doOnNext");
        stringBuffer.append("\n");
        Observable.just(1, 2, 3)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        stringBuffer.append("保存成功" + integer);
                        stringBuffer.append("\n");
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        stringBuffer.append("accept()" + integer);
                        stringBuffer.append("\n");
                    }
                });
        return stringBuffer.toString();
    }

    /**
     * 如同我们上面可说，interval操作符用于间隔时间执行某个操作，其接受三个参数，分别是第一次发送延迟，间隔时间，时间单位。
     * 如同Log日志一样，第一次延迟了3秒后接收到，后面每次间隔了2秒。
     * 然而，心细的小伙伴可能会发现，由于我们这个是间隔执行，所以当我们的Activity都销毁的时候，
     * 实际上这个操作还依然在进行，所以，我们得花点小心思让我们在不需要它的时候干掉它。
     * 查看源码发现，我们subscribe(Cousumer<?superT>onNext)返回的是Disposable，我们可以在这上面做文章。
     */
    Disposable disposableInterval = null;
    int i = 0;

    public String interval(StringBuffer stringBuffer) {
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        stringBuffer.append("RxJavaToJava timer");
        stringBuffer.append("\n");
        stringBuffer.append("3秒后见log日志,当前：" + DateUtils.INSTANCE.getDateTimeToStr(System.currentTimeMillis()));
        stringBuffer.append("\n");
        disposableInterval = Observable.interval(3, 2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long t) throws Exception {
                        i++;
                        System.out.println("2秒后：" + DateUtils.INSTANCE.getDateTimeToStr(System.currentTimeMillis()));
                        if (i == 2 && disposableInterval != null) {
                            disposableInterval.dispose();
                            i = 0;
                        }
                        System.out.println(" disposable.isDisposed()：" + disposableInterval.isDisposed());
                    }
                });
        return stringBuffer.toString();
    }

    /**
     * timer很有意思，相当于一个定时任务。在1.x中它还可以执行间隔逻辑，但在2.x中此功能被交给了interval，下一个会介绍。
     * 但需要注意的是，timer和interval均默认在新线程。
     */
    public String timer(StringBuffer stringBuffer) {
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        stringBuffer.append("RxJavaToJava timer");
        stringBuffer.append("\n");
        stringBuffer.append("两秒后见log日志,当前：" + DateUtils.INSTANCE.getDateTimeToStr(System.currentTimeMillis()));
        stringBuffer.append("\n");
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long t) throws Exception {
                        System.out.println("两秒后：" + DateUtils.INSTANCE.getDateTimeToStr(System.currentTimeMillis()));
                    }
                });
        return stringBuffer.toString();
    }


    /**
     * @buffer 如图，我们把1,2,3,4,5依次发射出来，经过buffer操作符，
     * 其中参数skip为3，count为4，而我们的输出依次是123，5。显而易见，
     * 我们buffer的第一个参数是count，代表最大取值，在事件足够的时候，一般都是取count个值，
     * 然后每次跳过skip个事件。其实看Log日志，我相信大家都明白了。
     */
    public String buffer(StringBuffer stringBuffer) {
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        stringBuffer.append("RxJavaToJava buffer");
        stringBuffer.append("\n");
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 4)////skip每次跳过几个步长  count每次最多几个
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        if (integers != null) {
                            stringBuffer.append("accept()t.size" + integers.size());
                            stringBuffer.append("\n");
                            for (Integer i : integers) {
                                stringBuffer.append("accept()i" + i);
                                stringBuffer.append("\n");
                            }
                        }
                    }
                });
        return stringBuffer.toString();
    }

    /**
     * @filter 可以看到，我们过滤器舍去了小于 10 的值，所以最好的输出只有 10, 25。
     */
    public String filter(StringBuffer stringBuffer) {
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        stringBuffer.append("RxJavaToJava filter");
        stringBuffer.append("\n");
        Observable.just(1, 10, -65, 25)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer >= 10; //过滤小于10的
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        stringBuffer.append("accept()" + integer);
                        stringBuffer.append("\n");
                    }
                });
        return stringBuffer.toString();
    }

    /**
     * @distinct Log 日志显而易见，我们在经过 dinstinct() 后接收器接收到的事件只有1,2,3了。
     */
    public String distinct(StringBuffer stringBuffer) {
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        stringBuffer.append("RxJavaToJava distinct");
        stringBuffer.append("\n");
        Observable.just(1, 1, 2, 3, 3)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        stringBuffer.append("accept()" + integer);
                        stringBuffer.append("\n");
                    }
                });
        return stringBuffer.toString();
    }

    /**
     * @concatMap 上面其实就说了，concatMap与FlatMap的唯一区别就是concatMap保证了顺序，
     * 所以，我们就直接把flatMap替换为concatMap验证吧。
     */
    public String concatMap(StringBuffer stringBuffer) {
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        stringBuffer.append("RxJavaToJava concatMap");
        stringBuffer.append("\n");
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
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    stringBuffer.append("this is result " + integer);
                    stringBuffer.append("\n");
                    list.add("this is result " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        stringBuffer.append("accept()" + s);
                        stringBuffer.append("\n");
                    }
                });
        return stringBuffer.toString();
    }

    /*
     * @flatMap FlatMap是一个很有趣的东西，我坚信你在实际开发中会经常用到。
     * 它可以把一个发射器Observable通过某种方法转换为多个Observables，
     * 然后再把这些分散的Observables装进一个单一的发射器Observable。
     * 但有个需要注意的是，flatMap并不能保证事件的顺序，如果需要保证，需要用到我们下面要讲的ConcatMap。
     * <p>
     * 一切都如我们预期中的有意思，为了区分concatMap（下一个会讲），我在代码中特意动了一点小手脚，我采用一个随机数，
     * 生成一个时间，然后通过delay（后面会讲）操作符，做一个小延时操作，而查看Log日志也确认验证了我们上面的说法，它是无序的。
     */
    public String flatMap(StringBuffer stringBuffer) {
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        stringBuffer.append("RxJavaToJava flatMap");
        stringBuffer.append("\n");
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
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    stringBuffer.append("this is result " + integer);
                    stringBuffer.append("\n");
                    list.add("this is result " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        stringBuffer.append("accept()" + s);
                        stringBuffer.append("\n");
                    }
                });
        return stringBuffer.toString();
    }


    /**
     * @concat 对于单一的把两个发射器连接成一个发射器，虽然zip不能完成，但我们还是可以自力更生，
     * 官方提供的concat让我们的问题得到了完美解决。
     * 如图，可以看到。发射器B把自己的三个孩子送给了发射器A，让他们组合成了一个新的发射器，非常懂事的孩子，有条不紊的排序接收。
     */
    public String concat(StringBuffer stringBuffer) {
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        stringBuffer.append("RxJavaToJava concat");
        stringBuffer.append("\n");
        Observable.concat(Observable.just(1, 2, 3)
                , Observable.just(4, 5, 6, 7)
                , Observable.just("8", "9"))
                .subscribe(new Consumer<Serializable>() {
                    @Override
                    public void accept(Serializable serializable) throws Exception {
                        if (serializable instanceof Integer) {
                            stringBuffer.append("accept()" + serializable);
                            stringBuffer.append("\n");
                        }

                        if (serializable instanceof String) {
                            stringBuffer.append("accept()" + serializable);
                            stringBuffer.append("\n");
                        }
                    }
                });
        return stringBuffer.toString();
    }


    /**
     * @zip zip专用于合并事件，该合并不是连接（连接操作符后面会说），而是两两配对，也就意味着，
     * 最终配对出的Observable发射事件数目只和少的那个相同。
     * <p>
     * zip组合事件的过程就是分别从发射器A和发射器B各取出一个事件来组合，并且一个事件只能被使用一次，
     * 组合的顺序是严格按照事件发送的顺序来进行的，所以上面截图中，可以看到，1永远是和A结合的，2永远是和B结合的。
     * <p>
     * 最终接收器收到的事件数量是和发送器发送事件最少的那个发送器的发送事件数目相同，所以如截图中，
     * 3很孤单，没有人愿意和它交往，孤独终老的单身狗。
     */
    public String zip(StringBuffer stringBuffer) {
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        stringBuffer.append("RxJavaToJava zip");
        stringBuffer.append("\n");
        Observable observableStr = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    stringBuffer.append("emitter a");
                    stringBuffer.append("\n");
                    emitter.onNext("a");
                    stringBuffer.append("emitter b");
                    stringBuffer.append("\n");
                    emitter.onNext("b");
//                stringBuffer.append("emitter c");
//                stringBuffer.append("\n");
//                emitter.onNext("c");
                    stringBuffer.append("emitter onComplete()");
                    stringBuffer.append("\n");
                    emitter.onComplete();
                }
            }
        });

        Observable observableInt = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    stringBuffer.append("emitter 1");
                    stringBuffer.append("\n");
                    emitter.onNext(1);
                    stringBuffer.append("emitter 2");
                    stringBuffer.append("\n");
                    emitter.onNext(2);
                    stringBuffer.append("emitter 3");
                    stringBuffer.append("\n");
                    emitter.onNext(3);
                    stringBuffer.append("emitter onComplete()");
                    stringBuffer.append("\n");
                    emitter.onComplete();
                }
            }
        });

        Observable.zip(observableStr, observableInt, new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                stringBuffer.append("accept()" + s);
                stringBuffer.append("\n");
            }
        });
        return stringBuffer.toString();
    }


    /**
     * @Map Map基本算是RxJava中一个最简单的操作符了，熟悉RxJava1.x的知道，
     * 它的作用是对发射时间发送的每一个事件应用一个函数，使得每一个事件都按照指定的函数去变化，而在2.x中它的作用几乎一致。
     * 是的，map基本作用就是将一个Observable通过某种函数关系，转换为另一种Observable，
     * 上面例子中就是把我们的Integer数据变成了String类型。从Log日志显而易见。
     */
    public String map(StringBuffer stringBuffer) {
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        stringBuffer.append("RxJavaToJava map");
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
//                stringBuffer.append("emitter 3");
//                stringBuffer.append("\n");
//                emitter.onNext(3);
                stringBuffer.append("emitter onComplete()");
                stringBuffer.append("\n");
                emitter.onComplete();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "this is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String str) throws Exception {
                stringBuffer.append("accept()" + str);
                stringBuffer.append("\n");
            }
        });
        return stringBuffer.toString();
    }


    /**
     * 同线程 简单订阅
     *
     * @ObservableEmitter：发射器
     * @Disposable：解除订阅关系
     * @create create操作符应该是最常见的操作符了，主要用于产生一个Obserable被观察者对象，为了方便大家的认知，
     * 以后的教程中统一把被观察者Observable称为发射器（上游事件），观察者Observer称为接收器（下游事件）。
     * @a 在发射事件中，我们在发射了数值3之后，直接调用了e.onComlete()，虽然无法接收事件，但发送事件还是继续的。
     * @b 另外一个值得注意的点是，在RxJava2.x中，可以看到发射事件方法相比1.x多了一个throws Excetion，意味着我们做一些特定操作再也不用try-catch了。
     * @Disposable 并且2.x中有一个Disposable概念，这个东西可以直接调用切断，可以看到，当它的isDisposed()返回为false的时候，
     * 接收器能正常接收事件，但当其为true的时候，接收器停止了接收。所以可以通过此参数动态控制接收事件了。
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
                if (integer == 1) {
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
