package com.zeronine.project1.screen.friends

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.R
import com.zeronine.project1.data.DB.DBKey.Companion.DB_FRIENDS
import com.zeronine.project1.data.DB.DBKey.Companion.DB_USERS
import com.zeronine.project1.databinding.FragmentFriendsBinding
import com.zeronine.project1.widget.adapter.FriendsAdapter
import com.zeronine.project1.widget.model.FriendsModel

class FriendsFragment : Fragment(R.layout.fragment_friends){

    private lateinit var friendsFragmentBinding: FragmentFriendsBinding
    private lateinit var friendsAdapter: FriendsAdapter
    private val friendsList = mutableListOf<FriendsModel>()

    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val friendsModel = snapshot.getValue(FriendsModel::class.java)
            friendsModel ?: return
            friendsList.add(friendsModel)
            Log.d("CHECK THIS!!"," $friendsModel")
            friendsAdapter.submitList(friendsList)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val friendsModel = snapshot.getValue(FriendsModel::class.java)
            friendsModel ?: return
            friendsList.remove(friendsModel)
            friendsAdapter.submitList(friendsList)
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(error: DatabaseError) {
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = Firebase.auth.currentUser?.uid.orEmpty()
        val friendsDB = Firebase.database.reference.child(DB_USERS).child(userId).child(DB_FRIENDS)
        friendsFragmentBinding = FragmentFriendsBinding.bind(view)


        friendsList.clear()


        //RecyclerView에 Adapter를 연결해준다.
        friendsAdapter = FriendsAdapter()
        friendsAdapter.setItemClickListener(object : FriendsAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val item = friendsList[position]
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("friendId", item.FriendId)
                intent.putExtra("friendName", item.FriendName)
                startActivity(intent)

            }
        })

        friendsFragmentBinding.friendsListRecyclerView.layoutManager = GridLayoutManager(activity,2)
        friendsFragmentBinding.friendsListRecyclerView.adapter = friendsAdapter

        friendsDB.addChildEventListener(listener)

    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//    }
}