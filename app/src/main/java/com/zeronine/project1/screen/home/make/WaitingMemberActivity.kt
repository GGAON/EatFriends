package com.zeronine.project1.screen.home.make

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.data.DB.DBKey.Companion.DB_GROUPSETTING
import com.zeronine.project1.data.DB.DBKey.Companion.DB_GROUPSETTINGMEMBER
import com.zeronine.project1.data.DB.DBKey.Companion.DB_USERS
import com.zeronine.project1.databinding.ActivityWaitingmemberBinding
import com.zeronine.project1.screen.order.OrderActivity
import com.zeronine.project1.widget.adapter.MemberAdapter
import com.zeronine.project1.widget.model.MemberModel

class WaitingMemberActivity : AppCompatActivity() {

    private lateinit var waitingMemberBinding: ActivityWaitingmemberBinding
    private lateinit var memberInfoDB: DatabaseReference
    private lateinit var groupSettingDB: DatabaseReference
    private lateinit var userDB: DatabaseReference
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var memberAdapter: MemberAdapter

    var memberNum = 0
    private val timeOut: Long = 5000 // 3000 : 1sec

    private val memberList = mutableListOf<MemberModel>()

    private val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            memberNum = snapshot.childrenCount.toInt()
            Log.d("Check memberDB num", "$memberNum")
            checkMemberNumANDTotalPeople()
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }

    private val listener2 = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            memberNum = snapshot.childrenCount.toInt()
            val memberModel = snapshot.getValue(MemberModel::class.java)
            memberModel ?: return
            memberList.add(memberModel)
            Log.d("CHECK THIS!!", "$memberModel")
            memberAdapter.submitList(memberList)
            waitingMemberBinding.showMemberRecyclerView.adapter = memberAdapter
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val memberModel = snapshot.getValue(MemberModel::class.java)
            memberModel ?: return
            memberList.remove(memberModel)
            memberAdapter.submitList(memberList)
            waitingMemberBinding.showMemberRecyclerView.adapter = memberAdapter
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(error: DatabaseError) {
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        waitingMemberBinding = ActivityWaitingmemberBinding.inflate(layoutInflater)
        val view = waitingMemberBinding.root
        setContentView(view)

        Log.d("public 변수 확인", "currentGroupSettingID = $currentGroupSettingID")

        memberInfoDB = Firebase.database.reference.child(DB_GROUPSETTINGMEMBER)
        groupSettingDB = Firebase.database.reference.child(DB_GROUPSETTING)
        userDB = Firebase.database.reference.child(DB_USERS)
        auth = Firebase.auth

        //RecyclerView에 Adapter를 연결해준다.
        memberAdapter = MemberAdapter()
        waitingMemberBinding.showMemberRecyclerView.layoutManager = LinearLayoutManager(this)
        waitingMemberBinding.showMemberRecyclerView.adapter = memberAdapter

        showGroupSetting()

        memberInfoDB.child(currentGroupSettingID!!).addChildEventListener(listener2)

        memberInfoDB.child(currentGroupSettingID!!).addValueEventListener(listener)

    }

    private fun checkMemberNumANDTotalPeople() {
        groupSettingDB.child(currentGroupSettingID!!).child("totalPeople").get()
            .addOnSuccessListener {
                Log.d("check member", "memberNum : ${memberNum}, totalPeople : ${it.value}")
                if (memberNum == it.value.toString()
                        .toInt()
                ) {  //공동구매 모집 설정인원과 현재 인원이 같다면  orderActivity로 간다
                    groupSettingDB.child(currentGroupSettingID!!).child("recruiting").setValue(2)
                    waitingMemberBinding.waitToJoinTextView.visibility = View.INVISIBLE
                    waitingMemberBinding.matchedTextView.visibility = View.VISIBLE
                    waitingMemberBinding.loadingTextView.visibility = View.VISIBLE
                    Log.d("goto OrderActivity", "OrderActivity")
                    Handler(Looper.getMainLooper()).postDelayed({
                        startActivity(Intent(this, OrderActivity::class.java))
                        finish()
                    }, timeOut)
                }
            }
    }


    override fun onBackPressed() {
        /*
        recruiting
        0 : 기본생성자 임의값
        1 : 공동구매 모집중
        2 : 공동구매 주문중 (모집성공)
        3 : 공동구매 성공 (주문완료)
        4 : 공동구매 실패
        */
        groupSettingDB.child(currentGroupSettingID!!).child("recruiting").get()
            .addOnSuccessListener {
                if (it.value == 2) {
                    AlertDialog.Builder(this)
                        .setTitle("This Group Purchase is already matched!")
                        .show()
                } else {
                    AlertDialog.Builder(this)
                        .setTitle("Do you give up waiting for friends?")
                        .setPositiveButton("Finish waiting") { _: DialogInterface, _: Int ->
                            //memberInfoDB에서 사용자를 삭제한다

                            memberInfoDB.child(currentGroupSettingID!!).child(getCurrentUserID())
                                .removeValue()


                            currentGroupSettingID = null
                            finish()
                        }
                        .setNegativeButton("Wait more") { _: DialogInterface, _: Int -> }
                        .show()
                }
            }


        //super.onBackPressed()
    }

    private fun getCurrentUserID(): String {
        if (auth.currentUser == null) {
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
            foodCategory = it.value.toString()
            waitingMemberBinding.showFoodSetting.text = "■ food category : ${it.value}"
        }.addOnFailureListener {
            Log.e("foodCategorySettingDB", "Error getting food category setting")
        }

        totalPeopleSettingDB!!.get().addOnSuccessListener {
            totalPeople = it.value.toString().toInt()
            Log.i("totalPeopleSettingDB", "Got value : ${it.value}")
            waitingMemberBinding.showTotalPeopleSetting.text = "■ total people : ${it.value}"
        }.addOnFailureListener {
            Log.e("totalPeopleSettingDB", "Error getting total people setting")
        }

        waitingTimeSettingDB!!.get().addOnSuccessListener {
            waitingTime = it.value.toString().toInt()
            Log.i("waitingTimeSettingDB", "Got value : ${it.value}")
            waitingMemberBinding.showWaitingTimeSetting.text = "■ waiting time : ${it.value} min"
        }.addOnFailureListener {
            Log.e("waitingTimeSettingDB", "Error getting waiting time setting")
        }
    }

}