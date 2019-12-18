package com.spark.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG: String = "MainActivity";

//    private var recyclerView: RecyclerView? = null
//    private var tv: TextView? = null
//    private var createKotlin: Button? = null
//    private var createJava: Button? = null

    private var rxJava2Adapter: RxJava2Adapter? = null;
    private var lists = ArrayList<RxJavaData>();

    private var stringBuffer: StringBuffer = StringBuffer()
    private var mRxJavaToKotlin: RxJavaToKotlin = RxJavaToKotlin()
    private var mRxJavaToJava: RxJavaToJava = RxJavaToJava()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        recyclerView = findViewById(R.id.recyclerView);
//        tv = findViewById(R.id.tv);
//        createKotlin = findViewById(R.id.createKotlin);
//        createJava = findViewById(R.id.createJava);
//
//        createKotlin!!.setOnClickListener(this)
//        createJava!!.setOnClickListener(this)

        lists.add(RxJavaData(getString(R.string.create_title), getString(R.string.create_desc)))
        lists.add(RxJavaData(getString(R.string.map_title), getString(R.string.map_desc)))
        lists.add(RxJavaData(getString(R.string.zip_title), getString(R.string.zip_desc)))
        lists.add(RxJavaData(getString(R.string.concat_title), getString(R.string.concat_desc)))
        lists.add(RxJavaData(getString(R.string.flatmap_title), getString(R.string.flatmap_desc)))
        lists.add(
            RxJavaData(
                getString(R.string.concatmap_title),
                getString(R.string.concatmap_desc)
            )
        )
        lists.add(RxJavaData(getString(R.string.distinct_title), getString(R.string.distinct_desc)))
        lists.add(RxJavaData(getString(R.string.filter_title), getString(R.string.filter_desc)))
        lists.add(RxJavaData(getString(R.string.buffer_title), getString(R.string.buffer_desc)))
        lists.add(RxJavaData(getString(R.string.timer_title), getString(R.string.timer_desc)))
        lists.add(RxJavaData(getString(R.string.interval_title), getString(R.string.interval_desc)))
        lists.add(RxJavaData(getString(R.string.doOnNext_title), getString(R.string.doOnNext_desc)))
        lists.add(RxJavaData(getString(R.string.skip_title), getString(R.string.skip_desc)))
        lists.add(RxJavaData(getString(R.string.take_title), getString(R.string.take_desc)))
        lists.add(RxJavaData(getString(R.string.just_title), getString(R.string.just_desc)))
        lists.add(RxJavaData(getString(R.string.single_title), getString(R.string.single_desc)))
        lists.add(RxJavaData(getString(R.string.debounce_title), getString(R.string.debounce_desc)))
        lists.add(RxJavaData(getString(R.string.defer_title), getString(R.string.defer_desc)))
        lists.add(RxJavaData(getString(R.string.last_title), getString(R.string.last_desc)))
        lists.add(RxJavaData(getString(R.string.merge_title), getString(R.string.merge_desc)))
        lists.add(RxJavaData(getString(R.string.reduce_title), getString(R.string.reduce_desc)))
        lists.add(RxJavaData(getString(R.string.scan_title), getString(R.string.scan_desc)))
        lists.add(RxJavaData(getString(R.string.window_title), getString(R.string.window_desc)))
        lists.add(RxJavaData(getString(R.string.PublishSubject), getString(R.string.PublishSubjectDesc)))
        lists.add(RxJavaData(getString(R.string.AsyncSubject), getString(R.string.AsyncSubjectDesc)))
        lists.add(RxJavaData(getString(R.string.BehaviorSubject), getString(R.string.BehaviorSubjectDesc)))
        lists.add(RxJavaData(getString(R.string.completable), getString(R.string.completableDesc)))
        lists.add(RxJavaData(getString(R.string.flowable), getString(R.string.flowableDesc)))

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = linearLayoutManager

        if (rxJava2Adapter == null) rxJava2Adapter = RxJava2Adapter(this);
        recyclerView!!.adapter = rxJava2Adapter;
        rxJava2Adapter!!.upDate(lists.reversed())
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.createKotlin -> {
                mRxJavaToKotlin.create(stringBuffer)
//                tv!!.setText(stringBuffer)
            }
            R.id.createJava -> {
                mRxJavaToJava.create(stringBuffer)
//                tv!!.setText(stringBuffer)
            }
            else -> {
                println("其余")
            }
        }
    }

}
