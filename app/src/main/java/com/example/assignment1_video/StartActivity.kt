package com.example.assignment1_video

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_start.*

const val RC_SIGN_IN = 123

class StartActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        auth = FirebaseAuth.getInstance()
        accountExists.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }

        Emai.setOnClickListener {
            if (auth.currentUser == null) {
                startActivity(Intent(this, OtpActivity::class.java))
            }else {
                startActivity(Intent(this, DashBoardActivity::class.java))
                finish()
            }
        }

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        //googleSignIn.setSize(SignInButton.SIZE_STANDARD)


        googleSignIn.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }


        getStarted.setOnClickListener {
            getStarted.visibility = View.INVISIBLE
            googleSignIn.visibility = View.VISIBLE
            Email.visibility = View.VISIBLE
            Emai.visibility = View.VISIBLE
        }
        Email.setOnClickListener {
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
            finish()
        }
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
            //sign_in_button.visibility = View.GONE
            if (account != null) {
                // tv_name.text = account.displayName
                //       FirebaseGoogleAuth(account)
                startActivity(Intent(this,DashBoardActivity::class.java))
                finish()

            }
            // tv_name.visibility = View.VISIBLE
        } catch (e: ApiException) {

           // sign_in_button.visibility = View.VISIBLE
            //     FirebaseGoogleAuth(null)
        }
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI1(currentUser)
        updateUI2(account)
    }
    private fun updateUI1(currentUser: FirebaseUser?) {
        if (currentUser!=null){
            if (currentUser.isEmailVerified ) {
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
    private fun updateUI2(currentUser: GoogleSignInAccount?){
        if (currentUser!=null){
            startActivity(Intent(this,DashBoardActivity::class.java))
            finish()
        }
    }
}