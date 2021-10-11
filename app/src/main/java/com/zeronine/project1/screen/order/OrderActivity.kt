package com.zeronine.project1.screen.order

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zeronine.project1.databinding.ActivityOrderBinding

class OrderActivity: AppCompatActivity() {

    private lateinit var orderBinding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        orderBinding = ActivityOrderBinding.inflate(layoutInflater)
        val view = orderBinding.root
        setContentView(view)


        orderBinding.completeButton.setOnClickListener {
            startActivity(Intent(this, DeliveryActivity::class.java))
        }

    }

}