package com.zeronine.project1.screen.order

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.zeronine.project1.databinding.ActivityOrderBinding
import com.zeronine.project1.widget.model.FoodListModel

class OrderActivity: AppCompatActivity() {

    private lateinit var orderBinding: ActivityOrderBinding
    private val foodList = mutableListOf<FoodListModel>()
    private val listener = object: ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onChildRemoved(snapshot: DataSnapshot) {

        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onCancelled(error: DatabaseError) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        orderBinding = ActivityOrderBinding.inflate(layoutInflater)
        val view = orderBinding.root
        setContentView(view)

        orderBinding.completeButton.setOnClickListener {
            startActivity(Intent(this, DeliveryActivity::class.java))
            finish()
        }

    }

}