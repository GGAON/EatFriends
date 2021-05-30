package com.zeronine.project1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.zeronine.project1.friends.FriendsFragment
import com.zeronine.project1.history.HistoryFragment
import com.zeronine.project1.home.HomeFragment
import com.zeronine.project1.intro.LoginActivity
import com.zeronine.project1.mypage.MyPageFragment

class MainActivity : AppCompatActivity() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val homeFragment = HomeFragment()
        val friendsFragment = FriendsFragment()
        val historyFragment = HistoryFragment()
        val myPageFragment = MyPageFragment()


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        replaceFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(homeFragment)
                R.id.friends -> replaceFragment(friendsFragment)
                R.id.history -> replaceFragment(historyFragment)
                R.id.myPage -> replaceFragment(myPageFragment)
            }
            true
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragmentContainer, fragment)
                commit()
            }
    }

    override fun onStart() {
        super.onStart()
        //val currentUser = auth.currentUser
        if (auth.currentUser == null) {//로그인이 되지 않았을 때
            //setContentView(R.layout.activity_login)
            startActivity(Intent(this, LoginActivity::class.java)) // 로그인이 되어있지 않을 때 LoginActivity로 이동하라
        }
    }
}