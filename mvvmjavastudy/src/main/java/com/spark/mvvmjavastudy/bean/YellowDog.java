package com.spark.mvvmjavastudy.bean;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

/*************************************************************************************************
 * 日期：2020/1/7 16:47
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class YellowDog {
    //这里必须是常量,ObservableField<参数类型>
    //其实写上了下面一句，就是BaseObservable，set，get, @Bindable,刷新都封装了。直接看构造方法
    public final ObservableField<String> name = new ObservableField<>();

    //其中也封装了基本数据类型:ObservableInt等
    public final ObservableInt age = new ObservableInt();

    public YellowDog() {
    }

    public YellowDog(String name) {
        this.name.set(name);
    }

    public YellowDog(String name, int age) {
        this.name.set(name);
        this.age.set(age);
    }
}
