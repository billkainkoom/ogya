package com.billkainkoom.ogya.quicklist.formcomponents

import android.view.View
import com.billkainkoom.ogya.R
import com.billkainkoom.ogya.databinding.ComponentQuickFormInputBinding
import com.billkainkoom.ogya.extentions.enableEditing
import com.billkainkoom.ogya.extentions.watch
import com.billkainkoom.ogya.quickdialog.QuickDatePicker
import com.billkainkoom.ogya.quickdialog.QuickTimePicker
import com.billkainkoom.ogya.quicklist.BaseComponent
import com.billkainkoom.ogya.quicklist.Listable
import com.billkainkoom.ogya.quicklist.ListableAdapter
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



        when (listable.type) {
            QuickFormInputType.Input -> {
                handleInputType(binding)
            }
            QuickFormInputType.Date -> {
                handleDateType(binding)
            }
            QuickFormInputType.Time -> {
                handleTimeType(binding)
            }
        }
    }

    fun render(binding: ComponentQuickFormInputBinding, listable: QuickFormInputElement, adapter: ListableAdapter<Listable>?, position: Int) {
        render(binding, listable)

        binding.input.watch { text ->
            listable.value = text
            adapter?.let {  listableAdapter ->
                listableAdapter.listables[position] = listable
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

    private fun handleInputType(binding: ComponentQuickFormInputBinding) {
        binding.input.enableEditing(true)
        toggleButtonVisibility(binding, false)
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