package com.spark.mvvmjavastudy.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.spark.mvvmjavastudy.R;
import com.spark.mvvmjavastudy.databinding.FragmentViewModelBinding;
import com.spark.mvvmjavastudy.viewmodel.ViewModelActivity;

public class ViewModelFragment extends Fragment {
    private static final String TAG = "ViewModelFragment";

    private FragmentViewModelBinding binding;

    private int type;

    public static ViewModelFragment getInstance(int type) {
        ViewModelFragment fragment = new ViewModelFragment();
        fragment.type = type;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_model, container, false);
        if (type == 1) {
            binding.relative.setBackgroundColor(getResources().getColor(R.color.color_ff3199fc));
            binding.btn.setText("Fragment - 1：打印当前ViewHolder");
        } else {
            binding.relative.setBackgroundColor(getResources().getColor(R.color.color_ffff0202));
            binding.btn.setText("Fragment - 2：打印当前ViewHolder");
        }
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), (ViewModelProviders.of(getActivity()).get(ViewModelActivity.class).hashCode()) + "", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}
