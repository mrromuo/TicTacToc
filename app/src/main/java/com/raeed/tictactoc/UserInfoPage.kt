package com.raeed.tictactoc

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.content.SharedPreferences
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import android.graphics.Bitmap
import android.os.Bundle
import com.raeed.tictactoc.R
import android.preference.PreferenceManager
import com.raeed.tictactoc.SelectActivity
import com.google.android.gms.tasks.OnSuccessListener
import android.graphics.BitmapFactory
import android.view.View
import android.widget.Button
import android.widget.ImageView

class UserInfoPage : AppCompatActivity() {
      var name: TextView? = null
      var phone: TextView? = null
      var email: TextView? = null
      var button_ok: Button? = null
      var image: ImageView? = null
      lateinit var sharedPreferences: SharedPreferences
      private val storage = FirebaseStorage.getInstance("gs://tictactoc-8bbfd.appspot.com")
      private var profRf: StorageReference? = null
      private var storageReference: StorageReference? = null
      var bt: Bitmap? = null
      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_user_info_page)
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            /*
                **************  Name section ********************
 */SelectActivity.MAIN_PLAYER_NAME =
                  sharedPreferences.getString(SelectActivity.MAIN_PLAYER, SelectActivity.NOT_YET)
            name = findViewById(R.id.info_personname)
            name?.setText(SelectActivity.MAIN_PLAYER_NAME)

            /*
         **************  Phone section ********************
         */SelectActivity.telephone =
                  sharedPreferences.getString(SelectActivity.PHONE_KEY, "0500000000")
            phone = findViewById(R.id.info_telphone)
            phone?.setText(SelectActivity.telephone)

            /*
         **************  Email section ********************
         */email = findViewById(R.id.info_email)
            email?.setText(sharedPreferences.getString(SelectActivity.EMAIL_KEY, "Email not Found"))

            /*
         **************  Image section ********************
         */image = findViewById(R.id.info_image)
            SelectActivity.IMAGE_NAME =
                  sharedPreferences.getString(SelectActivity.IMAGE_KEY, SelectActivity.IMAGE_NotYet)
            profRf = storage.reference.child(SelectActivity.PrfileImageChiled)
            storageReference = profRf!!.child(SelectActivity.IMAGE_NAME!!)
            storageReference!!.getBytes((1024 * 1024).toLong()).addOnSuccessListener { bytes ->
                  bt = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                  image?.setImageBitmap(bt)
            }
            button_ok = findViewById(R.id.info_Ok)
      }

      fun infodone(view: View?) {
            finish()
      }
}