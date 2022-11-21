package com.example.iat359_finalproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatScreen extends AppCompatActivity implements View.OnClickListener{
    // Show current character when entered
    TextView hpTV,strTV,intTV,defTV,spdTV;
    View characterStatRL;
    ImageView charIV;
    Button saveButton,cancelButton,cameraButton;

    String currentPhotoPath;

    private int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};

    //this bool is for flagging the select battle page to update the character
    boolean madeChange;

    //https://developer.android.com/training/camera-deprecated/photobasics#java how to store locally

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the action bar on top
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_stat_screen);

        hpTV=findViewById(R.id.hpTV);
        strTV=findViewById(R.id.strTV);
        intTV=findViewById(R.id.intTV);
        defTV=findViewById(R.id.defTV);
        spdTV=findViewById(R.id.spdTV);

        charIV=findViewById(R.id.characterStatIV);

        saveButton=findViewById(R.id.saveNewCharacterButton);
        cancelButton=findViewById(R.id.cancelNewCharacterButton);
        cameraButton=findViewById(R.id.cameraButton);
        characterStatRL=findViewById(R.id.rect_overlay_statScreen);

        saveButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);

        UITool.setButtonThemeColor(saveButton,UITool.THEME_TYPE_SOLID,this);
        UITool.setButtonThemeColor(cancelButton,UITool.THEME_TYPE_SOLID,this);
        UITool.setButtonThemeColor(cameraButton,UITool.THEME_TYPE_SOLID,this);
        UITool.setThemeColor(characterStatRL,UITool.THEME_TYPE_TRANS,this);

    }

    //see citation above
    //https://stackoverflow.com/questions/60027883/take-photo-in-android-app-and-save-it-into-gallery
    //https://stackoverflow.com/questions/14053338/save-bitmap-in-android-as-jpeg-in-external-storage-in-a-folder
    //storing as jpg
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case (R.id.saveNewCharacterButton):
                //save this character to database

                madeChange=true;
                break;
            case (R.id.cancelNewCharacterButton):
                madeChange=false;
                break;
            case (R.id.cameraButton):
                if(allPermissionsGranted()){
                    Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    resultlauncher.launch(camera_intent);
                } else{
                    ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                }
                break;


        }

    }

    @Override
    public void finish() {
        Intent data= getIntent();
        data.putExtra("CHANGE_MADE",madeChange);
        setResult(RESULT_OK,data);
        super.finish();
    }

    ActivityResultLauncher<Intent> resultlauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                    charIV.setImageBitmap(photo);
                }
            });

    private boolean allPermissionsGranted(){

        for(String permission : REQUIRED_PERMISSIONS){

            if(ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
    //see citation above
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(allPermissionsGranted()){
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (camera_intent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;

                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    Toast.makeText(this, "No Photo File", Toast.LENGTH_SHORT).show();
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.fileprovider", photoFile);
                    camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    Toast.makeText(this, photoURI.toString(), Toast.LENGTH_LONG).show();
                    resultlauncher.launch(camera_intent);
                }
            }
        } else{
            Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //see above citation
    public void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

}