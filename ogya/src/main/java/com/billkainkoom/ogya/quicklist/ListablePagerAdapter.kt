package com.billkainkoom.ogya.quicklist

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.viewpager.widget.PagerAdapter
import com.billkainkoom.ogya.shared.QuickAsync

/**
 * Created by  Bill Kwaku Ansah Inkoom on 6/22/2015.
 */


class ListablePagerAdapter<T : Listable> internal constructor(
        private val context: Context,
        private val listableType: ListableType,
        var listables: MutableList<T>,
        private val listableBindingListener: (T, ViewDataBinding, Int) -> Unit,
        private val listableClickedListener: (T, ViewDataBinding, Int) -> Unit)
    : PagedListAdapter<T, ListablePagerAdapter<T>.ListableViewHolder>(ListableAdapterDiffCallback<T>()) {

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
        if (getItem(position)?.getListableType() == null) {
            currentListableType = listableType
            return listableType.layout
        }
        currentListableType = getItem(position)?.getListableType()
        return getItem(position)?.getListableType()!!.layout

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListableViewHolder {
        return ListableViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), currentListableType!!.layout, null, false))
    }

    override fun onBindViewHolder(listableViewHolder: ListableViewHolder, listablePosition: Int) {
        getItem(listablePosition)?.let { item ->
            listableBindingListener(item, listableViewHolder.viewBinding, listablePosition)
        }

    }

    inner class ListableViewHolder(val viewBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        init {
            viewBinding.root.setOnClickListener {
                getItem(adapterPosition)?.let { item ->
                    listableClickedListener(item, viewBinding, adapterPosition)
                }
            }
        }
    }
}