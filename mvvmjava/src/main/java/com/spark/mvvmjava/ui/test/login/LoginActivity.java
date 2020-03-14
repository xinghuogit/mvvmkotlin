package com.spark.mvvmjava.ui.test.login;

import android.view.View;

import androidx.lifecycle.Observer;

import com.spark.mvvmjava.R;
import com.spark.mvvmjava.base.AppConstant;
import com.spark.mvvmjava.base.mvvm.MVVMBaseActivity;
import com.spark.mvvmjava.bean.UserInfo;
import com.spark.mvvmjava.databinding.ActivityLoginBinding;
import com.spark.mvvmjava.network.ParamsBuilder;
import com.spark.mvvmjava.network.Resource;

import java.util.HashMap;

public class LoginActivity extends MVVMBaseActivity<LoginViewModel, ActivityLoginBinding> implements View.OnClickListener {
    private static final String TAG = "TestActivity";

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        binding.tvLogin.setOnClickListener(this);
    }

    @Override
    public void setData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLogin:
                HashMap<String, Object> map = new HashMap<>();
                map.put("username", getStringByUI(binding.etPhone));
                map.put("password", getStringByUI(binding.etPassword));
                mViewModel.login(map, ParamsBuilder.build().isShowLoading(true).loadingMessage("登陆中...")).
                        observe(this, new Observer<Resource<UserInfo>>() {
                            @Override
                            public void onChanged(Resource<UserInfo> userInfoResource) {
                                userInfoResource.handler(new OnCallback<UserInfo>() {
                                    @Override
                                    public void onSuccess(UserInfo data) {
                                        AppConstant.updateUserInfo(data);
//                                        ActivityUtils.startHome();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(String message) {
                                        super.onFailure(message);
                                    }

                                    @Override
                                    public void onCompleted() {
                                        super.onCompleted();
                                    }
                                });
                            }
                        });
                break;
        }
    }
}
