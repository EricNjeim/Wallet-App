package com.example.eric_summer2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eric_summer2023.databinding.ActivityRecviewBinding

class recview : AppCompatActivity() {
//    val months = listOf("January", "February", "March","April","May","June","July","August","September","October","November","December")
    private lateinit var binding: ActivityRecviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRecviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
  //      val recyclerview=binding.recyclerView
    //    recyclerview.layoutManager= LinearLayoutManager(this)
      //  recyclerview.adapter=MyRecyclerViewAdapter(months)
    }
}