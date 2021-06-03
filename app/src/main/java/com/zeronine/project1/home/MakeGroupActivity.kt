package com.zeronine.project1.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.zeronine.project1.R
import com.zeronine.project1.intro.LoginActivity

class MakeGroupActivity : AppCompatActivity(){

//    private val koreanButton = findViewById<ToggleButton>(R.id.btn_Korean)
//    private val chickenButton = findViewById<ToggleButton>(R.id.btn_Chicken)
//    private val pizzaButton = findViewById<ToggleButton>(R.id.btn_Pizza)
//    private val schoolFoodButton = findViewById<ToggleButton>(R.id.btn_SchoolFood)
//    private val fastFoodButton = findViewById<ToggleButton>(R.id.btn_FastFood)
//    private val cafeDessertButton = findViewById<ToggleButton>(R.id.btn_Cafe_Dessert)
//    private val chineseFoodButton = findViewById<ToggleButton>(R.id.btn_ChineseFood)
//    private val japanFoodButton = findViewById<ToggleButton>(R.id.btn_JapanFood)
//    private val asianFoodButton = findViewById<ToggleButton>(R.id.btn_AsianFood)

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruiter_scroll)

        val koreanButton = findViewById<ToggleButton>(R.id.btn_Korean)
        val chickenButton = findViewById<ToggleButton>(R.id.btn_Chicken)
        val pizzaButton = findViewById<ToggleButton>(R.id.btn_Pizza)
        val schoolFoodButton = findViewById<ToggleButton>(R.id.btn_SchoolFood)
        val fastFoodButton = findViewById<ToggleButton>(R.id.btn_FastFood)
        val cafeDessertButton = findViewById<ToggleButton>(R.id.btn_Cafe_Dessert)
        val chineseFoodButton = findViewById<ToggleButton>(R.id.btn_ChineseFood)
        val japanFoodButton = findViewById<ToggleButton>(R.id.btn_JapanFood)
        val asianFoodButton = findViewById<ToggleButton>(R.id.btn_AsianFood)


        koreanButton.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                chickenButton.isChecked = false
                pizzaButton.isChecked = false
                schoolFoodButton.isChecked = false
                fastFoodButton.isChecked = false
                cafeDessertButton.isChecked = false
                chineseFoodButton.isChecked = false
                japanFoodButton.isChecked = false
                asianFoodButton.isChecked = false
            }
        }

        chickenButton.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                koreanButton.isChecked = false
                pizzaButton.isChecked = false
                schoolFoodButton.isChecked = false
                fastFoodButton.isChecked = false
                cafeDessertButton.isChecked = false
                chineseFoodButton.isChecked = false
                japanFoodButton.isChecked = false
                asianFoodButton.isChecked = false
            }
        }

        pizzaButton.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                koreanButton.isChecked = false
                chickenButton.isChecked = false
                schoolFoodButton.isChecked = false
                fastFoodButton.isChecked = false
                cafeDessertButton.isChecked = false
                chineseFoodButton.isChecked = false
                japanFoodButton.isChecked = false
                asianFoodButton.isChecked = false
            }
        }

        schoolFoodButton.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                koreanButton.isChecked = false
                chickenButton.isChecked = false
                pizzaButton.isChecked = false
                fastFoodButton.isChecked = false
                cafeDessertButton.isChecked = false
                chineseFoodButton.isChecked = false
                japanFoodButton.isChecked = false
                asianFoodButton.isChecked = false
            }
        }

        fastFoodButton.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                koreanButton.isChecked = false
                chickenButton.isChecked = false
                pizzaButton.isChecked = false
                schoolFoodButton.isChecked = false
                cafeDessertButton.isChecked = false
                chineseFoodButton.isChecked = false
                japanFoodButton.isChecked = false
                asianFoodButton.isChecked = false
            }
        }

        cafeDessertButton.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                koreanButton.isChecked = false
                chickenButton.isChecked = false
                pizzaButton.isChecked = false
                schoolFoodButton.isChecked = false
                fastFoodButton.isChecked = false
                chineseFoodButton.isChecked = false
                japanFoodButton.isChecked = false
                asianFoodButton.isChecked = false
            }
        }

        chineseFoodButton.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                koreanButton.isChecked = false
                chickenButton.isChecked = false
                pizzaButton.isChecked = false
                schoolFoodButton.isChecked = false
                fastFoodButton.isChecked = false
                cafeDessertButton.isChecked = false
                japanFoodButton.isChecked = false
                asianFoodButton.isChecked = false
            }
        }

        japanFoodButton.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                koreanButton.isChecked = false
                chickenButton.isChecked = false
                pizzaButton.isChecked = false
                schoolFoodButton.isChecked = false
                fastFoodButton.isChecked = false
                cafeDessertButton.isChecked = false
                chineseFoodButton.isChecked = false
                asianFoodButton.isChecked = false
            }
        }

        asianFoodButton.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                koreanButton.isChecked = false
                chickenButton.isChecked = false
                pizzaButton.isChecked = false
                schoolFoodButton.isChecked = false
                fastFoodButton.isChecked = false
                cafeDessertButton.isChecked = false
                chineseFoodButton.isChecked = false
                japanFoodButton.isChecked = false
            }
        }



        val startToMakeGroupButton = findViewById<Button>(R.id.startToMakeGroupButton)

        startToMakeGroupButton.setOnClickListener{
            startActivity(Intent(this, WaitingGroupActivity::class.java))
        }






    }








}