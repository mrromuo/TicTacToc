package com.raeed.tictactoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class SelectActivity extends AppCompatActivity {
    public static final String MAIN_PLAYER = "main_name";
    public static final String MAIN_USER_ID_KEY = "UsrId";
    public static final String NOT_YET = "NoName";
    public static final int PERSON_INFO_REQUEST = 1;
    public static final int NAME_INFO_REQUEST = 5;
    public static String MAIN_PLAYER_V;
    public static String MAIN_PLAYER_NAME;
    public static String MAIN_PLAYER_ID;
    public static String MAIN_PLAYER_I;
    public int lastId,NewId;
    Intent SelectedIntent = null;
    Button ok;
    RadioGroup Rg;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    // FireBase Storage and relTime database configurations:
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseDatabase MyData = FirebaseDatabase.getInstance();
    DatabaseReference myRef = MyData.getReference("users");
    DatabaseReference LastID = MyData.getReference("lastId");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        Checkstatus();
        readId();
        Rg = findViewById(R.id.Main_radioGroup);
        ok = findViewById(R.id.Select_Ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectedIntent != null) startActivity(SelectedIntent);
                else
                    Toast.makeText(getBaseContext(), "No selection found", Toast.LENGTH_LONG).show();
                // TODO Arabic Version
            }
        });

    }

    public void RadioGroubSelection(View view) {
        int selectin = view.getId();
        switch (selectin) {
            case R.id.androidSelected:
                SelectedIntent = new Intent(getApplicationContext(), AndroidGame.class);
                break;
            case R.id.HumanSelected:
                SelectedIntent = new Intent(getApplicationContext(), MainActivity.class);
                break;
            case R.id.RemotSelected:
                SelectedIntent = new Intent(getApplicationContext(), RemoPlay.class);
                break;
            case R.id.settingSelectted:
                SelectedIntent = new Intent(getApplicationContext(), gameseting.class);
                break;
        }
    }

    private void Checkstatus() {
        MAIN_PLAYER_V = sharedPreferences.getString(MAIN_PLAYER, NOT_YET);
        MAIN_PLAYER_I = sharedPreferences.getString(MAIN_USER_ID_KEY, "ID_X");
        Intent infoperson = new Intent(getBaseContext(), person.class);
        if (MAIN_PLAYER_V == NOT_YET) startActivityForResult(infoperson, PERSON_INFO_REQUEST);
        else if (MAIN_PLAYER_I == "ID_X") setId();
    }

    private void setId() {
        NewId = lastId +1;
        MAIN_PLAYER_ID = "ID_"+ NewId;
        editor.putString(MAIN_USER_ID_KEY, MAIN_PLAYER_ID);
        editor.apply();
        LastID.setValue(NewId);
        myRef.child(MAIN_PLAYER_ID).setValue(MAIN_PLAYER_NAME);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK & requestCode == PERSON_INFO_REQUEST) {
            MAIN_PLAYER_NAME = data.getStringExtra(MAIN_PLAYER);
            editor.putString(MAIN_PLAYER, MAIN_PLAYER_NAME);
            editor.apply();
        } else {
            if (resultCode == RESULT_CANCELED & requestCode == PERSON_INFO_REQUEST) {
                MAIN_PLAYER_NAME= NOT_YET;
                editor.putString(MAIN_PLAYER, NOT_YET);
                editor.apply();

            }
        }
        setId();
    }

 /*   public void setIdv(View view) {
        setId();
    }*/
    public void readId(){
        LastID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int id = snapshot.getValue(Integer.class);
                lastId = id;
                //Toast.makeText(getBaseContext(),Text,Toast.LENGTH_LONG).show();
                // todo remove this toast!!!
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String text = error.getMessage();
                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
            }
        });

    }
}