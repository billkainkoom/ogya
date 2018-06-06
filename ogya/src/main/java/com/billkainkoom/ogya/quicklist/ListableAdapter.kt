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

    fun addAt(position: Int, cardObject: T) {
        listables.add(position, cardObject)
        notifyItemInserted(position)
    }

    fun replaceAt(position: Int, cardObject: T) {
        listables[position] = cardObject
        notifyItemChanged(position)
    }

    fun addAt(position: Int, vararg cardObject: T) {
        listables.addAll(position, cardObject.asList())
        notifyItemInserted(listables.size - 1)
        notifyItemRangeChanged(position, listables.size)
    }

    fun addAt(position: Int, cardObjects: List<T>) {
        listables.addAll(position, cardObjects)
        notifyItemInserted(listables.size - 1)
        notifyItemRangeChanged(position, listables.size)
    }

    fun add(cardObjects: List<T>) {
        (cardObjects as ArrayList).removeAll(listables)
        val preSize = listables.size
        listables.addAll(cardObjects)
        notifyItemInserted(listables.size - 1)
        notifyItemRangeChanged(preSize, listables.size)
    }

    fun add(cardObject: T) {
        listables.add(cardObject)
        notifyItemInserted(listables.size - 1)

    }

}
