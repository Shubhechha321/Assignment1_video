package com.example.assignment1_video

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
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

        if (tv_username2.text.toString().isEmpty()) {

            tv_username2.error = "Please enter email"

            tv_username2.requestFocus()

            return

        }



        if (!Patterns.EMAIL_ADDRESS.matcher(tv_username2.text.toString()).matches()) {

            tv_username2.error = "Please enter valid email"

            tv_username2.requestFocus()

            return

        }



        if (tv_password2.text.toString().isEmpty()) {

            tv_password2.error = "Please enter password"

            tv_password2.requestFocus()

            return

        }
        if (tv_password2.length()<6) {

            tv_password2.error = "Password must be at least 6 characters!"

            return

        }


        auth.createUserWithEmailAndPassword(tv_username2.text.toString(), tv_password2.text.toString())

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

            tv_password2.transformationMethod = HideReturnsTransformationMethod.getInstance()

            visible.setImageResource(R.drawable.visiblility_on)

        } else {

            // To hide the password

            tv_password2.transformationMethod = PasswordTransformationMethod.getInstance()

            visible.setImageResource(R.drawable.visiblility_off)

        }

        // To put the pointer at the end of the password string

        tv_password2.setSelection(tv_password2.text.toString().length)

    }


}