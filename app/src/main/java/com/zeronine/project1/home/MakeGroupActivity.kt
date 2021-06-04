package com.zeronine.project1.home

import android.app.ActivityGroup
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.zeronine.project1.R
import com.zeronine.project1.databinding.ActivitySettingBinding


class MakeGroupActivity : AppCompatActivity() {


    //private val foodBinding = ActivitySettingBinding.inflate(layoutInflater)
    private lateinit var foodBinding:ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_setting)

        foodBinding = ActivitySettingBinding.inflate(layoutInflater)
        val view = foodBinding.root
        setContentView(view)

        initNumberPicker()
        numberPickerListener()
        foodButtonMenuListener()





        foodBinding.startToMakeGroupButton.setOnClickListener {
            startActivity(Intent(this, WaitingGroupActivity::class.java))
//            val bundle = Bundle()
//            bundle.putString("food", "Chicken")
//            val fragment = GroupSettingDialogFragment()
//            fragment.arguments = bundle
            GroupSettingDialogFragment().show(supportFragmentManager, "GroupSettingDialogFragment")
        }

    }

    /*
    음식메뉴 고르기
    버튼 1개 누른 후 다른 버튼 누르면 모두 안눌림으로 변경
     */
    private fun foodButtonMenuListener() {
        foodBinding.btnKorean.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                foodBinding.btnChicken.isChecked = false
                foodBinding.btnPizza.isChecked = false
                foodBinding.btnSchoolFood.isChecked = false
                foodBinding.btnFastFood.isChecked = false
                foodBinding.btnCafeDessert.isChecked = false
                foodBinding.btnChineseFood.isChecked = false
                foodBinding.btnJapanFood.isChecked = false
                foodBinding.btnAsianFood.isChecked = false
            }
        }

        foodBinding.btnChicken.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                foodBinding.btnKorean.isChecked = false
                foodBinding.btnPizza.isChecked = false
                foodBinding.btnSchoolFood.isChecked = false
                foodBinding.btnFastFood.isChecked = false
                foodBinding.btnCafeDessert.isChecked = false
                foodBinding.btnChineseFood.isChecked = false
                foodBinding.btnJapanFood.isChecked = false
                foodBinding.btnAsianFood.isChecked = false
            }
        }

        foodBinding.btnPizza.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                foodBinding.btnKorean.isChecked = false
                foodBinding.btnChicken.isChecked = false
                foodBinding.btnSchoolFood.isChecked = false
                foodBinding.btnFastFood.isChecked = false
                foodBinding.btnCafeDessert.isChecked = false
                foodBinding.btnChineseFood.isChecked = false
                foodBinding.btnJapanFood.isChecked = false
                foodBinding.btnAsianFood.isChecked = false
            }
        }

        foodBinding.btnSchoolFood.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                foodBinding.btnKorean.isChecked = false
                foodBinding.btnChicken.isChecked = false
                foodBinding.btnPizza.isChecked = false
                foodBinding.btnFastFood.isChecked = false
                foodBinding.btnCafeDessert.isChecked = false
                foodBinding.btnChineseFood.isChecked = false
                foodBinding.btnJapanFood.isChecked = false
                foodBinding.btnAsianFood.isChecked = false
            }
        }

        foodBinding.btnFastFood.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                foodBinding.btnKorean.isChecked = false
                foodBinding.btnChicken.isChecked = false
                foodBinding.btnPizza.isChecked = false
                foodBinding.btnSchoolFood.isChecked = false
                foodBinding.btnCafeDessert.isChecked = false
                foodBinding.btnChineseFood.isChecked = false
                foodBinding.btnJapanFood.isChecked = false
                foodBinding.btnAsianFood.isChecked = false
            }
        }

        foodBinding.btnCafeDessert.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                foodBinding.btnKorean.isChecked = false
                foodBinding.btnChicken.isChecked = false
                foodBinding.btnPizza.isChecked = false
                foodBinding.btnSchoolFood.isChecked = false
                foodBinding.btnFastFood.isChecked = false
                foodBinding.btnChineseFood.isChecked = false
                foodBinding.btnJapanFood.isChecked = false
                foodBinding.btnAsianFood.isChecked = false
            }
        }

        foodBinding.btnChineseFood.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                foodBinding.btnKorean.isChecked = false
                foodBinding.btnChicken.isChecked = false
                foodBinding.btnPizza.isChecked = false
                foodBinding.btnSchoolFood.isChecked = false
                foodBinding.btnFastFood.isChecked = false
                foodBinding.btnCafeDessert.isChecked = false
                foodBinding.btnJapanFood.isChecked = false
                foodBinding.btnAsianFood.isChecked = false
            }
        }

        foodBinding.btnJapanFood.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                foodBinding.btnKorean.isChecked = false
                foodBinding.btnChicken.isChecked = false
                foodBinding.btnPizza.isChecked = false
                foodBinding.btnSchoolFood.isChecked = false
                foodBinding.btnFastFood.isChecked = false
                foodBinding.btnCafeDessert.isChecked = false
                foodBinding.btnChineseFood.isChecked = false
                foodBinding.btnAsianFood.isChecked = false
            }
        }

        foodBinding.btnAsianFood.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                foodBinding.btnKorean.isChecked = false
                foodBinding.btnChicken.isChecked = false
                foodBinding.btnPizza.isChecked = false
                foodBinding.btnSchoolFood.isChecked = false
                foodBinding.btnFastFood.isChecked = false
                foodBinding.btnCafeDessert.isChecked = false
                foodBinding.btnChineseFood.isChecked = false
                foodBinding.btnJapanFood.isChecked = false
            }
        }
    }


    private fun initNumberPicker() {
        foodBinding.numberPickerFriends.minValue = 1
        foodBinding.numberPickerFriends.maxValue = 3
        foodBinding.numberPickerFriends.wrapSelectorWheel = true
        foodBinding.numberPickerFriends.value = 1
        foodBinding.numberPickerWaitTime.minValue = 5
        foodBinding.numberPickerWaitTime.maxValue = 59
        foodBinding.numberPickerWaitTime.wrapSelectorWheel = true
        val firstTotalPeople = foodBinding.numberPickerFriends.value + 1
        foodBinding.showTotalPeopleNow.setText("Total number of people including you is ${firstTotalPeople}")
        foodBinding.totalPeopleWarningText.setText("(※ Because of COVID-19, the maximum number of people on group is 4. )")
    }

    private fun numberPickerListener() {
        foodBinding.numberPickerFriends.setOnValueChangedListener { picker, oldVal, newVal ->
            Log.d("numberPicker_friends", "oldVal : ${oldVal}, newVal : $newVal ")
            val totalPeople = newVal.toInt() + 1
            foodBinding.showTotalPeopleNow.setText("Total number of people including you is ${totalPeople}")
            foodBinding.totalPeopleWarningText.setText("(※ Because of COVID-19, the maximum number of people on group is 4. )")
        }
    }
}

