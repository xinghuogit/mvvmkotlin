package com.spark.mvvmjava.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.spark.mvvmjava.DBInstance;
import com.spark.mvvmjava.R;
import com.spark.mvvmjava.bean.Dog;
import com.spark.mvvmjava.bean.User;
import com.spark.mvvmjava.bean.YellowDog;
import com.spark.mvvmjava.databinding.ActivityLivedataBinding;
import com.spark.mvvmjava.databinding.ActivityRoomBinding;
import com.spark.mvvmjava.viewmodel.LiveDataViewModel;

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
        }
    }
}
