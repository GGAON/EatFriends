package com.zeronine.project1.screen.friends

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.R
import com.zeronine.project1.data.DB.DBKey
import com.zeronine.project1.databinding.ActivityChatBinding
import com.zeronine.project1.databinding.ItemMessageBinding
import com.zeronine.project1.widget.model.ChatModel
import com.zeronine.project1.widget.model.FriendsModel
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatBinding: ActivityChatBinding

    private lateinit var chatDB: DatabaseReference
    private lateinit var userDB: DatabaseReference

    private var destinationUid: String? = null
    private var destinationName: String? = null
    private var myUid: String? = null
    private var chatRoomId: String? = null

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chatBinding = ActivityChatBinding.inflate(layoutInflater)
        val view = chatBinding.root
        setContentView(view)

        chatDB = Firebase.database.reference.child(DBKey.DB_CHATROOMS)
        userDB = Firebase.database.reference.child(DBKey.DB_USERS)

        destinationUid = intent.getStringExtra("friendId")
        destinationName = intent.getStringExtra("friendName")
        chatBinding.showChatFriendName.text = destinationName
        myUid = Firebase.auth.currentUser?.uid.toString()

        //메세지를 보낸시간
        val time = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("MM월 dd일 hh:mm")
        val curTime = dateFormat.format(Date(time)).toString()


        //메세지 전송버튼을 눌렀을 때
        chatBinding.sendMessageImageView.setOnClickListener {
            Log.d("sendChat", "")
            val chatModel = ChatModel()
            chatModel.users.put(myUid.toString(), true)
            chatModel.users.put(destinationUid.toString(), true)

            val comment = ChatModel.Comment(myUid, chatBinding.editText.text.toString(), curTime)
            if (chatRoomId == null) {
                chatBinding.sendMessageImageView.isEnabled = false
                chatDB.push().setValue(chatModel).addOnSuccessListener {
                    //채팅방 생성
                    checkChatRoom()
                    //메세지 보내기
                    Handler().postDelayed({
                        println(chatRoomId)
                        chatDB.child(chatRoomId.toString()).child(DBKey.DB_COMMENTS).push()
                            .setValue(comment)
                        chatBinding.editText.text = null
                    }, 1000L)
                }
            } else {
                chatDB.child(chatRoomId.toString()).child(DBKey.DB_COMMENTS).push()
                    .setValue(comment)
                chatBinding.editText.text = null
            }
        }
        checkChatRoom()
    }

    private fun checkChatRoom() {
        chatDB.orderByChild("users/$myUid").equalTo(true)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (item in snapshot.children) {
                        println(item)
                        val chatModel = item.getValue<ChatModel>()
                        if (chatModel?.users!!.containsKey(destinationUid)) {
                            chatRoomId = item.key
                            chatBinding.sendMessageImageView.isEnabled = true
                            chatBinding.chatRecyclerView.layoutManager =
                                LinearLayoutManager(this@ChatActivity)
                            chatBinding.chatRecyclerView.adapter = ChatAdapter()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }


    inner class ChatAdapter:
        RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {
        private val comments = ArrayList<ChatModel.Comment>()

        //        private var friend: FriendsModel? = null
        init {
                        getMessageList()
        }

        fun getMessageList() {
            chatDB.child(chatRoomId.toString()).child(DBKey.DB_COMMENTS)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        comments.clear()
                        for (data in snapshot.children) {
                            val item = data.getValue<ChatModel.Comment>()
                            comments.add(item!!)
                            Log.d("CHECK COMMENTS", "$comments")
                            println(comments)
                        }
                        notifyDataSetChanged()
                        chatBinding.chatRecyclerView.scrollToPosition(comments.size - 1)
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })
        }

        inner class MessageViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
            val message = binding.messageTextView
            val name = binding.nameTextView
            val time = binding.timeTextView
            val messageLayout = binding.messageLayout
        }


        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MessageViewHolder {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
            return MessageViewHolder(
                ItemMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        @SuppressLint("RtlHardcoded")
        override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
            holder.message.text = comments[position].message
            Log.d("CHAT MESSAGE", "${holder.message.text}")
            holder.time.text = comments[position].time

            if (comments[position].uid.equals(myUid)) {
                // 사용자 본인 채팅인 경우
                Log.d("사용자 채팅", "사용자 본인 채팅입니다")
                holder.message.setBackgroundResource(R.drawable.newrigtbubble)
                holder.name.visibility = View.INVISIBLE
                holder.messageLayout.gravity = Gravity.RIGHT
            } else {
                // 상대방 채팅인 경우
                holder.message.setBackgroundResource(R.drawable.newleftbubble)
                holder.name.text = destinationName
                holder.name.visibility = View.VISIBLE
                holder.messageLayout.gravity = Gravity.LEFT
            }
        }

        override fun getItemCount(): Int {
            return comments.size
        }


    }


}