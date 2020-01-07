package com.spark.mvvmjava;

/*************************************************************************************************
 * 日期：2020/1/7 11:32
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class MVVMData {
    private String title;
    private String desc;

    public MVVMData() {
    }

    public MVVMData(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
