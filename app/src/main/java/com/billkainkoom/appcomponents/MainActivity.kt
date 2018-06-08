package com.billkainkoom.appcomponents

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.widget.Toast
import com.billkainkoom.appcomponents.databinding.AnimalBinding
import com.billkainkoom.appcomponents.databinding.FurnitureBinding
import com.billkainkoom.appcomponents.databinding.PersonBinding
import com.billkainkoom.ogya.quickdialog.QuickDialog
import com.billkainkoom.ogya.quickdialog.QuickDialogType
import com.billkainkoom.ogya.shared.QuickObject
import com.billkainkoom.ogya.quicklist.*
import com.billkainkoom.ogya.quickpermissions.PermissionHelper
import kotlinx.android.synthetic.main.activity_main.*

object ListableTypes {
    val Person = ListableType(R.layout.person)
    val Animal = ListableType(R.layout.animal)
    val Furniture = ListableType(R.layout.furniture)
}

data class MyPerson(val name: String = "", val email: String = "", val type: ListableType = ListableTypes.Person) : Listable {
    override fun getListableType(): ListableType? {

        return type
    }
}

data class Animal(val name: String = "", val specie: String = "") : Listable {
    override fun getListableType(): ListableType? {
        return ListableTypes.Animal
    }
}

data class Furniture(val name: String = "", val specie: String = "") : Listable {
    override fun getListableType(): ListableType? {
        return ListableType(R.layout.furniture)
    }
}

data class L(val p: Int = 0)
class MainActivity : AppCompatActivity() {

    var context: Context? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Quick Dialog"
        context = this


        val a = loadList(context!!, card_objects)

        Handler().postDelayed({
            a.add(Furniture(name = "Cat", specie = "Felidae"))
        }, 3000)



