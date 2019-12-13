package com.library.common.test

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.functions.Function
import java.util.ArrayList
import java.util.concurrent.TimeUnit


/*************************************************************************************************
 * 日期：2019/12/11 16:17
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
object RxJavaTestKotlin {
    private val TAG = "RxJavaTestKotlin"


    @JvmStatic
    fun main(args: Array<String>) {
        println(Thread.currentThread().name)
//        test1()
//        test2()
//        testMap()
//        testFlatMap()
//        testZip()
    }

    /**
     * 比如一个界面需要展示用户的一些信息, 而这些信息分别要从两个服务器接口中获取, 而只有当两个都获取到了之后才能进行展示, 这个时候就可以用Zip了:
     * Observable<UserBaseInfoResponse> observable1 =
    api.getUserBaseInfo(new UserBaseInfoRequest()).subscribeOn(Schedulers.io());

    Observable<UserExtraInfoResponse> observable2 =
    api.getUserExtraInfo(new UserExtraInfoRequest()).subscribeOn(Schedulers.io());

    Observable.zip(observable1, observable2,
    new BiFunction<UserBaseInfoResponse, UserExtraInfoResponse, UserInfo>() {
    @Override
    public UserInfo apply(UserBaseInfoResponse baseInfo,
    UserExtraInfoResponse extraInfo) throws Exception {
    return new UserInfo(baseInfo, extraInfo);
    }
    }).observeOn(AndroidSchedulers.mainThread())
    .subscribe(new Consumer<UserInfo>() {
    @Override
    public void accept(UserInfo userInfo) throws Exception {
    //do something;
    }
    });

    作者：Season
    链接：https://juejin.im/post/584a6dd9128fe100589bf29d
    来源：掘金
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */

    /**
     *Zip通过一个函数将多个Observable发送的事件结合到一起，然后发送这些组合到一起的事件.
     *它按照严格的顺序应用这个函数。它只发射与发射数据项最少的那个Observable一样多的数据。
     *
     *其中一根水管负责发送圆形事件，另外一根水管负责发送三角形事件，通过Zip操作符，
     *使得圆形事件和三角形事件 合并为了一个矩形事件。
     *
     * 组合的过程是分别从 两根水管里各取出一个事件 来进行组合, 并且一个事件只能被使用一次,
     * 组合的顺序是严格按照事件发送的顺利 来进行的, 也就是说不会出现圆形1 事件和三角形B 事件进行合并,
     * 也不可能出现圆形2 和三角形A 进行合并的情况.

     * 最终下游收到的事件数量 是和上游中发送事件最少的那一根水管的事件数量 相同.
     * 这个也很好理解, 因为是从每一根水管 里取一个事件来进行合并, 最少的 那个肯定就最先取完 ,
     * 这个时候其他的水管尽管还有事件 , 但是已经没有足够的事件来组合了, 因此下游就不会收到剩余的事件了.
     */
    fun testZip() {
        val observable1 = Observable.create(ObservableOnSubscribe<Int> { emitter ->
            println("emit 1")
            emitter.onNext(1)
            Thread.sleep(1000)

            println("emit 2")
            emitter.onNext(2)
            Thread.sleep(1000)

            println("emit 3")
            emitter.onNext(3)
            Thread.sleep(1000)

            println("emit 4")
            emitter.onNext(4)
            Thread.sleep(1000)

            println("emit complete1")
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())

        val observable2 = Observable.create(ObservableOnSubscribe<String> { emitter ->
            println("emit A")
            emitter.onNext("A")
            Thread.sleep(1000)

            println("emit B")
            emitter.onNext("B")
            Thread.sleep(1000)

            println("emit C")
            emitter.onNext("C")
            Thread.sleep(1000)

            println("emit complete2")
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())

        Observable.zip(observable1, observable2, object :
            BiFunction<Int, String, String> {

            override fun apply(t1: Int, t2: String): String {
                return t1.toString() + t2
            }
        }).subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                println("onSubscribe")
            }

            override fun onNext(value: String) {
                println("onNext: $value")
            }

            override fun onError(e: Throwable) {
                println("onError")
            }

            override fun onComplete() {
                println("onComplete")
            }
        })
    }


    /** 两个接口前后调用  注册--登录
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
    private fun testFlatMap() {
        Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
//            it.onComplete()
        })
            .flatMap(object : Function<Int, ObservableSource<String>> {
                override fun apply(t: Int): ObservableSource<String> {
                    val list = ArrayList<String>()
                    for (i in 0..3) {
//                        println("flatMap$t1")
                        list.add("this$t")
                    }
                    return Observable.fromIterable(list)
                }
            })
            .subscribe {
                println(it)
            }
    }

    /**
     * 通过Map, 可以将上游发来的事件转换为任意的类型, 可以是一个Object, 也可以是一个集合,
     */
    private fun testMap() {
        Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
            it.onComplete()
        }).map(object : Function<Int, String> {
            override fun apply(t: Int): String {
                return "this$t"
            }
        }).subscribe { println(it) }
    }


    /**
     * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
     * Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
     * Schedulers.newThread() 代表一个常规的新线程
     * AndroidSchedulers.mainThread()  代表Android的主线程
     *
     * 出现这个问题的原因是：AndroidSchedulers.mainThread返回的是LooperScheduler的一个实例，
     * 但是LooperScheduler依赖于Android，我们在纯Java的单元测试中是无法使用它的。
     * 找到了问题的原因解决起来就容易多了。我们需要在单元测试运行之前，
     * 初始化RxAndroidPlugins让它返回一个我们指定的不依赖于Android的Scheduler。下面给出两种解决方案。
     */
    private fun test2() {
        println("test2：" + Thread.currentThread().name)
        Observable.create(ObservableOnSubscribe<String> {
            println(Thread.currentThread().name)
            it.onNext("线程")
            it.onComplete()
        }).subscribeOn(Schedulers.newThread())//上游发送事件的线程 指定第一个有效
//            .observeOn(AndroidSchedulers.mainThread())//下游接收事件的线程  下游可以改变线程
//            .observeOn(Schedulers.io())//下游接收事件的线程  下游可以改变线程
            .doOnNext {
                println("doOnNext$it")
                println(Thread.currentThread().name)
            }
            .observeOn(Schedulers.io())//下游接收事件的线程  下游可以改变线程
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
                    println("onSubscribe")
                }

                override fun onNext(t: String) {
                    println("onNext$t")
                    println(Thread.currentThread().name)
                }

                override fun onError(e: Throwable) {
                    println("onError")
                }

                override fun onComplete() {
                    println("onComplete")
                }
            })
    }

    /**
     * 同线程 简单订阅   ObservableEmitter：发射器   Disposable：开关
     */
    private fun test1() {
        //创建一个上游 Observable：
        val observable = Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
            it.onComplete()
        })
        val observer = object : Observer<Int> {
            //创建一个下游 Observer
            override fun onSubscribe(d: Disposable) {
                println("常规:onSubscribe")
//                d.dispose()
            }

            override fun onNext(t: Int) {
                println("常规:$t")
            }

            override fun onError(e: Throwable) {
                println("常规:onError")
            }

            override fun onComplete() {
                println("常规:onComplete")
            }
        }
//        observable.subscribe(observer)//建立连接

        //链式操作
        Observable.create(ObservableOnSubscribe<Int> {
            println("it 4")
            it.onNext(4)
            println("it 5")
            it.onNext(5)
            println("it 6")
            it.onNext(6)
            println("it 7")
            it.onNext(7)
            println("it 8")
            it.onNext(8)
            println("it onComplete()")
            it.onComplete()
        }).subscribe(object : Observer<Int> {
            lateinit var disposable: Disposable
            var i: Int = 0
            override fun onSubscribe(d: Disposable) {
                disposable = d;
                println("链式:onSubscribe")
            }

            override fun onNext(t: Int) {
                println("链式onNext:$t")
//                if (t1 == 6 && disposable != null) {
//                    println("链式disposable.isDisposed:" + disposable.isDisposed)
//                    disposable.dispose()
//                    println("链式disposable.isDisposed:" + disposable.isDisposed)
//                }
            }

            override fun onError(e: Throwable) {
                println("链式:onError")
            }

            override fun onComplete() {
                println("链式:onComplete")
                println("链式:onComplete:" + (disposable.isDisposed))
            }
        })
    }
}