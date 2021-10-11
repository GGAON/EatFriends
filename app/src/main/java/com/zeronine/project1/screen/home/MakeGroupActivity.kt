package com.zeronine.project1.screen.home


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zeronine.project1.databinding.ActivitySettingBinding
import com.zeronine.project1.databinding.DialogGroupsettingBinding

//공동구매 그룹세팅 변수 3가지
public var foodCategory : String? = null
public var totalPeople : Int? = 2
public var waitingTime : Int? = 5

//뒤로가기 버튼을 누르기 위한 시간 세팅
private var backWait: Long = 0

class MakeGroupActivity : AppCompatActivity() {

    private lateinit var foodBinding: ActivitySettingBinding
    private lateinit var settingBinding: DialogGroupsettingBinding
    //private val totalPeople = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        foodBinding = ActivitySettingBinding.inflate(layoutInflater)
        val view = foodBinding.root
        setContentView(view)

        settingBinding = DialogGroupsettingBinding.inflate(layoutInflater)



        initNumberPicker()
        foodButtonMenuListener()
        numberPickerListener()

//        settingBinding.yes.setOnClickListener {
//            finish()
//            Log.d("yes click", "finish make group activity")
//        }


        //settingBinding.showGroupPeopleSetting.setText(totalPeople.toString())

        foodBinding.startToMakeGroupButton.setOnClickListener {
            if(foodCategory == null) {
                Toast.makeText(this, "음식카테고리를 골라주세요!!", Toast.LENGTH_SHORT).show()
            }
            else {
                GroupSettingDialogFragment().show(supportFragmentManager, "GroupSettingDialogFragment")
            }
        }

    }

    /*
    음식메뉴 고르기
    버튼 1개 누른 후 다른 메뉴 버튼 누르면 모두 안눌림으로 변경
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
                foodCategory = "Korean"
                Log.d("food clicked", "Korean is clicked")
            }
            else {
                foodCategory = null
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
                foodCategory = "Chicken"
                Log.d("food clicked", "Chicken is clicked")
            }
            else {
                foodCategory = null
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
                foodCategory = "Pizza"
                Log.d("food clicked", "Pizza is clicked")
            }
            else {
                foodCategory = null
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
                foodCategory = "School Food"
                Log.d("food clicked", "School Food is clicked")
            }
            else {
                foodCategory = null
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
                foodCategory = "Fast Food"
                Log.d("food clicked", "Fast Food is clicked")
            }
            else {
                foodCategory = null
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
                foodCategory = "Cafe/Dessert"
                Log.d("food clicked", "Cafe/Dessert is clicked")
            }
            else {
                foodCategory = null
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
                foodCategory = "Chinese Food"
                Log.d("food clicked", "Chinese Food is clicked")
            }
            else {
                foodCategory = null
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
                foodCategory = "Japan Food"
                Log.d("food clicked", "Japan Food is clicked")
            }
            else {
                foodCategory = null
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
                foodCategory = "Asian Food"
                Log.d("food clicked", "Asian Food is clicked")
            }
            else {
                foodCategory = null
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
//        foodBinding.showTotalPeopleNow.setText("Total number of people including you is ${firstTotalPeople}")
        foodBinding.showTotalPeopleNow.text =
            "Total number of people including you is ${firstTotalPeople}"
//        foodBinding.totalPeopleWarningText.setText("(※ Because of COVID-19, the maximum number of people on group is 4. )")
        foodBinding.totalPeopleWarningText.text =
            "(※ Because of COVID-19, the maximum number of people on group is 4. )"
    }

    private fun numberPickerListener() {
        foodBinding.numberPickerFriends.setOnValueChangedListener { _, oldVal, newVal ->
            Log.d("numberPicker_friends", "oldVal : ${oldVal}, newVal : $newVal ")
            totalPeople = newVal.toInt() + 1
            Log.d("checkVar", "${totalPeople}")
            foodBinding.showTotalPeopleNow.text = "Total number of people including you is ${totalPeople}"
            foodBinding.totalPeopleWarningText.text = "(※ The maximum number of people on group is 4. )"
        }
        foodBinding.numberPickerWaitTime.setOnValueChangedListener { _, oldVal, newVal ->
            waitingTime = newVal.toInt()
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backWait >= 2000) {
            backWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 공동구매 그룹 세팅이 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {

            super.onBackPressed()
            foodCategory = null
            totalPeople = 2
            waitingTime = 5
            finish()
        }
    }
}

