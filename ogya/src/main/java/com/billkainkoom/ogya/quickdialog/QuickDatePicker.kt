package com.billkainkoom.ogya.quickdialog

import android.app.Dialog
import android.content.Context
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.Window
import com.billkainkoom.ogya.R
import com.billkainkoom.ogya.databinding.QuickDialogDatePickerBinding
import java.text.DecimalFormat

/**
 * Created by Bill on 10/17/2017.
 */
class QuickDatePicker(val context: Context) {

    private val dialog: Dialog = Dialog(context)
    private val decimalFormat = DecimalFormat("00");
    private val quickDialogDatePickerBinding: QuickDialogDatePickerBinding

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.quick_dialog_date_picker)

        quickDialogDatePickerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.quick_dialog_date_picker, null, false)
        dialog.setContentView(quickDialogDatePickerBinding.root)

        quickDialogDatePickerBinding.negativeButton.setOnClickListener({ v -> dismiss() })
    }

    fun show(dateSelected: (String) -> Unit) {
        quickDialogDatePickerBinding.positiveButton.setOnClickListener({ v ->
            val date = quickDialogDatePickerBinding.datePicker.year.toString() + "-" + decimalFormat.format((quickDialogDatePickerBinding.datePicker.month + 1).toLong()) + "-" + decimalFormat.format(quickDialogDatePickerBinding.datePicker.dayOfMonth.toLong())
            dateSelected(date)
            dismiss()
        })
        dialog.show()
    }

    fun dismiss() {
        dialog.hide()
    }
}