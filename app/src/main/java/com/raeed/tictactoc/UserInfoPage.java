package com.raeed.tictactoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import static com.raeed.tictactoc.SelectActivity.EMAIL_KEY;
import static com.raeed.tictactoc.SelectActivity.IMAGE_KEY;
import static com.raeed.tictactoc.SelectActivity.IMAGE_NAME;
import static com.raeed.tictactoc.SelectActivity.IMAGE_NotYet;
import static com.raeed.tictactoc.SelectActivity.MAIN_PLAYER;
import static com.raeed.tictactoc.SelectActivity.MAIN_PLAYER_NAME;
import static com.raeed.tictactoc.SelectActivity.NOT_YET;
import static com.raeed.tictactoc.SelectActivity.PHONE_KEY;
import static com.raeed.tictactoc.SelectActivity.PrfileImageChiled;
import static com.raeed.tictactoc.SelectActivity.telephone;


public class UserInfoPage extends AppCompatActivity {

    TextView name,phone,email;
    Button button_ok;
    ImageView image;
    SharedPreferences sharedPreferences;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://tictactoc-8bbfd.appspot.com");
    private StorageReference profRf,storageReference ;
    Bitmap bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_page);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
/*
                **************  Name section ********************
 */
        MAIN_PLAYER_NAME = sharedPreferences.getString(MAIN_PLAYER, NOT_YET);
        name=findViewById(R.id.info_personname);
        name.setText(MAIN_PLAYER_NAME);

        /*
         **************  Phone section ********************
         */

        telephone = sharedPreferences.getString(PHONE_KEY, "0500000000");
        phone=findViewById(R.id.info_telphone);
        phone.setText(telephone);

        /*
         **************  Email section ********************
         */

        email = findViewById(R.id.info_email);
        email.setText(sharedPreferences.getString(EMAIL_KEY, "Email not Found"));

        /*
         **************  Image section ********************
         */

        image = findViewById(R.id.info_image);
        IMAGE_NAME= sharedPreferences.getString(IMAGE_KEY, IMAGE_NotYet);
        profRf = storage.getReference().child(PrfileImageChiled);
        storageReference = profRf.child(IMAGE_NAME);
        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes)
            {
                bt = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                image.setImageBitmap(bt);
            }
        });

        button_ok = findViewById(R.id.info_Ok);

    }

    public void infodone(View view) {
        finish();
    }


}