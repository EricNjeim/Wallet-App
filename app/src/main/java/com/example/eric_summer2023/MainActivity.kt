package com.example.eric_summer2023

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 3000 // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.splashxml)
        findViewById<View>(R.id.imageView2).startAnimation(fadeIn)

        // Handler to navigate to the main activity after the splash timeout
        Handler().postDelayed({
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}