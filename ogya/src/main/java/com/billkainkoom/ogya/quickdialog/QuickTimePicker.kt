package com.billkainkoom.ogya.quickdialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.Window
import com.billkainkoom.ogya.R
import com.billkainkoom.ogya.databinding.QuickDialogTimePickerBinding
import java.text.DecimalFormat

/**
 * Created by Bill on 10/17/2017.
 */
class QuickTimePicker(val context: Context) {
    private val dialog: Dialog = Dialog(context)


    private val decimalFormat = DecimalFormat("00");

    private val quickDialogTimePickerBinding: QuickDialogTimePickerBinding

    init {

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        quickDialogTimePickerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.quick_dialog_time_picker, null, false)
        dialog.setContentView(quickDialogTimePickerBinding.root)

        quickDialogTimePickerBinding.negativeButton.setOnClickListener({ v -> dismiss() })
    }

    fun show(timeSelected: (String) -> Unit) {
        quickDialogTimePickerBinding.positiveButton.setOnClickListener({ v ->
            val date = decimalFormat.format(quickDialogTimePickerBinding.timePicker.currentHour.toLong()) + ":" + decimalFormat.format((quickDialogTimePickerBinding.timePicker.currentMinute).toLong()) + ":00"
            timeSelected(date)
            dismiss()
        })
        dialog.show()
    }

    fun dismiss() {
        dialog.hide()
    }
}
