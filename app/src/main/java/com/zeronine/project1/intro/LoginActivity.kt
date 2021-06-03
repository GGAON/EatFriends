package com.zeronine.project1.intro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.MainActivity
import com.zeronine.project1.R

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var backWait:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        findViewById<Button>(R.id.MoveToSignUpButton).setOnClickListener {
            //finish()
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        loginButtonEnable() //로그인 버튼 활성화
        initLoginButton() //로그인하기

        val loginButton = findViewById<Button>(R.id.LoginButton)
        loginButton.isEnabled = false

    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() - backWait >= 2000) {
            backWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }

    /*
    로그인하기
     */
    private fun initLoginButton() {
        val loginButton = findViewById<Button>(R.id.LoginButton)
        loginButton.setOnClickListener {
            val email = getInputEmail()
            val password = getInputPassword()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {task ->
                    if(task.isSuccessful) {
                        finish()
                        Toast.makeText(this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))

                        //handleSuccessLogin()

                    }else {
                        Toast.makeText(this, "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }


    /*
    로그인 버튼 활성화 : 이메일과 패스워드에 변화가 있을 때 모두 공란이 아닐때에만 loginButton이 활성화
     */
    private fun loginButtonEnable() {
        val emailEditText = findViewById<EditText>(R.id.EmailEditText)
        val passwordEditText = findViewById<EditText>(R.id.PasswordEditText)
        val loginButton = findViewById<Button>(R.id.LoginButton)

        emailEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled = enable
        }
        passwordEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled = enable
        }


    }


    private fun getInputEmail() : String {
        return findViewById<EditText>(R.id.EmailEditText).text.toString()
    }
    private fun getInputPassword() : String {
        return findViewById<EditText>(R.id.PasswordEditText).text.toString()
    }


}