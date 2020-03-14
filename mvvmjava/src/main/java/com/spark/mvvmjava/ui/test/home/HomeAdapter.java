package com.spark.mvvmjava.ui.test.home;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.library.common.adapter.BaseAdapter;
import com.library.common.adapter.BaseViewHolder;
import com.spark.mvvmjava.R;
import com.spark.mvvmjava.base.AppConstant;
import com.spark.mvvmjava.bean.HomeBean;
import com.spark.mvvmjava.databinding.ItemHomeBinding;

/*************************************************************************************************
 * 日期：2020/3/13 15:28
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class HomeAdapter extends BaseAdapter<HomeBean> {

    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        ItemHomeBinding binding = DataBindingUtil.inflate(mInflater, R.layout.item_home, viewGroup, false);
        return new BaseViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, HomeBean item, int position) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
        ItemHomeBinding binding = (ItemHomeBinding) baseViewHolder.binding;
        binding.tvTitle.setText(item.getTitle());
        if (TextUtils.isEmpty(item.getSuperChapterName())) {
            binding.tvCome.setText("火星");
        } else {
            binding.tvCome.setText(item.getSuperChapterName());
        }
        if (TextUtils.isEmpty(item.getAuthor())) {
            binding.tvAurtor.setText(item.getShareUser());
        } else {
            binding.tvAurtor.setText(item.getAuthor());
        }

        binding.tvBlogFrom.setText(item.getNiceShareDate());

        if (AppConstant.isLogin) {
            if (item.isCollect()) {
                binding.tvCollect.setText("已收藏");
            } else {
                binding.tvCollect.setText("未收藏");
            }
        } else {
            binding.tvCollect.setText("收藏");
        }
        binding.tvCollect.setTag(item);
        binding.tvCollect.setOnClickListener(onClickListener);
        binding.rootView.setOnClickListener(onItemClickListener);
    }
}
