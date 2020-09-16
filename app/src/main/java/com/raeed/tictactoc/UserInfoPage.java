package com.raeed.tictactoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.raeed.tictactoc.SelectActivity.MAIN_PLAYER;
import static com.raeed.tictactoc.SelectActivity.MAIN_PLAYER_NAME;
import static com.raeed.tictactoc.SelectActivity.NOT_YET;
import static com.raeed.tictactoc.SelectActivity.telephone;


public class UserInfoPage extends AppCompatActivity {
    TextView name,phone;
    Button button_ok;
    ImageView image;
    Uri imageUri;
    Uri uri;
    FileDownloadTask FDT;

    SharedPreferences sharedPreferences;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private String Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_page);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        MAIN_PLAYER_NAME = sharedPreferences.getString(MAIN_PLAYER, NOT_YET);
        telephone = sharedPreferences.getString("TelePhone", "0500000000");
        Image= sharedPreferences.getString("imagekey", "8f5eb8ce-1374-4cab-8722-4ccbdb54d6d9");
        MAIN_PLAYER_NAME = sharedPreferences.getString(MAIN_PLAYER, NOT_YET);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        StorageReference riversRef = storageReference.child(Image);
        //String urlImage = storageReference.child("images/"+ Image).getPath();
        //String urlImage = "https://firebasestorage.googleapis.com/v0/b/tictactoc-8bbfd.appspot.com/o/images%2Fe3bc1266-627c-4fa9-83bf-eeed66f47896?alt=media&token=7fb3612b-242d-4074-a683-73c1efd3e3ff";
        name=findViewById(R.id.info_personname);
        name.setText(MAIN_PLAYER_NAME);
        phone=findViewById(R.id.info_telphone);
        phone.setText(telephone);
        image = findViewById(R.id.info_image);
        button_ok = findViewById(R.id.info_Ok);
        //Glide.with(UserInfoPage.this).load(urlImage).into(image);
        riversRef.getFile(uri);
        image.setImageURI(uri);
    }

    public void infodone(View view) {
        finish();
    }


}