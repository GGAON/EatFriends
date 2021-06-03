package com.zeronine.project1.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.MainActivity
import com.zeronine.project1.R
import com.zeronine.project1.intro.LoginActivity

class MyPageFragment : Fragment(R.layout.fragment_mypage){

    //var fbAuth = FirebaseAuth.getInstance()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val logoutButton = view.findViewById<Button>(R.id.LogOutButton)
        logoutButton.setOnClickListener{
            Toast.makeText(activity, "Log Out", Toast.LENGTH_SHORT).show()
            FirebaseAuth.getInstance().signOut()
            activity?.let{
                startActivity(Intent(context, MainActivity::class.java))
            }
        }


    }



}