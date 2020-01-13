package com.library.common.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;

/*************************************************************************************************
 * 日期：2020/1/13 13:09
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：基础Activity
 ************************************************************************************************/
public abstract class BaseActivity extends AppCompatActivity {
    public static int total;//总量
    public static int pageIndex = 1;//当前页码
    public static int pageSize = 20;//每页数量

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        initView();
        ImmersionBar.with(this).init();
    }

    public abstract int getContentViewId();

    public abstract void initView();

    public abstract void initData();

    public abstract void setData();

    public abstract void initListener();

    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }
}
