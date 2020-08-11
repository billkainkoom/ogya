package com.billkainkoom.ogya.quicklist

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedList
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
                                useCustomSpan: Boolean = false,
                                inputTags: List<String> = listOf(),
                                inputChangeListener: (T, Int, String, String) -> Unit = { w, x, y, z -> },
                                isRecyclable: Boolean = true

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
                },
                listableClickedListener = { listable, listableBinding, position ->
                    listableClickedListener(listable, listableBinding, position)
                },
                inputTags = inputTags,
                inputChangeListener = inputChangeListener,
                isRecyclable = isRecyclable
        )

        recyclerView.adapter = adapter
        adapter.submitList(listables)

        return adapter
    }

    fun <T : Listable> loadPagerList(context: Context,
                                     recyclerView: RecyclerView,
                                     listableType: ListableType,
                                     listableBindingListener: (T, ViewDataBinding, Int) -> Unit = { x, y, z -> },
                                     listableClickedListener: (T, ViewDataBinding, Int) -> Unit = { x, y, z -> },
                                     layoutManagerType: LayoutManager = LayoutManager.Vertical,
                                     stackFromEnd: Boolean = false,
                                     gridSize: Int = 0,
                                     useCustomSpan: Boolean = false

    ): ListablePagerAdapter<T> {


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
            }
            else -> {
            }
        }

        val adapter = ListablePagerAdapter<T>(context, listableType,
                listableBindingListener = { listable, listableBinding, position ->
                    listableBindingListener(listable, listableBinding, position)
                },
                listableClickedListener = { listable, listableBinding, position ->
                    listableClickedListener(listable, listableBinding, position)
                }
        )

        recyclerView.adapter = adapter

        if (layoutManagerType == LayoutManager.Grid) {
            if (useCustomSpan) {
                (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override

                    fun getSpanSize(position: Int): Int {
                        val numerator = gridSize.toDouble()
                        val denominator = (adapter.currentList!![position]?.span
                                ?: 1.0)

                        return (numerator / denominator).toInt()
                    }
                }
            }
        }


        return adapter
    }


}