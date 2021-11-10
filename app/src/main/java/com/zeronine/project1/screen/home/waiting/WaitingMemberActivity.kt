package com.zeronine.project1.screen.home.waiting

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.data.DB.DBKey.Companion.DB_GROUPSETTING
import com.zeronine.project1.data.DB.DBKey.Companion.DB_GROUPSETTINGMEMBER
import com.zeronine.project1.data.DB.DBKey.Companion.DB_USERS
import com.zeronine.project1.databinding.ActivityWaitingmemberBinding
import com.zeronine.project1.screen.home.currentGroupSettingID
import com.zeronine.project1.screen.home.totalPeople
import com.zeronine.project1.screen.order.OrderActivity

class WaitingMemberActivity : AppCompatActivity() {

    private lateinit var waitingMemberBinding: ActivityWaitingmemberBinding
    private lateinit var memberInfoDB : DatabaseReference
    private lateinit var groupSettingDB: DatabaseReference
    private lateinit var userDB: DatabaseReference
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    var memberNum = 0
    private val timeOut:Long = 9000 // 3000 : 1sec

    private val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if(currentGroupSettingID != null) {
                memberNum = snapshot.childrenCount.toInt()
                Log.d("Check memberDB num", "${memberNum}")
                checkMemberNumANDTotalPeople()
            }

        }

        override fun onCancelled(error: DatabaseError) {
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        waitingMemberBinding = ActivityWaitingmemberBinding.inflate(layoutInflater)
        val view = waitingMemberBinding.root
        setContentView(view)

        memberInfoDB = Firebase.database.reference.child(DB_GROUPSETTINGMEMBER)
        groupSettingDB = Firebase.database.reference.child(DB_GROUPSETTING)
        userDB = Firebase.database.reference.child(DB_USERS)
        auth = Firebase.auth

        showGroupSetting()

        memberInfoDB.child(currentGroupSettingID!!).addValueEventListener(listener)

        checkMemberNumANDTotalPeople()

//        groupSettingDB.child(currentGroupSettingID!!).child("totalPeople").get().addOnSuccessListener {
//            Log.d("check member", "memberNum : ${memberNum}, totalPeople : ${totalPeople}")
//            if(memberNum == totalPeople) {  //공동구매 모집 설정인원과 현재 인원이 같다면  orderActivity로 간다
//                //groupSettingDB.child(currentGroupSettingID!!).child("recruiting").setValue(2)
//                Log.d("goto OrderActivity", "OrderActivity")
//                startActivity(Intent(this, OrderActivity::class.java))
//                finish()
//            }
//        }
    }

    private fun checkMemberNumANDTotalPeople() {
        groupSettingDB.child(currentGroupSettingID!!).child("totalPeople").get().addOnSuccessListener {
            Log.d("check member", "memberNum : ${memberNum}, totalPeople : ${it.value}")
            if(memberNum == it.value.toString().toInt()) {  //공동구매 모집 설정인원과 현재 인원이 같다면  orderActivity로 간다
                groupSettingDB.child(currentGroupSettingID!!).child("recruiting").setValue(2)
                Log.d("goto OrderActivity", "OrderActivity")
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, OrderActivity::class.java))
                    finish()
                }, timeOut)
//                startActivity(Intent(this, OrderActivity::class.java))
//                finish()
            }
        }
    }

//    private fun checkWaitingStatus() {
//        val recruiting = groupSettingDB.child(currentGroupSettingID!!).child("recruiting").get().toString().toInt()
//        when(recruiting){
//            1 -> Log.d("checkWaitingStatus", "공동구매 모집중")
//            2 -> {
//                Log.d("checkWaitingStatus", "공동구매 주문중")
//                startActivity(Intent(this, OrderActivity::class.java))
//                finish()
//            }
//            3 -> {
//                Log.d("checkWaitingStatus", "공동구매 성공")
//                Toast.makeText(this, "This recruiting have finished", Toast.LENGTH_SHORT).show()
//                //finish()
//            }
//            4 -> {
//                Log.d("checkWaitingStatus", "공동구매 실패")
//                Toast.makeText(this, "This recruiting have failed", Toast.LENGTH_SHORT).show()
//                //finish()
//            }
//        }
//    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Do you give up waiting for friends?")
            .setPositiveButton("Finish waiting") { _: DialogInterface, _: Int ->
                //memberInfoDB에서 사용자를 삭제한다
                memberInfoDB.child(currentGroupSettingID!!).child(getCurrentUserID()).removeValue()
//                currentGroupSettingID?.let { memberInfoDB.child(it).child("recruiting").setValue(4) }
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

    private fun getCurrentUserID(): String {
        if(auth.currentUser == null){
            Log.e("currentUser", "noCurrentUser")
        }
        return auth.currentUser?.uid.orEmpty()
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
            waitingMemberBinding.showFoodSetting.text = "■ food category : ${it.value}"
        }.addOnFailureListener {
            Log.e("foodCategorySettingDB", "Error getting food category setting")
        }

        totalPeopleSettingDB!!.get().addOnSuccessListener {
            Log.i("totalPeopleSettingDB", "Got value : ${it.value}")
            waitingMemberBinding.showTotalPeopleSetting.text = "■ total people : ${it.value}"
        }.addOnFailureListener {
            Log.e("totalPeopleSettingDB", "Error getting total people setting")
        }

        waitingTimeSettingDB!!.get().addOnSuccessListener {
            Log.i("waitingTimeSettingDB", "Got value : ${it.value}")
            waitingMemberBinding.showWaitingTimeSetting.text = "■ waiting time : ${it.value} min"
        }.addOnFailureListener {
            Log.e("waitingTimeSettingDB", "Error getting waiting time setting")
        }
    }

}