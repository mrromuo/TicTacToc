package com.raeed.tictactoc;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

public class gameseting extends AppCompatActivity {
    public static final String SONG_Key="songplay";
    public int backgroundImage,o_letters,backgroundSong,x_letters;
    public static final String SELECTED_B_IMAGE_Key="backimage";
    public static final String SELECTED_X_Key="xkey";
    public static final String SELECTED_O_Key="okey";
    public static final String SELECTED_BG_SONG_Key="BackgroindSong";
    public boolean sw;
    SharedPreferences ssPreferences;
    SharedPreferences.Editor seditor;
    private Switch aSwitch;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameseting);
        ssPreferences=getSharedPreferences("settings",MODE_PRIVATE);
        seditor = ssPreferences.edit();
        aSwitch = (Switch) findViewById(R.id.switch1);

        oldSit();
    }

    public void aswitchd(View view) {
        sw=aSwitch.isChecked();
        MainActivity.issong = sw;
        updatsettings();
    }

    private void oldSit(){
        sw=ssPreferences.getBoolean(SONG_Key,false);
        MainActivity.issong =sw;
        aSwitch.setChecked(sw);
        o_letters=ssPreferences.getInt(SELECTED_O_Key,1);
        switch (o_letters){
            case 2:
                radioButton=findViewById(R.id.o2);
                radioButton.toggle();
                break;
            case 3:
                radioButton=findViewById(R.id.o3);
                radioButton.toggle();
                break;
            default:
                radioButton=findViewById(R.id.o1);
                radioButton.toggle();
        }
        x_letters=ssPreferences.getInt(SELECTED_X_Key,1);
        switch (x_letters){
            case 2:
                radioButton=findViewById(R.id.x2);
                radioButton.toggle();
                break;
            case 3:
                radioButton=findViewById(R.id.x3);
                radioButton.toggle();
                break;
            default:
                radioButton=findViewById(R.id.x1);
                radioButton.toggle();
        }
        backgroundSong=ssPreferences.getInt(SELECTED_BG_SONG_Key,1);
        switch (backgroundSong){
            case 2:
                radioButton=findViewById(R.id.song2);
                radioButton.toggle();
                break;
            case 3:
                radioButton=findViewById(R.id.song3);
                radioButton.toggle();
                break;
            default:
                radioButton=findViewById(R.id.song1);
                radioButton.toggle();
        }
        backgroundImage=ssPreferences.getInt(SELECTED_B_IMAGE_Key,1);
        switch (backgroundImage){
            case 2:
                radioButton=findViewById(R.id.backG2);
                radioButton.toggle();
                break;
            case 3:
                radioButton=findViewById(R.id.backG3);
                radioButton.toggle();
                break;
            default:
                radioButton=findViewById(R.id.backG1);
                radioButton.toggle();
        }
    }
    private void updatsettings(){
        seditor.putInt(SELECTED_O_Key,o_letters);
        seditor.putInt(SELECTED_X_Key,x_letters);
        seditor.putInt(SELECTED_B_IMAGE_Key,backgroundImage);
        seditor.putInt(SELECTED_BG_SONG_Key,backgroundSong);
        seditor.putBoolean(SONG_Key,sw);
        seditor.apply();
    }

    public void selc(View view) {
        int v=view.getId();
        switch (v){
            case R.id.song1: backgroundSong=1;
                break;
            case R.id.song2: backgroundSong=2;
                break;
            case R.id.song3: backgroundSong=3;
                break;
            case R.id.x1: x_letters=1;
                break;
            case R.id.x2:x_letters=2;
                break;
            case R.id.x3:x_letters=3;
                break;
            case R.id.o1: o_letters=1;
                break;
            case R.id.o2: o_letters=2;
                break;
            case R.id.o3: o_letters=3;
                break;
            case R.id.backG1: backgroundImage=1;
                break;
            case R.id.backG2: backgroundImage=2;
                break;
            case R.id.backG3: backgroundImage=3;
                break;
            default:
                Toast.makeText(getBaseContext(),"No selection found",Toast.LENGTH_LONG).show();
        }
        updatsettings();
    }
}