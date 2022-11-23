package com.example.iat359_finalproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.Switch;

public class StageSelectScreen extends AppCompatActivity implements View.OnClickListener, SensorEventListener{
    Button selectStageButton1,selectStageButton2,selectStageButton3,characterButton;
    LinearLayout characterLL;
    final int CHARACTER_REQUEST_CODE=1;

    float[] light_vals;
    public SensorManager mySensorManager;
    public Sensor myLightSensor;
    int brightness;
    boolean isBright;

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
