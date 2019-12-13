package com.spark.myapplication

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/*************************************************************************************************
 * 日期：2019/12/13 17:23
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 */
class RxJavaToKotlin {

    /**
     * 同线程 简单订阅
     * @ObservableEmitter：发射器
     * @Disposable：解除订阅关系
     */
    fun create(stringBuffer: StringBuffer): String {
        stringBuffer.append("\n")
        stringBuffer.append("\n")
        stringBuffer.append("RxJavaToKotlin create")
        stringBuffer.append("\n")
        // 第一步：初始化Observable
        Observable.create(ObservableOnSubscribe<Int> {
            stringBuffer.append("emitter 1")
            stringBuffer.append("\n")
            it.onNext(1)
            stringBuffer.append("emitter 2")
            stringBuffer.append("\n")
            it.onNext(2)
            stringBuffer.append("emitter 3")
            stringBuffer.append("\n")
            it.onNext(3)
            stringBuffer.append("emitter onComplete")
            stringBuffer.append("\n")
            it.onComplete()
        }).subscribe(object : Observer<Int> { // 第三步：订阅
            // 第二步：初始化Observer
            lateinit var mDisposable: Disposable

            override fun onComplete() {
                stringBuffer.append("onComplete():完成")
                stringBuffer.append("\n")
            }

            override fun onSubscribe(d: Disposable) {
                stringBuffer.append("onSubscribe():建立连接")
                stringBuffer.append("\n")
                mDisposable = d;
            }

            override fun onNext(t: Int) {
                stringBuffer.append("onNext():$t")
                stringBuffer.append("\n")
                if (t == 2) {
                    stringBuffer.append("1mDisposable.isDisposed:${mDisposable.isDisposed}")
                    stringBuffer.append("\n")
                    mDisposable.dispose()
                    stringBuffer.append("2mDisposable.isDisposed:${mDisposable.isDisposed}")
                    stringBuffer.append("\n")
                }
            }

            override fun onError(e: Throwable) {
                stringBuffer.append("onError():${e.message}")
                stringBuffer.append("\n")
            }
        })
        return stringBuffer.toString();
    }
}
