package com.example.iat359_finalproject;

import static androidx.core.graphics.ColorKt.getBlue;
import static androidx.core.graphics.ColorKt.getGreen;
import static androidx.core.graphics.ColorKt.getRed;

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

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StatScreen extends AppCompatActivity implements View.OnClickListener{
    // Show current character when entered
    TextView hpTV,strTV,intTV,defTV,lvTV,typeTV;
    EditText characterNameET;
    View characterStatRL;
    ImageView charIV;
    Button saveButton,cancelButton,cameraButton;
    String currentPhotoPath, photoPath;
    Bitmap srcImg;
    File imgFile;

    private int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};

    //this bool is for flagging the select battle page to update the character
    boolean madeChange;
    EnemyDatabase edb;
    PlayerDatabase pdb;
    EnemyHelper eHelper;
    PlayerHelper pHelper;

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
        lvTV=findViewById(R.id.lvTV);
        typeTV=findViewById(R.id.typeTV);

        characterNameET=findViewById(R.id.characterNameET);

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

        edb = new EnemyDatabase(this);
        eHelper = new EnemyHelper(this);
        pdb = new PlayerDatabase(this);
        pHelper = new PlayerHelper(this);

    }
    @Override
    public void onRestart() {
        super.onRestart();
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
                addCharacter();
                break;
            case (R.id.cancelNewCharacterButton):
                madeChange=false;
                finish();
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
public void addCharacter(){

    int hp, str, inte, def;

    int level = 1;

    String name = characterNameET.getText().toString();

    hp = 8 + (int)(Math.random() * ((14 - 8) + level));
    str = 8 + (int)(Math.random() * ((14 - 8) + level));
    inte = 8 + (int)(Math.random() * ((14 - 8) + level));
    def = 8 + (int)(Math.random() * ((14 - 8) + level));

    int scannedType = scanType();
    String type= UITool.isType(scannedType);
    Toast.makeText(this, "Scanned Type: " + type, Toast.LENGTH_SHORT).show();

    long id = pdb.insertData(name, type, level,hp,str, def, inte, 0, photoPath);
    Toast.makeText(this, "ID: " + id, Toast.LENGTH_SHORT).show();
    SharedPreferences sharedPrefs = getSharedPreferences("CaptureFightData", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPrefs.edit();
    editor.putLong("currentID", id);
    editor.putInt("currentType", scannedType);
    editor.putInt("currentHP", hp);
    editor.putInt("currentMaxHP", hp);
    editor.putInt("currentLV", level);
    editor.putInt("currentStr", str);
    editor.putInt("currentDef", def);
    editor.putInt("currentInt", inte);

    editor.commit();
    if (id < 0)
    {
        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
    }
    else
    {
//        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
    }


    hpTV.setText(String.valueOf(hp));
    strTV.setText(String.valueOf(str));
    intTV.setText(String.valueOf(inte));
    defTV.setText(String.valueOf(def));
    typeTV.setText(type);
    lvTV.setText(String.valueOf(level));

    }

    public int scanType(){
        if(srcImg == null){
            Random random= new Random();
            int typeRan= random.nextInt(4);
            return typeRan;
        }
        int width = srcImg.getWidth();
        int height = srcImg.getHeight();

        int totalRed = 0;
        int totalGreen =0;
        int totalBlue = 0;

        int avgRed = 0;
        int avgGreen = 0;
        int avgBlue = 0;

        for(int i=0; i<width; i++) {
            for(int q=0; q< height; q++) {
                int colour = srcImg.getPixel(i, q);

                int red = getRed(colour);
                int green = getGreen(colour);
                int blue = getBlue(colour);

                totalRed += red;
                totalGreen += green;
                totalBlue += blue;
            }
        }
        avgRed = totalRed/(width*height);
        avgGreen = totalGreen/(width*height);
        avgBlue = totalBlue/(width*height);


        float[] hsv = new float[3];
        Color.RGBToHSV(avgRed, avgGreen, avgBlue, hsv);
        float hue = hsv[0];
        float sat = hsv[1];
        float val = hsv[2];
        System.out.println("Hue = " + hue);
        System.out.println("Val = " + val);

        if (((hue >= 0 && hue <= 30)||(hue >= 300)) && val > 0.5){
            //red
            return 0;
        }
        else if (hue >= 170 && hue <= 300 && val > 0.2){
            //blue
            return 1;
        }
        else if ((hue >= 30 && hue <= 76 )&& val > 0.5){
            //yellow
            return 2;
        }
        else if ((hue >= 76 && hue <= 170) && val > 0.25) {
            //green
            return 3;
        }
        //brown/other
        else return 4;
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
                    srcImg = photo;

                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(getApplicationContext(), photo);

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(getRealPathFromURI(tempUri));

                    imgFile = finalFile;

                    photoPath = finalFile.getAbsolutePath();

                    System.out.println("Filepath = " + photoPath);
                }
            });
//https://stackoverflow.com/questions/15432592/get-file-path-of-image-on-android

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, timeStamp, null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
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
}