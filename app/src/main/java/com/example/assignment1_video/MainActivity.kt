package com.example.assignment1_video

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_main.*


//const val RC_SIGN_IN = 123

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var mAuth: FirebaseAuth
    private var mIsShowPass = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        auth = FirebaseAuth.getInstance()

        SignIn.setOnClickListener {
            tv_username.visibility = View.VISIBLE
            tv_password.visibility = View.VISIBLE
            visibility.visibility = View.VISIBLE
            btn_log_in.visibility = View.VISIBLE
            frgtPass.visibility = View.VISIBLE
            SignIn.visibility = View.INVISIBLE
            phone_verify.visibility = View.INVISIBLE
            sign_in_button.visibility = View.INVISIBLE
        }
        /* * val gso =
              GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                  .requestEmail()
                  .build()

          val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)*/

        sign_in_button.visibility = View.VISIBLE
        //tv_name.visibility = View.GONE
        //*sign_in_button.setSize(SignInButton.SIZE_STANDARD)

        /*  *    sign_in_button.setOnClickListener {
                val signInIntent = mGoogleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }*/

        visibility.setOnClickListener{
            mIsShowPass = !mIsShowPass
            showPassword(mIsShowPass)
        }
        showPassword(mIsShowPass)

        phone_verify.setOnClickListener {
            if (mAuth.currentUser == null) {
                Toast.makeText(this, "Not registered :)", Toast.LENGTH_LONG).show()
            }else {
                startActivity(Intent(this, DashBoardActivity::class.java))
                finish()
            }
        }

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
        //   val acct = GoogleSignIn.getLastSignedInAccount(applicationContext)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

    }

    /* *override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)

         // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
         if (requestCode == RC_SIGN_IN) {
             // The Task returned from this call is always completed, no need to attach
             // a listener.
             val task =
                 GoogleSignIn.getSignedInAccountFromIntent(data)
             handleSignInResult(task)
         }
     }
     private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
         try {
             val account = completedTask.getResult(ApiException::class.java)
             sign_in_button.visibility = View.GONE
             if (account != null) {
                // tv_name.text = account.displayName
          //       FirebaseGoogleAuth(account)
                 startActivity(Intent(this,DashBoardActivity::class.java))
                 finish()

             }
            // tv_name.visibility = View.VISIBLE
         } catch (e: ApiException) {

             sign_in_button.visibility = View.VISIBLE
        //     FirebaseGoogleAuth(null)
         }
     }*/
    /* private fun FirebaseGoogleAuth(account: GoogleSignInAccount){
         val authCredential = GoogleAuthProvider.getCredential(account.idToken(),null)
         mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, OnCompleteListener<AuthResult>() {

         })
     }*/

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
                    updateUI(null)
                }
            }
    }
    public override fun onStart() {
        super.onStart()
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
            Toast.makeText(baseContext, "Login failed.",
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
        tv_password.setSelection(tv_password.text.toString().length)
    }
}




