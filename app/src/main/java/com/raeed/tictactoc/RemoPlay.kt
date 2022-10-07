package com.raeed.tictactoc

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.raeed.tictactoc.R
import com.raeed.tictactoc.Game
import android.widget.TextView
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import com.raeed.tictactoc.RemoPlay
import com.raeed.tictactoc.Gameseting
import com.raeed.tictactoc.SelectActivity
import android.widget.Toast
import com.raeed.tictactoc.HistoryArray
import java.text.SimpleDateFormat
import java.util.*

class RemoPlay : AppCompatActivity() {
      private var myAuth: FirebaseAuth? = null
      private var UsersRef: DatabaseReference? = null
      private var PlayingInviteRef: DatabaseReference? = null
      var currentUserId: String? = null
      var kyMap = intArrayOf(
            R.id.bu1,
            R.id.bu2,
            R.id.bu3,
            R.id.bu4,
            R.id.bu5,
            R.id.bu6,
            R.id.bu7,
            R.id.bu8,
            R.id.bu9
      )
      var game = Game()
      var player1: TextView? = null
      var player2: TextView? = null
      var rest: Button? = null
      lateinit var sharedPreferences: SharedPreferences
      lateinit var editor: SharedPreferences.Editor
      lateinit var ssPreferences: SharedPreferences
      lateinit var seditor: SharedPreferences.Editor
      var mediaPlayer: MediaPlayer? = null
      var sadendsong: MediaPlayer? = null
      var happysong: MediaPlayer? = null
      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_remo_play)
            myAuth = FirebaseAuth.getInstance()
            currentUserId = myAuth!!.uid
            UsersRef = FirebaseDatabase.getInstance().reference.child("users")
            PlayingInviteRef = FirebaseDatabase.getInstance().reference.child("Invitations")
            player1 = findViewById(R.id.player1)
            player2 = findViewById(R.id.player2)
            val name2 = getString(R.string.player2)
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            editor = sharedPreferences.edit()
            ssPreferences = getSharedPreferences("settings", MODE_PRIVATE)
            seditor = ssPreferences.edit()
            S_PLAYER_NAME = ssPreferences.getString(S_PLAYER, name2)
            issong = ssPreferences.getBoolean(Gameseting.SONG_Key, true)
            mediaPlayer = MediaPlayer.create(baseContext, R.raw.playtime01)
            sadendsong = MediaPlayer.create(this, R.raw.sadend01)
            happysong = MediaPlayer.create(this, R.raw.yaybaby)
            mainPlayer
            UpdatUserStatus(UserStatesOn)
            if (issong) playIT()
      }

      private val mainPlayer: Unit
            private get() {
                  MAIN_PLAYER_NAME =
                        sharedPreferences!!.getString(MAIN_PLAYER, SelectActivity.NOT_YET)
                  MAIN_PLAYER_ID = sharedPreferences!!.getString(MAIN_USER_ID_KEY, "ID_X")
                  player1!!.text = MAIN_PLAYER_NAME
                  player2!!.text = getString(R.string.player2)
            }

      fun butclicked(view: View?) {}
      override fun onPause() {
            super.onPause()
            if (mediaPlayer != null) mediaPlayer!!.release()
      }

      override fun onDestroy() {
            UpdatUserStatus(UserStatesOff)
            super.onDestroy()
            stopIT()
      }

      fun playIT() {
            if (mediaPlayer != null) mediaPlayer = MediaPlayer.create(baseContext, R.raw.playtime01)
            mediaPlayer!!.start()
      }

      fun playhappy() {
            if (sadendsong != null) mediaPlayer!!.release()
            sadendsong = null
            if (happysong != null) happysong = MediaPlayer.create(baseContext, R.raw.yaybaby)
            happysong!!.start()
            Toast.makeText(baseContext, "Happy winner!!", Toast.LENGTH_LONG).show()
            // TODO Arabic version and player name to be use in this toast
      }

      fun playsadend() {
            if (happysong != null) mediaPlayer!!.release()
            happysong = null
            if (sadendsong != null) sadendsong = MediaPlayer.create(baseContext, R.raw.sadend01)
            sadendsong!!.start()
            Toast.makeText(baseContext, "play", Toast.LENGTH_LONG).show()
            // TODO Arabic version and player name to be use in this toast
      }

      fun stopIT() {
            if (mediaPlayer != null) mediaPlayer!!.release()
            mediaPlayer = null
      }

      fun UpdatUserStatus(Status: String?) {
            val SaveCurrentDate: String
            val SaveCurrentTime: String
            // getting current Date in specific format:
            val calendarForDate = Calendar.getInstance()
            val currentDateFormat = SimpleDateFormat("MMM dd, yyyy")
            SaveCurrentDate = currentDateFormat.format(calendarForDate.time)

            // getting current Time in specific format:
            val calendarForTime = Calendar.getInstance()
            val currentTimeFormat = SimpleDateFormat("hh:mm a")
            SaveCurrentTime = currentTimeFormat.format(calendarForTime.time)

            // Save it in the database of the user:
            val currentStateMap: MutableMap<String, String> = HashMap<String, String>()
            currentStateMap["Time"] = SaveCurrentTime
            currentStateMap["Date"] = SaveCurrentDate
            currentStateMap["Type"] = Status.toString()
            UsersRef!!.child(MAIN_PLAYER_ID!!).child("userstate").updateChildren(currentStateMap as Map<String, Any>)
      }

      companion object {
            const val MAIN_PLAYER = "main_name"
            const val MAIN_USER_ID_KEY = "UsrId"
            const val S_PLAYER = "S_name"
            const val GAME_SQ_KEY = "gmaesq"
            const val GAME_QOUNT_KEY = "gmqounter"
            const val UserStatesOn = "online"
            const val UserStatesOff = "offline"
            const val PERSON_INFO_REQUEST = 1
            const val O_samble_color = R.color.image_backgroind_o
            const val X_samble_color = R.color.image_backgroind_x
            var MAIN_PLAYER_NAME: String? = null
            var MAIN_PLAYER_ID: String? = null
            var S_PLAYER_NAME: String? = null
            var historyArray = ArrayList<HistoryArray>()
            var HISTORY_INDEX = 0
            var gamesequence = 0
            var GameCounter = 0
            var issong = false
      }
}