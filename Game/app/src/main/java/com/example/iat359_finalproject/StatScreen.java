package com.example.iat359_finalproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StatScreen extends AppCompatActivity implements View.OnClickListener {
    // Show current character when entered
    TextView hpTV,strTV,intTV,defTV,spdTV;
    ImageView charIV;
    Button saveButton,cancelButton,cameraButton;
    private int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};

    //this bool is for flagging the select battle page to update the character
    boolean madeChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_screen);
        //Hide the action bar on top
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        hpTV=findViewById(R.id.hpTV);
        strTV=findViewById(R.id.strTV);
        intTV=findViewById(R.id.intTV);
        defTV=findViewById(R.id.defTV);
        spdTV=findViewById(R.id.spdTV);

        charIV=findViewById(R.id.characterStatIV);

        saveButton=findViewById(R.id.saveNewCharacterButton);
        cancelButton=findViewById(R.id.cancelNewCharacterButton);
        cameraButton=findViewById(R.id.cameraButton);

        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(allPermissionsGranted()){
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            resultlauncher.launch(camera_intent);
        } else{
            Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}