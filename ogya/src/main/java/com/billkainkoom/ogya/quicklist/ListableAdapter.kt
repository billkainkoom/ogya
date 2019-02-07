package com.billkainkoom.ogya.quicklist

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.billkainkoom.ogya.shared.QuickAsync

/**
 * Created by  Bill Kwaku Ansah Inkoom on 6/22/2015.
 */




class ListableAdapter<T : Listable> internal constructor(
        private val context: Context,
        private val listableType: ListableType,
        var listables: MutableList<T>,
        private val listableBindingListener: (T, ViewDataBinding, Int) -> Unit,
        private val listableClickedListener: (T, ViewDataBinding, Int) -> Unit)
    : ListAdapter<T, ListableAdapter<T>.ListableViewHolder>(ListableAdapterDiffCallback<T>()) {

    private var currentListableType: ListableType? = null

    class ListableAdapterDiffCallback<T : Listable> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.identifier == newItem.identifier
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position).getListableType() == null) {
            currentListableType = listableType
            return listableType.layout
        }
        currentListableType = getItem(position).getListableType()
        return getItem(position).getListableType()!!.layout

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListableViewHolder {
        return ListableViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), currentListableType!!.layout, null, false))
    }

    override fun onBindViewHolder(listableViewHolder: ListableViewHolder, listablePosition: Int) {
        listableBindingListener(getItem(listablePosition), listableViewHolder.viewBinding, listablePosition)
    }

    inner class ListableViewHolder(val viewBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        init {
            viewBinding.root.setOnClickListener { listableClickedListener(getItem(adapterPosition), viewBinding, adapterPosition) }
        }
    }

    fun removeAt(position: Int) {
        listables.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, listables.size)
    }

    fun clear() {
        val size = listables.size
        listables.clear()
        notifyItemRangeRemoved(0, size)
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