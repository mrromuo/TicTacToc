package com.raeed.tictactoc


import androidx.appcompat.app.AppCompatActivity
import com.raeed.tictactoc.Game
import com.raeed.tictactoc.R
import android.widget.TextView
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.preference.PreferenceManager
import com.raeed.tictactoc.AndroidGame
import com.raeed.tictactoc.Gameseting
import android.widget.Toast
import com.raeed.tictactoc.SelectActivity
import android.content.Intent
import android.app.Activity
import com.raeed.tictactoc.UserInfoPage
import com.raeed.tictactoc.Gamehistory
import com.raeed.tictactoc.MainActivity
import com.raeed.tictactoc.Helpp
import android.view.MenuInflater
import android.widget.ImageButton
import com.raeed.tictactoc.HistoryArray
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import java.util.*

class AndroidGame : AppCompatActivity() {
      var random = Random()
      var game = Game()
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
            setContentView(R.layout.activity_android_game)
            player1 = findViewById(R.id.player1)
            player2 = findViewById(R.id.player2)
            val name2 = getString(R.string.player2)
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            editor = sharedPreferences.edit()
            ssPreferences = getSharedPreferences("settings", MODE_PRIVATE)
            seditor = ssPreferences.edit()
            S_PLAYER_NAME = ssPreferences.getString(S_PLAYER, name2)
            GAME_ANDROID = ssPreferences.getBoolean(GAME_ANDROID_KEY, false)
            issong = ssPreferences.getBoolean(Gameseting.SONG_Key, true)
            mediaPlayer = MediaPlayer.create(baseContext, R.raw.playtime01)
            sadendsong = MediaPlayer.create(this, R.raw.sadend01)
            happysong = MediaPlayer.create(this, R.raw.yaybaby)
            GAME_ANDROID = true
            if (issong) playIT()
            //if (issong) mediaPlayer.start();
            isNameVailable
            rest = findViewById(R.id.reset)
            rest?.setOnClickListener(View.OnClickListener { resetgame() })
      }

      override fun onPause() {
            super.onPause()
            if (mediaPlayer != null) mediaPlayer!!.release()
      }

      override fun onDestroy() {
            super.onDestroy()
            stopIT()
      }

      fun playIT() {
            if (mediaPlayer != null) mediaPlayer = MediaPlayer.create(baseContext, R.raw.playtime01)
            mediaPlayer!!.start()
      }

      fun playhappy() {
            if (happysong != null) happysong = MediaPlayer.create(baseContext, R.raw.yaybaby)
            happysong!!.start()
            Toast.makeText(baseContext, "Happy winner!!", Toast.LENGTH_LONG).show()
            // TODO Arabic version and player name to be use in this toast
      }

      fun playsadend() {
            if (sadendsong != null) sadendsong = MediaPlayer.create(baseContext, R.raw.sadend01)
            sadendsong!!.start()
            Toast.makeText(baseContext, "play", Toast.LENGTH_LONG).show()
            // TODO Arabic version and player name to be use in this toast
      }

      fun stopIT() {
            if (mediaPlayer != null) mediaPlayer!!.release()
            mediaPlayer = null
      }

      private val isNameVailable: Unit
            private get() {
                  MAIN_PLAYER_NAME =
                        sharedPreferences!!.getString(MAIN_PLAYER, SelectActivity.NOT_YET)
                  MAIN_PLAYER_ID = sharedPreferences!!.getString(MAIN_USER_ID_KEY, "ID_X")
                  player1!!.text = MAIN_PLAYER_NAME
            }

      override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == RESULT_OK && requestCode == PERSON_INFO_REQUEST) {
                  MAIN_PLAYER_NAME = data!!.getStringExtra(MAIN_PLAYER)
                  MAIN_PLAYER_ID = data.getStringExtra(MAIN_USER_ID_KEY)
                  player1!!.text = MAIN_PLAYER_NAME
                  editor.putString(MAIN_PLAYER, MAIN_PLAYER_NAME)
                  editor.putString(MAIN_USER_ID_KEY, MAIN_PLAYER_ID)
                  editor.apply()
            } else {
                  if (resultCode == RESULT_CANCELED && requestCode == PERSON_INFO_REQUEST) {
                        player1!!.setText(R.string.player1)
                  }
            }
      }

      override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle item selection
            return when (item.itemId) {
                  R.id.Info_page -> {
                        val InfoIntent = Intent(baseContext, UserInfoPage::class.java)
                        startActivity(InfoIntent)
                        true
                  }
                  R.id.bakgnd -> {
                        val toast2 = Toast.makeText(
                              baseContext,
                              "Background change option coming soon",
                              Toast.LENGTH_LONG
                        )
                        toast2.show()
                        //backgroundChanger();
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
                        startActivityForResult(infoperson, PERSON_INFO_REQUEST)
                        true
                  }
                  R.id.androidgm -> {
                        val toast4 = Toast.makeText(
                              baseContext,
                              getText(R.string.playwithAndroid),
                              Toast.LENGTH_LONG
                        )
                        toast4.show()
                        true
                  }
                  R.id.humangm -> {
                        val HumanGM = Intent(applicationContext, MainActivity::class.java)
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

      fun butclicked(view: View) {
            gamesequence = sharedPreferences!!.getInt(GAME_SQ_KEY, 0)
            val SIMPLE_X_OR_O_color: Int
            var playersimple = 0
            GameCounter++
            gamesequence = 1
            SIMPLE_X_OR_O_color = O_samble_color
            playersimple = R.drawable.o
            if (GameCounter < 10) {
                  val but = view.id
                  val presdBut = findViewById<ImageButton>(but)
                  presdBut.setBackgroundColor(
                        resources.getColor(
                              SIMPLE_X_OR_O_color,
                              baseContext.theme
                        )
                  )
                  presdBut.setImageResource(playersimple)
                  presdBut.isClickable = false
                  // users buttons click respond
                  game.setbutoon(but, gamesequence, baseContext)

                  // find winner
                  // find the Winner if referee feed back 1 : player 1 win and winner(1); method run;
                  //             but if referee feed back 2 : player 2 win and winner(2); method run;
                  //             else nothing going to be happen.
                  val ref = game.referee(GameCounter)
                  when (ref) {
                        1 -> {
                              GameCounter = 10
                              winner(1)
                        }
                        2 -> {
                              GameCounter = 10
                              winner(2)
                        }
                  }
            }
            editor!!.putInt(GAME_SQ_KEY, gamesequence)
            editor!!.putInt(GAME_QOUNT_KEY, GameCounter)
            editor!!.apply()
            if (GAME_ANDROID && GameCounter < 10) androidply()
      }

      // game reset
      private fun resetgame() {
            val sX = arrayOf("s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8")
            var x = 0
            do {
                  for (i in 0..8) Game.GAME_BOARD[x][i] = false
                  x++
            } while (x < 2)
            gamesequence = 1
            GameCounter = 0
            for (i in 0..8) {
                  val button = findViewById<ImageButton>(kyMap[i])
                  button.setBackgroundColor(
                        resources.getColor(
                              R.color.colorwhite,
                              baseContext.theme
                        )
                  )
                  button.setImageResource(0)
                  button.isClickable = true
                  editor!!.putBoolean(sX[i], false)
            }
            editor!!.putInt(GAME_SQ_KEY, gamesequence)
            editor!!.putInt(GAME_QOUNT_KEY, GameCounter)
            editor!!.apply()
            mediaPlayer!!.release()
            if (issong) playIT()
      }

      // winner announcement
      private fun winner(playerId: Int) {
            val p1 = getText(R.string.player1win).toString() + " " + MAIN_PLAYER_NAME
            val p2 = getText(R.string.player2win).toString() + " " + S_PLAYER_NAME
            if (playerId == 1) {
                  playhappy()
                  Toast.makeText(baseContext, p1, Toast.LENGTH_LONG).show()
            } else {
                  playsadend()
                  Toast.makeText(baseContext, p2, Toast.LENGTH_LONG).show()
            }
            val neitem = HistoryArray(MAIN_PLAYER_NAME, S_PLAYER_NAME, playerId)
            historyArray.add(HISTORY_INDEX, neitem)
            HISTORY_INDEX++
      }

      override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
            super.onSaveInstanceState(outState, outPersistentState)
            outState.putInt(GAME_SQ_KEY, gamesequence)
            outState.putInt(GAME_QOUNT_KEY, GameCounter)
      }

      override fun onRestoreInstanceState(savedInstanceState: Bundle) {
            super.onRestoreInstanceState(savedInstanceState)
            gamesequence = sharedPreferences!!.getInt(GAME_SQ_KEY, 0)
            GameCounter = sharedPreferences!!.getInt(GAME_QOUNT_KEY, 0)
            val xo = arrayOf("xo0", "xo1", "xo2", "xo3", "xo4", "xo5", "xo6", "xo7", "xo8")
            val sX = arrayOf("s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9")
            for (i in 0..8) {
                  val button = findViewById<ImageButton>(kyMap[i])
                  val satatus = sharedPreferences!!.getBoolean(sX[i], false)
                  val theXO = sharedPreferences!!.getInt(xo[i], 0)
                  Game.GAME_BOARD[theXO][i] = satatus
                  if (satatus) {
                        if (theXO == 0) {
                              button.setBackgroundColor(
                                    resources.getColor(
                                          R.color.image_backgroind_x,
                                          baseContext.theme
                                    )
                              )
                              button.setImageResource(R.drawable.x)
                              button.isClickable = false
                        } else {
                              button.setBackgroundColor(
                                    resources.getColor(
                                          R.color.image_backgroind_o,
                                          baseContext.theme
                                    )
                              )
                              button.setImageResource(R.drawable.o)
                              button.isClickable = false
                        }
                  } else {
                        button.setBackgroundColor(
                              resources.getColor(
                                    R.color.colorwhite,
                                    baseContext.theme
                              )
                        )
                        button.setImageResource(0)
                        button.isClickable = true
                  }
            }
      }

      private fun androidply() {
            val androidbut: ImageButton
            val but = butnum()
            GameCounter++
            gamesequence = 0
            androidbut = findViewById(but)
            androidbut.setBackgroundColor(
                  resources.getColor(
                        R.color.image_backgroind_x,
                        baseContext.theme
                  )
            )
            androidbut.setImageResource(R.drawable.x)
            androidbut.isClickable = false
            game.setbutoon(but, gamesequence, baseContext)

            // find winner
            // find the Winner if referee feed back 1 : player 1 win and winner(1); method run;
            //             but if referee feed back 2 : player 2 win and winner(2); method run;
            //             else nothing going to be happen.
            val ref = game.referee(GameCounter)
            when (ref) {
                  1 -> {
                        GameCounter = 10
                        winner(1)
                  }
                  2 -> {
                        GameCounter = 10
                        winner(2)
                  }
            }
            editor!!.putInt(GAME_SQ_KEY, gamesequence)
            editor!!.putInt(GAME_QOUNT_KEY, GameCounter)
            editor!!.apply()
      }

      private fun butnum(): Int {
            val fbut = ArrayList<Int>()
            val newbord = BooleanArray(9)
            for (i in 0..8) newbord[i] = Game.GAME_BOARD[0][i] || Game.GAME_BOARD[1][i]
            var x = 0
            var but = 0
            for (i in 0..8) {
                  if (Game.GAME_BOARD[0][i] || Game.GAME_BOARD[1][i]) x++ else {
                        x++
                        fbut.add(kyMap[i])
                  }
            }
            if (!newbord[4]) {
                  but = kyMap[4]
                  return but
            } else if (!newbord[0]) {
                  but = kyMap[0]
                  return but
            } else if (!newbord[2]) {
                  but = kyMap[2]
                  return but
            } else if (!newbord[1]) {
                  but = kyMap[1]
                  return but
            } else if (!newbord[6]) {
                  but = kyMap[6]
                  return but
            } else {
                  val d = fbut.size
                  val h = random.nextInt(d)
                  but = fbut[h]
            }
            return but
      }

      companion object {
            const val MAIN_PLAYER = "main_name"
            const val MAIN_USER_ID_KEY = "UsrId"
            const val S_PLAYER = "S_name"
            const val GAME_SQ_KEY = "gmaesq"
            const val GAME_QOUNT_KEY = "gmqounter"
            const val GAME_ANDROID_KEY = "gmandroid"
            const val PERSON_INFO_REQUEST = 1
            const val O_samble_color = R.color.image_backgroind_o
            const val X_samble_color = R.color.image_backgroind_x
            var MAIN_PLAYER_V: String? = null
            var MAIN_PLAYER_NAME: String? = null
            var MAIN_PLAYER_ID: String? = null
            var S_PLAYER_NAME: String? = null
            var historyArray = ArrayList<HistoryArray>()
            var HISTORY_INDEX = 0
            var gamesequence = 0
            var GameCounter = 0
            var PLAYER1_ST = false
            var PLAYER2_ST = false
            var GAME_ANDROID = true

            // setting
            var issong = false
      }
}