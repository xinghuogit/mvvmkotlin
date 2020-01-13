package com.library.common.mvvm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import com.library.common.base.BaseActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/*************************************************************************************************
 * 日期：2020/1/13 13:28
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public abstract class MVVMBaseActivity<VM extends BaseViewModel, VDB extends ViewDataBinding> extends BaseActivity {

    private VM mViewModel;
    private VDB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getContentViewId());//初始化binding
        binding.setLifecycleOwner(this);///给binding加上感知生命周期，BaseActivity就是lifeOwner，ComponentActivity实现lifeOwner
        createViewModel();
    }

    public void createViewModel() {
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                modelClass = BaseViewModel.class; //如果没有指定泛型参数，则默认使用BaseViewModel
            }
            mViewModel = (VM) ViewModelProviders.of(this).get(modelClass);
        }
    }
}
