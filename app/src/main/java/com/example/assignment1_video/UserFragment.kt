package com.example.assignment1_video

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.SimpleExoPlayer
//import butterknife.BindView
//import butterknife.ButterKnife
//import butterknife.OnClick
//import com.bumptech.glide.Glide
//import com.example.assignment1_video.ImagePickerActivity.Companion.showImagePickerOptions
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.common.reflect.Reflection.getPackageName
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
//import com.karumi.dexter.Dexter
//import com.karumi.dexter.MultiplePermissionsReport
//import com.karumi.dexter.PermissionToken
//import com.karumi.dexter.listener.PermissionRequest
//import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.view.*
import java.io.IOException


open class UserFragment : Fragment() {

    //@BindView(R.id.img_profile)
    //internal var imgProfile: ImageView?=null


    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_user, container, false)

        //ButterKnife.bind(this)
      //  ButterKnife.bind(view)
        //loadProfileDefault()
        // Clearing older images from cache directory
        // don't call this line if you want to choose multiple images in the same activity
        // call this once the bitmap(s) usage is over
      //  context?.let { ImagePickerActivity.clearCache(it) }

        auth = FirebaseAuth.getInstance()
        view.tv_name.visibility = View.GONE
        val acct = GoogleSignIn.getLastSignedInAccount(this.context)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        //val m = GoogleApiClient.Builder(this.activity).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build()
        val m = GoogleApiClient.Builder(this.requireContext()).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build()
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
                                Toast.makeText(
                                    activity,
                                    "Re-authentication successful.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                user!!.updatePassword(et_new_pass.text.toString())
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                activity,
                                                "Password changed successfully.",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        }
                                    }
                            }else{
                                Toast.makeText(
                                    activity,
                                    "Re-authentication failed! Enter correct password!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }else{
                    val intent = Intent(this.context, MainActivity::class.java)

                    startActivity(intent)
                   // finish()
                }
            }else{
                Toast.makeText(activity, "Password mismatching!", Toast.LENGTH_SHORT).show()
            }
        }else{
            et_curr_pass.error = "required!"
            et_new_pass.error = "required!"
            et_confirm_pass.error = "required!"
        }


       /* fun loadProfile(url:String) {
            Log.d(TAG, "Image cache path: " + url)
            //GlideApp.with(this).load(url).into(imgProfile)
            context?.let { Glide.with(it).load(url).into(imgProfile) }
            imgProfile.setColorFilter(ContextCompat.getColor(context!!, android.R.color.transparent))
        }

        @OnClick(R.id.img_plus, R.id.img_profile)
        fun onProfileImageClick() {
            Dexter.withActivity(context as Activity?)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object: MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.areAllPermissionsGranted())
                        {
                            showImagePickerOptions()
                        }
                        if (report.isAnyPermissionPermanentlyDenied())
                        {
                            showSettingsDialog()
                        }
                    }
                    override fun onPermissionRationaleShouldBeShown(permissions:List<PermissionRequest>, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).check()
        }



        fun onActivityResult(requestCode:Int, resultCode:Int, @Nullable data:Intent) {
            if (requestCode == REQUEST_IMAGE) {
                if (resultCode == Activity.RESULT_OK) {
                    val uri = data.getParcelableExtra("path")
                    try {
                        // You can update this bitmap to your server
                        val bitmap =
                            MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                        // loading profile image from local cache
                        loadProfile(uri.toString())
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }*/
    }
