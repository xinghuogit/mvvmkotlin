package com.spark.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG: String = "MainActivity";

    private var tv: TextView? = null
    private var createKotlin: Button? = null
    private var createJava: Button? = null

    private var stringBuffer: StringBuffer = StringBuffer()
    
    private var mRxJavaToKotlin: RxJavaToKotlin = RxJavaToKotlin()
    private var mRxJavaToJava: RxJavaToJava = RxJavaToJava()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.tv);
        createKotlin = findViewById(R.id.createKotlin);
        createJava = findViewById(R.id.createJava);

        createKotlin!!.setOnClickListener(this)
        createJava!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.createKotlin -> {
                mRxJavaToKotlin.create(stringBuffer)
                tv!!.setText(stringBuffer)
            }
            R.id.createJava -> {
                mRxJavaToJava.create(stringBuffer)
                tv!!.setText(stringBuffer)
            }
            else -> {
                println("")
            }
        }
    }

}
