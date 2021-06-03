package com.zeronine.project1.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.zeronine.project1.R
import com.zeronine.project1.intro.LoginActivity
import kotlinx.android.synthetic.main.activity_recruiter_scroll.*


class MakeGroupActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruiter_scroll)

        initNumberPicker()
        numberPickerListener()
        foodButtonMenuListener()




        startToMakeGroupButton.setOnClickListener {
            //startActivity(Intent(this, WaitingGroupActivity::class.java))
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
        btn_Korean.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                btn_Chicken.isChecked = false
                btn_Pizza.isChecked = false
                btn_SchoolFood.isChecked = false
                btn_FastFood.isChecked = false
                btn_Cafe_Dessert.isChecked = false
                btn_ChineseFood.isChecked = false
                btn_JapanFood.isChecked = false
                btn_AsianFood.isChecked = false
            }
        }

        btn_Chicken.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                btn_Korean.isChecked = false
                btn_Pizza.isChecked = false
                btn_SchoolFood.isChecked = false
                btn_FastFood.isChecked = false
                btn_Cafe_Dessert.isChecked = false
                btn_ChineseFood.isChecked = false
                btn_JapanFood.isChecked = false
                btn_AsianFood.isChecked = false
            }
        }

        btn_Pizza.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                btn_Korean.isChecked = false
                btn_Chicken.isChecked = false
                btn_SchoolFood.isChecked = false
                btn_FastFood.isChecked = false
                btn_Cafe_Dessert.isChecked = false
                btn_ChineseFood.isChecked = false
                btn_JapanFood.isChecked = false
                btn_AsianFood.isChecked = false
            }
        }

        btn_SchoolFood.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                btn_Korean.isChecked = false
                btn_Chicken.isChecked = false
                btn_Pizza.isChecked = false
                btn_FastFood.isChecked = false
                btn_Cafe_Dessert.isChecked = false
                btn_ChineseFood.isChecked = false
                btn_JapanFood.isChecked = false
                btn_AsianFood.isChecked = false
            }
        }

        btn_FastFood.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                btn_Korean.isChecked = false
                btn_Chicken.isChecked = false
                btn_Pizza.isChecked = false
                btn_SchoolFood.isChecked = false
                btn_Cafe_Dessert.isChecked = false
                btn_ChineseFood.isChecked = false
                btn_JapanFood.isChecked = false
                btn_AsianFood.isChecked = false
            }
        }

        btn_Cafe_Dessert.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                btn_Korean.isChecked = false
                btn_Chicken.isChecked = false
                btn_Pizza.isChecked = false
                btn_SchoolFood.isChecked = false
                btn_FastFood.isChecked = false
                btn_ChineseFood.isChecked = false
                btn_JapanFood.isChecked = false
                btn_AsianFood.isChecked = false
            }
        }

        btn_ChineseFood.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                btn_Korean.isChecked = false
                btn_Chicken.isChecked = false
                btn_Pizza.isChecked = false
                btn_SchoolFood.isChecked = false
                btn_FastFood.isChecked = false
                btn_Cafe_Dessert.isChecked = false
                btn_JapanFood.isChecked = false
                btn_AsianFood.isChecked = false
            }
        }

        btn_JapanFood.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                btn_Korean.isChecked = false
                btn_Chicken.isChecked = false
                btn_Pizza.isChecked = false
                btn_SchoolFood.isChecked = false
                btn_FastFood.isChecked = false
                btn_Cafe_Dessert.isChecked = false
                btn_ChineseFood.isChecked = false
                btn_AsianFood.isChecked = false
            }
        }

        btn_AsianFood.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                btn_Korean.isChecked = false
                btn_Chicken.isChecked = false
                btn_Pizza.isChecked = false
                btn_SchoolFood.isChecked = false
                btn_FastFood.isChecked = false
                btn_Cafe_Dessert.isChecked = false
                btn_ChineseFood.isChecked = false
                btn_JapanFood.isChecked = false
            }
        }
    }


    private fun initNumberPicker() {
        numberPicker_friends.minValue = 1
        numberPicker_friends.maxValue = 3
        numberPicker_friends.wrapSelectorWheel = true
        numberPicker_friends.value = 1
        numberPicker_waitTime.minValue = 5
        numberPicker_waitTime.maxValue = 59
        numberPicker_waitTime.wrapSelectorWheel = true
        val firstTotalPeople = numberPicker_friends.value + 1
        showTotalPeopleNow.setText("Total number of people including you is ${firstTotalPeople}")
        totalPeopleWarningText.setText("(※ Because of COVID-19, the maximum number of people on group is 4. )")
    }

    private fun numberPickerListener() {
        numberPicker_friends.setOnValueChangedListener { picker, oldVal, newVal ->
            Log.d("numberPicker_friends", "oldVal : ${oldVal}, newVal : $newVal ")
            val totalPeople = newVal.toInt() + 1
            showTotalPeopleNow.setText("Total number of people including you is ${totalPeople}")
            totalPeopleWarningText.setText("(※ Because of COVID-19, the maximum number of people on group is 4. )")
        }
    }
}

