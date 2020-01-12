package com.spark.mvvmjava.bean.roomdao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.spark.mvvmjava.bean.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

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

1、在数据库执行@Insert、@Delete、@Update操作时候可以使用（注意是可以使用）RxJava里的类型有：Completable，Single，Maybe
2、在执行@Query操作时，可以返回的类型有：Single，Maybe，Observable，Flowable
这里需要注意：

你想一次性查询就用： Single，Maybe；这样的话，如果查询数据库之后数据库有改变时，后面不会有任何事务。
如果你是想观察数据库:  Observable，Flowable。那么当已经查询数据了，如果之后数据还有改变，那么将自动执行Observable，Flowable里观察的代码。意思就是对数据可持续观察，实时显示数据库中最新的数据

这里可能大家对Single，Maybe，Completable，Observable，Flowable不大了解，这里做个简单介绍：
1、Completable：只有onComplete和onError方法，即只有“完成”和“错误”两种状态，不会返回具体的结果。
2、Single：其回调为onSuccess和onError，查询成功会在onSuccess中返回结果，需要注意的是，如果未查询到结果，即查询结果为空，会直接走onError回调，抛出EmptyResultSetException异常。
3、Maybe：其回调为onSuccess，onError，onComplete，查询成功，如果有数据，会先回调onSuccess再回调onComplete，如果没有数据，则会直接回调onComplete。
4、Flowable/Observable：这是返回一个可观察的对象，查询的部分有变化时，都会回调它的onNext方法，没有数据变化的话，不回调。直到Rx流断开。
*/
@Dao
public interface UserDao {

    /**
     * xJava获取全部
     * 根据uuid去查找数据
     */
    @Query("Select * from user Where uuid = :uuid")
    Single<User> getUserUuidRx(long uuid);

    /**
     * RxJava获取全部
     * 查询所有数据
     */
    @Query("Select * from user")
    Flowable<List<User>> getAllRx();

    /**
     * 插入单条或者多条数据
     */
//    @Insert(onConflict = OnConflictStrategy.REPLACE),这个是干嘛的呢，下面有详细教程
    @Insert
    List<Long> insertL(User... users);


    @Query("select * from user where uuid >=:minId and uuid<=:maxId")
    LiveData<List<User>> getToListData(int minId, int maxId);

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
    @Query("DELETE FROM user")
    void deleteAll();

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
