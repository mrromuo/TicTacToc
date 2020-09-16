package com.raeed.tictactoc;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import static com.raeed.tictactoc.SelectActivity.NOT_YET;
import static com.raeed.tictactoc.gameseting.SONG_Key;
import static com.raeed.tictactoc.Game.GAME_BOARD;

public class RemoPlay extends AppCompatActivity {
    public static final String MAIN_PLAYER = "main_name";
    public static final String MAIN_USER_ID_KEY = "UsrId";
    public static final String S_PLAYER = "S_name";
    public static final String GAME_SQ_KEY = "gmaesq";
    public static final String GAME_QOUNT_KEY = "gmqounter";
    public static final String UserStatesOn="online";
    public static final String UserStatesOff="offline";
    public static final int PERSON_INFO_REQUEST = 1;
    public static final int O_samble_color = (R.color.image_backgroind_o);
    public static final int X_samble_color = (R.color.image_backgroind_x);
    public static String MAIN_PLAYER_NAME;
    public static String MAIN_PLAYER_ID;
    public static String S_PLAYER_NAME;
    public static ArrayList<HistoryArray> historyArray = new ArrayList<>();
    public static int HISTORY_INDEX;
    public static int gamesequence, GameCounter;
    public static boolean issong;;
    private FirebaseAuth myAuth;
    private DatabaseReference UsersRef,PlayingInviteRef;
    String currentUserId;
    int[] kyMap = {R.id.bu1, R.id.bu2, R.id.bu3, R.id.bu4, R.id.bu5, R.id.bu6, R.id.bu7, R.id.bu8, R.id.bu9};
    Game game = new Game();
    TextView player1, player2;
    Button rest;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences ssPreferences;
    SharedPreferences.Editor seditor;
    MediaPlayer mediaPlayer;
    MediaPlayer sadendsong;
    MediaPlayer happysong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remo_play);
        myAuth = FirebaseAuth.getInstance();
        currentUserId = myAuth.getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");
        PlayingInviteRef = FirebaseDatabase.getInstance().getReference().child("Invitations");
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        String name2 = getString(R.string.player2);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        ssPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        seditor = ssPreferences.edit();
        S_PLAYER_NAME = ssPreferences.getString(S_PLAYER, name2);
        issong = ssPreferences.getBoolean(SONG_Key, true);
        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.playtime01);
        sadendsong = MediaPlayer.create(this, R.raw.sadend01);
        happysong = MediaPlayer.create(this, R.raw.yaybaby);
        getMainPlayer();
        UpdatUserStatus(UserStatesOn);
        if (issong) playIT();
    }
    private void getMainPlayer() {
        MAIN_PLAYER_NAME = sharedPreferences.getString(MAIN_PLAYER, NOT_YET);
        MAIN_PLAYER_ID = sharedPreferences.getString(MAIN_USER_ID_KEY, "ID_X");
        player1.setText(MAIN_PLAYER_NAME);
        player2.setText(getString(R.string.player2));
    }

    public void butclicked(View view) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) mediaPlayer.release();
    }

    @Override
    protected void onDestroy() {
        UpdatUserStatus(UserStatesOff);
        super.onDestroy();
        stopIT();
    }

    public void playIT() {
        if (mediaPlayer != null)
            mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.playtime01);
        mediaPlayer.start();
    }

    public void playhappy() {
        if (sadendsong != null) mediaPlayer.release();
        sadendsong = null;
        if (happysong != null) happysong = MediaPlayer.create(getBaseContext(), R.raw.yaybaby);
        happysong.start();
        Toast.makeText(getBaseContext(), "Happy winner!!", Toast.LENGTH_LONG).show();
        // TODO Arabic version and player name to be use in this toast
    }
    public void playsadend() {
        if (happysong != null) mediaPlayer.release();
        happysong = null;
        if (sadendsong != null) sadendsong = MediaPlayer.create(getBaseContext(), R.raw.sadend01);
        sadendsong.start();
        Toast.makeText(getBaseContext(), "play", Toast.LENGTH_LONG).show();
        // TODO Arabic version and player name to be use in this toast
    }

    public void stopIT() {
        if (mediaPlayer != null) mediaPlayer.release();
        mediaPlayer = null;
    }
    public void UpdatUserStatus(String Status){
        String SaveCurrentDate, SaveCurrentTime;
        // getting current Date in specific format:
        Calendar calendarForDate = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        SaveCurrentDate = currentDateFormat.format(calendarForDate.getTime());

        // getting current Time in specific format:
        Calendar calendarForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
        SaveCurrentTime = currentTimeFormat.format(calendarForTime.getTime());

        // Save it in the database of the user:
        Map currentStateMap = new HashMap();
        currentStateMap.put("Time",SaveCurrentTime);
        currentStateMap.put("Date",SaveCurrentDate);
        currentStateMap.put("Type",Status);
        UsersRef.child(MAIN_PLAYER_ID).child("userstate").updateChildren(currentStateMap);

    }
}