package com.zeronine.project1.screen.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.databinding.DialogGroupsettingBinding
import java.text.SimpleDateFormat
import java.util.*

public lateinit var groupSettingId : String

class GroupSettingDialogFragment : DialogFragment() {

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

    private fun getCurrentTime() : String {
        val now : Long = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ko", "KR"))
        val stringTime = dateFormat.format(date)
        Log.d("Check currentTime", "Current Time : ${stringTime}")
        return stringTime.toString()
    }

    private fun saveGroupSetting() {
// "Group Setting을 하기로 결정하였으므로 database에 저장한다"

        val recruiterId = Firebase.auth.currentUser?.uid.orEmpty()
        groupSettingId = Firebase.database.reference.child("GroupSetting").push().key.orEmpty()
        val currentGroupSettingDB = Firebase.database.reference.child("GroupSetting").child(groupSettingId)
        val groupSettingInfo = mutableMapOf<String,Any>()
        groupSettingInfo["recruiter id"] = recruiterId
        groupSettingInfo["food category"] = foodCategory.toString()
        groupSettingInfo["total people"] = totalPeople.toString()
        groupSettingInfo["waiting time"] = waitingTime.toString()
        groupSettingInfo["recruiter latitude"] = recruiterLat.toString()
        groupSettingInfo["recruiter longitude"] = recruiterLng.toString()
        groupSettingInfo["recruiting"] = "True"
        groupSettingInfo["start time"] = getCurrentTime()
        currentGroupSettingDB.updateChildren(groupSettingInfo)

    }

    fun getGroupSettingId() : String {
        return groupSettingId
    }

}