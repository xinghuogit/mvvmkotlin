package com.spark.mvvmjava.base.mvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.JsonSyntaxException;
import com.library.common.base.BaseActivity;
import com.library.common.base.BaseFragment;
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
public abstract class MVVMBaseFragment<VM extends BaseViewModel, VDB extends ViewDataBinding> extends BaseFragment {

    protected VM mViewModel;
    protected VDB binding;
    protected View mContentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            binding = DataBindingUtil.inflate(inflater, getContentViewId(), null, false);//初始化binding
            mContentView = binding.getRoot();
            binding.setLifecycleOwner(this);///给binding加上感知生命周期，BaseActivity就是lifeOwner，ComponentActivity实现lifeOwner
            createViewModel();
            initView(mContentView);
            initData();
            initListener();
            setData();
        } else {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
        return mContentView;
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
            mViewModel.setLifecycleTransformer(bindToLifecycle());
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
