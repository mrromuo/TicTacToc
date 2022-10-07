package com.raeed.tictactoc

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.content.Intent
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import android.graphics.Bitmap
import com.firebase.ui.auth.AuthUI.IdpConfig.EmailBuilder
import com.firebase.ui.auth.AuthUI.IdpConfig.GoogleBuilder
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.auth.AuthUI
import android.graphics.BitmapFactory
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.firebase.ui.auth.IdpResponse
import com.canhub.cropper.CropImageView.Guidelines
import android.app.ProgressDialog
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import com.canhub.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
import com.canhub.cropper.CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE
import java.util.*

class Person : AppCompatActivity() {
      var imageView: ImageView? = null
      var newname: EditText? = null
      var phone: EditText? = null
      var Email: EditText? = null
      var save: Button? = null
      var cancel: Button? = null
     private lateinit var intent1: Intent
      var AUTHUI_REQUEST_CODE = 11476

      // FireBase Storage and relTime database configurations:
      private val storage = FirebaseStorage.getInstance("gs://tictactoc-8bbfd.appspot.com")
      private var profRf: StorageReference? = null
      private var loadingRf: StorageReference? = null
      private var storageReference: StorageReference? = null
      var imageUri: Uri? = null
      var uri: Uri? = null
      private var bt: Bitmap? = null

      // Easily sign-in to  Android app with FirebaseUI
      var provider = Arrays.asList(
            EmailBuilder().build(),
            GoogleBuilder().build()
      )

      // App Shared Preferences file
      lateinit var sharedPreferences: SharedPreferences
      lateinit var editor: SharedPreferences.Editor

      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_person)
            imageView = findViewById(R.id.imageView)
            newname = findViewById(R.id.editPersonName)
            phone = findViewById(R.id.telphone)
            Email = findViewById(R.id.personemail)
            save = findViewById(R.id.submit)
            cancel = findViewById(R.id.ok)
            storageReference = storage.reference
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext)
            if (FirebaseAuth.getInstance().currentUser != null) {
                  //startActivity(new Intent(this,SelectActivity.class));
                  oldinfo
                  loadprofileImage()
            } else {
                  val intent = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(provider) // you can add .setlogo(R.drawable.logo)  and setTosAndPrivacyUrls()
                        .setAlwaysShowSignInMethodScreen(true)
                        .build()
                  startActivityForResult(intent, AUTHUI_REQUEST_CODE)
            }
            imageView?.setOnClickListener(View.OnClickListener {
                 // startPickImageActivity(this@Person)
                  val intent2 = Intent(Intent.ACTION_PICK)
                  intent2.type = "image/*"
                  startActivityForResult(intent2, CROP_IMAGE_ACTIVITY_REQUEST_CODE)
            })
            save?.setOnClickListener(View.OnClickListener { setNew() })
            cancel?.setOnClickListener(View.OnClickListener {
                  intent1 = Intent()
                  setResult(RESULT_CANCELED, intent1)
                  finish()
            })
      }

      private fun loadprofileImage() {
            SelectActivity.IMAGE_NAME = sharedPreferences!!.getString(
                  SelectActivity.IMAGE_KEY,
                  SelectActivity.IMAGE_NotYet
            )
            profRf = storage.reference.child(SelectActivity.PrfileImageChiled)
            loadingRf = profRf!!.child(SelectActivity.IMAGE_NAME!!)
            loadingRf!!.getBytes((1024 * 1024).toLong()).addOnSuccessListener { bytes ->
                  bt = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                  imageView!!.setImageBitmap(bt)
            }
      }

      private fun setNew() {
            intent1 = Intent()
            val name = newname!!.text.toString()
            val Tphone = phone!!.text.toString()
            val email = Email!!.text.toString()
            val photo = ImageName
            //int NameLinth = name.length();
            if (name !== "") {
                  intent1.putExtra(MainActivity.MAIN_PLAYER, name)
                  intent1.putExtra(SelectActivity.PHONE_KEY, Tphone)
                  intent1.putExtra(SelectActivity.EMAIL_KEY, email)
                  intent1.putExtra(SelectActivity.IMAGE_KEY, photo)
                  setResult(RESULT_OK, intent1)
                  finish()
            } else Toast.makeText(
                  applicationContext,
                  getText(R.string.pleasename),
                  Toast.LENGTH_LONG
            ).show()
      }

      //MAIN_PLAYER_ID = sharedPreferences.getString(MAIN_USER_ID_KEY, "ID_X");
      private val oldinfo: Unit
            private get() {
                  MainName = sharedPreferences!!.getString(
                        MainActivity.MAIN_PLAYER,
                        SelectActivity.NOT_YET
                  )
                  //MAIN_PLAYER_ID = sharedPreferences.getString(MAIN_USER_ID_KEY, "ID_X");
                  oldPhone = sharedPreferences!!.getString(SelectActivity.PHONE_KEY, "0500000000")
                  oldEmail = sharedPreferences!!.getString(SelectActivity.EMAIL_KEY, "ex@ex.com")
                  ImageName = sharedPreferences!!.getString(
                        SelectActivity.IMAGE_KEY,
                        SelectActivity.IMAGE_NotYet
                  )
                  phone!!.setText(oldPhone)
                  newname!!.setText(MainName)
                  Email!!.setText(oldEmail)
            }

      @Deprecated("Deprecated in Java")
