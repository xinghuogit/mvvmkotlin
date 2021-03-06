package com.library.common.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.trello.rxlifecycle3.components.support.RxFragmentActivity;

import java.lang.ref.WeakReference;

/*************************************************************************************************
 * 日期：2020/1/13 13:09
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：基础Activity
 ************************************************************************************************/
public abstract class BaseActivity extends RxFragmentActivity {
    public static int total;//总量
    public static int pageIndex = 1;//当前页码
    public static int pageSize = 20;//每页数量

    public boolean mRefresh = false;

    private WeakReference<FragmentActivity> currentActivity = null;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
        currentActivity = new WeakReference<FragmentActivity>(this);
    }

    /**
     * 获取当前activity的引用
     *
     * @return
     */
    public FragmentActivity getCurrentActivity() {
        return currentActivity == null ? null : currentActivity.get();
    }


    public abstract int getContentViewId();

    public abstract void initView();

    public abstract void initData();

    public abstract void initListener();

    public abstract void setData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (currentActivity != null) {
            currentActivity.clear();
            currentActivity = null;
        }
    }

    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    //快速获取textView 或 EditText上文字内容
    public String getStringByUI(View view) {
        if (view instanceof EditText) {
            return ((EditText) view).getText().toString().trim();
        } else if (view instanceof TextView) {
            return ((TextView) view).getText().toString().trim();
        }
        return "";
    }

    public void showLoading() {
        showLoading("请求中...");
    }

    public void showLoading(String message) {
        showLoading(message, true);
    }

    /**
     * @param message
     * @param isCancelable 是否可以回退 {@code true}: 是<br>{@code false}: 否
     */
    public void showLoading(String message, boolean isCancelable) {
        if (progressDialog == null) progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(isCancelable);
        progressDialog.setMessage(message);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
