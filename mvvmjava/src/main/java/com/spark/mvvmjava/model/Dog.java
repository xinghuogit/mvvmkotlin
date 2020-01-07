package com.spark.mvvmjava.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/*************************************************************************************************
 * 日期：2020/1/7 16:24
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class Dog extends BaseObservable {
    @Bindable
    public String name; //如果是public修饰的，直接用@Bindable
    private String color; //如果是private修饰的，则在get方法使用@Bindable

    public Dog() {
    }

    public Dog(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDataName(String name, String color) {
        this.name = name;
        this.color = color;
        notifyPropertyChanged(com.spark.mvvmjava.BR.name);  //只刷新name字段
    }

    public void setDataAll(String name, String color) {
        this.name = name;
        this.color = color;
        notifyChange();  //刷新全部字段
    }
}
