package com.example.assignment2_video

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var mIsShowPass = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        visibility.setOnClickListener{

            mIsShowPass = !mIsShowPass

            showPassword(mIsShowPass)

        }
        showPassword(mIsShowPass)

        frgtPass.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
            val username = view.findViewById<EditText>(R.id.et_username)
            builder.setView(view)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _,  _ ->
                forgotPass(username)
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _,  _ ->  })
            builder.show()
        }
        btn_sign_up.setOnClickListener() {
            startActivity(Intent(this, SignUp::class.java))
            finish()
        }
        btn_log_in.setOnClickListener() {
            doLogin();
        }

    }
    private fun forgotPass(username:EditText){
        if (username.text.toString().isEmpty()) {
            return

        }



        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            return
        }
        auth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"Email sent.",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun doLogin(){
        if (tv_username.text.toString().isEmpty()) {

            tv_username.error = "Please enter email"

            tv_username.requestFocus()

            return

        }



        if (!Patterns.EMAIL_ADDRESS.matcher(tv_username.text.toString()).matches()) {

            tv_username.error = "Please enter valid email"

            tv_username.requestFocus()

            return

        }



        if (tv_password.text.toString().isEmpty()) {

            tv_password.error = "Please enter password"

            tv_password.requestFocus()

            return

        }
        auth.signInWithEmailAndPassword(tv_username.text.toString(), tv_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                   // Toast.makeText(baseContext, "Login failed.",
                     //   Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser!=null){
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this,DashBoardActivity::class.java))

                finish()
            }else{
                Toast.makeText(baseContext, "Please verify your email address.",
                    Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(baseContext, "Login failed. Check password or email.",
                Toast.LENGTH_SHORT).show()
        }
    }
    private fun showPassword(isShow: Boolean) {

        if (isShow) {

            // To show the password

            tv_password.transformationMethod = HideReturnsTransformationMethod.getInstance()

            visibility.setImageResource(R.drawable.visiblility_on)

        } else {

            // To hide the password

            tv_password.transformationMethod = PasswordTransformationMethod.getInstance()

            visibility.setImageResource(R.drawable.visiblility_off)

        }

        // To put the pointer at the end of the password string

        tv_password.setSelection(tv_password.text.toString().length)

    }


}