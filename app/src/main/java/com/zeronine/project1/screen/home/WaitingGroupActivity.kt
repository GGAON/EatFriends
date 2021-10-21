package com.zeronine.project1.screen.home

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.databinding.ActivityWaitingBinding

private lateinit var groupSettingDB: DatabaseReference

class WaitingGroupActivity : AppCompatActivity() {

    private lateinit var waitingBinding: ActivityWaitingBinding

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
            .setPositiveButton("Finish waiting") { _: DialogInterface, _: Int ->
                currentGroupSettingID?.let { groupSettingDB.child(it).child("recruiting").setValue(4) }
        /*
        recruiting
        0 : 기본생성자 임의값
        1 : 공동구매 모집중
        2 : 공동구매 주문중 (모집성공)
        3 : 공동구매 성공 (주문완료)
        4 : 공동구매 실패
         */
                currentGroupSettingID = null
                finish()
            }
            .setNegativeButton("Wait more") { _: DialogInterface, _: Int -> }
            .show()
        //super.onBackPressed()
    }

    private fun showGroupSetting() {
        val foodCategorySettingDB =
            currentGroupSettingID?.let { groupSettingDB.child(it).child("foodCategory") }
        val totalPeopleSettingDB =
            currentGroupSettingID?.let { groupSettingDB.child(it).child("totalPeople") }
        val waitingTimeSettingDB =
            currentGroupSettingID?.let { groupSettingDB.child(it).child("waitingTime") }


        foodCategorySettingDB!!.get().addOnSuccessListener {
            Log.i("foodCategorySettingDB", "Got value : ${it.value}")
            waitingBinding.showFoodSetting.text = "■ food category : ${it.value}"
        }.addOnFailureListener {
            Log.e("foodCategorySettingDB", "Error getting food category setting")
        }

        totalPeopleSettingDB!!.get().addOnSuccessListener {
            Log.i("totalPeopleSettingDB", "Got value : ${it.value}")
            waitingBinding.showTotalPeopleSetting.text = "■ total people : ${it.value}"
        }.addOnFailureListener {
            Log.e("totalPeopleSettingDB", "Error getting total people setting")
        }

        waitingTimeSettingDB!!.get().addOnSuccessListener {
            Log.i("waitingTimeSettingDB", "Got value : ${it.value}")
            waitingBinding.showWaitingTimeSetting.text = "■ waiting time : ${it.value} min"
        }.addOnFailureListener {
            Log.e("waitingTimeSettingDB", "Error getting waiting time setting")
        }


    }
}