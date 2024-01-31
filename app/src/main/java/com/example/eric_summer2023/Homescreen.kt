package com.example.eric_summer2023


import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eric_summer2023.data.repository.Myrepository
import com.example.eric_summer2023.databinding.ActivityHomescreenBinding
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("DEPRECATION")
@AndroidEntryPoint
class Homescreen : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyRecyclerViewAdapter
    private lateinit var binding: ActivityHomescreenBinding
    private val db= FirebaseFirestore.getInstance()
    private val viewModel: CoroutineViewModel by viewModels()
    private var trans = ArrayList<ArrayList<String>>()
    private var fullname:String=""
    companion object {
        private const val REQUEST_CODE = 123
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomescreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings
        */
        viewModel.performAsyncOperation()
        observeViewModel()


        val sharedPreferences = getSharedPreferences(fullname, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("Recycler List", "")
        val type = object : TypeToken<ArrayList<ArrayList<String>>>() {}.type
        trans = gson.fromJson(json, type) ?: ArrayList()
        recyclerView = binding.rv
        val layoutManager= LinearLayoutManager(this)
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
            startActivityForResult(intent90, REQUEST_CODE) }
            binding.cvnew.setOnClickListener {
            val intent00 = Intent(this, Retro::class.java)
            startActivityForResult(intent00, REQUEST_CODE)
        }
    }
    private fun observeViewModel() {
        viewModel.balance.observe(this) { balance ->
            binding.textView6.text = String.format("%.2f", balance)
        }
        Log.e(ContentValues.TAG, "ddddd")
        viewModel.fullName.observe(this) { name ->
            binding.textView3.text = name
        }
    }

    @Deprecated("Deprecated in Java")
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
                        val newTotal:Double
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
                adapter.notifyItemInserted(trans.size-1)
            }
        }
    }
}
