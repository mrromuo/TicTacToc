package com.raeed.tictactoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import static com.raeed.tictactoc.gameseting.SONG_Key;

public class MainActivity extends AppCompatActivity {
    public static final String MAIN_PLAYER = "main_name";
    public static final String NOT_YET = "NoName";
    public static final String GAME_SQ_KEY = "gmaesq";
    public static final String GAME_QOUNT_KEY = "gmqounter";
    public static final String GAME_ANDROID_KEY = "gmandroid";
    public static final int PERSON_INFO_REQUEST = 1;
    public static final int O_samble_color = (R.color.image_backgroind_o);
    public static final int X_samble_color = (R.color.image_backgroind_x);
    public static String MAIN_PLAYER_V;
    public static String MAIN_PLAYER_NAME;
    public static int gamesequence, GameCounter;
    public static boolean PLAYER1_ST, PLAYER2_ST, GAME_ANDROID;
    public static boolean[][] GAME_BOARD = new boolean[2][9];
    int[] kyMap = {R.id.bu1, R.id.bu2, R.id.bu3, R.id.bu4, R.id.bu5, R.id.bu6, R.id.bu7, R.id.bu8, R.id.bu9};
    TextView player1, player2;
    Button rest;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences ssPreferences;
    SoundPool soundPool;
    AudioAttributes audioAttributes;
    int song_01,sound_a,sound_b;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        ssPreferences=getSharedPreferences("settings",MODE_PRIVATE);
        boolean issong=ssPreferences.getBoolean(SONG_Key,true);
        mediaPlayer=MediaPlayer.create(MainActivity.this,R.raw.playtime01);
        //if (issong) mediaPlayer.start();

