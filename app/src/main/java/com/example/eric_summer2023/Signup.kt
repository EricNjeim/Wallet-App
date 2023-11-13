package com.example.eric_summer2023


import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.eric_summer2023.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    val db = Firebase.firestore
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

        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()


        binding.button.setOnClickListener {

            val email=binding.editTextTextem.text.toString()
            val pass=binding.pass.text.toString()
            val confirmpass=binding.confpass.text.toString()
            val fn=binding.fullname.text.toString()
            if(email.isNotEmpty() && pass.isNotEmpty() && confirmpass.isNotEmpty()){
                if(pass==confirmpass){
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if (it.isSuccessful){
                            saveUserData()
                            val intent = Intent(this, signin::class.java).apply{

                                putExtra("Extra_Message", email)
                            }

                            startActivity(intent)



                            }
                        else{
                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_LONG).show() } } }
                else{
                    Toast.makeText(this,"Passwords not matching", Toast.LENGTH_LONG).show() } }
            else{
                Toast.makeText(this,"Empty fields not allowed", Toast.LENGTH_LONG).show()
            } }

    }
      fun saveUserData(){

         // Create a new user with a first and last name
         val user = hashMapOf(
             "Full Name" to binding.fullname.text.toString(),
             "Email" to binding.editTextTextem.text.toString(),
             "Birth Year" to 2003,
             "Balance" to 0.0,
         )


         db.collection(binding.fullname.text.toString())
             .document("Details")
             .set(user)
             .addOnSuccessListener { _ ->

             }
             .addOnFailureListener { e ->
                 Log.w(TAG, "Error adding document", e)
             }
    }



}


