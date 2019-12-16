package com.library.common.adapte;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.library.common.R;

import java.util.ArrayList;
import java.util.List;

/*************************************************************************************************
 * 日期：2019/12/16 15:13
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class Main1Adapter extends RecyclerView.Adapter<Main1Adapter.MainViewHolder> {
    private static final String TAG = "Main1Adapter";

    private List<String> mData = new ArrayList<>();
    private Context context;


    public Main1Adapter() {
    }

    public Main1Adapter(Context context) {
        this.context = context;
    }

    @Override
    public Main1Adapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Main1Adapter.MainViewHolder mainViewHolder = new Main1Adapter.MainViewHolder(LayoutInflater.from
                (context).inflate(R.layout.item_recycler, parent, false));
        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(Main1Adapter.MainViewHolder holder, int position) {
        holder.tv.setText(mData.get(position));
    }

    public void upDate(List<String> mData) {
        this.mData.clear();
        addDate(mData);
    }


    public void addDate(List<String> mData) {
        if (mData != null) this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MainViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.itemRecyclerName);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (tv.getText().toString().trim()) {
                        case "发送1.2.3后再发送onComplete":
                            Log.i(TAG, "onClick: 1");
                            break;
                        case "发送1.2.3后再发送onComplete的链式操作":
                            Log.i(TAG, "onClick: 2");
                            Log.i(TAG, "onClick: 2");
                            Log.i(TAG, "onClick: 2");
                            Log.i(TAG, "onClick: 2");
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }

}