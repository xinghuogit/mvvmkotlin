package com.spark.mvvmjava;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
@Database(entities = {User.class}, version = 2)
public abstract class AppRoomDataBase extends RoomDatabase {
    public abstract UserDao getUserDao();

//    public abstract Book getBook();

    /**
     * 1升级2
     * 数据库变动添加Migration
     */
    public static final Migration migration_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("alter table user add column phone text"); //告诉user表，增添一个String类型的字段phone
        }
    };
}

