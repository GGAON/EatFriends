package com.zeronine.project1.screen.order

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.data.DB.DBKey
import com.zeronine.project1.data.DB.DBKey.Companion.DB_FOODLIST
import com.zeronine.project1.data.DB.DBKey.Companion.DB_ORDER
import com.zeronine.project1.databinding.ActivityOrderBinding
import com.zeronine.project1.screen.home.currentGroupSettingID
import com.zeronine.project1.screen.home.totalPeople
import com.zeronine.project1.widget.adapter.FoodListAdapter
import com.zeronine.project1.widget.model.FoodListModel

class OrderActivity : AppCompatActivity() {

    private lateinit var orderBinding: ActivityOrderBinding
    private lateinit var foodListAdapter: FoodListAdapter
    private lateinit var foodListDB: DatabaseReference
    private lateinit var groupSettingDB: DatabaseReference
    private lateinit var orderDB: DatabaseReference

    var foodID: String? = null

    private val foodList = mutableListOf<FoodListModel>()
    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val foodListModel = snapshot.getValue(FoodListModel::class.java)
            foodListModel ?: return
            foodList.add(foodListModel)
            Log.d("CHECK THIS!!", "$foodListModel")
            foodListAdapter.submitList(foodList)

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

        groupSettingDB = Firebase.database.reference.child(DBKey.DB_GROUPSETTING)
        val currentUserId = Firebase.auth.currentUser?.uid.orEmpty()

        Log.d("public 변수 확인", "currentGroupSettingID = $currentGroupSettingID")

        val recruiterId = groupSettingDB.child(currentGroupSettingID!!).child("recruiterId")
        recruiterId.get().addOnSuccessListener {
            if(it.value == currentUserId) {
                orderBinding.myDeliveryFee.text = "0"
            }
            else
            {
                when(totalPeople) {
                    2 -> orderBinding.myDeliveryFee.text = "1000"
                    3 -> orderBinding.myDeliveryFee.text = "800"
                    4 -> orderBinding.myDeliveryFee.text = "400"
                }
            }
        }

        foodListAdapter = FoodListAdapter()
        foodListAdapter.setItemClickListener(object : FoodListAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val item = foodList[position]
                orderBinding.showMyMenuNameHere.text = item.foodName
                orderBinding.showMyMenuPriceHere.text = item.foodPrice.toString()
                val totalPrice = orderBinding.myDeliveryFee.text.toString().toInt() + item.foodPrice
                orderBinding.myTotalPrice.text = "$totalPrice 원"
                foodID = item.foodId
            }
        })
        orderBinding.foodListRecyclerView.layoutManager = LinearLayoutManager(this)
        orderBinding.foodListRecyclerView.adapter = foodListAdapter


        val foodCategory = groupSettingDB.child(currentGroupSettingID!!).child("foodCategory")
        foodListDB = Firebase.database.reference.child(DB_FOODLIST)
        foodCategory.get().addOnSuccessListener {
            orderBinding.foodCategory.text = it.value.toString()
            foodListDB.child(it.value.toString()).addChildEventListener(listener)
        }

        orderDB = Firebase.database.reference.child(DB_ORDER)

        orderBinding.completeButton.setOnClickListener {

            if (foodID == null) {
                Toast.makeText(this, "주문할 음식을 골라주세요!!", Toast.LENGTH_SHORT).show()
            } else {
                val orderInfo = mutableMapOf<String, Any>()
                orderInfo["userId"] = currentUserId
                orderInfo["foodId"] = foodID!!
                orderInfo["foodName"] = orderBinding.showMyMenuNameHere.text
                orderInfo["foodPrice"] = orderBinding.showMyMenuPriceHere.text
                val orderInfoDB = orderDB.child(currentGroupSettingID!!).child(currentUserId)
                orderInfoDB.updateChildren(orderInfo)

                groupSettingDB.child(currentGroupSettingID!!).child("recruiting").setValue(3)
                startActivity(Intent(this, DeliveryActivity::class.java))
                finish()
            }
        }

    }

}