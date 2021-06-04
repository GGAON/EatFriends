package com.zeronine.project1.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zeronine.project1.databinding.ActivityWaitingBinding

class WaitingGroupActivity:AppCompatActivity() {

    private lateinit var waitingBinding : ActivityWaitingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_waiting)

        waitingBinding = ActivityWaitingBinding.inflate(layoutInflater)
        val view = waitingBinding.root
        setContentView(view)

        waitingBinding.joinOtherGroup.setOnClickListener {
            finish()
            startActivity(Intent(this, JoinGroupActivity::class.java))
        }

    }
}