package com.billkainkoom.ogya.quicklist

import android.databinding.ViewDataBinding

abstract class QuickBaseComponent<V : ViewDataBinding, L : Listable> {

    abstract fun render(binding: V, listable: L)

    abstract fun listableType(): ListableType
}