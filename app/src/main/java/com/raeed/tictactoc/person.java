package com.raeed.tictactoc;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static com.raeed.tictactoc.MainActivity.MAIN_PLAYER;
import static com.raeed.tictactoc.SelectActivity.EMAIL_KEY;
import static com.raeed.tictactoc.SelectActivity.IMAGE_KEY;
import static com.raeed.tictactoc.SelectActivity.IMAGE_NAME;
import static com.raeed.tictactoc.SelectActivity.IMAGE_NotYet;
import static com.raeed.tictactoc.SelectActivity.MAIN_USER_ID_KEY;
import static com.raeed.tictactoc.SelectActivity.NOT_YET;
import static com.raeed.tictactoc.SelectActivity.PHONE_KEY;
import static com.raeed.tictactoc.SelectActivity.PrfileImageChiled;
import static com.raeed.tictactoc.SelectActivity. MAIN_PLAYER_ID;


public class person extends AppCompatActivity {
    ImageView imageView;
    EditText newname, phone,Email;
    Button save,cancel;
    Intent intent;


    private static final String TAG = "person";
    int AUTHUI_REQUEST_CODE = 11476;

    private static String ImageName,MainName,MainId,oldPhone,oldEmail;
    //final static int REQUEST_CODE=1; //requestCode

    // FireBase Storage and relTime database configurations:
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://tictactoc-8bbfd.appspot.com");
    private StorageReference profRf,loadingRf,storageReference ;
    public Uri imageUri,uri;
    private Bitmap bt;
    List<AuthUI.IdpConfig> provider = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build());
    // App Shared Preferences file
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        imageView = findViewById(R.id.imageView);
        newname = findViewById(R.id.editPersonName);
        phone = findViewById(R.id.telphone);
        Email = findViewById(R.id.personemail);
        save = findViewById(R.id.submit);
        cancel = findViewById(R.id.ok);


        storageReference = storage.getReference();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());


        if (FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            //startActivity(new Intent(this,SelectActivity.class));
            getOldinfo();
            loadprofileImage();
        }
        else
        {
            Intent intent =AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(provider)   // you can add .setlogo(R.drawable.logo)  and setTosAndPrivacyUrls()
                    .build();
            startActivityForResult(intent,AUTHUI_REQUEST_CODE);
        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.startPickImageActivity(person.this);

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            setNew();

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

    private void loadprofileImage()
    {
        IMAGE_NAME= sharedPreferences.getString(IMAGE_KEY, IMAGE_NotYet);
        profRf = storage.getReference().child(PrfileImageChiled);
        loadingRf = profRf.child(IMAGE_NAME);
        loadingRf.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes)
            {
                bt = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imageView.setImageBitmap(bt);
            }
        });
    }


    private void setNew()
    {
        intent=new Intent();
        String name = newname.getText().toString();
        String Tphone = phone.getText().toString();
        String email = Email.getText().toString();
        String photo = ImageName;
        //int NameLinth = name.length();
        if (name !="") {
            intent.putExtra(MAIN_PLAYER,name);
            intent.putExtra(PHONE_KEY,Tphone);
            intent.putExtra(EMAIL_KEY,email);
            intent.putExtra(IMAGE_KEY,photo);
            setResult(RESULT_OK,intent);
            finish();
        } else
            Toast.makeText(getApplicationContext(),getText(R.string.pleasename),Toast.LENGTH_LONG).show();
    }

    private void getOldinfo()
    {
        MainName = sharedPreferences.getString(MAIN_PLAYER, NOT_YET);
        //MAIN_PLAYER_ID = sharedPreferences.getString(MAIN_USER_ID_KEY, "ID_X");
        oldPhone= sharedPreferences.getString(PHONE_KEY, "0500000000");
        oldEmail= sharedPreferences.getString(EMAIL_KEY, "ex@ex.com");
        ImageName= sharedPreferences.getString(IMAGE_KEY , IMAGE_NotYet);
        phone.setText(oldPhone);
        newname.setText(MainName);
        Email.setText(oldEmail);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
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
                Snackbar.make(findViewById(R.id.personemail),"Image Update Done",Snackbar.LENGTH_LONG).show();
                uploadimage();
            }
        }

        if(requestCode == AUTHUI_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp())
                {
                    String text = getString(R.string.welcomback);
                    Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
                    Email.setText(user.getEmail());
                    newname.setText(user.getDisplayName());


                }else
                    {
                        String text = getString(R.string.welcome);
                        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
                    }
            }
            else
                {
                // Loging Failed
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (response == null) {
                    Log.d(TAG, "onActivityResult: the user has cancelled the sign in request");
                } else
                    {
                        Log.d(TAG, "onActivityResult: ",response.getError());
                    }
                }
        }
    }

    private void StartCropping(Uri imageUri)
    {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .setMinCropResultSize(250,320)
                .setMaxCropResultSize(500,640)
                .start(this);
    }

    private void uploadimage()
    {
        intent=new Intent();
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading....");
        pd.show();
        //ImageName = UUID.randomUUID().toString()+".jpeg";
        if (ImageName == IMAGE_NotYet )
        {
            ImageName = UUID.randomUUID().toString()+".jpeg";
        }
        else if (ImageName == null) ImageName = UUID.randomUUID().toString()+".jpeg";
        

        StorageReference riversRef = storageReference.child(PrfileImageChiled).child(ImageName);

        riversRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(R.id.personemail),"Image Uploading Done",Snackbar.LENGTH_LONG).show();
                        intent.putExtra(IMAGE_KEY,ImageName);

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