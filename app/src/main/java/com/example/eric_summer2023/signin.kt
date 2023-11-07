package com.example.eric_summer2023


import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eric_summer2023.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class signin : AppCompatActivity() {

    private lateinit var binding:ActivitySigninBinding
    private lateinit var firebaseAuth: FirebaseAuth
   // private lateinit var sf:SharedPreferences
    private lateinit var editor:SharedPreferences.Editor

    private lateinit var emailtext:EditText
    lateinit var fn: TextView

    var db = FirebaseFirestore.getInstance()

     public override fun onStart() {
         super.onStart()
         val currentUser = firebaseAuth.currentUser
         if (currentUser != null) {
             val intent=Intent(this,Homescreen::class.java)
             startActivity(intent)
         }
     }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth=FirebaseAuth.getInstance()
        val ss=intent.getStringExtra("Extra_Message")

        binding.gotosignup.setOnClickListener {
            val intent2 = Intent(this, Signup::class.java)
            intent2.setAction("");
            startActivity(intent2)
        }

        binding.button.setOnClickListener {
            val email=binding.email.text.toString()
            val pass=binding.pass.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if (it.isSuccessful) {

                        val intent3= Intent(this,AddTransactoin::class.java)
                         startActivity(intent3)

                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                    }


                }

            }
            else{
                Toast.makeText(this,"Empty fields not allowed", Toast.LENGTH_LONG).show() }
        }

    }
    }

/*
    override fun onPause() {
        super.onPause()
        emailtext=binding.email
        val emailsf=emailtext.text.toString()
         editor.apply {
             putString("sf_email",emailsf)
             commit()
         }
    }

    override fun onResume() {
        super.onResume()
        emailtext=binding.email
        val emailsf=sf.getString("sf_email", null)
        emailtext.setText(emailsf)
    }*/




