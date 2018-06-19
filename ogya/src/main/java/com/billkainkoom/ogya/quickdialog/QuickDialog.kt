package com.billkainkoom.ogya.quickdialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.InputFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.billkainkoom.ogya.R
import com.billkainkoom.ogya.databinding.QuickDialogBinding

/**
 * The quick dialog is a simple dialog helper that helps you create consistent variations of dialogs for your app
 */
class QuickDialog(
        val context: Context,
        style: QuickDialogType,
        title: String = "",
        message: String = "",
        image: Int = R.drawable.ic_info_outline_black_24dp,
        val optionList: Array<String> = Array(0, { i -> "" })
) {

    private val TAG = "QuickDialog"
    private var quickDialog: Dialog? = null
    private var quickDialogBinding: QuickDialogBinding? = null


    val inputText: String get() = quickDialogBinding?.input?.text.toString()
    var optionSelectedPosition = 0
    var optionSelectedValue = ""


    init {

        initialize(context, style)
        setDialogTitle(title)
        setDialogMessage(message)
        setDialogImage(image)
    }

    private fun initialize(context: Context, style: QuickDialogType) {

        quickDialog = Dialog(context)
        quickDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        quickDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.quick_dialog, null, false)
        quickDialog?.setContentView(quickDialogBinding!!.root)

        quickDialog?.setCancelable(false)


        switchStyle(style)

        /**
         * Set default click behaviour on buttons
         */
        overrideClicks(positiveClick = { ->

        }, negativeClick = { ->

        }, neutralClick = { ->

        })
    }

    fun switchStyle(style: QuickDialogType):QuickDialog {
        when (style) {
            QuickDialogType.Progress -> {
                quickDialogBinding?.image?.visibility = View.GONE
                quickDialogBinding?.negativeButton?.visibility = View.GONE
                quickDialogBinding?.positiveButton?.visibility = View.GONE
            }
            QuickDialogType.Alert -> {
                quickDialogBinding?.progressBar?.visibility = View.GONE
                quickDialogBinding?.spinnerContainer?.visibility = View.GONE
            }
            QuickDialogType.Message -> {
                quickDialogBinding?.progressBar?.visibility = View.GONE
                quickDialogBinding?.negativeButton?.visibility = View.GONE
                quickDialogBinding?.spinnerContainer?.visibility = View.GONE
            }
            QuickDialogType.WithInput -> {
                quickDialogBinding?.progressBar?.visibility = View.GONE
                quickDialogBinding?.input?.visibility = View.VISIBLE
                quickDialogBinding?.neutralButton?.visibility = View.GONE
                quickDialogBinding?.spinnerContainer?.visibility = View.GONE
            }
            QuickDialogType.Option -> {
                quickDialogBinding?.progressBar?.visibility = View.GONE
                quickDialogBinding?.spinnerContainer?.visibility = View.VISIBLE

                quickDialogBinding?.spinner?.adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, optionList)
                quickDialogBinding?.spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(adapterView: AdapterView<*>, view: View, index: Int, l: Long) {
                        optionSelectedPosition = index
                        optionSelectedValue = optionList[index]
                    }

                    override fun onNothingSelected(adapterView: AdapterView<*>) {
                    }
                }
            }
            QuickDialogType.List -> {
                quickDialogBinding?.progressBar?.visibility = View.GONE
                quickDialogBinding?.negativeButton?.visibility = View.GONE
                quickDialogBinding?.cardObjects?.visibility = View.VISIBLE

            }
        }
        return this
    }

    fun setData(title: String,message: String,image: Int):QuickDialog{
        setDialogTitle(title)
        setDialogMessage(message)
        setDialogImage(image)
        return  this
    }

    private fun setDialogImage(image: Int) {
        quickDialogBinding?.image?.setImageResource(image)
    }

    private fun setDialogImage(image: BitmapDrawable) {
        quickDialogBinding?.image?.setImageBitmap(image.bitmap)
    }


    private fun setDialogTitle(messsage: String) {
        quickDialogBinding?.title?.text = messsage
    }

    private fun setDialogMessage(infoMessage: String?) {
        quickDialogBinding?.info?.text = Html.fromHtml(infoMessage)
    }

    fun configureList(configuration: (dismiss: () -> Unit, recyclerView: RecyclerView) -> Unit = { d, r -> }): QuickDialog {
        configuration(dismiss, getRecyclerView())
        return this
    }

    fun hideButtons(): QuickDialog {
        quickDialogBinding?.positiveButton?.visibility = View.GONE
        quickDialogBinding?.negativeButton?.visibility = View.GONE
        quickDialogBinding?.neutralButton?.visibility = View.GONE
        return this
    }

    fun overrideClicks(
            positiveClick: () -> Unit = {},
            negativeClick: () -> Unit = {},
            neutralClick: () -> Unit = {}
    ): QuickDialog {
        quickDialogBinding?.positiveButton?.setOnClickListener {
            positiveClick()
            dismiss()
        }
        quickDialogBinding?.negativeButton?.setOnClickListener {
            negativeClick()
            dismiss()
        }
        quickDialogBinding?.neutralButton?.setOnClickListener {
            neutralClick()
            dismiss()
        }
        return this
    }

    fun overrideClicks(
            positiveClick: (dismiss: () -> Unit) -> Unit = { d -> },
            negativeClick: (dismiss: () -> Unit) -> Unit = { d -> },
            neutralClick: (dismiss: () -> Unit) -> Unit = { d -> }
    ): QuickDialog {
        quickDialogBinding?.positiveButton?.setOnClickListener {
            positiveClick(dismiss)
        }
        quickDialogBinding?.negativeButton?.setOnClickListener {
            negativeClick(dismiss)
        }
        quickDialogBinding?.neutralButton?.setOnClickListener {
            neutralClick(dismiss)
        }
        return this
    }

    fun overrideClicks(
            positiveClick: (dismiss: () -> Unit, inputText: String) -> Unit = { d, s -> },
            negativeClick: (dismiss: () -> Unit, inputText: String) -> Unit = { d, s -> },
            neutralClick: (dismiss: () -> Unit, inputText: String) -> Unit = { d, s -> }
    ): QuickDialog {
        quickDialogBinding?.positiveButton?.setOnClickListener {
            positiveClick(dismiss, inputText)
        }
        quickDialogBinding?.negativeButton?.setOnClickListener {
            negativeClick(dismiss, inputText)
        }
        quickDialogBinding?.neutralButton?.setOnClickListener {
            neutralClick(dismiss, inputText)
        }
        return this
    }

    fun withInputHint(hint: String): QuickDialog {
        quickDialogBinding?.input?.hint = hint
        return this
    }

    fun withInputLength(length: Int): QuickDialog {
        quickDialogBinding?.input?.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(length))
        return this
    }

    fun withInputType(inputType: Int): QuickDialog {
        try {
            quickDialogBinding?.input?.inputType = inputType
        } catch (e: Exception) {
            Log.e(TAG, "Invalid input type")
            e.printStackTrace()
        }
        return this
    }

    fun showPositiveButton(): QuickDialog {
        quickDialogBinding?.positiveButton?.visibility = View.VISIBLE
        return this
    }

    fun showNeutralButton(): QuickDialog {
        quickDialogBinding?.neutralButton?.visibility = View.VISIBLE
        return this
    }

    fun hideNeutralButton(): QuickDialog {
        quickDialogBinding?.neutralButton?.visibility = View.GONE
        return this
    }

    fun getRecyclerView(): RecyclerView {
        return quickDialogBinding!!.cardObjects
    }

    fun overrideButtonNames(positive: String = "OK", negative: String = "Cancel", neutral: String = ""): QuickDialog {
        quickDialogBinding?.positiveButton?.text = positive.toUpperCase()
        quickDialogBinding?.negativeButton?.text = negative.toUpperCase()
        quickDialogBinding?.neutralButton?.text = neutral.toUpperCase()
        return this
    }

    fun overrideButtonNames(positive: Int = R.string.ok, negative: Int = R.string.string_cancel, neutral: Int = R.string.maybe): QuickDialog {
        quickDialogBinding?.positiveButton?.text = context.resources.getString(positive).toUpperCase()
        quickDialogBinding?.negativeButton?.text = context.resources.getString(negative).toUpperCase()
        quickDialogBinding?.neutralButton?.text = context.resources.getString(neutral).toUpperCase()
        return this
    }

    fun show(): QuickDialog {
        try {
            quickDialog?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return this
    }

    val dismiss: () -> Unit = {
        try {
            quickDialog?.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

