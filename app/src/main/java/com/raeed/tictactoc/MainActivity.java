package com.raeed.tictactoc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String MAIN_PLAYER ="main_name";
    public static final String NOT_YET ="NoName";
    public static final int PERSON_INFO_REQUEST = 1;
    public static String MAIN_PLAYER_V ;
    public static String MAIN_PLAYER_NAME ;
    TextView player1;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player1 = findViewById(R.id.player1);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        isNameVailable();
        
    }
    private void isNameVailable () {
        MAIN_PLAYER_V = sharedPreferences.getString(MAIN_PLAYER,NOT_YET);
        if  (MAIN_PLAYER_V == NOT_YET) {
            Intent infoperson =new Intent(getBaseContext(),person.class);
            startActivityForResult(infoperson,PERSON_INFO_REQUEST);
        } else {player1.setText(MAIN_PLAYER_V);}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK & requestCode == PERSON_INFO_REQUEST ){
            MAIN_PLAYER_NAME =data.getStringExtra(MAIN_PLAYER);
            player1.setText(MAIN_PLAYER_NAME);
        } else { if (resultCode == RESULT_CANCELED & requestCode == PERSON_INFO_REQUEST ){
            player1.setText(R.string.player1);
            }
        }
    }
}