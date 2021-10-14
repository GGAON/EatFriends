package com.zeronine.project1.screen.home

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.databinding.ActivityWaitingBinding
import com.zeronine.project1.screen.order.OrderActivity

private lateinit var groupSettingDB: DatabaseReference

class WaitingGroupActivity:AppCompatActivity() {

    private lateinit var waitingBinding : ActivityWaitingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        waitingBinding = ActivityWaitingBinding.inflate(layoutInflater)
        val view = waitingBinding.root
        setContentView(view)

        groupSettingDB = Firebase.database.reference.child("GroupSetting")

        showGroupSetting()

//        waitingBinding.joinOtherGroup.setOnClickListener {
//            finish()
//            startActivity(Intent(this, JoinGroupActivity::class.java))
//        }
//
//        waitingBinding.orderNowButton.setOnClickListener {
//            finish()
//            startActivity(Intent(this, OrderActivity::class.java))
//        }

    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Are you no longer waiting for your friends?")
            .setPositiveButton("Finish waiting") { _: DialogInterface, _:Int -> finish()}
            .setNegativeButton("Wait more") { _: DialogInterface, _:Int->}
            .show()
        //super.onBackPressed()
    }

    private fun showGroupSetting() {

        val foodCategorySettingDB = groupSettingDB.child(groupSettingId).child("foodCategory")
        val totalPeopleSettingDB = groupSettingDB.child(groupSettingId).child("totalPeople")
        val waitingTimeSettingDB = groupSettingDB.child(groupSettingId).child("waitingTime")


        foodCategorySettingDB.get().addOnSuccessListener {
            Log.i("foodCategorySettingDB", "Got value : ${it.value}")
            waitingBinding.showFoodSetting.text= "■ food category : ${it.value}"
        }.addOnFailureListener {
            Log.e("foodCategorySettingDB", "Error getting food category setting")
        }

        totalPeopleSettingDB.get().addOnSuccessListener {
            Log.i("totalPeopleSettingDB", "Got value : ${it.value}")
            waitingBinding.showTotalPeopleSetting.text= "■ total people : ${it.value}"
        }.addOnFailureListener {
            Log.e("totalPeopleSettingDB", "Error getting total people setting")
        }

        waitingTimeSettingDB.get().addOnSuccessListener {
            Log.i("waitingTimeSettingDB", "Got value : ${it.value}")
            waitingBinding.showWaitingTimeSetting.text= "■ waiting time : ${it.value} min"
        }.addOnFailureListener {
            Log.e("waitingTimeSettingDB", "Error getting waiting time setting")
        }





    }
}