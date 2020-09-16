package com.raeed.tictactoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
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
import java.util.ArrayList;
import java.util.Random;
import static com.raeed.tictactoc.SelectActivity.NOT_YET;
import static com.raeed.tictactoc.gameseting.SONG_Key;
import static com.raeed.tictactoc.Game.GAME_BOARD;

public class AndroidGame extends AppCompatActivity {
    public static final String MAIN_PLAYER = "main_name";
    public static final String MAIN_USER_ID_KEY = "UsrId";
    public static final String S_PLAYER = "S_name";
    public static final String GAME_SQ_KEY = "gmaesq";
    public static final String GAME_QOUNT_KEY = "gmqounter";
    public static final String GAME_ANDROID_KEY = "gmandroid";
    public static final int PERSON_INFO_REQUEST = 1;
    public static final int O_samble_color = (R.color.image_backgroind_o);
    public static final int X_samble_color = (R.color.image_backgroind_x);
    public static String MAIN_PLAYER_V;
    public static String MAIN_PLAYER_NAME;
    public static String MAIN_PLAYER_ID;
    public static String S_PLAYER_NAME;
    public static ArrayList<HistoryArray> historyArray = new ArrayList<>();
    public static int HISTORY_INDEX;
    public static int gamesequence, GameCounter;
    Random random = new Random();
    public static boolean PLAYER1_ST, PLAYER2_ST;
    public static boolean GAME_ANDROID = true;
    Game game = new Game();
    int[] kyMap = {R.id.bu1, R.id.bu2, R.id.bu3, R.id.bu4, R.id.bu5, R.id.bu6, R.id.bu7, R.id.bu8, R.id.bu9};
    TextView player1, player2;
    Button rest;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences ssPreferences;
    SharedPreferences.Editor seditor;
    MediaPlayer mediaPlayer;
    MediaPlayer sadendsong;
    MediaPlayer happysong;
    // setting
    public static boolean issong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_game);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        String name2 = getString(R.string.player2);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        ssPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        seditor = ssPreferences.edit();
        S_PLAYER_NAME = ssPreferences.getString(S_PLAYER, name2);
        GAME_ANDROID = ssPreferences.getBoolean(GAME_ANDROID_KEY, false);
        issong = ssPreferences.getBoolean(SONG_Key, true);
        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.playtime01);
        sadendsong = MediaPlayer.create(this, R.raw.sadend01);
        happysong = MediaPlayer.create(this, R.raw.yaybaby);
        GAME_ANDROID = true;
        if (issong) playIT();
        //if (issong) mediaPlayer.start();

        isNameVailable();
        rest = findViewById(R.id.reset);
        rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetgame();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) mediaPlayer.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopIT();
    }

    public void playIT() {
        if (mediaPlayer != null)
            mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.playtime01);
        mediaPlayer.start();
    }

    public void playhappy() {
        if (happysong != null) happysong = MediaPlayer.create(getBaseContext(), R.raw.yaybaby);
        happysong.start();
        Toast.makeText(getBaseContext(), "Happy winner!!", Toast.LENGTH_LONG).show();
        // TODO Arabic version and player name to be use in this toast
    }

    public void playsadend() {

        if (sadendsong != null) sadendsong = MediaPlayer.create(getBaseContext(), R.raw.sadend01);
        sadendsong.start();
        Toast.makeText(getBaseContext(), "play", Toast.LENGTH_LONG).show();
        // TODO Arabic version and player name to be use in this toast
    }

    public void stopIT() {
        if (mediaPlayer != null) mediaPlayer.release();
        mediaPlayer = null;
    }

    private void isNameVailable() {
        MAIN_PLAYER_NAME = sharedPreferences.getString(MAIN_PLAYER, NOT_YET);
        MAIN_PLAYER_ID = sharedPreferences.getString(MAIN_USER_ID_KEY, "ID_X");
        player1.setText(MAIN_PLAYER_NAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK & requestCode == PERSON_INFO_REQUEST) {
            MAIN_PLAYER_NAME = data.getStringExtra(MAIN_PLAYER);
            MAIN_PLAYER_ID = data.getStringExtra(MAIN_USER_ID_KEY);
            player1.setText(MAIN_PLAYER_NAME);
            editor.putString(MAIN_PLAYER, MAIN_PLAYER_NAME);
            editor.putString(MAIN_USER_ID_KEY, MAIN_PLAYER_ID);
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
            case R.id.Info_page:
                Intent InfoIntent=new Intent(getBaseContext(),UserInfoPage.class);
                startActivity(InfoIntent);
                return true;
            case R.id.bakgnd:
                Toast toast2 = Toast.makeText(getBaseContext(), "Background change option coming soon", Toast.LENGTH_LONG);
                toast2.show();
                //backgroundChanger();
                return true;
            case R.id.historygm:
                Intent intentHist = new Intent(getBaseContext(), gamehistory.class);
                //startActivity(intentHist);
                Toast.makeText(this, "Coming soon", Toast.LENGTH_LONG).show();
                return true;
            case R.id.info:
                Intent infoperson = new Intent(getBaseContext(), person.class);
                startActivityForResult(infoperson, PERSON_INFO_REQUEST);
                return true;
            case R.id.androidgm:
                Toast toast4 = Toast.makeText(getBaseContext(), getText(R.string.playwithAndroid), Toast.LENGTH_LONG);
                toast4.show();
                return true;
            case R.id.humangm:
                Intent HumanGM = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(HumanGM);
                return true;
            case R.id.settingactivity:
                Intent intentset = new Intent(getBaseContext(), gameseting.class);
                startActivity(intentset);
                return true;
            case R.id.help:
                Intent intenthlp = new Intent(getBaseContext(), helpp.class);
                startActivity(intenthlp);
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
        gamesequence = sharedPreferences.getInt(GAME_SQ_KEY, 0);
        int SIMPLE_X_OR_O_color;
        int playersimple = 0;
        GameCounter++;
        gamesequence = 1;
        SIMPLE_X_OR_O_color = O_samble_color;
        playersimple = (R.drawable.o);

        if (GameCounter < 10) {
            int but = view.getId();
            ImageButton presdBut = findViewById(but);
            presdBut.setBackgroundColor(getResources().getColor(SIMPLE_X_OR_O_color, getBaseContext().getTheme()));
            presdBut.setImageResource(playersimple);
            presdBut.setClickable(false);
            // users buttons click respond
            game.setbutoon(but,gamesequence,getBaseContext());

            // find winner
            // find the Winner if referee feed back 1 : player 1 win and winner(1); method run;
            //             but if referee feed back 2 : player 2 win and winner(2); method run;
            //             else nothing going to be happen.

            int ref= game.referee(GameCounter);
            switch (ref) {
                case 1:
                    GameCounter = 10;
                    winner(1);
                    break;
                case 2:
                    GameCounter = 10;
                    winner(2);
                    break;
            }


        }
        editor.putInt(GAME_SQ_KEY, gamesequence);
        editor.putInt(GAME_QOUNT_KEY, GameCounter);
        editor.apply();
        if (GAME_ANDROID && GameCounter < 10) androidply();
    }

    // game reset
    private void resetgame() {
        String sX[] = {"s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8"};
        int x = 0;
        do {
            for (int i = 0; i < 9; i++) GAME_BOARD[x][i] = false;
            x++;
        } while (x < 2);
        gamesequence = 1;
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
        mediaPlayer.release();
        if (issong) playIT();
    }

    // winner announcement
    private void winner(int playerId) {
        String p1 = getText(R.string.player1win).toString() + " " + MAIN_PLAYER_NAME;
        String p2 = getText(R.string.player2win).toString() + " " + S_PLAYER_NAME;
        if (playerId == 1) {
            playhappy();
            Toast.makeText(getBaseContext(), p1, Toast.LENGTH_LONG).show();
        } else {
            playsadend();
            Toast.makeText(getBaseContext(), p2, Toast.LENGTH_LONG).show();
        }
        HistoryArray neitem = new HistoryArray(MAIN_PLAYER_NAME, S_PLAYER_NAME, playerId);
        historyArray.add(HISTORY_INDEX, neitem);
        HISTORY_INDEX++;
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
            GAME_BOARD[theXO][i] = satatus;
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

    private void androidply() {
        ImageButton androidbut;
        int but = butnum();
        GameCounter++;
        gamesequence = 0;
        androidbut = findViewById(but);
        androidbut.setBackgroundColor(getResources().getColor(R.color.image_backgroind_x, getBaseContext().getTheme()));
        androidbut.setImageResource(R.drawable.x);
        androidbut.setClickable(false);

        game.setbutoon(but,gamesequence,getBaseContext());

        // find winner
        // find the Winner if referee feed back 1 : player 1 win and winner(1); method run;
        //             but if referee feed back 2 : player 2 win and winner(2); method run;
        //             else nothing going to be happen.

        int ref= game.referee(GameCounter);
        switch (ref) {
            case 1:
                GameCounter = 10;
                winner(1);
                break;
            case 2:
                GameCounter = 10;
                winner(2);
                break;
        }

        editor.putInt(GAME_SQ_KEY, gamesequence);
        editor.putInt(GAME_QOUNT_KEY, GameCounter);
        editor.apply();
    }

    private int butnum() {
        ArrayList<Integer> fbut = new ArrayList<>();
        boolean[] newbord = new boolean[9];
        for (int i = 0; i < 9; i++) newbord[i] = GAME_BOARD[0][i] || GAME_BOARD[1][i];
        int x = 0;
        int but = 0;
        for (int i = 0; i < 9; i++) {
            if (GAME_BOARD[0][i] || GAME_BOARD[1][i]) x++;
            else {
                x++;
                fbut.add(kyMap[i]);
            }
        }
        if (!newbord[4]) {
            but = kyMap[4];
            return but;
        } else if (!newbord[0]) {
            but = kyMap[0];
            return but;
        } else if (!newbord[2]) {
            but = kyMap[2];
            return but;
        } else if (!newbord[1]) {
            but = kyMap[1];
            return but;
        } else if (!newbord[6]) {
            but = kyMap[6];
            return but;
        } else {
            int d = fbut.size();
            int h = random.nextInt(d);
            but = fbut.get(h);
        }
        return but;
    }
}
