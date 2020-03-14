package com.spark.mvvmjava.bean;

import java.io.Serializable;
import java.util.ArrayList;

/*************************************************************************************************
 * 日期：2020/3/13 11:25
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class HomeFatherBean implements Serializable {
    private ArrayList<HomeBean> datas;

    public ArrayList<HomeBean> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<HomeBean> datas) {
        this.datas = datas;
    }
}
