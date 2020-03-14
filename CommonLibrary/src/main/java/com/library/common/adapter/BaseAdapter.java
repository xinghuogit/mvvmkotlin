package com.library.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.library.common.callback.OnClickListener;
import com.library.common.callback.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/*************************************************************************************************
 * 日期：2020/3/13 15:15
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public abstract class BaseAdapter<T> extends RecyclerView.Adapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    private List<T> mItems;

    protected OnClickListener onClickListener;
    protected OnItemClickListener onItemClickListener;

    public BaseAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final RecyclerView.ViewHolder holder = getViewHolder(parent, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder(holder, mItems.get(position), position);
    }

    protected abstract RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType);

    protected abstract void onBindViewHolder(RecyclerView.ViewHolder holder, T item, int position);

    public void updateItems(List<T> list) {
        this.mItems = list;
        notifyDataSetChanged();
    }

    public void addItems(List<T> list) {
        if (this.mItems == null) this.mItems = new ArrayList<>();
        this.mItems.addAll(list);
        notifyDataSetChanged();
    }

    final T getItem(int position) {
        if (position < 0 || position >= mItems.size())
            return null;
        return mItems.get(position);
    }

    public List<T> getItems() {
        return mItems;
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
