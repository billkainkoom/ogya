package com.billkainkoom.ogya.quicklist.formcomponents

import android.text.InputFilter
import android.util.Log
import android.view.View
import com.billkainkoom.ogya.R
import com.billkainkoom.ogya.databinding.ComponentQuickFormInputBinding
import com.billkainkoom.ogya.extentions.enableEditing
import com.billkainkoom.ogya.quickdialog.QuickDatePicker
import com.billkainkoom.ogya.quickdialog.QuickTimePicker
import com.billkainkoom.ogya.quicklist.BaseComponent
import com.billkainkoom.ogya.quicklist.ListableType
import com.billkainkoom.ogya.shared.OgyaListableTypes
import com.billkainkoom.ogya.shared.QuickFormInputElement
import com.billkainkoom.ogya.shared.QuickFormInputType


object ComponentQuickFormInput : BaseComponent<ComponentQuickFormInputBinding, QuickFormInputElement>() {

    override fun render(binding: ComponentQuickFormInputBinding, listable: QuickFormInputElement) {
        binding.input.setText(listable.value)
        binding.inputLayout.hint = listable.hint
        binding.input.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.input.hint = listable.placeholder
            } else {
                binding.input.hint = ""
            }
        }

        if (listable.isFocusable) {
            binding.input.requestFocus()
            binding.input.isFocusable = listable.isFocusable
            binding.input.isEnabled = listable.isFocusable
        } else {
            binding.input.requestFocus()
            binding.input.isFocusable = false
            binding.input.isEnabled = false
        }

        when (listable.type) {
            QuickFormInputType.Input -> {
                handleInputType(binding, listable)
            }
            QuickFormInputType.Date -> {
                handleDateType(binding)
            }
            QuickFormInputType.Time -> {
                handleTimeType(binding)
            }
        }
    }

    private fun toggleButtonVisibility(binding: ComponentQuickFormInputBinding, visible: Boolean) {
        if (visible) {
            binding.button.visibility = View.VISIBLE
        } else {
            binding.button.visibility = View.GONE
        }
    }

    private fun handleInputType(binding: ComponentQuickFormInputBinding, listable: QuickFormInputElement) {
        binding.input.enableEditing(true)
        binding.input.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(listable.inputLength))
        if (listable.actionButton != null) {
            toggleButtonVisibility(binding, true)
            binding.button.setImageResource(listable.actionButton.image)
            binding.button.setOnClickListener {
                listable.actionButton.handler()
            }
        } else {
            toggleButtonVisibility(binding, false)
        }


        try {
            binding.input.inputType = listable.inputType
        } catch (e: Exception) {
            Log.e("QuickForm", "Invalid input type")
            e.printStackTrace()
        }
    }

    private fun handleDateType(binding: ComponentQuickFormInputBinding) {
        binding.input.enableEditing(false)
        toggleButtonVisibility(binding, true)
        binding.button.setImageResource(R.drawable.ic_today_black_24dp)
        binding.button.setOnClickListener {
            QuickDatePicker(binding.root.context).show { dateSelected ->
                binding.input.setText(dateSelected)
            }
        }
    }

    private fun handleTimeType(binding: ComponentQuickFormInputBinding) {
        toggleButtonVisibility(binding, true)
        binding.input.enableEditing(false)
        binding.button.setImageResource(R.drawable.ic_access_time_black_24dp)
        binding.button.setOnClickListener {
            QuickTimePicker(binding.root.context).show { timeSelected ->
                binding.input.setText(timeSelected)
            }
        }
    }

    override fun listableType(): ListableType {
        return OgyaListableTypes.QuickFormInput
    }
}