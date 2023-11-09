package com.example.eric_summer2023


import android.R
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.eric_summer2023.databinding.ActivityBillsspinnerBinding
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.util.Calendar

class Billsspinner : AppCompatActivity() {

    private lateinit var binding: ActivityBillsspinnerBinding
    private var bills= ArrayList<ArrayList<String>>()
   private var alarm: AlarmManager= getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    lateinit var selectedItem:String

    private val optionsMap = mapOf(
                "Withdrawal" to arrayOf(
                    "Select One",
                    "Food/Drink",
                    "Game",
                    "Transportation",
                    "Entertainment",
                    "Tuition",
                    "Clothes",
                    "Other"
                ),
                "Deposit" to arrayOf("Select One", "Gift", "Investment Returns", "Allowance", "Other")
            )



            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = ActivityBillsspinnerBinding.inflate(layoutInflater)
                setContentView(binding.root)




                spinner1 = binding.coursesspinner
                spinner2 = binding.spinner

                val dayPicker=binding.dayPicker
                val monthPicker=binding.monthPicker
                val yearPicker=binding.yearPicker
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                yearPicker.minValue = currentYear
                yearPicker.maxValue = currentYear+5


                val allmonths= arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
                monthPicker.displayedValues=allmonths
                monthPicker.minValue = 1
                monthPicker.maxValue = 12
                dayPicker.minValue = 1
                dayPicker.maxValue = 31
                monthPicker.setOnValueChangedListener { _, _, newwVal ->
                    monthschange(newwVal,yearPicker,dayPicker)
                }

                yearPicker.setOnValueChangedListener { _, _, newVal ->
                    yearchange(newVal,monthPicker,dayPicker)
                }




                val spinner1Adapter = ArrayAdapter(
                    this,
                    R.layout.simple_spinner_item,
                    optionsMap.keys.toList()
                )
                spinner1Adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                spinner1.adapter = spinner1Adapter

                // Create ArrayAdapter for spinner2 with empty options
                val spinner2Adapter = ArrayAdapter(
                    this,
                    R.layout.simple_spinner_item,
                    mutableListOf<String>()
                )
                spinner2Adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
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

                    val temp=ArrayList<String>()
                    temp.add(spinner1.selectedItem.toString())
                    temp.add(spinner2.selectedItem.toString())
                    val tt:String=dayPicker.value.toString()+"/"+monthPicker.value.toString()+"/"+yearPicker.value.toString()



                    var cal= Calendar.getInstance();
                    cal.set(Calendar.DAY_OF_MONTH, dayPicker as Int);
                    cal.set(Calendar.MONTH, allmonths.indexOf(monthPicker.toString())+1)
                    cal.set(Calendar.YEAR, yearPicker as Int);
                    cal.set(Calendar.HOUR_OF_DAY, 9 );
                    val intent799 = Intent(this, ReminderService::class.java)
                    intent799.putExtra("Date", tt)
                    val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, intent799, PendingIntent.FLAG_UPDATE_CURRENT)
                    alarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.timeInMillis,pendingIntent);

                    temp.add(tt)
                    val preferences = getSharedPreferences("Eric-Bills", Context.MODE_PRIVATE)
                    val gson = Gson()
                    val json = preferences.getString("Recycler List", "")

                    val type = object : TypeToken<ArrayList<ArrayList<String>>>() {}.type
                    bills = gson.fromJson(json, type) ?: ArrayList()


                    bills.add(temp)
                    if(bills.size==1){
                        startService(Intent(this, ReminderService::class.java))
                    }
                    val sharedPreferences: SharedPreferences =
                        getSharedPreferences("Eric-Bills", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    val g = Gson()
                    val j = g.toJson(bills)
                    editor.putString("Recycler List", j)
                    editor.apply()
                    finish()



            }}
    fun yearchange(newVal:Int,monthPicker:NumberPicker,dayPicker:NumberPicker){
        if ( newVal% 4 == 0) {

                if (monthPicker.value == 2) {
                    dayPicker.minValue = 1
                    dayPicker.maxValue = 29
                } else if (monthPicker.value== 4 || monthPicker.value  == 6 || monthPicker.value == 9 || monthPicker.value == 11) {
                    dayPicker.minValue = 1
                    dayPicker.maxValue = 30
                } else {
                    dayPicker.minValue = 1
                    dayPicker.maxValue = 31
                }


        } else {

                if (monthPicker.value == 2) {
                    dayPicker.minValue = 1
                    dayPicker.maxValue = 28
                } else if (monthPicker.value == 4 || monthPicker.value == 6 || monthPicker.value == 9 || monthPicker.value == 11) {
                    dayPicker.minValue = 1
                    dayPicker.maxValue = 30
                } else {
                    dayPicker.minValue = 1
                    dayPicker.maxValue = 31
                }
            }}


    fun monthschange(newwVal:Int,yearPicker:NumberPicker,dayPicker:NumberPicker){
        if (yearPicker.value % 4 == 0) {

            if (newwVal== 2) {
                dayPicker.minValue = 1
                dayPicker.maxValue = 29
            } else if (newwVal == 4 || newwVal == 6 || newwVal == 9 || newwVal== 11) {
                dayPicker.minValue = 1
                dayPicker.maxValue = 30
            } else {
                dayPicker.minValue = 1
                dayPicker.maxValue = 31
            }}
        else {
            if(newwVal == 2) {
                dayPicker.minValue = 1
                dayPicker.maxValue = 28
            } else if (newwVal == 4 || newwVal == 6 || newwVal == 9 || newwVal == 11) {
                dayPicker.minValue = 1
                dayPicker.maxValue = 30
            } else {
                dayPicker.minValue = 1
                dayPicker.maxValue = 31
            }
        }
    }
        }
