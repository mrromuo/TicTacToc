package com.raeed.tictactoc

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.widget.RadioGroup
import android.content.SharedPreferences
import com.google.firebase.storage.FirebaseStorage
import android.os.Bundle
import com.raeed.tictactoc.R
import android.preference.PreferenceManager
import android.widget.Toast
import com.raeed.tictactoc.AndroidGame
import com.raeed.tictactoc.MainActivity
import com.raeed.tictactoc.RemoPlay
import com.raeed.tictactoc.Gameseting
import com.raeed.tictactoc.SelectActivity
import com.google.android.gms.tasks.OnCompleteListener
import android.app.Activity
import android.util.Log
import android.view.Menu
import com.raeed.tictactoc.UserInfoPage
import com.raeed.tictactoc.Gamehistory
import com.raeed.tictactoc.Helpp
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.google.firebase.database.*
import java.util.HashMap

class SelectActivity : AppCompatActivity(), FirebaseAuth.AuthStateListener {
      var lastId = 0
      var NewId = 0
      var SelectedIntent: Intent? = null
      var ok: Button? = null
      var Rg: RadioGroup? = null
      var sharedPreferences: SharedPreferences? = null
      var editor: SharedPreferences.Editor? = null

      // FireBase Storage and relTime database configurations:
      var storage = FirebaseStorage.getInstance()
      var auth = FirebaseAuth.getInstance()
      var RootRef: DatabaseReference? = null
      var MyData = FirebaseDatabase.getInstance()
      var LastID = MyData.getReference("lastId")

      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_select)

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            editor = sharedPreferences?.edit()
            readId()
            Rg = findViewById(R.id.Main_radioGroup)
            ok = findViewById(R.id.Select_Ok)
            ok?.setOnClickListener(View.OnClickListener {
                  if (SelectedIntent != null) startActivity(SelectedIntent) else Toast.makeText(
                        baseContext,
                        "No selection found",
                        Toast.LENGTH_LONG
                  ).show()
                  // TODO Arabic Version
            })
      }

      fun RadioGroubSelection(view: View) {
            val selectin = view.id
            when (selectin) {
                  R.id.androidSelected -> SelectedIntent =
                        Intent(applicationContext, AndroidGame::class.java)
                  R.id.HumanSelected -> SelectedIntent =
                        Intent(applicationContext, MainActivity::class.java)
                  R.id.RemotSelected -> SelectedIntent =
                        Intent(applicationContext, RemoPlay::class.java)
                  R.id.settingSelectted -> SelectedIntent =
                        Intent(applicationContext, Gameseting::class.java)
            }
      }

      // todo ________________>>>> Data handling  <<<<<_________________
      private fun setId() {
            RootRef = FirebaseDatabase.getInstance().reference
            NewId = lastId + 1
            MAIN_PLAYER_ID = "ID_$NewId"
            editor!!.putString(MAIN_USER_ID_KEY, MAIN_PLAYER_ID)
            editor!!.apply()
            LastID.setValue(NewId)
            val intent = Intent()
            intent.putExtra(MAIN_USER_ID_KEY, MAIN_PLAYER_ID)
            editor!!.putString(MAIN_USER_ID_KEY, MAIN_PLAYER_ID)
            editor!!.apply()
            RootRef!!.child("users").child(MAIN_PLAYER_ID!!).child(IMAGE_KEY).setValue(IMAGE_NAME)

            //myRef.child(MAIN_PLAYER_ID).setValue(MAIN_PLAYER_NAME); // this way was working but we go for more advance way using HashMap.
            RootRef!!.addListenerForSingleValueEvent(object : ValueEventListener {
                  override fun onDataChange(snapshot: DataSnapshot) {
                        val userdataMap = HashMap<String, Any?>()
                        userdataMap[MAIN_PLAYER] = MAIN_PLAYER_NAME
                        userdataMap[IMAGE_KEY] = IMAGE_NAME
                        userdataMap[PHONE_KEY] = telephone
                        userdataMap[EMAIL_KEY] = email
                        RootRef!!.child("users").child(MAIN_PLAYER_ID!!).setValue(userdataMap)
                              .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                          val NewTet = getString(R.string.accountdone)
                                          Toast.makeText(baseContext, NewTet, Toast.LENGTH_LONG)
                                                .show()
                                    } else {
                                          val NewText = getString(R.string.sorryaccountnot)
                                          Toast.makeText(baseContext, NewText, Toast.LENGTH_LONG)
                                                .show()
                                    }
                              }
                  }

                  override fun onCancelled(error: DatabaseError) {
                        val NewText = getString(R.string.sorryaccountnot)
                        Toast.makeText(baseContext, NewText, Toast.LENGTH_LONG).show()
                  }
            })
      }

      private fun update() {
            RootRef = FirebaseDatabase.getInstance().reference
            readId()
            //myRef.child(MAIN_PLAYER_ID).setValue(MAIN_PLAYER_NAME); // this way was working but we go for more advance way using HashMap.
            RootRef!!.child("users").child(MAIN_PLAYER_ID!!).child(IMAGE_KEY).setValue(IMAGE_NAME)
            RootRef!!.addListenerForSingleValueEvent(object : ValueEventListener {
                  override fun onDataChange(snapshot: DataSnapshot) {
                        val userdataMap = HashMap<String, Any?>()
                        userdataMap[MAIN_PLAYER] = MAIN_PLAYER_NAME
                        userdataMap[IMAGE_KEY] = IMAGE_NAME
                        userdataMap[PHONE_KEY] = telephone
                        userdataMap[EMAIL_KEY] = email
                        RootRef!!.child("users").child(MAIN_PLAYER_ID!!).updateChildren(userdataMap)
                              .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                          val NewTet = getString(R.string.accountdone)
                                          Toast.makeText(baseContext, NewTet, Toast.LENGTH_LONG)
                                                .show()
                                    } else {
                                          val NewText = getString(R.string.sorryaccountnot)
                                          Toast.makeText(baseContext, NewText, Toast.LENGTH_LONG)
                                                .show()
                                    }
                              }
                  }

                  override fun onCancelled(error: DatabaseError) {
                        val NewText = getString(R.string.sorryaccountnot)
                        Toast.makeText(baseContext, NewText, Toast.LENGTH_LONG).show()
                  }
            })
      }

      override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == RESULT_OK) {
                  if (requestCode == PERSON_INFO_REQUEST) {
                        MAIN_PLAYER_NAME = data!!.getStringExtra(MAIN_PLAYER)
                        IMAGE_NAME = data.getStringExtra(IMAGE_KEY)
                        telephone = data.getStringExtra(PHONE_KEY)
                        email = data.getStringExtra(EMAIL_KEY)
                        editor!!.putString(MAIN_PLAYER, MAIN_PLAYER_NAME)
                        editor!!.putString(IMAGE_KEY, IMAGE_NAME)
                        editor!!.putString(PHONE_KEY, telephone)
                        editor!!.putString(EMAIL_KEY, email)
                        editor!!.apply()
                        setId()
                  } else {
                        MAIN_PLAYER_NAME = data!!.getStringExtra(MAIN_PLAYER)
                        IMAGE_NAME = data.getStringExtra(IMAGE_KEY)
                        telephone = data.getStringExtra(PHONE_KEY)
                        email = data.getStringExtra(EMAIL_KEY)
                        editor!!.putString(MAIN_PLAYER, MAIN_PLAYER_NAME)
                        editor!!.putString(IMAGE_KEY, IMAGE_NAME)
                        editor!!.putString(PHONE_KEY, telephone)
                        editor!!.putString(EMAIL_KEY, email)
                        editor!!.apply()
                        update()
                  }
            } else Toast.makeText(baseContext, "RESULT CANCELED", Toast.LENGTH_SHORT).show()
      }

      fun readId() {
            LastID.addValueEventListener(object : ValueEventListener {
                  override fun onDataChange(snapshot: DataSnapshot) {
                        val id = snapshot.getValue(Int::class.java)!!
                        lastId = id
                        //Toast.makeText(getBaseContext(),Text,Toast.LENGTH_LONG).show();
                  }

                  override fun onCancelled(error: DatabaseError) {
                        val text = error.message
                        Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
                  }
            })
      }

      //todo _______________________8( Options Menu area )8__________________________>
      override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle item selection
            return when (item.itemId) {
                  R.id.Info_page -> {
                        val InfoIntent = Intent(baseContext, UserInfoPage::class.java)
                        startActivity(InfoIntent)
                        true
                  }
                  R.id.bakgnd -> {
                        SingMeOut()
                        true
                  }
                  R.id.historygm -> {
                        val intentHist = Intent(baseContext, Gamehistory::class.java)
                        //startActivity(intentHist);
                        Toast.makeText(this, "Coming soon", Toast.LENGTH_LONG).show()
                        true
                  }
                  R.id.info -> {
                        val infoperson = Intent(baseContext, Person::class.java)
                        startActivityForResult(infoperson, PERSON_update_REQUEST)
                        true
                  }
                  R.id.androidgm -> {
                        val AndroidGM = Intent(applicationContext, AndroidGame::class.java)
                        startActivity(AndroidGM)
                        true
                  }
                  R.id.humangm -> {
                        val HumanGM = Intent(applicationContext, RemoPlay::class.java)
                        startActivity(HumanGM)
                        true
                  }
                  R.id.settingactivity -> {
                        val intentset = Intent(baseContext, Gameseting::class.java)
                        startActivity(intentset)
                        true
                  }
                  R.id.help -> {
                        val intenthlp = Intent(baseContext, Helpp::class.java)
                        startActivity(intenthlp)
                        super.onOptionsItemSelected(item)
                  }
                  else -> super.onOptionsItemSelected(item)
            }
      }

      override fun onCreateOptionsMenu(menu: Menu): Boolean {
            val inflater = menuInflater
            inflater.inflate(R.menu.menu_main, menu)
            return true
      }

      //todo ____________________8( Login and register management area )8__________________________>
      override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
            if (FirebaseAuth.getInstance().currentUser == null) logmein()
      }

      private fun logmein() {
            val infoperson = Intent(baseContext, Person::class.java)
            startActivityForResult(infoperson, PERSON_INFO_REQUEST)
      }

      private fun SingMeOut() {
            AuthUI.getInstance().signOut(this)
                  .addOnCompleteListener { task -> Log.d(TAG, "OnComplete: " + task.result) }
      }

      override fun onStart() {
            super.onStart()
            FirebaseAuth.getInstance().addAuthStateListener(this)
      }

      override fun onStop() {
            super.onStop()
            FirebaseAuth.getInstance().removeAuthStateListener(this)
      }

      companion object {
            const val MAIN_PLAYER = "main_name"
            const val MAIN_USER_ID_KEY = "UsrId"
            const val NOT_YET = "NoName"
            const val PHONE_KEY = "TelePhone"
            const val EMAIL_KEY = "userEmail"
            const val IMAGE_KEY = "imagekey"
            const val IMAGE_NotYet = "notfound.png"
            const val PrfileImageChiled = "profileImage"
            const val PERSON_INFO_REQUEST = 1
            const val PERSON_update_REQUEST = 15
            private const val TAG = "SelectActivity"
            @JvmField
            var MAIN_PLAYER_NAME: String? = null
            var MAIN_PLAYER_ID: String? = null
            @JvmField
            var telephone: String? = null
            @JvmField
            var IMAGE_NAME: String? = null
            var email: String? = null
      }
}