        isNameVailable();
        //play_song();
        //soundPool.play(song_01,1.0f,1.0f,1,0,1.0f);
        //soundPool.autoPause();
        playIT();
        rest = findViewById(R.id.reset);
        rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetgame();
            }
        });

    }
    public void play_song(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
             //audioAttributes =new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).build();
              audioAttributes= new AudioAttributes.Builder()
                      .setUsage(AudioAttributes.USAGE_GAME)
                      .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                      .build();
            soundPool= new SoundPool.Builder().setMaxStreams(3).setAudioAttributes(audioAttributes).build();
        }else {
            soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        }
        Toast.makeText(getBaseContext(),"Song try to play",Toast.LENGTH_LONG).show();
        song_01=soundPool.load(MainActivity.this,R.raw.playtime01,1);
        //song_01=soundPool.load(MainActivity.this,R.raw.playtime02,1);
        sound_a=soundPool.load(MainActivity.this,R.raw.sadend01,2);
        sound_b=soundPool.load(MainActivity.this,R.raw.winner01,3);

    }


    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer != null) mediaPlayer.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopIT();
    }
    public void playIT(){
        if (mediaPlayer != null) mediaPlayer = MediaPlayer.create(getBaseContext(),R.raw.playtime01);
        mediaPlayer.start();
        Toast.makeText(getBaseContext(),"STARTED",Toast.LENGTH_LONG).show();
    }
    public void stopIT(){
        if (mediaPlayer != null) mediaPlayer.release();
        mediaPlayer=null;
        Toast.makeText(getBaseContext(),"S T O P E D",Toast.LENGTH_LONG).show();
    }


    private void isNameVailable() {
        MAIN_PLAYER_V = sharedPreferences.getString(MAIN_PLAYER, NOT_YET);
        if (MAIN_PLAYER_V == NOT_YET) {
            gamesequence = 0;
            editor.putInt(GAME_SQ_KEY, gamesequence);
            editor.apply();
            Intent infoperson = new Intent(getBaseContext(), person.class);
            startActivityForResult(infoperson, PERSON_INFO_REQUEST);
        } else {
            player1.setText(MAIN_PLAYER_V);
            MAIN_PLAYER_NAME = MAIN_PLAYER_V;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK & requestCode == PERSON_INFO_REQUEST) {
            MAIN_PLAYER_NAME = data.getStringExtra(MAIN_PLAYER);
            player1.setText(MAIN_PLAYER_NAME);
            editor.putString(MAIN_PLAYER, MAIN_PLAYER_NAME);
            editor.apply();
        } else {
            if (resultCode == RESULT_CANCELED & requestCode == PERSON_INFO_REQUEST) {
                player1.setText(R.string.player1);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.newgm:
                resetgame();
                return true;
            case R.id.bakgnd:
                Toast toast2 = Toast.makeText(getBaseContext(), "Background change option coming soon", Toast.LENGTH_LONG);
                toast2.show();
                //backgroundChanger();
                return true;
            case R.id.historygm:
                Toast toast3 = Toast.makeText(getBaseContext(), "making history activity link", Toast.LENGTH_LONG);
                toast3.show();
                //todo making history activity link
                return true;
            case R.id.info:
                Intent infoperson = new Intent(getBaseContext(), person.class);
                startActivityForResult(infoperson, PERSON_INFO_REQUEST);
                return true;
            case R.id.androidgm:
                Toast toast4 = Toast.makeText(getBaseContext(), getText(R.string.playwithAndroid), Toast.LENGTH_LONG);
                toast4.show();
                GAME_ANDROID=true;
                return true;
            case R.id.humangm:
                GAME_ANDROID=false;
                Toast.makeText(getBaseContext(), getText(R.string.playwithHuman), Toast.LENGTH_LONG).show();
                return true;
            case R.id.help:
                Toast toast6 = Toast.makeText(getBaseContext(), "help activity", Toast.LENGTH_LONG);
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

    public void butclicked(View view) {
        // TODO make sprouted auto replay game with Android
        gamesequence = sharedPreferences.getInt(GAME_SQ_KEY, 0);
        int SIMPLE_X_OR_O_color;
        int playersimple = 0;
        GameCounter++;
        if (gamesequence == 0) {
            gamesequence = 1;
            SIMPLE_X_OR_O_color = O_samble_color;
            playersimple = (R.drawable.o);
        } else {
            gamesequence = 0;
            SIMPLE_X_OR_O_color = X_samble_color;
            playersimple = (R.drawable.x);
            ;
        }
        if (GameCounter < 10) {
            int but = view.getId();
            ImageButton presdBut = findViewById(but);
            presdBut.setBackgroundColor(getResources().getColor(SIMPLE_X_OR_O_color, getBaseContext().getTheme()));
            presdBut.setImageResource(playersimple);
            presdBut.setClickable(false);
            switch (but) {
                case R.id.bu1:
                    GAME_BOARD[gamesequence][0] = true;
                    editor.putBoolean("s0", true);
                    editor.putInt("xo0", gamesequence);
                    break;
                case R.id.bu2:
                    GAME_BOARD[gamesequence][1] = true;
                    editor.putBoolean("s1", true);
                    editor.putInt("xo1", gamesequence);
                    break;
                case R.id.bu3:
                    GAME_BOARD[gamesequence][2] = true;
                    editor.putBoolean("s2", true);
                    editor.putInt("xo2", gamesequence);
                    break;
                case R.id.bu4:
                    GAME_BOARD[gamesequence][3] = true;
                    editor.putBoolean("s3", true);
                    editor.putInt("xo3", gamesequence);
                    break;
                case R.id.bu5:
                    GAME_BOARD[gamesequence][4] = true;
                    editor.putBoolean("s4", true);
                    editor.putInt("xo4", gamesequence);
                    break;
                case R.id.bu6:
                    GAME_BOARD[gamesequence][5] = true;
                    editor.putBoolean("s5", true);
                    editor.putInt("xo5", gamesequence);
                    break;
                case R.id.bu7:
                    GAME_BOARD[gamesequence][6] = true;
                    editor.putBoolean("s6", true);
                    editor.putInt("xo6", gamesequence);
                    break;
                case R.id.bu8:
                    GAME_BOARD[gamesequence][7] = true;
                    editor.putBoolean("s7", true);
                    editor.putInt("xo7", gamesequence);
                    break;
                case R.id.bu9:
                    GAME_BOARD[gamesequence][8] = true;
                    editor.putBoolean("s8", true);
                    editor.putInt("xo8", gamesequence);
                    break;
            }
            // find winner
            String winner;
            int x = 0;
            int s = 0;
            int secdS[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 0, 4, 8, 0, 3, 6, 1, 4, 7, 2, 5, 8, 6, 4, 2};
            do {
                PLAYER2_ST = GAME_BOARD[0][secdS[s]] & GAME_BOARD[0][secdS[s + 1]] & GAME_BOARD[0][secdS[s + 2]];
                PLAYER1_ST = GAME_BOARD[1][secdS[s]] & GAME_BOARD[1][secdS[s + 1]] & GAME_BOARD[1][secdS[s + 2]];
                if (PLAYER1_ST) {
                    GameCounter = 10;
                    winner(1);
                    break;
                } else if (PLAYER2_ST) {
                    GameCounter = 10;
                    winner(2);
                    break;
                } else winner = "Draw";
                s += 3;
                x++;
            } while (x < 8);
        }
        editor.putInt(GAME_SQ_KEY, gamesequence);
        editor.putInt(GAME_QOUNT_KEY, GameCounter);
        editor.apply();
    }

    private void resetgame() {
        String sX[] = {"s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8"};
        int x = 0;
        do {
            for (int i = 0; i < 9; i++) GAME_BOARD[x][i] = false;
            x++;
        } while (x < 2);
        gamesequence = 0;
        GameCounter = 0;
        for (int i = 0; i < 9; i++) {
            ImageButton button = findViewById(kyMap[i]);
            button.setBackgroundColor(getResources().getColor(R.color.colorwhite, getBaseContext().getTheme()));
            button.setImageResource(0);
            button.setClickable(true);
            editor.putBoolean(sX[i], false);
        }
        editor.putInt(GAME_SQ_KEY, gamesequence);
        editor.putInt(GAME_QOUNT_KEY, GameCounter);
        editor.apply();
    }

    // winner announcement
    private void winner(int playerId) {
        String p1 = getText(R.string.player1win).toString() + " " + MAIN_PLAYER_NAME;
        String p2 = getText(R.string.player2win).toString();
        if (playerId == 1) {
            Toast.makeText(getBaseContext(), p1, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(), p2, Toast.LENGTH_LONG).show();
        }
        // TODO Make activity for winner announcement
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(GAME_SQ_KEY, gamesequence);
        outState.putInt(GAME_QOUNT_KEY, GameCounter);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        gamesequence = sharedPreferences.getInt(GAME_SQ_KEY, 0);
        GameCounter = sharedPreferences.getInt(GAME_QOUNT_KEY, 0);
        String xo[] = {"xo0", "xo1", "xo2", "xo3", "xo4", "xo5", "xo6", "xo7", "xo8"};
        String sX[] = {"s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9"};

        for (int i = 0; i < 9; i++) {
            ImageButton button = findViewById(kyMap[i]);
            boolean satatus = sharedPreferences.getBoolean(sX[i], false);
            int theXO = sharedPreferences.getInt(xo[i], 0);
            GAME_BOARD[theXO][i]=satatus;
            if (satatus) {
                if (theXO == 0) {
                    button.setBackgroundColor(getResources().getColor(R.color.image_backgroind_x, getBaseContext().getTheme()));
                    button.setImageResource(R.drawable.x);
                    button.setClickable(false);
                } else {
                    button.setBackgroundColor(getResources().getColor(R.color.image_backgroind_o, getBaseContext().getTheme()));
                    button.setImageResource(R.drawable.o);
                    button.setClickable(false);
                }
            } else {
                button.setBackgroundColor(getResources().getColor(R.color.colorwhite, getBaseContext().getTheme()));
                button.setImageResource(0);
                button.setClickable(true);
            }
        }
    }
}