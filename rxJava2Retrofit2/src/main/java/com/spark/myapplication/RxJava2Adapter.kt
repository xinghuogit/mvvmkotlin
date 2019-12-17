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
                mContext!!.getString(R.string.distinct_title) -> {
                    mRxJavaToKotlin.distinct(stringBuffer)
                }
                mContext!!.getString(R.string.filter_title) -> {
                    mRxJavaToKotlin.filter(stringBuffer)
                }
                mContext!!.getString(R.string.buffer_title) -> {
                    mRxJavaToKotlin.buffer(stringBuffer)
                }
                mContext!!.getString(R.string.timer_title) -> {
                    mRxJavaToKotlin.timer(stringBuffer)
                }
                mContext!!.getString(R.string.interval_title) -> {
                    mRxJavaToKotlin.interval(stringBuffer)
                }
                mContext!!.getString(R.string.doOnNext_title) -> {
                    mRxJavaToKotlin.doOnNext(stringBuffer)
                }
                mContext!!.getString(R.string.skip_title) -> {
                    mRxJavaToKotlin.skip(stringBuffer)
                }
                mContext!!.getString(R.string.take_title) -> {
                    mRxJavaToKotlin.take(stringBuffer)
                }
                mContext!!.getString(R.string.just_title) -> {
                    mRxJavaToKotlin.just(stringBuffer)
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
                mContext!!.getString(R.string.distinct_title) -> {
                    mRxJavaToJava.distinct(stringBuffer)
                }
                mContext!!.getString(R.string.filter_title) -> {
                    mRxJavaToJava.filter(stringBuffer)
                }
                mContext!!.getString(R.string.buffer_title) -> {
                    mRxJavaToJava.buffer(stringBuffer)
                }
                mContext!!.getString(R.string.timer_title) -> {
                    mRxJavaToJava.timer(stringBuffer)
                }
                mContext!!.getString(R.string.interval_title) -> {
                    mRxJavaToJava.interval(stringBuffer)
                }
                mContext!!.getString(R.string.doOnNext_title) -> {
                    mRxJavaToJava.doOnNext(stringBuffer)
                }
                mContext!!.getString(R.string.skip_title) -> {
                    mRxJavaToJava.skip(stringBuffer)
                }
                mContext!!.getString(R.string.take_title) -> {
                    mRxJavaToJava.take(stringBuffer)
                }
                mContext!!.getString(R.string.just_title) -> {
                    mRxJavaToJava.just(stringBuffer)
                }
            }
            viewHolder.itemView.tvContent!!.setText(stringBuffer)
        }
    }

    inner class RxJava2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}