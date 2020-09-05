package com.raeed.tictactoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import static com.raeed.tictactoc.MainActivity.MAIN_PLAYER;
import static com.raeed.tictactoc.MainActivity.MAIN_PLAYER_V;
import static com.raeed.tictactoc.MainActivity.NOT_YET;

public class person extends AppCompatActivity {
    ImageView imageView;
    EditText newname;
    Button save,cancel;
    Intent intent;
    final static int REQUEST_CODE=1; //requestCode
    boolean isNewUser;
    public int lastId,NewId;
    String NewUserId,OldUserId;
    // FireBase Storage and relTime database configurations:
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseDatabase MyData = FirebaseDatabase.getInstance();
    DatabaseReference myRef = MyData.getReference("users");
    DatabaseReference LastID = MyData.getReference("lastId");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        imageView = findViewById(R.id.imageView);
        newname = findViewById(R.id.editPersonName);
        save = findViewById(R.id.submit);
        cancel = findViewById(R.id.ok);
        isNameVailable();
        setLastID();
        NewId = lastId + 1;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                if (intent.resolveActivity(getPackageManager()) != null) startActivityForResult(intent,REQUEST_CODE);

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();
                if (isNewUser) {
                    NewUserId="ID_"+NewId;
                } else NewUserId = OldUserId;
                String name = newname.getText().toString();
                if (name != "") {
                    adduser(NewUserId,name);
                    UpDateLastUser();
                    intent.putExtra(MAIN_PLAYER,name);
                    intent.putExtra("UsrId", NewUserId);
                    setResult(RESULT_OK,intent);
                    finish();
                } else
                    Toast.makeText(getApplicationContext(),getText(R.string.pleasename),Toast.LENGTH_LONG).show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap img=(Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(img);
        } else imageView.setImageResource(R.drawable.test);
    }

    void setLastID(){
        LastID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int id = snapshot.getValue(Integer.class);
                lastId = id; //Integer.valueOf(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String text = error.getMessage();
                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();

            }
        });

    }

    void adduser(String UserId,String UserName){
        myRef.child(UserId).setValue(UserName);
    }
    void UpDateLastUser(){
        LastID.setValue(NewId);

    }

    private void isNameVailable() {
        if (MAIN_PLAYER_V == NOT_YET) isNewUser=true; else {
            isNewUser= false;
            // ToDo get the old Id;
        }}
}