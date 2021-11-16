package com.zeronine.project1.widget.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.data.DB.DBKey.Companion.DB_FRIENDS
import com.zeronine.project1.data.DB.DBKey.Companion.DB_GROUPSETTING
import com.zeronine.project1.data.DB.DBKey.Companion.DB_USERS
import com.zeronine.project1.databinding.ItemMemberBinding
import com.zeronine.project1.screen.home.make.currentGroupSettingID
import com.zeronine.project1.widget.model.FriendsModel
import com.zeronine.project1.widget.model.MemberModel

class MemberAdapter
    : ListAdapter<MemberModel, MemberAdapter.ViewHolder>(diffUtil) {

    private val friendList = mutableListOf<String>()

    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val friendsModel = snapshot.getValue(FriendsModel::class.java)
            friendsModel ?: return
            friendList.add(friendsModel.FriendId)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val friendsModel = snapshot.getValue(FriendsModel::class.java)
            friendsModel ?: return
            friendList.remove(friendsModel.FriendId)
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(error: DatabaseError) {
        }

    }

    inner class ViewHolder(private val binding: ItemMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(memberModel: MemberModel) {
            Log.d("Checking Recruiting", "It is ${memberModel.MemberId}")
            val userId = Firebase.auth.currentUser?.uid.orEmpty()
            val friendsDB =
                Firebase.database.reference.child(DB_USERS).child(userId).child(DB_FRIENDS)
            val friendsInfo = mutableMapOf<String, Any>()

            binding.memberNameView.text = memberModel.MemberName
            binding.addFriendButton.setOnClickListener {
                friendsInfo["FriendId"] = memberModel.MemberId
                friendsInfo["FriendName"] = memberModel.MemberName
                friendsDB.child(memberModel.MemberId).updateChildren(friendsInfo)
                binding.addFriendButton.visibility = View.INVISIBLE
            }
            val recruiterId =
                Firebase.database.reference.child(DB_GROUPSETTING).child(currentGroupSettingID!!)
                    .child("recruiterId")
            recruiterId.get().addOnSuccessListener {
                if (memberModel.MemberId == it.value) {
                    // 현재 member가 공동구매 모집자(HOST)라면, hostTextView를 VISIBLE
                    binding.hostTextView.visibility = View.VISIBLE
                }
                if (memberModel.MemberId == userId) {
                    // 현재 member가 자신(currentUser)라면, 친구추가 버튼을 INVISIBLE, showMeTextView 를 VISIBLE
                    binding.addFriendButton.visibility = View.INVISIBLE
                    binding.showMeTextView.visibility = View.VISIBLE
                }
            }

            //현재 member가 내 friendDB list에 있는지 확인 -> 친구라면 친구추가 버튼 INVISIBLE
            friendsDB.addChildEventListener(listener)
            if ( friendList.contains(memberModel.MemberId)) {
                binding.addFriendButton.visibility = View.INVISIBLE
            }

        }
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MemberModel>() {
            override fun areItemsTheSame(
                oldItem: MemberModel,
                newItem: MemberModel
            ): Boolean {
                //고유키값이 같은지 확인
                return oldItem.MemberId == newItem.MemberId
            }

            override fun areContentsTheSame(
                oldItem: MemberModel,
                newItem: MemberModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMemberBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(currentList[position])
    }
}