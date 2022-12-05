package com.example.iat359_finalproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;

public class StageSelectScreen extends AppCompatActivity implements View.OnClickListener, SensorEventListener{
    Button selectStageButton1,selectStageButton2,selectStageButton3,characterButton;
    LinearLayout characterLL;
    ImageButton characterIV;
    TextView characterNameTV, characterLevelTV;
    final int CHARACTER_REQUEST_CODE=1;

    PlayerDatabase pdb;
    PlayerHelper pHelper;

    float[] light_vals;
    public SensorManager mySensorManager;
    public Sensor myLightSensor;
    int brightness;
    boolean isBright;
    String currentPath, currentName, currentLevel;
    File imgFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the action bar on top
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_stage_select_screen);
        selectStageButton1=findViewById(R.id.stage1Button);
        selectStageButton2=findViewById(R.id.stage2Button);
        selectStageButton3=findViewById(R.id.stage3Button);
        characterButton=findViewById(R.id.characterButton);
        characterLL=findViewById(R.id.stageSelectSecondaryLL);

        characterNameTV = findViewById(R.id.characterNameTV);
        characterLevelTV = findViewById(R.id.characterLevelTV);

        pdb = new PlayerDatabase(this);
        pHelper = new PlayerHelper(this);

        //setting the image of the stage select to the shared pref of current ID
        characterIV=findViewById(R.id.characterIV);
        SharedPreferences sharedPrefs = getSharedPreferences("CaptureFightData", Context.MODE_PRIVATE);
        long currentID = sharedPrefs.getLong("currentID",0);
        String userInputType = String.valueOf(currentID);
//        Toast.makeText(this, "selection:"+ userInputType, Toast.LENGTH_SHORT).show();
        Cursor queryResults = pdb.getSelectedData(userInputType);

        int index1 = queryResults.getColumnIndex(PlayerConstants.FILE_PATH);
        int index2 = queryResults.getColumnIndex(PlayerConstants.NAME);
        int index3 = queryResults.getColumnIndex(PlayerConstants.LEVEL);

        queryResults.moveToFirst();
        while (!queryResults.isAfterLast()) {

            //            Toast.makeText(this,"index",Toast.LENGTH_LONG).show();
            currentPath = queryResults.getString(index1);
            currentName = queryResults.getString(index2);
            currentLevel = queryResults.getString(index3);
            queryResults.moveToNext();
        }
        //https://stackoverflow.com/questions/4181774/show-image-view-from-file-path
        if(currentPath!=null) {
            imgFile = new File(currentPath);

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                System.out.println("File Path = " + currentPath +" "+ index2 +" " + index2);

                characterIV.setImageBitmap(myBitmap);

                characterNameTV.setText(currentName);
                characterLevelTV.setText(currentLevel);
            }
        }

        UITool.setButtonThemeColor(selectStageButton1,UITool.THEME_TYPE_SOLID,this);
        UITool.setButtonThemeColor(selectStageButton2,UITool.THEME_TYPE_SOLID,this);
        UITool.setButtonThemeColor(selectStageButton3,UITool.THEME_TYPE_SOLID,this);
        UITool.setThemeColor(characterLL,UITool.THEME_TYPE_TRANS,this);

        selectStageButton1.setOnClickListener(this);
        selectStageButton2.setOnClickListener(this);
        selectStageButton3.setOnClickListener(this);
        characterButton.setOnClickListener(this);

        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        myLightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
//        if(myLightSensor != null) {
//            mySensorManager.registerListener(this, myLightSensor, mySensorManager.SENSOR_DELAY_NORMAL);
//        }

    }


    @Override
    public void onRestart() {
        super.onRestart();
        UITool.setButtonThemeColor(selectStageButton1,UITool.THEME_TYPE_SOLID,this);
        UITool.setButtonThemeColor(selectStageButton2,UITool.THEME_TYPE_SOLID,this);
        UITool.setButtonThemeColor(selectStageButton3,UITool.THEME_TYPE_SOLID,this);
        UITool.setThemeColor(characterLL,UITool.THEME_TYPE_TRANS,this);
    }
    
    @Override
    public void onResume(){
        super.onResume();
        if(myLightSensor != null) {
            mySensorManager.registerListener(this, myLightSensor, mySensorManager.SENSOR_DELAY_NORMAL);
        }

    }
    @Override
    public void onPause(){
        super.onPause();
        mySensorManager.unregisterListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent ;
        switch (view.getId()){
            case (R.id.stage1Button):
                intent=new Intent(this,BattleScreen.class);
                intent.putExtra("BATTLE",1);
                intent.putExtra("BRIGHT",isBright);
                startActivity(intent);
                break;

            case (R.id.stage2Button):
                intent=new Intent(this,BattleScreen.class);
                intent.putExtra("BATTLE",2);
                intent.putExtra("BRIGHT",isBright);
                startActivity(intent);
                break;

            case (R.id.stage3Button):
                intent=new Intent(this,BattleScreen.class);
                intent.putExtra("BATTLE",3);
                intent.putExtra("BRIGHT",isBright);
                startActivity(intent);
                break;

            case (R.id.characterButton):
                intent=new Intent(this,StatScreen.class);
                characterChanged.launch(intent);
                break;
                }


    }

    ActivityResultLauncher<Intent> characterChanged= registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()== Activity.RESULT_OK){
                        Intent data=result.getData();
                        if(data.hasExtra("CHANGE_MADE")){
                            if(data.getExtras().getBoolean("CHANGE_MADE")){


                                //Update character



                            }
                        }
                    }
                }
            }

    );

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type=sensorEvent.sensor.getType();
        if (type ==Sensor.TYPE_LIGHT){

            brightness= (int) sensorEvent.values[0];
            Log.i("SELECT_DEBUG","sensor value:"+ brightness );
            if (brightness>40){
                isBright=true;
            }
            else{
                isBright=false;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
