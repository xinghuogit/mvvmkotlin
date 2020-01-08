package com.spark.mvvmjava;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.spark.mvvmjava.bean.Book;
import com.spark.mvvmjava.bean.User;
import com.spark.mvvmjava.bean.roomdao.UserDao;

/*************************************************************************************************
 * 日期：2020/1/8 17:23
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
//注解指定了database的表映射实体数据以及版本等信息(后面会详细讲解版本升级)
@Database(entities = {User.class}, version = 1)
public abstract class AppRoomDataBase extends RoomDatabase {
    public abstract UserDao getUserDao();

//    public abstract Book getBook();
}

