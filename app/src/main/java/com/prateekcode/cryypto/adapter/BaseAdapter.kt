package com.prateekcode.cryypto.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int = getListItemCount()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bindHolder(holder, position)
    }

    abstract fun getListItemCount(): Int

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    abstract fun bindHolder(holder: RecyclerView.ViewHolder, position: Int)
}