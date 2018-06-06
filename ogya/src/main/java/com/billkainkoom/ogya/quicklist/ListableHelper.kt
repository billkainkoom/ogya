package com.billkainkoom.ogya.quicklist

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

object ListableHelper {

    fun <T : Listable> loadList(context: Context, recyclerView: RecyclerView, listables: MutableList<T>, listableType: ListableType,
                                listableBindingListener: (T, ViewDataBinding, Int) -> Unit = { x, y, z -> },
                                listableClickedListener: (T, ViewDataBinding,Int) -> Unit = { x, y,z -> },
                                layoutManagerType: LayoutManager = LayoutManager.Vertical,
                                stackFromEnd: Boolean = false
    ): ListableAdapter<T> {


        recyclerView.setHasFixedSize(true)
        recyclerView.isNestedScrollingEnabled = false

        when (layoutManagerType) {
            LayoutManager.Vertical -> {
                recyclerView.layoutManager = LinearLayoutManager(context)
                if (stackFromEnd) {
                    (recyclerView.layoutManager as LinearLayoutManager).stackFromEnd = true
                }
            }
            LayoutManager.Horizontal -> {
                recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

                if (stackFromEnd) {
                    (recyclerView.layoutManager as LinearLayoutManager).stackFromEnd = true
                }
            }
        }

        val adapter = ListableAdapter(context, listableType, listables,
                listableBindingListener = { listable, listableBinding, position ->
                    listableBindingListener(listable, listableBinding, position)
                }, listableClickedListener = { listable,listableBinding, position ->
            //do something on card clicked
            listableClickedListener(listable,listableBinding, position)
        })

        recyclerView.adapter = adapter

        return adapter


        /*if (listObjects.size == 0) {
            card_objects.visibility = View.GONE
            empty.visibility = View.VISIBLE
            header.text = "No Invitation Card"
            sub_header.text = "You have not uploaded any images. Please click on the + button to add one now."
        }*/
    }

}