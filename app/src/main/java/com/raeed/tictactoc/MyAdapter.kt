package com.raeed.tictactoc

import android.content.Context
import com.raeed.tictactoc.HistoryArray
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.raeed.tictactoc.R
import android.widget.TextView
import java.util.ArrayList

class MyAdapter(
      private val context: Context,
      resorsec: Int,
      historyArrays: ArrayList<HistoryArray>
) : BaseAdapter() {
      var information: HistoryArray? = null
      private var historyArrays = ArrayList<HistoryArray>()
      private val resorsec: Int

      init {
            this.historyArrays = historyArrays
            this.resorsec = resorsec
      }

      override fun getCount(): Int {
            return historyArrays.size
      }

      override fun getItem(position: Int): Any {
            information = historyArrays[position]
            return information as HistoryArray
      }

      override fun getItemId(position: Int): Long {
            return 0
      }

      override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
            var view: View
            view = convertView
            if (view == null) {
                  view = LayoutInflater.from(context).inflate(R.layout.listviewlayout, null, false)
            }
            val player_A_Image = view.findViewById<ImageView>(R.id.Aimage)
            val player_B_Image = view.findViewById<ImageView>(R.id.Bimage)
            val aVsb = view.findViewById<TextView>(R.id.AvsB)
            val serialnumber = view.findViewById<TextView>(R.id.number)
            val player_A_ruselt = view.findViewById<TextView>(R.id.Atext)
            val player_B_ruselt = view.findViewById<TextView>(R.id.Btext)
            player_A_Image.setImageResource(R.drawable.test)
            player_B_Image.setImageResource(R.drawable.test)
            val player_A = information!!.player_A // Todo حل مشكلة null object refernce
            val player_B = information!!.player_B
            aVsb.text = "$player_A Vs $player_B"
            serialnumber.text = position.toString() + ""
            val id = information!!.winer_Id
            if (id == 0) {
                  player_A_ruselt.text = "winer"
                  player_B_ruselt.text = "Looser"
            } else {
                  player_A_ruselt.text = "Looser"
                  player_B_ruselt.text = "winer"
            }
            return view
      }
}