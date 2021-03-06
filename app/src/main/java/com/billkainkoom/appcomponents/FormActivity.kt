package com.billkainkoom.appcomponents

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.billkainkoom.appcomponents.databinding.ActivityFormBinding
import com.billkainkoom.appcomponents.databinding.AnimalBinding
import com.billkainkoom.appcomponents.databinding.FurnitureBinding
import com.billkainkoom.appcomponents.databinding.PersonBinding
import com.billkainkoom.ogya.databinding.ComponentQuickFormInputBinding
import com.billkainkoom.ogya.quicklist.LayoutManager
import com.billkainkoom.ogya.quicklist.Listable
import com.billkainkoom.ogya.quicklist.ListableAdapter
import com.billkainkoom.ogya.quicklist.ListableHelper
import com.billkainkoom.ogya.quicklist.formcomponents.ComponentQuickFormInput
import com.billkainkoom.ogya.shared.*

class FormActivity : AppCompatActivity() {

    var results = hashMapOf<String, String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_form)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        val cardObjects: RecyclerView = findViewById(R.id.card_objects)
        val adapter = loadList(this, binding, cardObjects)

    }

    fun loadList(context: Context, binding: ActivityFormBinding, recyclerView: RecyclerView): ListableAdapter<Listable> {
        var prev = 0
        val form = mutableListOf(
                QuickFormInputElement(
                        name = "time",
                        value = "",
                        hint = "Time",
                        placeholder = "17:00",
                        type = QuickFormInputType.Time
                ),
                QuickFormInputElement(
                        name = "firstname",
                        value = "",
                        hint = "Firstname",
                        placeholder = "Kwame",
                        actionButton = ActionButton(image = R.drawable.ic_info_outline_black_24dp,showOnFocus = true){

                        },
                        inputLength = 5,
                        textWatcher = TextWatcher(afterTextChanged = { s->

                            if(s.length == 2 && (s.length - prev > 0)){
                                s.insert(2,"/")
                            }
                            prev = s.length
                        }),
                        listableSpan = 2.0
                ),
                QuickFormInputElement(
                        name = "lastname",
                        value = "",
                        hint = "Lastname",
                        placeholder = "Lee",
                        inputLength = 3,
                        listableSpan = 2.0
                ),
                QuickFormInputElement(
                        name = "address",
                        value = "",
                        hint = "Address",
                        placeholder = "Kasoa 212 LP",
                        inputType = InputType.TYPE_CLASS_NUMBER,
                        inputLength = 3
                ),
                QuickFormInputElement(
                        name = "phone_number",
                        value = "",
                        hint = "Phone Number",
                        placeholder = "0266 175 924",
                        inputType = InputType.TYPE_CLASS_PHONE,
                        inputLength = 3
                ),
                MyPerson(name = "Adwoa Lee", email = "adwoalee@gmail.com"),
                QuickFormInputElement(
                        name = "date",
                        value = "",
                        hint = "Date",
                        placeholder = "22-01-1992",
                        type = QuickFormInputType.Date
                ),
                QuickFormInputElement(
                        name = "time",
                        value = "",
                        hint = "Time",
                        placeholder = "17:00",
                        type = QuickFormInputType.Time
                )
        )


        var adapter: ListableAdapter<Listable>? = null
        adapter = ListableHelper.loadList(
                context = context,
                recyclerView = recyclerView,
                listableType = ListableTypes.Person,
                useCustomSpan = true,
                gridSize = 2,
                listables = form,
                listableBindingListener = { listable, listableBinding, position ->
                    when (listable.getListableType()) {
                        OgyaListableTypes.QuickFormInput -> {
                            ComponentQuickFormInput.render(listableBinding as ComponentQuickFormInputBinding, listable as QuickFormInputElement)
                        }
                        ListableTypes.Person -> {
                            MyPersonComponent.render(listableBinding as PersonBinding, listable as MyPerson)
                        }
                        ListableTypes.Animal -> {
                            AnimalComponent.render(listableBinding as AnimalBinding, listable as Animal)
                        }
                        ListableTypes.Furniture -> {
                            FurnitureComponent.render(listableBinding as FurnitureBinding, listable as Furniture)
                        }
                    }

                },
                listableClickedListener = { listable, listableBinding, position ->

                },
                layoutManagerType = LayoutManager.Grid
        )

        binding.submit.setOnClickListener {
            var results: HashMap<String, String> = adapter.retrieveFormValues()
        }

        return adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
