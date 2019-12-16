package com.library.common.adapte;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.library.common.R;

import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ItemActivity";

    private Button studyRxJava;
    private RecyclerView mainRecyclerView;

    private List<String> mData = new ArrayList<>();
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

//        mData = Arrays.asList(getResources().getStringArray(R.array.basis_rxjava));
        mData.add("1");
        mData.add("2");
        mData.add("3");
        mData.add("4");
        mData.add("5");

        studyRxJava = (Button) findViewById(R.id.studyRxJava);
        mainRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mainRecyclerView.setLayoutManager(linearLayoutManager);
        if (mainAdapter == null) {
            mainAdapter = new MainAdapter();
        }
        mainRecyclerView.setAdapter(mainAdapter);
        mainAdapter.upDate(mData);
    }

    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

        private List<String> mData = new ArrayList<>();

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MainViewHolder mainViewHolder = new MainViewHolder(LayoutInflater.from
                    (ItemActivity.this).inflate(R.layout.item_recycler, parent, false));
            return mainViewHolder;
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case 1:
                Log.i(TAG, "onClick: 1");
                break;
            case 2:
                Log.i(TAG, "onClick: 2");
                break;
            default:
                break;
        }
    }


}
