package com.example.eric_summer2023


import android.app.Activity
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.gson.Gson
@Suppress("DEPRECATION")
class Homescreen : AppCompatActivity() {
    val TAG="HELLOWORLD"
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyRecyclerViewAdapter
    private lateinit var binding: ActivityHomescreenBinding
    private var totalbalance: Double=0.0
    private var trans = ArrayList<ArrayList<String>>()
    private var fullname:String=""
    val db= FirebaseFirestore.getInstance()
    companion object {
        private const val REQUEST_CODE = 123
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomescreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings
        Log.d(TAG,"I am talking to you from "+Thread.currentThread())
        //fullname=intent.getStringExtra("FN")!!
        db.collection("MYWALLET").document("Details")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful ) {
                    Log.d(TAG,"I am talking to you from "+Thread.currentThread())
                    val documentSnapshot = task.result
                    if (documentSnapshot.exists()) {
                        totalbalance = documentSnapshot.getDouble("Balance")!!
                        binding.textView6.text = String.format("%.2f", totalbalance)
                        fullname=documentSnapshot.getString("Full Name")!!
                        binding.textView3.text= fullname
                    }}}

        var sharedPreferences = getSharedPreferences(fullname, Context.MODE_PRIVATE)
        var gson = Gson()
        var json = sharedPreferences.getString("Recycler List", "")
        var type = object : TypeToken<ArrayList<ArrayList<String>>>() {}.type
        trans = gson.fromJson(json, type) ?: ArrayList()
/*
         sharedPreferences = getSharedPreferences(fullname, Context.MODE_PRIVATE)
        gson = Gson()
        json = sharedPreferences.getString("Recycler List", "")

         type = object : TypeToken<ArrayList<ArrayList<String>>>() {}.type
        trans = gson.fromJson(json, type) ?: ArrayList()

*/

        recyclerView = binding.rv
        var layoutManager= LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        recyclerView.layoutManager =layoutManager
        adapter = MyRecyclerViewAdapter(trans)
        recyclerView.adapter = adapter
    }
    override fun onResume() {
            super.onResume()
            binding.cvvv.setOnClickListener {
            val intent0=Intent(this, AddTransactoin::class.java)
            startActivityForResult(intent0, REQUEST_CODE) }
            binding.cvv.setOnClickListener {
            val intent = Intent(this,Billsspinner::class.java)
            startActivityForResult(intent, REQUEST_CODE) }
            binding.cvbills.setOnClickListener {
            val intent90 = Intent(this, Duebills::class.java)
            startActivityForResult(intent90, REQUEST_CODE)
        }
            binding.cvnew.setOnClickListener {
            val intent00 = Intent(this, Retro::class.java)
            startActivityForResult(intent00, REQUEST_CODE)
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

                val documentRef = db.collection("MYWALLET").document("Details")
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
                    getSharedPreferences(fullname, Context.MODE_PRIVATE)
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