package com.example.eric_summer2023

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eric_summer2023.databinding.ActivityDuebillsBinding
import com.example.eric_summer2023.databinding.ActivityHomescreenBinding
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class Duebills : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Duebillsadapter
    private lateinit var binding: ActivityDuebillsBinding
    private var bills = ArrayList<ArrayList<String>>()

    //var closestbill = HashMap<String,Double>()
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDuebillsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val preferences = getSharedPreferences("Eric-Bills", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = preferences.getString("Recycler List", "")

        val type = object : TypeToken<ArrayList<ArrayList<String>>>() {}.type
        bills = gson.fromJson(json, type) ?: ArrayList()

        recyclerView = binding.rvv
        var layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager

        adapter = Duebillsadapter(bills)
        recyclerView.adapter = adapter
    }
}


