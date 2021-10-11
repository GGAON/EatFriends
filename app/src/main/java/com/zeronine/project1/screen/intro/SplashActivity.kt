package com.zeronine.project1.screen.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.zeronine.project1.screen.base.MainActivity
import com.zeronine.project1.R

class SplashActivity : AppCompatActivity() {

    private val splashTimeOut:Long = 2000 // 3000 : 1sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, splashTimeOut)





    }
}