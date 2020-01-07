package com.spark.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spark.myapplication.java.RxJavaToJava
import com.spark.myapplication.kotlin.RetrofitToKotlin
import com.spark.myapplication.kotlin.RxJavaToKotlin
import com.spark.myapplication.model.RxJavaData
import kotlinx.android.synthetic.main.activity_retrofit2.*
import kotlinx.android.synthetic.main.item_rxjava.view.*

/*************************************************************************************************
 * 日期：2019/12/16 15:10
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
class Retrofit2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit2)
        recyclerView!!.layoutManager = LinearLayoutManager(this);
        val retrofit2Adapter = Retrofit2Adapter(this)
        recyclerView!!.adapter = retrofit2Adapter

        var lists = ArrayList<RxJavaData>();
        lists.add(
            RxJavaData(
                getString(R.string.simple),
                getString(R.string.simpleDesc)
            )
        )
        lists.add(
            RxJavaData(
                getString(R.string.cacheNetWork),
                getString(R.string.cacheNetWorkDesc)
            )
        )
        lists.add(
            RxJavaData(
                getString(R.string.moreNetWorkFlatMap),
                getString(R.string.moreNetWorkFlatMapDesc)
            )
        )
        retrofit2Adapter.upDate(lists.reversed())
    }

    inner class Retrofit2Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
        var mData = ArrayList<RxJavaData>();
        var mContext: Context? = null;

        constructor(mContext: Context?) : super() {
            this.mContext = mContext
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Retrofit2ViewHolder {
            return Retrofit2ViewHolder(
                LayoutInflater.from(mContext).inflate(
                    R.layout.item_rxjava,
                    parent,
                    false
                )
            )
        }

        fun upDate(mData: List<RxJavaData>) {
            this.mData.clear()
            addDate(mData)
        }


        fun addDate(mData: List<RxJavaData>?) {
            if (mData != null) this.mData.addAll(mData)
            notifyDataSetChanged()
        }


        override fun getItemCount(): Int {
            return mData.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as Retrofit2ViewHolder;
            var item: RxJavaData = mData[position]
            var stringBuffer = StringBuffer()
            viewHolder.itemView.tvTitle.text = item.title
            viewHolder.itemView.tvDesc.text = item.desc

            viewHolder.itemView.tvKotlin.setOnClickListener {
                when (item.title) {
                    mContext!!.getString(R.string.simple) -> {
                        RetrofitToKotlin().simple(stringBuffer, viewHolder.itemView.tvContent)
                    }
                    mContext!!.getString(R.string.cacheNetWork) -> {
                        RetrofitToKotlin().cacheNetWork(stringBuffer, viewHolder.itemView.tvContent)
                    }
                    mContext!!.getString(R.string.moreNetWorkFlatMap) -> {
                        RetrofitToKotlin().moreNetWorkFlatMap(stringBuffer, viewHolder.itemView.tvContent)
                    }
                }
                viewHolder.itemView.tvContent!!.setText(stringBuffer)
            }
            viewHolder.itemView.tvJava.setOnClickListener {
                when (item.title) {
                    mContext!!.getString(R.string.simple) -> {
//                        mRxJavaToJava.create(stringBuffer)
                    }
                }
                viewHolder.itemView.tvContent!!.setText(stringBuffer)
            }
        }

        inner class Retrofit2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        }
    }
}
