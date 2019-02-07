package com.billkainkoom.ogya.shared

import com.billkainkoom.ogya.quicklist.Listable
import com.billkainkoom.ogya.quicklist.ListableType


enum class QuickFormInputType {
    Input,
    Date,
    Time
}

data class QuickFormInputElement(
        val name: String = "",
        var value: String = "",
        val placeholder: String = "",
        val hint: String = "",
        val type: QuickFormInputType = QuickFormInputType.Input,
        val oflistableType: ListableType = OgyaListableTypes.QuickFormInput
) : Listable() {

    override fun getListableType(): ListableType? {
        return oflistableType
    }
}