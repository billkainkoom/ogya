package com.billkainkoom.ogya.shared

import android.text.Editable
import android.text.InputType
import com.billkainkoom.ogya.quicklist.Listable
import com.billkainkoom.ogya.quicklist.ListableType
import com.google.android.material.textfield.TextInputEditText


enum class QuickFormInputType {
    Input,
    Date,
    Time
}

data class ActionButton(val image: Int, val showOnFocus: Boolean = false, val handler: () -> Unit)
data class TextWatcher(val afterTextChanged: (Editable) -> Unit = { })

data class QuickFormInputElement(
        val name: String = "",
        var value: String = "",
        val placeholder: String = "",
        val hint: String = "",
        val inputLength: Int = 1000,
        val inputType: Int = InputType.TYPE_CLASS_TEXT,
        val type: QuickFormInputType = QuickFormInputType.Input,
        val ofListableType: ListableType = OgyaListableTypes.QuickFormInput,
        val listableSpan: Double = 1.0,
        val actionButton: ActionButton? = null,
        val textWatcher: TextWatcher? = null
) : Listable(span = listableSpan) {

    override fun getListableType(): ListableType? {
        return ofListableType
    }
}