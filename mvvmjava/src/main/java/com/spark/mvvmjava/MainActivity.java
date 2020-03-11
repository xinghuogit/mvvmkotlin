package com.spark.mvvmjava;

import android.view.View;

import androidx.lifecycle.Observer;

import com.library.common.utils.DateUtils;
import com.library.common.utils.LogUtils;
import com.spark.mvvmjava.base.mvvm.MVVMBaseActivity;
import com.spark.mvvmjava.bean.Advert;
import com.spark.mvvmjava.databinding.ActivityMainBinding;
import com.spark.mvvmjava.network.Resource;
import com.spark.mvvmjava.utils.GlideImageLoader;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MVVMBaseActivity<MainViewModel, ActivityMainBinding> implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        binding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        binding.banner.setImageLoader(new GlideImageLoader());
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        binding.btn.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                getAdverts();
                LogUtils.INSTANCE.i(TAG, "点击时间：" + DateUtils.INSTANCE.getTimeStampToDateTime(System.currentTimeMillis()) + "  System.currentTimeMillis()：" + System.currentTimeMillis());
                break;
        }
    }

    private void getAdverts() {
        mViewModel.getAdverts().observe(this, new Observer<Resource<List<Advert>>>() {
            @Override
            public void onChanged(Resource<List<Advert>> listResource) {
                listResource.handler(new OnCallback<List<Advert>>() {
                    @Override
                    public void onSuccess(List<Advert> data) {
                        updateBanner(data);
                    }
                });
            }
        });
    }

    private void updateBanner(List<Advert> adverts) {
        if (adverts != null && !adverts.isEmpty()) {
            List<String> urls = new ArrayList<>();
            List<String> titles = new ArrayList<>();
            for (int i = 0; i < adverts.size(); i++) {
                titles.add(adverts.get(i).getTitle());
                urls.add(adverts.get(i).getImagePath());
            }
            binding.banner.setBannerTitles(titles);
            binding.banner.setImages(urls);
            binding.banner.start();
        }
    }
}
