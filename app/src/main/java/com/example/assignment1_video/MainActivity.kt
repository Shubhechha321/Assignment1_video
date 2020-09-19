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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_phone_verification.*
import java.util.concurrent.TimeUnit


const val RC_SIGN_IN = 123

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var mIsShowPass = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        auth.setLanguageCode("fr")
// To apply the default app language instead of explicitly setting it.
// auth.useAppLanguage()

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)






        sign_in_button.visibility = View.VISIBLE
        //tv_name.visibility = View.GONE
        sign_in_button.setSize(SignInButton.SIZE_STANDARD)
        sign_in_button.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        visibility.setOnClickListener{
            mIsShowPass = !mIsShowPass
            showPassword(mIsShowPass)
        }
        showPassword(mIsShowPass)
        phone_verify.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Sign in using phone number")
            val view = layoutInflater.inflate(R.layout.dialog_phone_verification, null)
            val phoneNo = view.findViewById<EditText>(R.id.ph)
            builder.setView(view)
            builder.setPositiveButton("Submit", DialogInterface.OnClickListener { _,  _ ->
               verify(phoneNo)
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _,  _ ->  })
            builder.show()
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
        val acct = GoogleSignIn.getLastSignedInAccount(this)

       /* if (acct != null) {
            sign_in_button.visibility = View.GONE
            //tv_name.text = acct.displayName
            //tv_name.visibility = View.VISIBLE
        }*/
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
                startActivity(Intent(this,DashBoardActivity::class.java))
                finish()
            }
           // tv_name.visibility = View.VISIBLE
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            sign_in_button.visibility = View.VISIBLE
           // tv_name.text = ""
            //tv_name.visibility = View.GONE
        }
    }
    private fun verify(phoneNo:EditText){
        if (phoneNo.text.toString().isEmpty()) {
            return
        }else{
/*
            callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // This callback will be invoked in two situations:
                    // 1 - Instant verification. In some cases the phone number can be instantly
                    //     verified without needing to send or enter a verification code.
                    // 2 - Auto-retrieval. On some devices Google Play services can automatically
                    //     detect the incoming verification SMS and perform verification without
                    //     user action.
                    Log.d(TAG, "onVerificationCompleted:$credential")

                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // This callback is invoked in an invalid request for verification is made,
                    // for instance if the the phone number format is not valid.
                    Log.w(TAG, "onVerificationFailed", e)

                    if (e is FirebaseAuthInvalidCredentialsException) {
                        // Invalid request
                        // ...
                    } else if (e is FirebaseTooManyRequestsException) {
                        // The SMS quota for the project has been exceeded
                        // ...
                    }

                    // Show a message and update the UI
                    // ...
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    // The SMS verification code has been sent to the provided phone number, we
                    // now need to ask the user to enter the code and then construct a credential
                    // by combining the code with a verification ID.
                    Log.d(TAG, "onCodeSent:$verificationId")

                    // Save verification ID and resending token so we can use them later
                    storedVerificationId = verificationId
                    resendToken = token

                    // ...
                }
            }



            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo, // Phone number to verify
                60, // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                this, // Activity (for callback binding)
                callbacks) // OnVerificationStateChangedCallbacks*/
        }
        /* auth.sendPasswordResetEmail(username.text.toString())
             .addOnCompleteListener { task ->
                 if (task.isSuccessful) {
                     Toast.makeText(this,"Email sent.",Toast.LENGTH_SHORT).show()
                 }
             }*/
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

        // To put the pointer at the end of the password string

        tv_password.setSelection(tv_password.text.toString().length)

    }


}

private fun PhoneAuthProvider.verifyPhoneNumber(phoneNo: EditText, i: Int, seconds: TimeUnit, mainActivity: MainActivity, callbacks: (command: Runnable) -> Unit) {

}

private fun PhoneAuthProvider.verifyPhoneNumber(phoneNo: EditText, i: Int, seconds: Long, mainActivity: MainActivity, callbacks: (command: Runnable) -> Unit) {

}
