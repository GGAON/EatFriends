package com.zeronine.project1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.zeronine.project1.friends.FriendsFragment
import com.zeronine.project1.history.HistoryFragment
import com.zeronine.project1.home.HomeFragment
import com.zeronine.project1.intro.LoginActivity
import com.zeronine.project1.mypage.MyPageFragment

class PageActivity:AppCompatActivity() {


    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var backWait: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page)

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
        //val currentUser = auth.currentUser
        if (auth.currentUser == null) {//로그인이 되지 않았을 때
            //setContentView(R.layout.activity_login)
            finish()
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            ) // 로그인이 되어있지 않을 때 LoginActivity로 이동하라
            //finish()
        }
        super.onStart()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backWait >= 2000) {
            backWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {

            super.onBackPressed()
            finish()
        }
    }
}