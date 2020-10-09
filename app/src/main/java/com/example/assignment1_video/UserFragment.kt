package com.example.assignment1_video

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.assignment1_video.R
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.view.*


class UserFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_user,container,false)
        auth = FirebaseAuth.getInstance()
        view.tv_name.visibility = View.GONE
        val acct = GoogleSignIn.getLastSignedInAccount(this.context)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        //val m = GoogleApiClient.Builder(this.activity).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build()
        val m = GoogleApiClient.Builder(this.requireContext()).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build()
        m.connect()
        if (acct != null) {
            //sign_in_button.visibility = View.GONE
            view.tv_name.text = acct.displayName
            view.tv_name.visibility = View.VISIBLE
        }
        view.btn_change_pass.setOnClickListener {
            changePass()
        }
        view.btn_log_out.setOnClickListener(){
            auth.signOut()
            Auth.GoogleSignInApi.signOut(m)
            val intent = Intent(this.context, StartActivity::class.java)
            startActivity(intent)
            //finish()
        }
        return view
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    private fun changePass() {
        if (et_new_pass.text.isNotEmpty() && et_curr_pass.text.isNotEmpty() && et_confirm_pass.text.isNotEmpty()){
            if(et_new_pass.text.toString().equals(et_new_pass.text.toString())){
                val user = auth.currentUser
                if ((user != null && user.email != null)){
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, et_curr_pass.text.toString())

                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(activity,"Re-authentication successful.",Toast.LENGTH_SHORT).show()

                                user!!.updatePassword(et_new_pass.text.toString())
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(activity,"Password changed successfully.",Toast.LENGTH_SHORT).show()

                                        }
                                    }
                            }else{
                                Toast.makeText(activity,"Re-authentication failed! Enter correct password!",Toast.LENGTH_SHORT).show()
                            }
                        }
                }else{
                    val intent = Intent(this.context, MainActivity::class.java)

                    startActivity(intent)
                   // finish()
                }
            }else{
                Toast.makeText(activity,"Password mismatching!",Toast.LENGTH_SHORT).show()
            }
        }else{
            et_curr_pass.error = "required!"
            et_new_pass.error = "required!"
            et_confirm_pass.error = "required!"
        }
    }


}