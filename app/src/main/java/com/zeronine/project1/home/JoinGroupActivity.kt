package com.zeronine.project1.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zeronine.project1.databinding.ActivityJoinBinding

class JoinGroupActivity : AppCompatActivity(){

    private lateinit var joinBinding : ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        joinBinding = ActivityJoinBinding.inflate(layoutInflater)
        val view = joinBinding.root
        setContentView(view)


    }


}