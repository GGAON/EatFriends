package com.zeronine.project1.mypage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zeronine.project1.databinding.ActivityHowToUseBinding

class HowToUseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHowToUseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHowToUseBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }

}