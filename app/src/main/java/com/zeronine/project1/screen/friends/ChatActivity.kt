package com.zeronine.project1.screen.friends

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.data.DB.DBKey.Companion.DB_CHATROOMS
import com.zeronine.project1.databinding.ActivityChatBinding
import com.zeronine.project1.widget.adapter.ChatAdapter
import com.zeronine.project1.widget.model.ChatModel
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatBinding: ActivityChatBinding

    private lateinit var chatDB: DatabaseReference

    private var destinationUid: String? = null
    private var destinationName: String? = null
    private var myUid: String? = null
    private var chatRoomId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chatBinding = ActivityChatBinding.inflate(layoutInflater)
        val view = chatBinding.root
        setContentView(view)

        chatDB = Firebase.database.reference.child(DB_CHATROOMS)

        destinationUid = intent.getStringExtra("friendId")
        destinationName = intent.getStringExtra("friendName")
        chatBinding.showChatFriendName.text = destinationName
        myUid = Firebase.auth.currentUser?.uid.toString()

        //메세지를 보낸시간
        val time = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("MM월 dd일 hh:mm")
        val curTime = dateFormat.format(Date(time)).toString()


        //메세지 전송버튼을 눌렀을 때
//        chatBinding.sendMessageImageView.setOnClickListener {
//            Log.d("sendChat", "")
//            val chatModel = ChatModel()
//            chatModel.users.put(myUid.toString(), true)
//            chatModel.users.put(destinationUid.toString(), true)
//
//            val comment = ChatModel.Comment(myUid, chatBinding.editText.text.toString(), curTime)
//            if (chatRoomId == null) {
//                chatBinding.sendMessageImageView.isEnabled = false
//                chatDB.push().setValue(chatModel).addOnSuccessListener {
//                    //채팅방 생성
//                    checkChatRoom()
//                    //메세지 보내기
//
//                }
//            }
//
//        }
    }

    private fun checkChatRoom() {
        chatDB.orderByChild("users/uid").equalTo(true)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(item in snapshot.children) {
                        val chatModel = item.getValue<ChatModel>()
                        if(chatModel?.users!!.containsKey(destinationUid)) {
                            chatRoomId = item.key
                            chatBinding.sendMessageImageView.isEnabled = true
                            chatBinding.chatRecyclerView.layoutManager = LinearLayoutManager(this@ChatActivity)
                            //chatBinding.chatRecyclerView.adapter = ChatAdapter()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

}