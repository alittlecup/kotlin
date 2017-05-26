package com.example.hbl.kotlin.dir

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater

abstract class RecyclerDirectoryBaseAdapter<T>(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val layoutInflater: LayoutInflater
    private val mData: MutableList<T>

    init {
        layoutInflater = LayoutInflater.from(context)
        mData = mutableListOf()
    }

    fun updateData(data: List<T>?) {
        setData(data)
        notifyDataSetChanged()
    }

    fun setData(data: List<T>?) {
        this.mData.clear()
        if (data != null) {
            mData.addAll(data)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = this.getItem(position)
        this.bindView(data, position, holder)
    }

    abstract fun bindView(var1: T, var2: Int, var3: RecyclerView.ViewHolder)

    fun getItem(position: Int): T {
        return mData[position]
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}