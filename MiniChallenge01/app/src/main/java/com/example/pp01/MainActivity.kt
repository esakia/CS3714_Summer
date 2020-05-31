package com.example.pp01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

//sample solution for PP01
class MainActivity : AppCompatActivity() {

    val hp:String ="HP"
    val lenovo:String ="Lenovo"
    private var selected_brand: String? =null
    private var selected_cpu: String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Source: https://developer.android.com/guide/topics/ui/controls/spinner
        val spinner: Spinner = this.findViewById(R.id.brands)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.laptops,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selected_brand = parent?.getItemAtPosition(position).toString()
                updatePrice()
            }
        }
    }

    fun radioButtonSelected(view: View){
        selected_cpu = view.tag.toString()
        updatePrice()
    }


    private fun updatePrice(){



        if(selected_brand==lenovo){

            Log.d("sometag","debug message")
                when(selected_cpu){
                    "i3"-> setTextView("$330")
                    "i5"-> setTextView("$550")
                    "i7"-> setTextView("$770")

                }

        }
        else if(selected_brand==hp){


            when(selected_cpu){
                "i3"-> setTextView("$300")
                "i5"-> setTextView("$500")
                "i7"-> setTextView("$700")

            }
        }

    }


    private fun setTextView(price: String){
        findViewById<TextView>(R.id.price).text = price
    }
}
