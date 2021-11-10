package com.zeronine.project1.screen.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.data.DB.DBKey
import com.zeronine.project1.databinding.DialogGroupsettingBinding
import com.zeronine.project1.screen.home.waiting.WaitingGroupActivity
import java.text.SimpleDateFormat
import java.util.*

var currentGroupSettingID: String? = null

class GroupSettingDialogFragment : DialogFragment() {

    private lateinit var userDB: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = DialogGroupsettingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDB = Firebase.database.reference.child(DBKey.DB_USERS)

        val binding2 = DialogGroupsettingBinding.bind(view)

        binding2.checkGroupPeopleSetting.setText(totalPeople.toString())
        binding2.checkFoodSetting.setText(foodCategory)
        binding2.checkTimeSetting.setText(waitingTime.toString())

        binding2.yes.setOnClickListener {

            Log.d("yes click", "yes button is clicked!!")
            //getCurrentTime()
            saveGroupSetting()

            activity?.let {
                startActivity(Intent(context, WaitingGroupActivity::class.java))
            }
            dismiss()
        }
        binding2.no.setOnClickListener {
            dismiss()
        }
    }

    private fun getCurrentTime(): String {
        val now: Long = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ko", "KR"))
        val stringTime = dateFormat.format(date)
        Log.d("Check currentTime", "Current Time : ${stringTime}")
        return stringTime.toString()
    }

    private fun saveGroupSetting() {
// "Group Setting을 하기로 결정하였으므로 database에 저장한다"

        val recruiterId = Firebase.auth.currentUser?.uid.orEmpty()
        currentGroupSettingID =
            Firebase.database.reference.child("GroupSetting").push().key.orEmpty()
        val currentGroupSettingDB = Firebase.database.reference.child("GroupSetting").child(
            currentGroupSettingID!!
        )
        val groupSettingInfo = mutableMapOf<String, Any>()
        groupSettingInfo["groupSettingId"] = currentGroupSettingID.toString()
        groupSettingInfo["recruiterId"] = recruiterId
        groupSettingInfo["foodCategory"] = foodCategory.toString()
        groupSettingInfo["totalPeople"] = totalPeople!!
        groupSettingInfo["waitingTime"] = waitingTime!!
        groupSettingInfo["recruiterLat"] = recruiterLat!!
        groupSettingInfo["recruiterLng"] = recruiterLng!!
        /*
        recruiting
        0 : 기본생성자 임의값
        1 : 공동구매 모집중
        2 : 공동구매 주문중 (모집성공)
        3 : 공동구매 성공 (주문완료)
        4 : 공동구매 실패
         */
        groupSettingInfo["recruiting"] = 1
        groupSettingInfo["startTime"] = getCurrentTime()
        currentGroupSettingDB.updateChildren(groupSettingInfo)


//Save Group Setting Member
        val userId = Firebase.auth.currentUser?.uid.orEmpty()
//        var myName = ""
//        userDB.child(userId).child("name").get().addOnSuccessListener {
//            myName = it.value.toString()
//        }
        val memberInfo = mutableMapOf<String, Any>()
        val memberInfoDB =
            Firebase.database.reference.child("GroupSettingMember").child(currentGroupSettingID!!)
        memberInfo["MemberId"] = userId
//        userDB.child(userId).child("name").get().addOnSuccessListener {
//            memberInfo["MemberName"] = it.value.toString()
//        }
        memberInfoDB.child(userId).updateChildren(memberInfo)
    }

    public fun getGroupSettingId(): String? {
        return currentGroupSettingID
    }

}