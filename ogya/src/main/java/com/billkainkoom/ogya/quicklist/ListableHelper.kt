package com.billkainkoom.ogya.quicklist

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object ListableHelper {

    fun <T : Listable> loadList(context: Context,
                                recyclerView: RecyclerView,
                                listables: MutableList<T>,
                                listableType: ListableType,
                                listableBindingListener: (T, ViewDataBinding, Int) -> Unit = { x, y, z -> },
                                listableClickedListener: (T, ViewDataBinding, Int) -> Unit = { x, y, z -> },
                                layoutManagerType: LayoutManager = LayoutManager.Vertical,
                                stackFromEnd: Boolean = false,
                                gridSize: Int = 0,
                                useCustomSpan: Boolean = false

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
            LayoutManager.Grid -> {
                recyclerView.layoutManager = GridLayoutManager(context, gridSize)
                if (useCustomSpan) {
                    (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override

                        fun getSpanSize(position: Int): Int {
                            return (gridSize.toDouble() / listables[position].span).toInt()
                        }
                    }
                }

            }
        }

        val adapter = ListableAdapter(context, listableType, listables,
                listableBindingListener = { listable, listableBinding, position ->
                    listableBindingListener(listable, listableBinding, position)
                }, listableClickedListener = { listable, listableBinding, position ->
            //do something on card clicked
            listableClickedListener(listable, listableBinding, position)
        })

        recyclerView.adapter = adapter
        adapter.submitList(listables)

        return adapter
    }

}