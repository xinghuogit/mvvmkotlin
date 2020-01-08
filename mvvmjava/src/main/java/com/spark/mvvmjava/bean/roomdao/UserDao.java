package com.spark.mvvmjava.bean.roomdao;

import android.app.Person;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.spark.mvvmjava.bean.User;

import java.util.List;

/*************************************************************************************************
 * 日期：2020/1/8 17:06
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
/*
这里唯一特殊的就是@Insert。其有一段介绍：对数据库设计时，不允许重复数据的出现。
否则，必然造成大量的冗余数据。实际上，难免会碰到这个问题：冲突。
当我们像数据库插入数据时，该数据已经存在了，必然造成了冲突。
该冲突该怎么处理呢？在@Insert注解中有conflict用于解决插入数据冲突的问题，其默认值为OnConflictStrategy.ABORT。
对于OnConflictStrategy而言，它封装了Room解决冲突的相关策略。

        OnConflictStrategy.REPLACE：冲突策略是取代旧数据同时继续事务
        OnConflictStrategy.ROLLBACK：冲突策略是回滚事务
        OnConflictStrategy.ABORT：冲突策略是终止事务
        OnConflictStrategy.FAIL：冲突策略是事务失败
        OnConflictStrategy.IGNORE：冲突策略是忽略冲突

这里比如在插入的时候我们加上了OnConflictStrategy.REPLACE，
那么往已经有uuid="1"的user表里再插入uuid =1的user数据，那么新数据会覆盖就数据。
如果我们什么都不加，那么久是默认的OnConflictStrategy.ABORT，重复上面的动作，你会发现，程序崩溃了。
也就是上面说的终止事务。其他大家可以自己试试
*/
@Dao
public interface UserDao {

    /**
     * 插入单条或者多条数据
     */
//    @Insert(onConflict = OnConflictStrategy.REPLACE),这个是干嘛的呢，下面有详细教程
    @Insert
    void insert(User... users);

    /**
     * 删除单条或者多条数据
     */
    @Delete
    void delete(User... users);

//    /**
//     * 删除所有数据
//     */
//    @Query("DELETE FROM user")
//    List<User> deleteAll();

    /**
     * 更新单条或者多条数据
     */
    @Update
    void update(User... users);

    /**
     * 根据uuid去查找数据
     */
    @Query("Select * from user Where uuid = :uuid")
    User getUserUuid(long uuid);

    /**
     * 根据多个uuid查找多个数据
     */
    @Query("SELECT * FROM user WHERE uuid = (:userIds)")
    List<User> loadAllByIds(List<Long> userIds);

    /**
     * 根据多个条件查找一个集合
     */
    @Query("select * from user where name = :name and sex =:sex")
//    List<User> getUserByNameSex(String name, String sex);
    User getUserByNameSex(String name, String sex);

    /**
     * 查询所有数据
     */
    @Query("Select * from user")
    List<User> getAll();
}
