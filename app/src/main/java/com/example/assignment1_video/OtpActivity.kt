package com.example.assignment1_video

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_otp.*
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {
   // lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var mAuth: FirebaseAuth
    lateinit var verificationId :String
   // lateinit var btnVerify:Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        mAuth = FirebaseAuth.getInstance()
       // btnVerify=findViewById(R.id.veriBtn)

        send.setOnClickListener {
            sendVerification()
        }

        veriBtn.setOnClickListener {
              //  view: View? -> progress.visibility = View.VISIBLE
           view: View?->progress.visibility = View.VISIBLE
            val code = verifiTxt.text.toString()
            verifyVerificationCode (code)
        }
       /* authBtn.setOnClickListener {
                view: View? -> progress.visibility = View.VISIBLE
            authenticate()
        }*/
    }
/*    private fun verificationCallbacks () {
        mCallbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                progress.visibility = View.INVISIBLE
               // signIn(credential)

            }

            override fun onVerificationFailed(p0: FirebaseException) {
               // if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // }
            }


            override fun onCodeSent(verfication: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verfication, p1)
                verificationId = verfication.toString()
                progress.visibility = View.INVISIBLE
            }
        }
    }*/
    private fun sendVerification () {
       // verificationCallbacks()

       // val phnNo = "+"+ countryCodePicker.get
    val phnNo = phnNoTxt.text.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phnNo,
            60,
            TimeUnit.SECONDS,
            this,
           // this,
            mCallbacks
        )
    }
    private val mCallbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks = object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        override fun onVerificationCompleted(p0: PhoneAuthCredential){
            val code:String? = p0?.smsCode
            if (code!=null)
                verifiTxt.text=Editable.Factory.getInstance().newEditable(code)
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Toast.makeText(this@OtpActivity ,p0?.message,Toast.LENGTH_SHORT).show()
           // Toast.makeText(this@OtpActivity)
        }

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            verificationId=p0!!
        }
    }
    private fun verifyVerificationCode(code:String){
        val credentials:PhoneAuthCredential= PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credentials)
    }
    private fun signInWithPhoneAuthCredential(credentials:PhoneAuthCredential){
        mAuth.signInWithCredential(credentials).addOnCompleteListener(this@OtpActivity, object :OnCompleteListener<AuthResult>{
            override fun onComplete(p0: Task<AuthResult>) {
                if (p0.isSuccessful){
                    Toast.makeText(this@OtpActivity,"welcome",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@OtpActivity,DashBoardActivity::class.java))
                  //  finish()
                }else
                    Toast.makeText(this@OtpActivity,"wrong otp",Toast.LENGTH_SHORT).show()
            }
        })
    }
}

