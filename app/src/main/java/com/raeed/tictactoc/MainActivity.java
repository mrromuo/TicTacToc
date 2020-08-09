package com.raeed.tictactoc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static final String MAIN_PLAYER ="main_name";
    public static final String NOT_YET ="NoName";
    public static final int PERSON_INFO_REQUEST = 1;
    public static String MAIN_PLAYER_V ;
    public static String MAIN_PLAYER_NAME ;
    TextView player1,player2;
    Button but1,but2,but3,but4,but5,but6,but7,but8,but9,rest;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        isNameVailable();
        but1 = findViewById(R.id.bu1);
        but2 = findViewById(R.id.bu2);
        but3 = findViewById(R.id.bu3);
        but4 = findViewById(R.id.bu4);
        but5 = findViewById(R.id.bu5);
        but6 = findViewById(R.id.bu6);
        but7 = findViewById(R.id.bu7);
        but8 = findViewById(R.id.bu8);
        but9 = findViewById(R.id.bu9);
        rest = findViewById(R.id.reset);

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
            editor.putString(MAIN_PLAYER,MAIN_PLAYER_NAME);
            editor.apply();
        } else { if (resultCode == RESULT_CANCELED & requestCode == PERSON_INFO_REQUEST ){
            player1.setText(R.string.player1);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.newgm:
                Toast toast1=Toast.makeText(getBaseContext(),"New game option coming soon",Toast.LENGTH_LONG);
                toast1.show();
                // Todo New game option coming soon
                //newGame();
                return true;
            case R.id.bakgnd:
                Toast toast2=Toast.makeText(getBaseContext(),"Background change option coming soon",Toast.LENGTH_LONG);
                toast2.show();
                //backgroundChanger();
                return true;
            case R.id.historygm:
                Toast toast3=Toast.makeText(getBaseContext(),"making history activity link",Toast.LENGTH_LONG);
                toast3.show();
                //todo making history activity link
                return true;
            case R.id.androidgm:
                Toast toast4=Toast.makeText(getBaseContext(),"play with android",Toast.LENGTH_LONG);
                toast4.show();
                //todo play with android class
                return true;
            case R.id.humangm:
                Toast toast5=Toast.makeText(getBaseContext(),"play with human",Toast.LENGTH_LONG);
                toast5.show();
                //Todo play with human class
                return true;
            case R.id.help:
                Toast toast6=Toast.makeText(getBaseContext(),"help activity",Toast.LENGTH_LONG);
                toast6.show();
                //Todo play with human class
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    private class humangame{
        void onClick(View view){
            switch (view.getId()){
                case R.id.bu1:
                    but1.setBackgroundColor(getResources().getColor(R.color.image_backgroind_o));
                    but1.setText("o");
                    break;
                case R.id.bu2:
                    but2.setBackgroundColor(getResources().getColor(R.color.image_backgroind_o));
                    but2.setText("o");
                    break;
            }
        }
    }
}