package com.zeronine.project1.mypage

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.zeronine.project1.R

class MyPageFragment : Fragment(R.layout.fragment_mypage){

    var fbAuth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val logoutButton = view.findViewById<Button>(R.id.LogOutButton)
        logoutButton.setOnClickListener{
            Snackbar.make(view, "Logging Out...", Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
            fbAuth.signOut()

        }


    }



}