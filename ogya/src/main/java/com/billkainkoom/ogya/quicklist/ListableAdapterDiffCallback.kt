package com.billkainkoom.ogya.quicklist

import androidx.recyclerview.widget.DiffUtil

class ListableAdapterDiffCallback<T : Listable>(private val oldList: List<T>,private  val newList: List<T>) : DiffUtil.Callback() {


    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition].identifier == newList[newPosition].identifier
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition] == newList[newPosition]
    }

}
