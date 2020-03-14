package com.spark.mvvmjava.ui.test;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.spark.mvvmjava.R;
import com.spark.mvvmjava.base.mvvm.MVVMBaseActivity;
import com.spark.mvvmjava.databinding.ActivityMainBinding;
import com.spark.mvvmjava.ui.test.home.HomeFragment;

public class TestActivity extends MVVMBaseActivity<TestViewModel, ActivityMainBinding> implements View.OnClickListener {
    private static final String TAG = "TestActivity";

    private View currentView;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (AppUtils.isFirstStart(this, getPackageName())) {
//            IntentWrapper.whiteListMatters(this, "后台持续运行");
//        }
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setCurrentItem(0);
        binding.viewPager.setOffscreenPageLimit(3);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        setCurrentView(binding.viewBottom.tvHome);
                        break;
                    case 1:
                        setCurrentView(binding.viewBottom.tvFind);
                        break;
                    case 2:
                        setCurrentView(binding.viewBottom.tvMy);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void initListener() {
        binding.viewBottom.tvHome.setOnClickListener(this);
        binding.viewBottom.tvFind.setOnClickListener(this);
        binding.viewBottom.tvMy.setOnClickListener(this);
    }

    @Override
    public void setData() {
        setCurrentView(binding.viewBottom.tvHome);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvHome:
                binding.viewPager.setCurrentItem(0);
                setCurrentView(v);
                break;
            case R.id.tvFind:
                binding.viewPager.setCurrentItem(1);
                setCurrentView(v);
                break;
            case R.id.tvMy:
                binding.viewPager.setCurrentItem(2);
                setCurrentView(v);
                break;
        }
    }

    public void setCurrentView(View view) {
        if (currentView != null && view.equals(currentView)) {
            return;
        }
        currentView = view;
        removeSelected();
        view.setSelected(true);
    }

    private void removeSelected() {
        binding.viewBottom.tvHome.setSelected(false);
        binding.viewBottom.tvFind.setSelected(false);
        binding.viewBottom.tvMy.setSelected(false);
    }

    class PagerAdapter extends FragmentPagerAdapter {

        private Fragment[] fragmentArrays = new Fragment[1];

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentArrays[0] = new HomeFragment();
//            fragmentArrays[1] = new HomeFragment();
//            fragmentArrays[2] = new HomeFragment();
        }

        @Override
        public Fragment getItem(int position) {
            if (fragmentArrays[position] == null) {
                switch (position) {
                    case 0:
                        fragmentArrays[position] = new HomeFragment();
                        break;
                    case 1:
//                        fragmentArrays[position] = new HomeFragment();
                        break;
                    case 2:
//                        fragmentArrays[position] = new HomeFragment();
                        break;
                }
            }
            return fragmentArrays[position];
        }

        @Override
        public int getCount() {
            return fragmentArrays.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
