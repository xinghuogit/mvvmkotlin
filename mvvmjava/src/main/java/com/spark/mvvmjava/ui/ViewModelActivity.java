package com.spark.mvvmjava.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.spark.mvvmjava.R;
import com.spark.mvvmjava.databinding.ActivityViewModelBinding;
import com.spark.mvvmjava.ui.fragment.ViewModelFragment;

public class ViewModelActivity extends AppCompatActivity {
    private static final String TAG = "ViewModelActivity";

    private ActivityViewModelBinding binding;
    private com.spark.mvvmjava.viewmodel.ViewModelActivity viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_model);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_model);
        viewModel = ViewModelProviders.of(this).get(com.spark.mvvmjava.viewmodel.ViewModelActivity.class);
        Log.i(TAG, "onCreate: " + viewModel.hashCode());
        addFragment();
    }

    private void addFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ViewModelFragment fragment1 = ViewModelFragment.getInstance(1);
        ViewModelFragment fragment2 = ViewModelFragment.getInstance(2);

        ft.add(R.id.frameContainer1, fragment1);
        ft.add(R.id.frameContainer2, fragment2);
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //viewModel = ViewModelProviders.of(this).get(ViewModelActivity.class);
        Log.i(TAG, "onStart: " + viewModel.hashCode());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //viewModel = ViewModelProviders.of(this).get(ViewModelActivity.class);
        Log.i(TAG, "onResume: " + viewModel.hashCode());
    }

    @Override
    protected void onPause() {
        super.onPause();
        //viewModel = ViewModelProviders.of(this).get(ViewModelActivity.class);
        Log.i(TAG, "onPause: " + viewModel.hashCode());
    }

    @Override
    protected void onStop() {
        super.onStop();
        //viewModel = ViewModelProviders.of(this).get(ViewModelActivity.class);
        Log.i(TAG, "onStop: " + viewModel.hashCode());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //viewModel = ViewModelProviders.of(this).get(ViewModelActivity.class);
        Log.i(TAG, "onDestroy: " + viewModel.hashCode());
    }
}