//      override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//            super.onActivityResult(requestCode, resultCode, data)
//            if (requestCode == PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
//                  imageUri = getPickImageResultUri(this, data)
//                  imageView!!.setImageURI(imageUri)
//                  if (isReadExternalStoragePermissionsRequired(this, imageUri)) {
//                        // uri = imageuri;
//                        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 0)
//                  } else StartCropping(imageUri)
//            }
//            if (requestCode == CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//                  val result: ActivityResult = getActivityResult(data)
//                  if (resultCode == RESULT_OK) {
//                        uri = result.getUri()
//                        imageView!!.setImageURI(uri)
//                        Snackbar.make(
//                              findViewById(R.id.personemail),
//                              "Image Update Done",
//                              Snackbar.LENGTH_LONG
//                        ).show()
//                        uploadimage()
//                  }
//            }
//            if (requestCode == AUTHUI_REQUEST_CODE) {
//                  if (resultCode == RESULT_OK) {
//                        val user = FirebaseAuth.getInstance().currentUser
//                        if (user!!.metadata!!.creationTimestamp == user.metadata!!.lastSignInTimestamp) {
//                              val text = getString(R.string.welcomback)
//                              Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
//                              Email!!.setText(user.email)
//                              newname!!.setText(user.displayName)
//                        } else {
//                              val text = getString(R.string.welcome)
//                              Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
//                        }
//                  } else {
//                        // Loging Failed
//                        val response = IdpResponse.fromResultIntent(data)
//                        if (response == null) {
//                              Log.d(
//                                    TAG,
//                                    "onActivityResult: the user has cancelled the sign in request"
//                              )
//                        } else {
//                              Log.d(TAG, "onActivityResult: ", response.error)
//                        }
//                  }
//            }
//      }
//
//      private fun StartCropping(imageUri: Uri?) {
//            activity(imageUri)
//                  .setGuidelines(Guidelines.ON)
//                  .setMultiTouchEnabled(true)
//                  .setMinCropResultSize(250, 320)
//                  .setMaxCropResultSize(500, 640)
//                  .start(this)
//      }

      private fun uploadimage() {
            intent1 = Intent()
            val pd = ProgressDialog(this)
            pd.setTitle("Uploading....")
            pd.show()
            //ImageName = UUID.randomUUID().toString()+".jpeg";
            if (ImageName === SelectActivity.IMAGE_NotYet) {
                  ImageName = UUID.randomUUID().toString() + ".jpeg"
            } else if (ImageName == null) ImageName = UUID.randomUUID().toString() + ".jpeg"
            val riversRef = storageReference!!.child(SelectActivity.PrfileImageChiled).child(
                  ImageName!!
            )
            riversRef.putFile(uri!!)
                  .addOnSuccessListener {
                        pd.dismiss()
                        Snackbar.make(
                              findViewById(R.id.personemail),
                              "Image Uploading Done",
                              Snackbar.LENGTH_LONG
                        ).show()
                        intent1!!.putExtra(SelectActivity.IMAGE_KEY, ImageName)
                  }
                  .addOnFailureListener { // Handle unsuccessful uploads
                        // ...
                        pd.dismiss()
                        Toast.makeText(
                              applicationContext,
                              "Uploading Image failed",
                              Toast.LENGTH_LONG
                        ).show()
                  }
                  .addOnProgressListener { snapshot ->
                        val progress = 100.00 * snapshot.bytesTransferred / snapshot.totalByteCount
                        pd.setMessage("Percentage: " + progress.toInt() + "%")
                  }
      }

      companion object {
            private const val TAG = "person"
            private var ImageName: String? = null
            private var MainName: String? = null
            private val MainId: String? = null
            private var oldPhone: String? = null
            private var oldEmail: String? = null
      }
}