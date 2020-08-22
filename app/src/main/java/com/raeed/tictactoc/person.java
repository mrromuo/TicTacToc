package com.raeed.tictactoc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import static com.raeed.tictactoc.MainActivity.MAIN_PLAYER;

public class person extends AppCompatActivity {
    ImageView imageView;
    EditText newname;
    Button save;
    Button cancel;
    Intent intent;
    final static int REQUEST_CODE=1; //requestCode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        imageView = findViewById(R.id.imageView);

        newname = findViewById(R.id.editPersonName);
        save = findViewById(R.id.submit);
        cancel = findViewById(R.id.ok);
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
                String name = newname.getText().toString();
                if (name != "") {
                    intent.putExtra(MAIN_PLAYER,name);
                    setResult(RESULT_OK,intent);
                    finish();
                }
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
}