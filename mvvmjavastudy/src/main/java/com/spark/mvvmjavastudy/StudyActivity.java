package com.spark.mvvmjavastudy;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/*************************************************************************************************
 * 日期：2020/1/6 17:31
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class StudyActivity extends AppCompatActivity {

    public void showToast(String message) {
        System.out.println(message);
        System.out.println("message");
        Toast.makeText(StudyActivity.this, "message", Toast.LENGTH_SHORT).show();
    }
}
