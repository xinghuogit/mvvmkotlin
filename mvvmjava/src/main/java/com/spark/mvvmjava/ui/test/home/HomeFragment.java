package com.spark.mvvmjava.ui.test.home;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.library.common.callback.OnClickListener;
import com.library.common.callback.OnItemClickListener;
import com.library.common.utils.DateUtils;
import com.library.common.utils.LogUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.spark.mvvmjava.R;
import com.spark.mvvmjava.base.AppConstant;
import com.spark.mvvmjava.base.mvvm.MVVMBaseFragment;
import com.spark.mvvmjava.bean.Advert;
import com.spark.mvvmjava.bean.HomeBean;
import com.spark.mvvmjava.bean.HomeFatherBean;
import com.spark.mvvmjava.databinding.FragmentHomeBinding;
import com.spark.mvvmjava.network.ParamsBuilder;
import com.spark.mvvmjava.network.Resource;
import com.spark.mvvmjava.ui.test.login.LoginActivity;
import com.spark.mvvmjava.utils.ActivityUtils;
import com.spark.mvvmjava.utils.GlideImageLoader;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends MVVMBaseFragment<HomeViewModel, FragmentHomeBinding> implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener, OnClickListener, OnItemClickListener {
    private static final String TAG = "MainActivity";
    private HomeAdapter adapter;
    private ArrayList<HomeBean> homeBeans = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        initBanner();
    }

    @Override
    public void initData() {
        LogUtils.INSTANCE.i(TAG, "initData时间：" + DateUtils.INSTANCE.getTimeStampToDateTime(System.currentTimeMillis())
                + "  System.currentTimeMillis()：" + System.currentTimeMillis());
        getAdverts();
        onRefresh(binding.smartRefreshLayout);
        adapter = new HomeAdapter(getActivity());
        adapter.setOnClickListener(this);
        adapter.setOnItemClickListener(this);
        binding.rvHomeArticles.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        binding.btnLogin.setOnClickListener(this);
        binding.btnOutLogin.setOnClickListener(this);
        binding.smartRefreshLayout.setOnRefreshListener(this);
        binding.smartRefreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    public void setData() {
    }

    private void initBanner() {
        binding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        binding.banner.setImageLoader(new GlideImageLoader());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btnOutLogin:
                AppConstant.logOut();
                break;
            case R.id.tvCollect:
                if (!AppConstant.isLogin) {
                    ActivityUtils.startActivity(getActivity(), LoginActivity.class);
                    return;
                }
                TextView tvCollect = (TextView) v;
                HomeBean homeBean = (HomeBean) v.getTag();
                if (homeBean.isCollect()) {
                    tvCollect.setText("未收藏");
                    homeBean.setCollect(false);
                    unCollectArt(homeBean.getId());
                } else {
                    tvCollect.setText("已收藏");
                    homeBean.setCollect(true);
                    collectArt(homeBean);
                }
                break;
        }
    }

    @Override
    public void onItemClick(int position, Object o) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageIndex = 0;
        getHomeArticles();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageIndex++;
        getHomeArticles();
    }

    private void getAdverts() {
        mViewModel.getBannerList().observe(this, new Observer<Resource<List<Advert>>>() {
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

    private void getHomeArticles() {
        mViewModel.getHomeArticles(pageIndex, ParamsBuilder.build().isShowLoading(false)).observe(this
                , homeFatherBeanResource -> homeFatherBeanResource.handler(new OnCallback<HomeFatherBean>() {
                    @Override
                    public void onSuccess(HomeFatherBean data) {
                        if (data.getDatas() != null) {
                            if (data.getDatas().size() > 0) {
                                if (pageIndex == 1) homeBeans.clear();
                                homeBeans.addAll(data.getDatas());
                                adapter.updateItems(homeBeans);
                            } else {
                                binding.smartRefreshLayout.finishLoadMoreWithNoMoreData();
                            }
                        } else {
                            binding.smartRefreshLayout.finishLoadMoreWithNoMoreData();
                        }
                    }
                }, binding.smartRefreshLayout));
    }

    public void collectArt(HomeBean homeBean) {
        mViewModel.collectArticle(homeBean).observe(this, resource -> {
            resource.handler(new OnCallback<String>() {
                @Override
                public void onSuccess(String data) {

                }
            });
        });
    }

    public void unCollectArt(int id) {
        mViewModel.unCollectByHome(id).observe(this, resource -> {
            resource.handler(new OnCallback<String>() {
                @Override
                public void onSuccess(String data) {

                }
            });
        });
    }
}