        button.setOnClickListener {
            //d6(context!!)
            d8(context!!)

        }
    }

    fun loadList(context: Context, recyclerView: RecyclerView): ListableAdapter<Listable> {
        val people = mutableListOf(
                MyPerson(name = "Kwasi Malopo", email = "kwasimalopo@outlook.com"),
                MyPerson(name = "Adwoa Lee", email = "adwoalee@gmail.com", type = ListableTypes.Furniture),
                Animal(name = "Cassava", specie = "Plantae"),
                Animal(name = "Cat", specie = "Felidae"),
                Furniture(name = "Cat", specie = "Felidae")
        )


        val a = ListableHelper.loadList(
                context = context,
                recyclerView = recyclerView,
                listableType = ListableTypes.Person,
                listables = people,
                listableBindingListener = { listable, listableBinding, position ->
                    when (listable) {
                        is MyPerson -> {
                            if (listableBinding is PersonBinding) {
                                listableBinding.name.text = listable.name
                                listableBinding.email.text = listable.email
                            } else if (listableBinding is FurnitureBinding) {
                                listableBinding.image.setImageResource(R.drawable.ic_info_outline_black_24dp)
                                listableBinding.name.text = listable.name
                                listableBinding.specie.text = listable.email
                            }
                        }
                        is Animal -> {
                            if (listableBinding is AnimalBinding) {
                                listableBinding.name.text = listable.name
                                listableBinding.specie.text = listable.specie
                            }
                        }
                        is Furniture -> {
                            if (listableBinding is FurnitureBinding) {
                                listableBinding.image.setImageResource(R.drawable.ic_info_outline_black_24dp)
                                listableBinding.name.text = listable.name
                                listableBinding.specie.text = listable.specie
                            }
                        }
                    }

                },
                listableClickedListener = { listable, listableBinding, position ->
                    when (listable) {
                        is MyPerson -> {
                            Toast.makeText(context, listable.name, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                layoutManagerType = LayoutManager.Vertical
        )

        return a
    }


    fun d1(context: Context) {
        QuickDialog(
                context = this,
                style = QuickDialogType.Message,
                title = "Hello World",
                message = "The quick dialog jumped over the old dialog",
                image = R.drawable.ic_info_outline_black_24dp)
                .overrideButtonNames("OK").overrideClicks({ ->
                    Toast.makeText(context, "Clicked on OK", Toast.LENGTH_SHORT).show()
                }).show()
    }

    fun d2(context: Context) {
        QuickDialog(
                context = context,
                style = QuickDialogType.Progress,
                title = "Please wait",
                message = "Walking round the world")
                .show()
    }

    fun d3(context: Context) {
        QuickDialog(
                context = context,
                style = QuickDialogType.Progress,
                title = "Please wait",
                message = "Walking round the world")
                .overrideButtonNames("Hide Progress")
                .overrideClicks({ ->
                    Toast.makeText(context, "Clicked on Hide Progress", Toast.LENGTH_SHORT).show()
                }).showPositiveButton()
                .show()
    }

    fun d4(context: Context) {
        QuickDialog(
                context = context,
                style = QuickDialogType.Alert,
                title = "Proceed",
                message = "Do you want to take this action")
                .overrideButtonNames("Yes", "No")
                .overrideClicks(positiveClick = { ->
                    Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show()
                }, negativeClick = { ->
                    Toast.makeText(context, "No", Toast.LENGTH_SHORT).show()
                })
                .show()
    }

    fun d5(context: Context) {
        QuickDialog(
                context = context,
                style = QuickDialogType.Alert,
                title = "Proceed",
                message = "Do you want to take this action")
                .overrideButtonNames("Yes", "No")
                .overrideClicks(positiveClick = { dismiss ->
                    if (true) {
                        Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }, negativeClick = { dismiss ->
                    if (true) {
                        Toast.makeText(context, "No", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                })
                .show()
    }

    fun d6(context: Context) {
        QuickDialog(
                context = context,
                style = QuickDialogType.WithInput,
                title = "Verify Code",
                message = "Please verify the SMS code that was sent to you")
                .overrideButtonNames("Verify", "Cancel", "Re-send")
                .overrideClicks(positiveClick = { dismiss, inputText ->
                    if (inputText.length < 3) {
                        Toast.makeText(context, "Please enter a 4 digit code", Toast.LENGTH_SHORT).show()
                    } else if (inputText == "4000") {
                        Toast.makeText(context, "Verified", Toast.LENGTH_SHORT).show()
                        dismiss()
                    } else {
                        Toast.makeText(context, "You entered the wrong code", Toast.LENGTH_SHORT).show()
                    }
                }, negativeClick = { dismiss, inputText ->
                    dismiss()
                }, neutralClick = { dismiss, inputText ->
                    //Your action
                    dismiss()
                })
                .withInputHint("Code")
                .withInputLength(4)
                .withInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
                .showNeutralButton()
                .show()
    }

    fun d7(context: Context) {
        QuickDialog(
                context = this,
                style = QuickDialogType.List,
                title = "Hello World",
                message = "The quick dialog jumped over the old dialog",
                image = R.drawable.ic_info_outline_black_24dp)
                .overrideButtonNames("Close").overrideClicks({ ->
                    Toast.makeText(context, "Clicked on Close", Toast.LENGTH_SHORT).show()
                }).configureList(configuration = { dismiss, recyclerView ->
                    loadList(context, recyclerView)
                }).show()
    }

    val REQUEST_CODE = 100
    var permissionHelper: PermissionHelper? = null
    fun d8(context: Context) {
        permissionHelper = PermissionHelper(this, context)
        if (permissionHelper!!.requestPermissions(REQUEST_CODE, Manifest.permission.READ_CONTACTS)) {
            //permissions are granted , if not a call to ask for permission would be triggered
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        when (requestCode) {
            REQUEST_CODE -> {
                val quickObject = QuickObject(0, "Calling is great", "MainActivity wants to read your contacts", R.drawable.ic_info_outline_black_24dp, "")
                permissionHelper!!.handlePermissionRequestResponse(quickObject, requestCode, permissions, grantResults, object : PermissionHelper.PermissionRequestListener {
                    override fun onPermissionRequestResponse(granted: Boolean) {
                        if (granted) {
                            //activityAddMemberBinding!!.selectImage.performClick()
                            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }
}

