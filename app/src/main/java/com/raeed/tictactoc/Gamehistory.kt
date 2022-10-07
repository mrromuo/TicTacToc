package com.raeed.tictactoc

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.raeed.tictactoc.MyAdapter
import com.raeed.tictactoc.HistoryArray
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import com.raeed.tictactoc.R
import com.raeed.tictactoc.MainActivity

class Gamehistory : AppCompatActivity() {
      var textView: TextView? = null
      var imageView: ImageView? = null
      var listView: ListView? = null
      var adapter: MyAdapter? = null
      var test: HistoryArray? = null
      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_gamehistory)
            textView = findViewById(R.id.history_text)
            textView?.setText(getString(R.string.gamehistory))
            imageView = findViewById(R.id.history_image)
            imageView?.setImageResource(R.drawable.bk07)
            adapter = MyAdapter(this, R.id.history_list, MainActivity.historyArray)
            test = HistoryArray("Raeed", "Android", 0)
            MainActivity.historyArray.add(test!!)
            listView = findViewById(R.id.history_list)
            listView?.setAdapter(adapter)
      }
}