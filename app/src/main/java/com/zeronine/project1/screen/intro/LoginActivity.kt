package com.zeronine.project1.screen.intro

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.screen.base.MainActivity
import com.zeronine.project1.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var backWait: Long = 0

    //private val loginBinding by lazy{ActivityLoginBinding.inflate(layoutInflater)}
    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        auth = Firebase.auth

        loginBinding.LoginButton.isEnabled = false

        loginBinding.MoveToSignUpButton.setOnClickListener {
            //finish()
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        loginButtonEnable() //로그인 버튼 활성화
        initLoginButton() //로그인하기

//        loginBinding.LoginButton.isEnabled = false

    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backWait >= 2000) {
            backWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()

            //System.exit(0)
        } else {
            super.onBackPressed()
            finish()
        }
    }

    /*
    로그인하기
     */
    private fun initLoginButton() {
        loginBinding.LoginButton.setOnClickListener {
            val email = getInputEmail()
            val password = getInputPassword()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                        startActivity(Intent(this, MainActivity::class.java))

                    } else {
                        Toast.makeText(
                            this,
                            "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }


    /*
    로그인 버튼 활성화 : 이메일과 패스워드에 변화가 있을 때 모두 공란이 아닐때에만 loginButton이 활성화
     */
    private fun loginButtonEnable() {
        loginBinding.EmailEditText.addTextChangedListener {
            val enable =
                loginBinding.EmailEditText.text.isNotEmpty() && loginBinding.PasswordEditText.text.isNotEmpty()
            loginBinding.LoginButton.isEnabled = enable
        }
        loginBinding.PasswordEditText.addTextChangedListener {
            val enable =
                loginBinding.EmailEditText.text.isNotEmpty() && loginBinding.PasswordEditText.text.isNotEmpty()
            loginBinding.LoginButton.isEnabled = enable
        }


    }


    private fun getInputEmail(): String {
        return loginBinding.EmailEditText.text.toString()
    }

    private fun getInputPassword(): String {
        return loginBinding.PasswordEditText.text.toString()
    }


}