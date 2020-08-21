package com.raeed.tictactoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    HistoryArray information;
    private Context context;
    private ArrayList<HistoryArray> historyArrays = new ArrayList<>();
    private int resorsec;

    public MyAdapter(Context context, int resorsec ,ArrayList<HistoryArray> historyArrays) {
        this.context = context;
        this.historyArrays = historyArrays;
        this.resorsec=resorsec;
    }



    @Override
    public int getCount()
    {
        return historyArrays.size();
    }

    @Override
    public Object getItem(int position) {
        information= historyArrays.get(position);
        return information;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        view =convertView;
        if (view==null){
        view= LayoutInflater.from(context).inflate(R.layout.listviewlayout,null,false);}
        ImageView player_A_Image = view.findViewById(R.id.Aimage);
        ImageView player_B_Image = view.findViewById(R.id.Bimage);
        TextView aVsb=view.findViewById(R.id.AvsB);
        TextView serialnumber= view.findViewById(R.id.number);
        TextView player_A_ruselt= view.findViewById(R.id.Atext);
        TextView player_B_ruselt= view.findViewById(R.id.Btext);

        player_A_Image.setImageResource(R.drawable.test);
        player_B_Image.setImageResource(R.drawable.test);

        String player_A=information.getPlayer_A(); // Todo حل مشكلة null object refernce
        String player_B=information.getPlayer_B();

        aVsb.setText(player_A+ " Vs " + player_B);
        serialnumber.setText(position+"");
        int id=information.getWiner_Id();

        if (id==0) {
            player_A_ruselt.setText("winer");
            player_B_ruselt.setText("Looser");
            } else {
            player_A_ruselt.setText("Looser");
            player_B_ruselt.setText("winer");
        }
        return view;
    }
}
