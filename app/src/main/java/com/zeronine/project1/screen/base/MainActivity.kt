package com.zeronine.project1.screen.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.zeronine.project1.R
import com.zeronine.project1.screen.intro.LoginActivity

class MainActivity : AppCompatActivity() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var backWait: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        if (auth.currentUser == null) {//로그인이 되지 않았을 때
//            //setContentView(R.layout.activity_login)
//            finish()
//            startActivity(
//                Intent(
//                    this,
//                    LoginActivity::class.java
//                )
//            ) // 로그인이 되어있지 않을 때 LoginActivity로 이동하라
//            //finish()
//        }


//        val homeFragment = HomeFragment()
//        val friendsFragment = FriendsFragment()
//        val historyFragment = HistoryFragment()
//        val myPageFragment = MyPageFragment()
//
//
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//
//        replaceFragment(homeFragment)
//
//        bottomNavigationView.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.home -> replaceFragment(homeFragment)
//                R.id.friends -> replaceFragment(friendsFragment)
//                R.id.history -> replaceFragment(historyFragment)
//                R.id.myPage -> replaceFragment(myPageFragment)
//            }
//            true
//        }

    }


//    private fun replaceFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction()
//            .apply {
//                replace(R.id.fragmentContainer, fragment)
//                commit()
//            }
//    }

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
        else{
            startActivity(Intent(this, PageActivity::class.java))
            finish()
        }
        super.onStart()
    }

//    override fun onBackPressed() {
//        if (System.currentTimeMillis() - backWait >= 2000) {
//            backWait = System.currentTimeMillis()
//            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
//        } else {
//
//            super.onBackPressed()
//            finish()
//        }
//    }


}