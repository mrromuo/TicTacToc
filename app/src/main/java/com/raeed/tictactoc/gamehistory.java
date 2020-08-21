package com.raeed.tictactoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class gamehistory extends AppCompatActivity {
    TextView textView;
    ImageView imageView;
    ListView listView;
    MyAdapter adapter;
    HistoryArray test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamehistory);
        textView=findViewById(R.id.history_text);
        textView.setText(getString(R.string.gamehistory));
        imageView=findViewById(R.id.history_image);
        imageView.setImageResource(R.drawable.ic_launcher_background);
        adapter=new MyAdapter(this,R.id.history_list,MainActivity.historyArray);
        test = new HistoryArray("Raeed","Android",0);
        MainActivity.historyArray.add(test);
        listView=findViewById(R.id.history_list);
        listView.setAdapter(adapter);
    }
}