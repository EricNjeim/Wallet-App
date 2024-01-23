package com.example.eric_summer2023
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.eric_summer2023.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 3000
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.splashxml)
        binding.imageView2.startAnimation(fadeIn)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}