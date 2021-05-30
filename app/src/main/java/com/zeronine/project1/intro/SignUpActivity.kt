package com.zeronine.project1.intro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.R

class SignUpActivity :AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = Firebase.auth

        initSignUpButton() // 회원가입하기
        SignUpButtonEnable() //회원가입 버튼 활성화
    }



    /*
    회원가입하기
     */

    private fun initSignUpButton() {
        val signUpButton = findViewById<Button>(R.id.signupButton)
        signUpButton.setOnClickListener {
            val email = getInputEmail()
            val password = getInputPassword()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                        //handleSuccessSignUp()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()

                    } else {
                        Toast.makeText(this, "이미 가입한 이메일이거나, 회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }

                }
        }
    }

    private fun SignUpButtonEnable() {
        val emailEditText = findViewById<EditText>(R.id.signupEmailEditText)
        val passwordEditText = findViewById<EditText>(R.id.signupPasswordEditText)
        val nameEditText = findViewById<EditText>(R.id.signupNameEditText)
        val phoneNumberEditText = findViewById<EditText>(R.id.signupPhoneNumberEditText)
        val signUpButton = findViewById<Button>(R.id.signupButton)

        emailEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty() && nameEditText.text.isNotEmpty() && phoneNumberEditText.text.isNotEmpty()
            signUpButton.isEnabled = enable
        }
        passwordEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty() && nameEditText.text.isNotEmpty() && phoneNumberEditText.text.isNotEmpty()
            signUpButton.isEnabled = enable
        }
        nameEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty() && nameEditText.text.isNotEmpty() && phoneNumberEditText.text.isNotEmpty()
            signUpButton.isEnabled = enable
        }
        phoneNumberEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty() && nameEditText.text.isNotEmpty() && phoneNumberEditText.text.isNotEmpty()
            signUpButton.isEnabled = enable
        }

    }

    private fun getInputEmail(): String {
        return findViewById<EditText>(R.id.signupEmailEditText).text.toString()
    }

    private fun getInputPassword(): String {
        return findViewById<EditText>(R.id.signupPasswordEditText).text.toString()
    }

    private fun getInputName(): String {
        return findViewById<EditText>(R.id.signupNameEditText).text.toString()
    }

    private fun getInputPhoneNumber(): String {
        return findViewById<EditText>(R.id.signupPhoneNumberEditText).text.toString()
    }

//    private fun handleSuccessSignUp() {
//        val database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference()
//        var uid:String = ""
//
//        if(intent.hasExtra("uid")){
//            uid = intent.getStringExtra("uid")
//        }
//
//        val dataInput = UserDatabase(
//            getInputEmail(),
//            getInputPassword(),
//            getInputName(),
//            getInputPhoneNumber()
//        )
//
//        myRef.child(uid).setValue(dataInput)
//    }

}