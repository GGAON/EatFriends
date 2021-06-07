package com.zeronine.project1.intro

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.R
import com.zeronine.project1.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var signupBinding : ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_signup)

        signupBinding = ActivitySignupBinding.inflate(layoutInflater)
        val view = signupBinding.root
        setContentView(view)

        auth = Firebase.auth
        signupBinding.signupButton.isEnabled = false

        initSignUpButton() // 회원가입하기
        enableSignUpButton() //회원가입 버튼 활성화
    }


    /*
    회원가입하기
     */

    private fun initSignUpButton() {
        val signUpButton = signupBinding.signupButton
        signUpButton.setOnClickListener {
            val email = getInputEmail()
            val password = getInputPassword()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
                        handleSuccessSignUp(user)
                        finish()

                    } else {
                        Toast.makeText(this, "이미 가입한 이메일이거나, 회원가입에 실패했습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
                .addOnFailureListener {
                    Toast.makeText(this, "이미 가입한 이메일이거나, 회원가입에 실패했습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun enableSignUpButton() {
        val emailEditText = signupBinding.signupEmailEditText
        val passwordEditText = signupBinding.signupPasswordEditText
        val nameEditText = signupBinding.signupNameEditText
        val phoneNumberEditText = signupBinding.signupPhoneNumberEditText
        val signUpButton = signupBinding.signupButton

        emailEditText.addTextChangedListener {
            val enable =
                emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty() && nameEditText.text.isNotEmpty() && phoneNumberEditText.text.isNotEmpty()
            signUpButton.isEnabled = enable
        }
        passwordEditText.addTextChangedListener {
            val enable =
                emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty() && nameEditText.text.isNotEmpty() && phoneNumberEditText.text.isNotEmpty()
            signUpButton.isEnabled = enable
        }
        nameEditText.addTextChangedListener {
            val enable =
                emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty() && nameEditText.text.isNotEmpty() && phoneNumberEditText.text.isNotEmpty()
            signUpButton.isEnabled = enable
        }
        phoneNumberEditText.addTextChangedListener {
            val enable =
                emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty() && nameEditText.text.isNotEmpty() && phoneNumberEditText.text.isNotEmpty()
            signUpButton.isEnabled = enable
        }

    }

    private fun getInputEmail(): String {
        return signupBinding.signupEmailEditText.text.toString()
    }

    private fun getInputPassword(): String {
        return signupBinding.signupPasswordEditText.text.toString()
    }

    private fun getInputName(): String {
        return signupBinding.signupNameEditText.text.toString()
    }

    private fun getInputPhoneNumber(): String {
        return signupBinding.signupPhoneNumberEditText.text.toString()
    }

    private fun handleSuccessSignUp(user: FirebaseUser?) {



        val userId = user?.uid.orEmpty()
        val currentUserDB = Firebase.database.reference.child("Users").child(userId)
        val userInfo = mutableMapOf<String, Any>()
        userInfo["userId"] = userId
        userInfo["email"] = getInputEmail()
        userInfo["name"] = getInputName()
        userInfo["phone number"] = getInputPhoneNumber()
        currentUserDB.updateChildren(userInfo)


        finish()
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
    }

}