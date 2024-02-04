package com.example.eric_summer2023


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eric_summer2023.databinding.ActivityHomescreenBinding
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class Homescreen : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyRecyclerViewAdapter
    private lateinit var binding: ActivityHomescreenBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val db= FirebaseFirestore.getInstance()
    private val viewModel: CoroutineViewModel by viewModels()
    private var trans = ArrayList<ArrayList<String>>()
    private var fullname:String=""
    private var gson=Gson()
    private lateinit var json: String
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
        viewModel.AsyncGet()
        observeViewModel()


        sharedPreferences = getSharedPreferences(fullname, Context.MODE_PRIVATE)
        json = sharedPreferences.getString("Recycler List", "")!!
        val type = object : TypeToken<ArrayList<ArrayList<String>>>() {}.type
        trans = gson.fromJson(json, type) ?: ArrayList()
        recyclerView = binding.rv
        val layoutManager= LinearLayoutManager(this)
        recyclerView.layoutManager =layoutManager
        trans.reverse()
        adapter = MyRecyclerViewAdapter(trans)
        recyclerView.adapter = adapter
    }
    @Suppress("DEPRECATION")
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
        viewModel.fullName.observe(this) { name ->
            binding.textView3.text = name
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var newTotal:Double

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val x = data?.getDoubleExtra("Ex",0.0)
            val y = data?.getStringExtra("Extra")
            val z = data?.getStringExtra("Extra_Message")

            if (x != 0.0 && x!=null && y != null && z != null) {
                val temp = ArrayList<String>()
                temp.add(z)
                temp.add(y)
                temp.add(x.toString())
                        if(z=="Deposit"){
                            newTotal=binding.textView6.text.toString().toDouble()
                            newTotal += x
                            viewModel.AsyncUpdate(newTotal)
                            binding.textView6.text=newTotal.toString()}
                        else{
                            newTotal=binding.textView6.text.toString().toDouble()
                            newTotal -=x
                            viewModel.AsyncUpdate(newTotal)
                            binding.textView6.text=newTotal.toString()}
                trans.add(temp)
                sharedPreferences = getSharedPreferences(fullname, Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val j = gson.toJson(trans)
                editor.putString("Recycler List", j)
                editor.apply()
                adapter.notifyItemInserted(trans.size-1)
            }
        }
    }
}