/*
    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.dialog_permission_title))
        builder.setMessage(getString(R.string.dialog_permission_message))
        builder.setPositiveButton(getString(R.string.go_to_settings)) { dialog, which ->
            dialog.cancel()
            openSettings()
        }
        builder.setNegativeButton(getString(android.R.string.cancel), { dialog, which-> dialog.cancel() })
        builder.show()    }

    private fun launchCameraIntent() {
        val intent = Intent(context, ImagePickerActivity::class.java)
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE)
        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)
        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000)
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    private fun launchGalleryIntent() {
        val intent = Intent(context, ImagePickerActivity::class.java)
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE)
        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    private fun showImagePickerOptions() {
        context?.let {
            showImagePickerOptions(it, object:ImagePickerActivity.PickerOptionListener() {
                override fun onTakeCameraSelected() {
                    launchCameraIntent()
                }

                override fun onChooseGallerySelected() {
                    launchGalleryIntent()
                }
            })
        }   }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", getPackageName(), null)
        intent.setData(uri)
        startActivityForResult(intent, 101)
    }
    private fun loadProfileDefault() {
        context?.let { Glide.with(it).load(R.drawable.ic_action_profile).into(imgProfile) }
        imgProfile.setColorFilter(ContextCompat.getColor(context!!, R.color.profile_default_tint))    }
    companion object {
        private val TAG = MainActivity::class.java!!.getSimpleName()
        val REQUEST_IMAGE = 100
    }
*/
    /*
private fun loadProfile(url:String) {
    Log.d(TAG, "Image cache path: " + url)
    //GlideApp.with(this).load(url).into(imgProfile)
    context?.let { Glide.with(it).load(url).into(imgProfile!!) }
    imgProfile?.setColorFilter(ContextCompat.getColor(context!!, android.R.color.transparent))
}
    private fun loadProfileDefault() {
        //context?.let { Glide.with(it).load(R.drawable.ic_action_profile).into(imgProfile) }
        imgProfile?.let {
            Glide.with(this).load(R.drawable.ic_action_profile)
                .into(it)
        }
        imgProfile?.setColorFilter(ContextCompat.getColor(context!!, R.color.profile_default_tint))
       // imgProfile=i
    }
    @OnClick(R.id.img_plus, R.id.img_profile)
    internal fun onProfileImageClick() {
        Dexter.withActivity(context as Activity?)
            .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted())
                    {
                        showImagePickerOptions()
                    }
                    if (report.isAnyPermissionPermanentlyDenied)
                    {
                        showSettingsDialog()
                    }
                }
                override fun onPermissionRationaleShouldBeShown(permissions:List<PermissionRequest>, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }).check()
    }/*
    private fun showImagePickerOptions() {
        context?.let {
            showImagePickerOptions(it, object: ImagePickerActivity.PickerOptionListener {
                override fun onTakeCameraSelected() {
                    launchCameraIntent()
                }

                override fun onChooseGallerySelected() {
                    launchGalleryIntent()
                }
            })
        }
    }
    private fun launchCameraIntent() {
        val intent = Intent(context, ImagePickerActivity::class.java)
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE)
        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)
        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000)
        startActivityForResult(intent, REQUEST_IMAGE)
    }
    private fun launchGalleryIntent() {
        val intent = Intent(context, ImagePickerActivity::class.java)
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE)
        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                //val uri = data.getParcelableExtra("path")
                val uri = data?.getParcelableExtra<Uri>("path")
                try {
                    // You can update this bitmap to your server
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                    // loading profile image from local cache
                    loadProfile(uri.toString())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
   /* fun onActivityResult(requestCode:Int, resultCode:Int, @Nullable data:Intent) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                //val uri = data.getParcelableExtra("path")
                val uri = data.getParcelableExtra<Uri>("path")
                try {
                    // You can update this bitmap to your server
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                    // loading profile image from local cache
                    loadProfile(uri.toString())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }*/
    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.dialog_permission_title))
        builder.setMessage(getString(R.string.dialog_permission_message))
        builder.setPositiveButton(getString(R.string.go_to_settings)) { dialog, which ->
            dialog.cancel()
            openSettings()
        }
        builder.setNegativeButton(getString(android.R.string.cancel), { dialog, which-> dialog.cancel() })
        builder.show()
    }
    // navigating user to app settings
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
      //  val uri = Uri.fromParts("package", getPackageName<Uri>(), null)
        val uri = Uri.fromParts("package", getPackageName(""), null)
        intent.setData(uri)
        startActivityForResult(intent, 101)
    }
    companion object {
        private val TAG = MainActivity::class.java!!.getSimpleName()
        val REQUEST_IMAGE = 100
    }*/*/
}