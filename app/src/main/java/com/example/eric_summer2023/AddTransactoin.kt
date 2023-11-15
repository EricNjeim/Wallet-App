package com.example.eric_summer2023

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eric_summer2023.databinding.ActivityAddTransactoinBinding
import com.example.eric_summer2023.databinding.ActivityHomescreenBinding
import java.text.DecimalFormat

class AddTransactoin : AppCompatActivity() {
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
     lateinit var selectedItem:String
    var ss:String=" "
    private val optionsMap = mapOf(
        "Withdrawal" to arrayOf(
            "Select One",
            "Food & Drinks",
            "Shopping",
            "Housing",
            "Entertainment",
            "Transportation",
            "Investments",
            "Others"
        ),
        "Deposit" to arrayOf("Select One", "Salary","Gift", "Investment Returns", "Allowance", "Other")
    )


    private lateinit var binding: ActivityAddTransactoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactoinBinding.inflate(layoutInflater)
        setContentView(binding.root)


        spinner1 = binding.coursesspinner
        spinner2 = binding.spinner


        // Create ArrayAdapter for spinner1 with initial options
        val spinner1Adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            optionsMap.keys.toList()
        )
        spinner1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = spinner1Adapter

        // Create ArrayAdapter for spinner2 with empty options
        val spinner2Adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            mutableListOf<String>()
        )
        spinner2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = spinner2Adapter

        // Set a listener on spinner1 to update spinner2's options
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItem = spinner1.selectedItem.toString()
                val optionsForSpinner2 = optionsMap[selectedItem] ?: emptyArray()
                spinner2Adapter.clear()
                spinner2Adapter.addAll(*optionsForSpinner2)
                spinner2Adapter.notifyDataSetChanged()




            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected in spinner1 (if needed)
            }
        }



    binding.button2.setOnClickListener {
        if (spinner2.selectedItem != null) {
            ss = spinner2.selectedItem.toString()}
        if (binding.editTextNumberDecimal.text.toString() .isEmpty()) {
            Toast.makeText(this,"Please enter a value", Toast.LENGTH_LONG).show()}
        else{if(selectedItem=="Withdrawal" && ss=="Select One"){
            Toast.makeText(this,"Please select a withdrawal option", Toast.LENGTH_LONG).show()
        }
        else if(selectedItem=="Deposit" && ss=="Select One"){
            Toast.makeText(this,"Please select a deposit option", Toast.LENGTH_LONG).show()
        }
        else{
            val intent9 = Intent(this, Homescreen::class.java).apply{
                if(selectedItem=="Deposit") {
                    putExtra("Extra_Message", selectedItem)
                    putExtra("Extra", ss)
                    putExtra("Ex", binding.editTextNumberDecimal.text.toString().toDouble())
                }
                else{
                    putExtra("Extra_Message", selectedItem)
                    putExtra("Extra", ss)
                    putExtra("Ex", binding.editTextNumberDecimal.text.toString().toDouble())
                }
            }


            setResult(Activity.RESULT_OK, intent9)
            finish()
        }}
    }
}
}
