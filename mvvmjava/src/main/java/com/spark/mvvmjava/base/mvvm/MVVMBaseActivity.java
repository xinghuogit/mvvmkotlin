package com.spark.mvvmjava.base.mvvm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.JsonSyntaxException;
import com.library.common.base.BaseActivity;
import com.library.common.utils.NetWorkUtils;
import com.library.common.utils.ToastUtils;
import com.spark.mvvmjava.R;
import com.spark.mvvmjava.network.Resource;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/*************************************************************************************************
 * 日期：2020/1/13 13:28
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public abstract class MVVMBaseActivity<VM extends BaseViewModel, VDB extends ViewDataBinding> extends BaseActivity {

    protected VM mViewModel;
    protected VDB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getContentViewId());//初始化binding
        binding.setLifecycleOwner(this);///给binding加上感知生命周期，BaseActivity就是lifeOwner，ComponentActivity实现lifeOwner
        createViewModel();
        initView();
        initData();
        initListener();
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

    public abstract class OnCallback<T> implements Resource.OnHandleCallback<T> {
        @Override
        public void onLoading(String message) {
            showLoading(message);
        }

        @Override
        public void onError(Throwable throwable) {
            if (!NetWorkUtils.isConnected()) {
                ToastUtils.INSTANCE.showToast(getString(R.string.resultNetworkError));
                return;
            }

            if (throwable instanceof ConnectException) {
                ToastUtils.INSTANCE.showToast(getString(R.string.resultServerError));
            } else if (throwable instanceof SocketTimeoutException) {
                ToastUtils.INSTANCE.showToast(getString(R.string.resultServerTimeout));
            } else if (throwable instanceof JsonSyntaxException) {
                ToastUtils.INSTANCE.showToast("数据解析错误");
            } else {
                ToastUtils.INSTANCE.showToast(getString(R.string.resultEmptyError));
            }
        }

        @Override
        public void onFailure(String message) {
            ToastUtils.INSTANCE.showToast(message);
        }

        @Override
        public void onCompleted() {
            hideLoading();
        }

        @Override
        public void onProgress(int progress, long total) {

        }
    }

}
