package com.zeronine.project1.screen.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.screen.base.MainActivity
import com.zeronine.project1.R
import com.zeronine.project1.data.DB.DBKey.Companion.DB_USERS
import com.zeronine.project1.databinding.FragmentMypageBinding

class MyPageFragment : Fragment(R.layout.fragment_mypage) {

    private lateinit var userDB: DatabaseReference
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var myPageFragment: FragmentMypageBinding




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myPageFragment = FragmentMypageBinding.bind(view)
        auth = Firebase.auth
        userDB = Firebase.database.reference.child(DB_USERS)
        val currentUserName = userDB.child(getCurrentUserID()).child("name")

        currentUserName.get().addOnSuccessListener {
            Log.i("currentUserDB", "Got name : ${it.value}")
            myPageFragment.showName.text = "Hello, ${it.value}"
        }.addOnFailureListener {
            Log.e("currentUserDB", "Error getting name")
        }

        myPageFragment.howToUseButton.setOnClickListener {
            activity?.let {
                startActivity(Intent(context, HowToUseActivity::class.java))
            }
        }


        myPageFragment.LogOutButton.setOnClickListener {
            Toast.makeText(activity, "Log Out", Toast.LENGTH_SHORT).show()
            FirebaseAuth.getInstance().signOut()
            activity?.let {
                startActivity(Intent(context, MainActivity::class.java))
            }
            childFragmentManager
        }

    }

    private fun getCurrentUserID() : String{
        if(auth.currentUser == null){
            Log.e("currentUser", "noCurrentUser")
        }
        return auth.currentUser?.uid.orEmpty()
    }


}


