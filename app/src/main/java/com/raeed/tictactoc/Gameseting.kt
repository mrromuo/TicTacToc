package com.raeed.tictactoc

import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import android.widget.RadioButton
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.Toast

class Gameseting : AppCompatActivity() {
      var backgroundImage = 0
      var o_letters = 0
      var backgroundSong = 0
      var x_letters = 0
      var sw = false
      lateinit var ssPreferences: SharedPreferences
      lateinit var seditor: SharedPreferences.Editor
      private var aSwitch: Switch? = null
      private var radioButton: RadioButton? = null
      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_gameseting)
            ssPreferences = getSharedPreferences("settings", MODE_PRIVATE)
            seditor = ssPreferences.edit()
            aSwitch = findViewById<View>(R.id.switch1) as Switch
            oldSit()
      }

      fun aswitchd(view: View?) {
            sw = aSwitch!!.isChecked
            MainActivity.issong = sw
            updatsettings()
      }

      private fun oldSit() {
            sw = ssPreferences!!.getBoolean(SONG_Key, false)
            MainActivity.issong = sw
            aSwitch!!.isChecked = sw
            o_letters = ssPreferences!!.getInt(SELECTED_O_Key, 1)
            when (o_letters) {
                  2 -> {
                        radioButton = findViewById(R.id.o2)
                        radioButton?.toggle()
                  }
                  3 -> {
                        radioButton = findViewById(R.id.o3)
                        radioButton?.toggle()
                  }
                  else -> {
                        radioButton = findViewById(R.id.o1)
                        radioButton?.toggle()
                  }
            }
            x_letters = ssPreferences!!.getInt(SELECTED_X_Key, 1)
            when (x_letters) {
                  2 -> {
                        radioButton = findViewById(R.id.x2)
                        radioButton?.toggle()
                  }
                  3 -> {
                        radioButton = findViewById(R.id.x3)
                        radioButton?.toggle()
                  }
                  else -> {
                        radioButton = findViewById(R.id.x1)
                        radioButton?.toggle()
                  }
            }
            backgroundSong = ssPreferences!!.getInt(SELECTED_BG_SONG_Key, 1)
            when (backgroundSong) {
                  2 -> {
                        radioButton = findViewById(R.id.song2)
                        radioButton?.toggle()
                  }
                  3 -> {
                        radioButton = findViewById(R.id.song3)
                        radioButton?.toggle()
                  }
                  else -> {
                        radioButton = findViewById(R.id.song1)
                        radioButton?.toggle()
                  }
            }
            backgroundImage = ssPreferences!!.getInt(SELECTED_B_IMAGE_Key, 1)
            when (backgroundImage) {
                  2 -> {
                        radioButton = findViewById(R.id.backG2)
                        radioButton?.toggle()
                  }
                  3 -> {
                        radioButton = findViewById(R.id.backG3)
                        radioButton?.toggle()
                  }
                  else -> {
                        radioButton = findViewById(R.id.backG1)
                        radioButton?.toggle()
                  }
            }
      }

      private fun updatsettings() {
            seditor.putInt(SELECTED_O_Key, o_letters)
            seditor.putInt(SELECTED_X_Key, x_letters)
            seditor.putInt(SELECTED_B_IMAGE_Key, backgroundImage)
            seditor.putInt(SELECTED_BG_SONG_Key, backgroundSong)
            seditor.putBoolean(SONG_Key, sw)
            seditor.apply()
      }

      fun selc(view: View) {
            val v = view.id
            when (v) {
                  R.id.song1 -> backgroundSong = 1
                  R.id.song2 -> backgroundSong = 2
                  R.id.song3 -> backgroundSong = 3
                  R.id.x1 -> x_letters = 1
                  R.id.x2 -> x_letters = 2
                  R.id.x3 -> x_letters = 3
                  R.id.o1 -> o_letters = 1
                  R.id.o2 -> o_letters = 2
                  R.id.o3 -> o_letters = 3
                  R.id.backG1 -> backgroundImage = 1
                  R.id.backG2 -> backgroundImage = 2
                  R.id.backG3 -> backgroundImage = 3
                  else -> Toast.makeText(baseContext, "No selection found", Toast.LENGTH_LONG)
                        .show()
            }
            updatsettings()
      }

      companion object {
            const val SONG_Key = "songplay"
            const val SELECTED_B_IMAGE_Key = "backimage"
            const val SELECTED_X_Key = "xkey"
            const val SELECTED_O_Key = "okey"
            const val SELECTED_BG_SONG_Key = "BackgroindSong"
      }
}