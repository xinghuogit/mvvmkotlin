package com.spark.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_rxjava.view.*

/*************************************************************************************************
 * 日期：2019/12/16 15:10
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
class RxJava2Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var mData = ArrayList<RxJavaData>();
    var mContext: Context? = null;

    private var mRxJavaToKotlin: RxJavaToKotlin = RxJavaToKotlin()
    private var mRxJavaToJava: RxJavaToJava = RxJavaToJava()


    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RxJava2ViewHolder {
        return RxJava2ViewHolder(
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
        val viewHolder = holder as RxJava2ViewHolder;
        var item: RxJavaData = mData[position]
        var stringBuffer: StringBuffer = StringBuffer()
        viewHolder.itemView.tvTitle.text = item.title
        viewHolder.itemView.tvDesc.text = item.desc

        viewHolder.itemView.tvKotlin.setOnClickListener {
            when (item.title) {
                mContext!!.getString(R.string.create_title) -> {
                    mRxJavaToKotlin.create(stringBuffer)
                }
                mContext!!.getString(R.string.map_title) -> {
                    mRxJavaToKotlin.map(stringBuffer)
                }
                mContext!!.getString(R.string.zip_title) -> {
                    mRxJavaToKotlin.zip(stringBuffer)
                }
                mContext!!.getString(R.string.concat_title) -> {
                    mRxJavaToKotlin.concat(stringBuffer)
                }
                mContext!!.getString(R.string.flatmap_title) -> {
                    mRxJavaToKotlin.flatMap(stringBuffer)
                }
                mContext!!.getString(R.string.concatmap_title) -> {
                    mRxJavaToKotlin.concatMap(stringBuffer)
                }
            }

            viewHolder.itemView.tvContent!!.setText(stringBuffer)
        }
        viewHolder.itemView.tvJava.setOnClickListener {
            when (item.title) {
                mContext!!.getString(R.string.create_title) -> {
                    mRxJavaToJava.create(stringBuffer)
                }
                mContext!!.getString(R.string.map_title) -> {
                    mRxJavaToJava.map(stringBuffer)
                }
                mContext!!.getString(R.string.zip_title) -> {
                    mRxJavaToJava.zip(stringBuffer)
                }
                mContext!!.getString(R.string.concat_title) -> {
                    mRxJavaToJava.concat(stringBuffer)
                }
                mContext!!.getString(R.string.flatmap_title) -> {
                    mRxJavaToJava.flatMap(stringBuffer)
                }
                mContext!!.getString(R.string.concatmap_title) -> {
                    mRxJavaToJava.concatMap(stringBuffer)
                }
            }

            viewHolder.itemView.tvContent!!.setText(stringBuffer)
        }
    }

    inner class RxJava2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}