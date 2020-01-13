package com.library.common.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/*************************************************************************************************
 * 日期：2020/1/13 13:15
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public abstract class BaseFragment extends Fragment {
    public static int total;//总量
    public static int pageIndex = 1;//当前页码
    public static int pageSize = 20;//每页数量

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        super.onViewCreated(view, savedInstanceState);
    }

    public abstract int getContentViewId();

    public abstract void initView(View view);

    public abstract void initData();

    public abstract void setData();

    public abstract void initListener();

    public <T extends View> T findView(View view, int id) {
        return (T) view.findViewById(id);
    }
}
