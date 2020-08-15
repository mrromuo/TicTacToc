package com.raeed.tictactoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class gameseting extends AppCompatActivity {
    public boolean isSong;
    public static final String SONG_Key="songplay";
    public int backgroundImage,ox_letters;
    public static final String SELECTED_B_IMAGE_Key="backimage";
    public static final String SELECTED_XO_Key="xokey";
    SharedPreferences ssPreferences;
    SharedPreferences.Editor seditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameseting);
        ssPreferences=getSharedPreferences("settings",MODE_PRIVATE);
        seditor = ssPreferences.edit();


    }
}