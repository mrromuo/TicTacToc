package com.raeed.tictactoc;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.util.UUID;
import static com.raeed.tictactoc.MainActivity.MAIN_PLAYER;
import static com.raeed.tictactoc.SelectActivity.MAIN_USER_ID_KEY;
import static com.raeed.tictactoc.SelectActivity.NOT_YET;
import static com.raeed.tictactoc.SelectActivity.VIRSION;


public class person extends AppCompatActivity {
    ImageView imageView;
    EditText newname, phone;
    Button save,cancel;
    Intent intent;

    private static String Imagekey,MainName,MainId,oldPhone;
    //final static int REQUEST_CODE=1; //requestCode

    // FireBase Storage and relTime database configurations:
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference;
    public Uri imageUri,uri;

    // App Shared Preferences file
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int isSTSU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        imageView = findViewById(R.id.imageView);
        newname = findViewById(R.id.editPersonName);
        phone = findViewById(R.id.telphone);
        save = findViewById(R.id.submit);
        cancel = findViewById(R.id.ok);
        storageReference = storage.getReference();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        isSTSU= sharedPreferences.getInt("VR", 1);
        if (isSTSU == VIRSION) getOldinfo();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                         //if (intent.resolveActivity(getPackageManager()) != null)
//                startActivityForResult(intent,REQUEST_CODE);
                CropImage.startPickImageActivity(person.this);

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSTSU != VIRSION) setNew();else updateOld();

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

    private void updateOld()
    {
        intent=new Intent();
        String name = newname.getText().toString();
        String Tphone = phone.getText().toString();

        //int NameLinth = name.length();
        if (name !="") {
            intent.putExtra(MAIN_PLAYER,name);
            intent.putExtra("imagekey",Imagekey);
            intent.putExtra("TelePhone",Tphone);
            setResult(RESULT_OK,intent);
            finish();
        } else
            Toast.makeText(getApplicationContext(),getText(R.string.pleasename),Toast.LENGTH_LONG).show();
    }

    private void setNew()
    {
        intent=new Intent();
        String name = newname.getText().toString();
        String Tphone = phone.getText().toString();
        //int NameLinth = name.length();
        if (name !="") {
            intent.putExtra(MAIN_PLAYER,name);
            intent.putExtra("imagekey",Imagekey);
            intent.putExtra("TelePhone",Tphone);
            setResult(RESULT_OK,intent);
            finish();
        } else
            Toast.makeText(getApplicationContext(),getText(R.string.pleasename),Toast.LENGTH_LONG).show();
    }

    private void getOldinfo()
    {
        MainName = sharedPreferences.getString(MAIN_PLAYER, NOT_YET);
        MainId = sharedPreferences.getString(MAIN_USER_ID_KEY, "ID_X");
        oldPhone= sharedPreferences.getString("TelePhone", "0500000000");
        Imagekey= sharedPreferences.getString("imagekey", "0500000000");
        phone.setText(oldPhone);
        newname.setText(MainName);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK ){
            imageUri = CropImage.getPickImageResultUri(this,data);
            imageView.setImageURI(imageUri);
            if (CropImage.isReadExternalStoragePermissionsRequired(this,imageUri))
            {
                // uri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                ,0); } else StartCropping(imageUri);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                uri=result.getUri();
                imageView.setImageURI(uri);
                Snackbar.make(findViewById(R.id.personname),"Image Update Done",Snackbar.LENGTH_LONG).show();
                uploadimage();
            }
        }
    }

    private void StartCropping(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    private void uploadimage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading....");
        pd.show();
        Imagekey = UUID.randomUUID().toString()+".jpeg";
        //Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        StorageReference riversRef = storageReference.child(Imagekey);

        riversRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(R.id.personname),"Image Uploading Done",Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Uploading Image failed",Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                pd.setMessage("Percentage: " + (int) progress + "%");
            }
        });

    }


}