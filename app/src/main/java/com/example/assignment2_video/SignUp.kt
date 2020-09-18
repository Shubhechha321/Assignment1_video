package com.example.assignment2_video

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var mIsShowPass = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        visible.setOnClickListener{

            mIsShowPass = !mIsShowPass

            showPassword(mIsShowPass)

        }
        showPassword(mIsShowPass)

        btn_sign_u.setOnClickListener {

            signUpUser()

        }



    }



    private fun signUpUser() {

        if (tv_usernam.text.toString().isEmpty()) {

            tv_usernam.error = "Please enter email"

            tv_usernam.requestFocus()

            return

        }



        if (!Patterns.EMAIL_ADDRESS.matcher(tv_usernam.text.toString()).matches()) {

            tv_usernam.error = "Please enter valid email"

            tv_usernam.requestFocus()

            return

        }



        if (tv_passwor.text.toString().isEmpty()) {

            tv_passwor.error = "Please enter password"

            tv_passwor.requestFocus()

            return

        }
        if (tv_passwor.length()<6) {

            tv_passwor.error = "Password must be at least 6 characters!"

            return

        }

        progressBar.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(tv_usernam.text.toString(), tv_passwor.text.toString())

            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this,MainActivity::class.java))

                                finish()
                            }
                        }

                } else {

                    Toast.makeText(baseContext, "Sign Up failed. Try again after some time.",

                        Toast.LENGTH_SHORT).show()

                }

            }

    }
    private fun showPassword(isShow: Boolean) {

        if (isShow) {

            // To show the password

            tv_passwor.transformationMethod = HideReturnsTransformationMethod.getInstance()

            visible.setImageResource(R.drawable.visiblility_on)

        } else {

            // To hide the password

            tv_passwor.transformationMethod = PasswordTransformationMethod.getInstance()

            visible.setImageResource(R.drawable.visiblility_off)

        }

        // To put the pointer at the end of the password string

        tv_passwor.setSelection(tv_passwor.text.toString().length)

    }


}