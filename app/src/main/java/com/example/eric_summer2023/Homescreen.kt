package com.example.eric_summer2023


import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eric_summer2023.databinding.ActivityHomescreenBinding
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson


@Suppress("DEPRECATION")
class Homescreen : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyRecyclerViewAdapter
    private lateinit var binding: ActivityHomescreenBinding
    private var totalbalance: Double=0.0
    private var trans = ArrayList<ArrayList<String>>()

    val db=Firebase.firestore

    companion object {
        private const val REQUEST_CODE = 123 // Choose any request code you prefer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomescreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db.collection("Eric Njeim").document("Details")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful ) {
                    val documentSnapshot = task.result
                    if (documentSnapshot.exists()) {
                        totalbalance = documentSnapshot.getDouble("Balance")!!
                        binding.textView6.text = totalbalance.toString()
                        binding.textView3.text= documentSnapshot.getString("Full Name")!!

                    }}}

        val sharedPreferences = getSharedPreferences("Your_Pref_Name", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("Recycler List", "")

        val type = object : TypeToken<ArrayList<ArrayList<String>>>() {}.type
        trans = gson.fromJson(json, type) ?: ArrayList()




        recyclerView = binding.rv
        var layoutManager= LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        recyclerView.layoutManager =layoutManager

        adapter = MyRecyclerViewAdapter(trans)
        recyclerView.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        binding.cvbills.setOnClickListener {
            val intent0=Intent(this, Billsspinner::class.java)
            startActivityForResult(intent0, REQUEST_CODE)
        }
        binding.cvv.setOnClickListener {
            val intent = Intent(this, AddTransactoin::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
        binding.recbills.setOnClickListener {
            val intent90 = Intent(this, Duebills::class.java)
            startActivityForResult(intent90, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val x = data?.getDoubleExtra("Ex",0.0)
            val y = data?.getStringExtra("Extra")
            val z = data?.getStringExtra("Extra_Message")

            if (x != 0.0 && x!=null && y != null && z != null) {
                val temp = ArrayList<String>()
                temp.add(z)
                temp.add(y)
                temp.add(x.toString())

                val documentRef = db.collection("Eric Njeim").document("Details")
                db.runTransaction { transaction ->
                    val documentSnapshot = transaction.get(documentRef)

                    if (documentSnapshot.exists()) {
                        val currentTotal:Double?= documentSnapshot.getDouble("Balance")
                        var newTotal:Double
                        if(z=="Deposit"){
                         newTotal = currentTotal !!+x
                        binding.textView6.text=newTotal.toString()}
                        else{
                            newTotal = currentTotal !!-x
                            binding.textView6.text=newTotal.toString()}
                        transaction.update(documentRef, "Balance", newTotal)
                    }}


                trans.add(temp)
                val sharedPreferences: SharedPreferences =
                    getSharedPreferences("Your_Pref_Name", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val g = Gson()
                val j = g.toJson(trans)
                editor.putString("Recycler List", j)
                editor.apply()

                adapter.notifyDataSetChanged()
            }
        }
    }
}