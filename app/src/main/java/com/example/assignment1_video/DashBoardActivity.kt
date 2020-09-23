package com.example.assignment1_video

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dash_board.*

@Suppress("DEPRECATION")
class DashBoardActivity : AppCompatActivity() {

    //private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var auth: FirebaseAuth
   // private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        auth = FirebaseAuth.getInstance()
       // mAuth = Firebase.mAuth
      //  mAuth = FirebaseAuth.getInstance()
        tv_name.visibility = View.GONE
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
       // mGoogleApiClient = GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build()
       // m = GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build()
        val m = GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build()
       m.connect()

        if (acct != null) {
            //sign_in_button.visibility = View.GONE
            tv_name.text = acct.displayName
            tv_name.visibility = View.VISIBLE
        }
        btn_change_pass.setOnClickListener {
            changePass()
        }
        btn_log_out.setOnClickListener(){

                auth.signOut()
            Auth.GoogleSignInApi.signOut(m)
          startActivity(Intent(this, StartActivity::class.java))
            finish()
        }

    }
    private fun changePass(){
        if (et_new_pass.text.isNotEmpty() && et_curr_pass.text.isNotEmpty() && et_confirm_pass.text.isNotEmpty()){
            if(et_new_pass.text.toString().equals(et_new_pass.text.toString())){
                val user = auth.currentUser
                if ((user != null && user.email != null)){
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, et_curr_pass.text.toString())

                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(this,"Re-authentication successful.",Toast.LENGTH_SHORT).show()

                                user!!.updatePassword(et_new_pass.text.toString())
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(this,"Password changed successfully.",Toast.LENGTH_SHORT).show()

                                        }
                                    }
                            }else{
                                Toast.makeText(this,"Re-authentication failed! Enter correct password!",Toast.LENGTH_SHORT).show()
                            }
                        }
                }else{
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
            }else{
                Toast.makeText(this,"Password mismatching!",Toast.LENGTH_SHORT).show()
            }
        }else{
            et_curr_pass.error = "required!"
            et_new_pass.error = "required!"
            et_confirm_pass.error = "required!"
        }
    }
}

