package com.library.common.adapte

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.library.common.R

import java.util.ArrayList

/*************************************************************************************************
 * 日期：2019/12/16 15:13
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 */
class t1 : RecyclerView.Adapter<t1.MainViewHolder> {

    private val mData = ArrayList<String>()
    private var context: Context? = null


    constructor() {}

    constructor(context: Context) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): t1.MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_recycler,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: t1.MainViewHolder, position: Int) {
        holder.tv.text = mData[position]
    }

    fun upDate(mData: List<String>) {
        this.mData.clear()
        addDate(mData)
    }


    fun addDate(mData: List<String>?) {
        if (mData != null) this.mData.addAll(mData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv: TextView

        init {
            tv = itemView.findViewById<View>(R.id.itemRecyclerName) as TextView
            tv.setOnClickListener {
                when (tv.text.toString().trim { it <= ' ' }) {
                    "发送1.2.3后再发送onComplete" -> Log.i(TAG, "onClick: 1")
                    "发送1.2.3后再发送onComplete的链式操作" -> {
                        Log.i(TAG, "onClick: 2")
                        Log.i(TAG, "onClick: 2")
                        Log.i(TAG, "onClick: 2")
                        Log.i(TAG, "onClick: 2")
                    }
                    else -> {
                    }
                }
            }
        }
    }

    companion object {
        private val TAG = "Main1Adapter"
    }

}