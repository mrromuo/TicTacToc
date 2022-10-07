package com.raeed.tictactoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import java.util.HashMap;

public class SelectActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener{
    public static final String MAIN_PLAYER = "main_name";
    public static final String MAIN_USER_ID_KEY = "UsrId";
    public static final String NOT_YET = "NoName";
    public static final String PHONE_KEY = "TelePhone";
    public static final String EMAIL_KEY = "userEmail";
    public static final String IMAGE_KEY = "imagekey";
    public static final String IMAGE_NotYet = "notfound.png";
    public static final String PrfileImageChiled = "profileImage";
    public static final int PERSON_INFO_REQUEST = 1;
    public static final int PERSON_update_REQUEST = 15;
    private static final String TAG ="SelectActivity" ;
    public static String MAIN_PLAYER_NAME;
    public static String MAIN_PLAYER_ID;
    public static String telephone;
    public static String IMAGE_NAME;
    public static String email;
    public int lastId,NewId;
    Intent SelectedIntent = null;
    Button ok;
    RadioGroup Rg;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    // FireBase Storage and relTime database configurations:
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference RootRef;
    FirebaseDatabase MyData = FirebaseDatabase.getInstance();
    DatabaseReference LastID = MyData.getReference("lastId");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        readId();
        Rg = findViewById(R.id.Main_radioGroup);
        ok = findViewById(R.id.Select_Ok);
        ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (SelectedIntent != null) startActivity(SelectedIntent);
                else
                    Toast.makeText(getBaseContext(), "No selection found", Toast.LENGTH_LONG).show();
                // TODO Arabic Version
            }
        });

    }

    public void RadioGroubSelection(View view)
    {
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

    // todo ________________>>>> Data handling  <<<<<_________________
    private void setId()
    {

        RootRef = FirebaseDatabase.getInstance().getReference();
        NewId = lastId +1;
        MAIN_PLAYER_ID = "ID_"+ NewId;
        editor.putString(MAIN_USER_ID_KEY, MAIN_PLAYER_ID);
        editor.apply();
        LastID.setValue(NewId);
        Intent intent =new Intent();
        intent.putExtra(MAIN_USER_ID_KEY,MAIN_PLAYER_ID);
        editor.putString(MAIN_USER_ID_KEY,MAIN_PLAYER_ID);
        editor.apply();
        RootRef.child("users").child(MAIN_PLAYER_ID).child(IMAGE_KEY).setValue(IMAGE_NAME);

        //myRef.child(MAIN_PLAYER_ID).setValue(MAIN_PLAYER_NAME); // this way was working but we go for more advance way using HashMap.
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String,Object> userdataMap = new HashMap<>();
                userdataMap.put(MAIN_PLAYER,MAIN_PLAYER_NAME);
                userdataMap.put(IMAGE_KEY,IMAGE_NAME);
                userdataMap.put(PHONE_KEY,telephone);
                userdataMap.put(EMAIL_KEY,email);
                RootRef.child("users").child(MAIN_PLAYER_ID).setValue(userdataMap)
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
    private void update()
    {

        RootRef = FirebaseDatabase.getInstance().getReference();
        readId();
        //myRef.child(MAIN_PLAYER_ID).setValue(MAIN_PLAYER_NAME); // this way was working but we go for more advance way using HashMap.
        RootRef.child("users").child(MAIN_PLAYER_ID).child(IMAGE_KEY).setValue(IMAGE_NAME);
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String,Object> userdataMap = new HashMap<>();
                userdataMap.put(MAIN_PLAYER,MAIN_PLAYER_NAME);
                userdataMap.put(IMAGE_KEY,IMAGE_NAME);
                userdataMap.put(PHONE_KEY,telephone);
                userdataMap.put(EMAIL_KEY,email);

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
        if (resultCode == RESULT_OK) {
            if (requestCode == PERSON_INFO_REQUEST) {
                MAIN_PLAYER_NAME = data.getStringExtra(MAIN_PLAYER);
                IMAGE_NAME = data.getStringExtra(IMAGE_KEY);
                telephone = data.getStringExtra(PHONE_KEY);
                email = data.getStringExtra(EMAIL_KEY);
                editor.putString(MAIN_PLAYER, MAIN_PLAYER_NAME);
                editor.putString(IMAGE_KEY, IMAGE_NAME);
                editor.putString(PHONE_KEY, telephone);
                editor.putString(EMAIL_KEY, email);
                editor.apply();
                setId();
            } else {
                MAIN_PLAYER_NAME = data.getStringExtra(MAIN_PLAYER);
                IMAGE_NAME = data.getStringExtra(IMAGE_KEY);
                telephone = data.getStringExtra(PHONE_KEY);
                email = data.getStringExtra(EMAIL_KEY);
                editor.putString(MAIN_PLAYER, MAIN_PLAYER_NAME);
                editor.putString(IMAGE_KEY, IMAGE_NAME);
                editor.putString(PHONE_KEY, telephone);
                editor.putString(EMAIL_KEY, email);
                editor.apply();
                update();
            }

        } else Toast.makeText(getBaseContext(), "RESULT CANCELED", Toast.LENGTH_SHORT).show();

    }

    public void readId()
    {
        LastID.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int id = snapshot.getValue(Integer.class);
                lastId = id;
                //Toast.makeText(getBaseContext(),Text,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String text = error.getMessage();
                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
            }
        });

    }

    //todo _______________________8( Options Menu area )8__________________________>
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.Info_page:
                Intent InfoIntent=new Intent(getBaseContext(),UserInfoPage.class);
                startActivity(InfoIntent);
                return true;
            case R.id.bakgnd:
                SingMeOut();
                return true;
            case R.id.historygm:
                Intent intentHist = new Intent(getBaseContext(), gamehistory.class);
                //startActivity(intentHist);
                Toast.makeText(this, "Coming soon", Toast.LENGTH_LONG).show();
                return true;
            case R.id.info:
                Intent infoperson = new Intent(getBaseContext(), person.class);
                startActivityForResult(infoperson, PERSON_update_REQUEST);
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //todo ____________________8( Login and register management area )8__________________________>

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
    {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) logmein();
    }

    private void logmein()
    {

            Intent infoperson = new Intent(getBaseContext(), person.class);
            startActivityForResult(infoperson,PERSON_INFO_REQUEST);

    }
    private void SingMeOut()
    {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG,"OnComplete: "+ task.getResult()) ;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

}