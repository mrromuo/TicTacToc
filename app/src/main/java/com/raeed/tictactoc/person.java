package com.raeed.tictactoc;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.test);
        newname = findViewById(R.id.editPersonName);
        save = findViewById(R.id.submit);
        cancel = findViewById(R.id.ok);

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

}