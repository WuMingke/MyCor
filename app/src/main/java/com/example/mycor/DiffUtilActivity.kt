package com.example.mycor

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class DiffUtilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_util)

        val changeInfo = findViewById<TextView>(R.id.changeInfo)
        val ssb = SpannableStringBuilder()

        val rv = findViewById<RecyclerView>(R.id.rv)
        val layoutManager = LinearLayoutManager(rv.context)
        val data = arrayListOf<Int>().apply {
            repeat(20) {
                add(it)
            }
        }
        val adapter = MyAdapter(data)
        adapter.onChange = object : MyAdapter.OnChangeInfo {
            override fun onChange(position: Int) {
                ssb.append(position.toString()).append("\n")
                changeInfo.text = ssb.toString()
            }
        }

        findViewById<Button>(R.id.add).setOnClickListener {
            adapter.data.add(0, 222)
            adapter.notifyItemInserted(0)
            layoutManager.scrollToPosition(0)
        }
        findViewById<Button>(R.id.delete).setOnClickListener {
            adapter.data.removeAt(0)
            adapter.notifyItemRemoved(0)
        }

        findViewById<Button>(R.id.change).setOnClickListener {
            adapter.data[0] = 555
            adapter.notifyItemChanged(0)
        }

        findViewById<Button>(R.id.clearInfo).setOnClickListener {
            ssb.clear()
            changeInfo.text = ""
        }


        rv.layoutManager = layoutManager
        rv.adapter = adapter
    }

//    class DiffCallBack : DiffUtil.Callback() {
//        override fun getOldListSize(): Int {
//        }
//
//        override fun getNewListSize(): Int {
//        }
//
//        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        }
//
//        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        }
//
//    }

    class MyAdapter(val data: ArrayList<Int>) : RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder>() {

        private val colors = arrayListOf<Int>().apply {
            add(R.color.black)
            add(R.color.purple_200)
            add(R.color.purple_500)
            add(R.color.purple_700)
            add(R.color.teal_200)
            add(R.color.teal_700)
        }

        private val random = Random(colors.size)

        var onChange: OnChangeInfo? = null

        class MyAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tv = view.findViewById<TextView>(R.id.tv)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_my, parent, false);
            return MyAdapterViewHolder(view)
        }

        override fun getItemCount() = data.size

        override fun onBindViewHolder(holder: MyAdapterViewHolder, position: Int) {
            holder.tv.setBackgroundColor(random.nextInt())
            holder.tv.text = data[position].toString()
            onChange?.onChange(position)
        }

        interface OnChangeInfo {
            fun onChange(position: Int)
        }
    }
}