package com.spark.mvvmjavastudy.ui;

import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.library.common.utils.LogUtils;
import com.spark.mvvmjavastudy.DBInstance;
import com.spark.mvvmjavastudy.R;
import com.spark.mvvmjavastudy.bean.Dog;
import com.spark.mvvmjavastudy.bean.User;
import com.spark.mvvmjavastudy.databinding.ActivityRoomBinding;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 简介：
 * Room是google为了简化旧式的SQLite操作专门提供的一个覆盖SQLite抽象层框架库
 * 作用：
 * 实现SQLite的增删改查（通过注解的方式实现增删改查，类似Retrofit。）
 * <p>
 * 在使用Room，有4个模块：
 * <p>
 * Bean：实体类，表示数据库表的数据
 * Dao：数据操作类，包含用于访问数据库的方法
 * Database：数据库持有者 & 数据库版本管理者
 * Room：数据库的创建者 & 负责数据库版本更新的具体实现者
 * <p>
 * 注解
 *
 * @Entity ： 数据表的实体类。
 * @PrimaryKey ： 每一个实体类都需要一个唯一的标识。
 * @ColumnInfo ： 数据表中字段名称。
 * @Ignore ： 标注不需要添加到数据表中的属性。
 * @Embedded ： 实体类中引用其他实体类。
 * @ForeignKey ： 外键约束。
 * <p>
 * Dao
 * @Dao ： 标注数据库操作的类。
 * @Query ： 包含所有Sqlite语句操作。
 * @Insert ： 标注数据库的插入操作。
 * @Delete ： 标注数据库的删除操作。
 * @Update ： 标注数据库的更新操作。
 */
public class RoomActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LiveDataActivity";

    private ActivityRoomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_model);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room);
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvInsert1:
                User user = new User("第一条", "男", 12);
                User user2 = new User("第二条", "女", 12, new Dog("小狗", "白色"));
                DBInstance.getInstance().getUserDao().insert(user, user2);
                break;
            case R.id.tvGetRxJavaRoomUsers:
                DBInstance.getInstance().getUserDao().getAllRx()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<User>>() {
                            @Override
                            public void accept(List<User> users) throws Exception {
                                binding.tvGetRxJavaRoomUsers.setText("RxJavaRoom获取数据库条数:" + users.size());
                            }
                        });
                break;
            case R.id.tvGetRxJavaRoomCompletable:
                Completable.fromCallable(new Callable<List<Long>>() {
                    @Override
                    public List<Long> call() throws Exception {
                        User user = new User("Completable", "男", 12);
                        return DBInstance.getInstance().getUserDao().insertL(user);
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                LogUtils.INSTANCE.i(TAG, "tvGetRxJavaRoomCompletable onComplete: ");
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtils.INSTANCE.i(TAG, "tvGetRxJavaRoomCompletable onError: e.getMessage()：" + e.getMessage());
                            }
                        });

                break;
            case R.id.tvGetRxJavaRoomSingle:
               /* .subscribeOn(Schedulers.io())//也可以设置无返回值
                .subscribe();//也可以设置无返回值*/
                Single.fromCallable(new Callable<List<Long>>() {
                    @Override
                    public List<Long> call() throws Exception {
                        User user = new User("第100条", "男", 12);
                        return DBInstance.getInstance().getUserDao().insertL(user);
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<List<Long>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(List<Long> longs) {
                                for (Long data : longs) {
                                    LogUtils.INSTANCE.i(TAG, "tvGetRxJavaRoomSingle onSuccess：" + data);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtils.INSTANCE.i(TAG, "tvGetRxJavaRoomSingle onError: e.getMessage()：" + e.getMessage());
                            }
                        });
                break;
            case R.id.tvGetRxJavaRoomMaybe:
               /* .subscribeOn(Schedulers.io())//也可以设置无返回值
                .subscribe();//也可以设置无返回值*/
                Maybe.fromCallable(new Callable<List<Long>>() {
                    @Override
                    public List<Long> call() throws Exception {
                        User user = new User("第100条", "男", 12);
                        return DBInstance.getInstance().getUserDao().insertL(user);
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MaybeObserver<List<Long>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(List<Long> longs) {
                                for (Long data : longs) {
                                    LogUtils.INSTANCE.i(TAG, "tvGetRxJavaRoomMaybe onSuccess：" + data);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtils.INSTANCE.i(TAG, "tvGetRxJavaRoomMaybe onError: e.getMessage()：" + e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                LogUtils.INSTANCE.i(TAG, "tvGetRxJavaRoomMaybe onComplete()：");
                            }
                        });
                break;

            case R.id.tvRoomLiveData:
                DBInstance.getInstance().getUserDao().getToListData(2, 12)
                        .observe(this, new Observer<List<User>>() {
                            @Override
                            public void onChanged(List<User> users) {
                                LogUtils.INSTANCE.i(TAG, "tvRoomLiveData" + users.size());
                                for (int i = 0; i < users.size(); i++) {
                                    User user1 = users.get(i);
                                    LogUtils.INSTANCE.i(TAG, "user1.getName()" + user1.getName());
                                }
                            }
                        });
                break;
        }
    }
}
