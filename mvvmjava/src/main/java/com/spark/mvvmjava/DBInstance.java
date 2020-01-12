package com.spark.mvvmjava;

import androidx.room.Room;

/*************************************************************************************************
 * 日期：2020/1/8 17:27
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class DBInstance {
    private static final String DBName = "aa";
    private static AppRoomDataBase appRoomDataBase;

    public static AppRoomDataBase getInstance() {
        if (appRoomDataBase == null) {
            synchronized (DBInstance.class) {
                if (appRoomDataBase == null) {
                    appRoomDataBase = Room.databaseBuilder(BaseApplication.getInstance(), AppRoomDataBase.class, DBName)
                            /*allowMainThreadQueries()表示允许主线程进行数据库操作，但不推荐这样做。
                            在这里是为了Demo展示，稍后会结束和LiveData和RxJava的使用*/
//                            .allowMainThreadQueries()
                            /*.addMigrations(AppRoomDataBase.migration_1_2,AppRoomDataBase.migration_2_3)//例子*/
                            .addMigrations(AppRoomDataBase.migration_1_2)
                            .build();
                }
            }
        }
        return appRoomDataBase;
    }
}
