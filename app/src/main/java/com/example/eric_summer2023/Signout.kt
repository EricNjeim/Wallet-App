package com.example.eric_summer2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eric_summer2023.databinding.ActivitySignoutBinding
import com.example.eric_summer2023.databinding.ActivitySignupBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Signout : AppCompatActivity() {
    private lateinit var binding: ActivitySignoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button3.setOnClickListener {

            Firebase.auth.signOut()


        }

    }
}