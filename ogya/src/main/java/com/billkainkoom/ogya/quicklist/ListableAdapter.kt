package com.billkainkoom.ogya.quicklist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding

/**
 * Created by  develop on 6/22/2015.
 */

class ListableAdapter <T : Listable> internal  constructor(
        private val context: Context,
        private val listableType: ListableType,
        val listables: MutableList<T>,
        private val listableBindingListener: (T, ViewDataBinding, Int) -> Unit,
        private val listableClickedListener: (T, ViewDataBinding, Int) -> Unit
) : RecyclerView.Adapter<ListableAdapter<T>.ListableViewHolder>() {

    private var currentListableType: ListableType? = null


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListableViewHolder {
        val listableBinding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), currentListableType!!.layout, null, false)
        //val view = LayoutInflater.from(viewGroup.context).inflate(currentListableType!!.layout, viewGroup, false)
        return ListableViewHolder(listableBinding)

    }

    override fun onBindViewHolder(listableViewHolder: ListableViewHolder, listablePosition: Int) {

        listableViewHolder.listable = listables[listablePosition]
        val listable = listables[listablePosition]
        listableBindingListener(listable, listableViewHolder.viewBinding, listablePosition)
    }

    override fun getItemCount(): Int {
        return listables.size
    }

    override fun getItemViewType(position: Int): Int {
        if (listables[position].getListableType() == null) {
            currentListableType = listableType
            return listableType.layout
        }
        currentListableType = listables[position].getListableType()
        return listables[position].getListableType()!!.layout
    }

    inner class ListableViewHolder(val viewBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        var listable: T? = null


        init {
            viewBinding.root.setOnClickListener { listableClickedListener(listable!!, viewBinding, adapterPosition) }
        }
    }

    fun removeAt(position: Int) {
        listables.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, listables.size)
    }

    fun addAt(position: Int, listable: T) {
        listables.add(position, listable)
        notifyItemInserted(position)
    }

    fun replaceAt(position: Int, listable: T) {
        listables[position] = listable
        notifyItemChanged(position)
    }

    fun addAt(position: Int, vararg listable: T) {
        listables.addAll(position, listable.asList())
        notifyItemInserted(listables.size - 1)
        notifyItemRangeChanged(position, listables.size)
    }

    fun addAt(position: Int, newListables: List<T>) {
        listables.addAll(position, newListables)
        notifyItemInserted(listables.size - 1)
        notifyItemRangeChanged(position, listables.size)
    }

    fun add(newListables: List<T>) {
        (newListables as ArrayList).removeAll(listables)
        val preSize = listables.size
        listables.addAll(newListables)
        notifyItemInserted(listables.size - 1)
        notifyItemRangeChanged(preSize, listables.size)
    }

    fun add(listable: T) {
        listables.add(listable)
        notifyItemInserted(listables.size - 1)
    }
}
