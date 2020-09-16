package com.raeed.tictactoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import java.util.HashMap;

public class SelectActivity extends AppCompatActivity {
    public static final String MAIN_PLAYER = "main_name";
    public static final String MAIN_USER_ID_KEY = "UsrId";
    public static final String NOT_YET = "NoName";
    public static final int VIRSION = 2000;
    public static final int PERSON_INFO_REQUEST = 1;
    public static final int PERSON_update_REQUEST = 15;
    public static String MAIN_PLAYER_V;
    public static String MAIN_PLAYER_NAME;
    public static String MAIN_PLAYER_ID;
    public static String MAIN_PLAYER_I;
    public static String telephone;
    public static String imagekey;
    public int lastId,NewId;
    private HashMap hashMap;
    private String aname;
    private String anId;


    Intent SelectedIntent = null;
    Button ok;
    RadioGroup Rg;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    // FireBase Storage and relTime database configurations:
    FirebaseStorage storage = FirebaseStorage.getInstance();
    DatabaseReference RootRef;
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

    private void Checkstatus()
    {
        MAIN_PLAYER_V = sharedPreferences.getString(MAIN_PLAYER, NOT_YET);
        MAIN_PLAYER_I = sharedPreferences.getString(MAIN_USER_ID_KEY, "ID_X");
        int isVirsion= sharedPreferences.getInt("VR", 1);
        Intent infoperson = new Intent(getBaseContext(), person.class);
        if (isVirsion != VIRSION) startActivityForResult(infoperson, PERSON_INFO_REQUEST);
        else checkFireBase(MAIN_PLAYER_V ,MAIN_PLAYER_I);
    }
    void checkFireBase(String Name , String Id)
    {
        aname =Name;
        anId =Id;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(anId).child(aname).exists()) {
                    Intent infoperson = new Intent(getBaseContext(), person.class);
                    startActivityForResult(infoperson, PERSON_INFO_REQUEST);
                } else {
                    String text =getString(R.string.welcomback)+" "+ aname;
                    Toast.makeText(getBaseContext(),text,Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setId() {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        NewId = lastId +1;
        MAIN_PLAYER_ID = "ID_"+ NewId;
        editor.putString(MAIN_USER_ID_KEY, MAIN_PLAYER_ID);
        editor.apply();
        LastID.setValue(NewId);
        //myRef.child(MAIN_PLAYER_ID).setValue(MAIN_PLAYER_NAME); // this way was working but we go for more advance way using HashMap.
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String,Object> userdataMap = new HashMap<>();
                userdataMap.put("name",MAIN_PLAYER_NAME);
                userdataMap.put("imgekey",imagekey);
                userdataMap.put("TelePhone",telephone);
                RootRef.child("users").child(MAIN_PLAYER_ID).updateChildren(userdataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    String NewTet = getString(R.string.accountdone);
                                    Toast.makeText(getBaseContext(),NewTet,Toast.LENGTH_LONG).show();
                                } else {
                                    String NewText = getString(R.string.sorryaccountnot);
                                    Toast.makeText(getBaseContext(),NewText,Toast.LENGTH_LONG).show();}
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String NewText = getString(R.string.sorryaccountnot);
                Toast.makeText(getBaseContext(),NewText,Toast.LENGTH_LONG).show();
            }
        });
    }
    private void update() {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        //myRef.child(MAIN_PLAYER_ID).setValue(MAIN_PLAYER_NAME); // this way was working but we go for more advance way using HashMap.
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String,Object> userdataMap = new HashMap<>();
                userdataMap.put("name",MAIN_PLAYER_NAME);
                userdataMap.put("imgekey",imagekey);
                userdataMap.put("TelePhone",telephone);
                RootRef.child("users").child(MAIN_PLAYER_ID).updateChildren(userdataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    String NewTet = getString(R.string.accountdone);
                                    Toast.makeText(getBaseContext(),NewTet,Toast.LENGTH_LONG).show();
                                } else {
                                    String NewText = getString(R.string.sorryaccountnot);
                                    Toast.makeText(getBaseContext(),NewText,Toast.LENGTH_LONG).show();}
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String NewText = getString(R.string.sorryaccountnot);
                Toast.makeText(getBaseContext(),NewText,Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK & requestCode == PERSON_INFO_REQUEST) {
            MAIN_PLAYER_NAME = data.getStringExtra(MAIN_PLAYER);
            imagekey =data.getStringExtra("imagekey");
            telephone =data.getStringExtra("TelePhone");
            editor.putString(MAIN_PLAYER, MAIN_PLAYER_NAME);
            editor.putString("imagekey", imagekey);
            editor.putString("TelePhone", telephone);
            editor.putInt("VR",VIRSION);
            editor.apply();
            setId();
        }
        else if(resultCode == RESULT_OK & requestCode == PERSON_update_REQUEST)
        {
            MAIN_PLAYER_NAME = data.getStringExtra(MAIN_PLAYER);
            imagekey =data.getStringExtra("imagekey");
            telephone =data.getStringExtra("TelePhone");
            editor.putString(MAIN_PLAYER, MAIN_PLAYER_NAME);
            editor.putString("imagekey", imagekey);
            editor.putString("TelePhone", telephone);
            editor.putInt("VR",VIRSION);
            editor.apply();
            update();

        }
        else if (resultCode == RESULT_CANCELED & requestCode == PERSON_INFO_REQUEST)
        {
                MAIN_PLAYER_NAME= NOT_YET;
                editor.putString(MAIN_PLAYER, NOT_YET);
                editor.apply();
                setId();
        }
        else if (resultCode == RESULT_CANCELED & requestCode == PERSON_update_REQUEST)
        {
            Toast.makeText(getBaseContext(),"RESULT CANCELED",Toast.LENGTH_SHORT).show();
        }
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
                Intent AndroidGM = new Intent(getApplicationContext(), AndroidGame.class);
                startActivity(AndroidGM);
                return true;
            case R.id.humangm:
                Intent HumanGM = new Intent(getApplicationContext(), RemoPlay.class);
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
}