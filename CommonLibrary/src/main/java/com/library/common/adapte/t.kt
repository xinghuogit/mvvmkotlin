package com.library.common.adapte

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.library.common.R

import java.util.ArrayList

class t : AppCompatActivity(), View.OnClickListener {

    private var studyRxJava: Button? = null
    private var mainRecyclerView: RecyclerView? = null

    private val mData = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        //        mData = Arrays.asList(getResources().getStringArray(R.array.basis_rxjava));
        mData.add("1")
        mData.add("2")
        mData.add("3")
        mData.add("4")
        mData.add("5")

//        studyRxJava = findViewById<View>(R.id.studyRxJava) as Button
        mainRecyclerView = findViewById<View>(R.id.mainRecyclerView) as RecyclerView
        mainRecyclerView!!.layoutManager = LinearLayoutManager(this)
        mainRecyclerView!!.adapter = MainAdapter()

        studyRxJava!!.setOnClickListener(){

        }
    }

    private inner class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            return MainViewHolder(
                LayoutInflater.from(this@t).inflate(
                    R.layout.item_recycler,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.tv.text = mData[position]
        }

        override fun getItemCount(): Int {
            return mData.size
        }

        internal inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tv: TextView

            init {
                tv = itemView.findViewById<View>(R.id.itemRecyclerName) as TextView
                tv.setOnClickListener {
                    when (tv.text.toString().trim { it <= ' ' }) {
                        "发送1.2.3后再发送onComplete" -> Log.i(TAG, "onClick: 1")
                        "发送1.2.3后再发送onComplete的链式操作" -> Log.i(TAG, "onClick: 2")
                        else -> {
                        }
                    }//                                BasicRxJavaActivity.start(t1.this);
                }
            }
        }
    }


    override fun onClick(view: View) {
        when (view.id) {
            1 -> Log.i(TAG, "onClick: 1")
            2 -> Log.i(TAG, "onClick: 2")
            else -> {
            }
        }
    }

    companion object {
        private val TAG = "t1"
    }


}
