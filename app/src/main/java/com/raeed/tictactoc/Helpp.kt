package com.raeed.tictactoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class Helpp : AppCompatActivity() {
      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_helpp)
            val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
            setSupportActionBar(toolbar)
            val toolBarLayout = findViewById<View>(R.id.toolbar_layout) as CollapsingToolbarLayout
            toolBarLayout.title = title
            val fab = findViewById<View>(R.id.fab) as FloatingActionButton
            fab.setOnClickListener { view ->
                  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }
      }
}