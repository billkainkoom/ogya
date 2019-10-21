package com.billkainkoom.ogya.shared

import android.text.InputType
import com.billkainkoom.ogya.quicklist.Listable
import com.billkainkoom.ogya.quicklist.ListableType
import com.google.android.material.textfield.TextInputEditText


enum class QuickFormInputType {
    Input,
    Date,
    Time
}

data class ActionButton(val image: Int, val handler: () -> Unit)

data class QuickFormInputElement(
        val name: String = "",
        var value: String = "",
        val placeholder: String = "",
        val hint: String = "",
        val isFocusable: Boolean = false,
        val inputLength: Int = 1000,
        val inputType: Int = InputType.TYPE_CLASS_TEXT,
        val type: QuickFormInputType = QuickFormInputType.Input,
        val ofListableType: ListableType = OgyaListableTypes.QuickFormInput,
        val actionButton: ActionButton? = null
) : Listable() {

    override fun getListableType(): ListableType? {
        return ofListableType
    }
}