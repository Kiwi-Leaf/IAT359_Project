package com.example.iat359_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import java.util.Set;
public class MainActivity extends AppCompatActivity {
    ImageButton battleButton, hallButton, settingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the action bar on top
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        battleButton=findViewById(R.id.battleButton);
        hallButton=findViewById(R.id.hallOfFrameButton);



        setContentView(R.layout.activity_main);
    }

    public void gotoHallOfFrame(View v){
        Intent hof= new Intent(this, HallOfFrameScreen.class );
        startActivity(hof);
    }

    public void gotoBattle(View v){
        Intent battle= new Intent(this, StageSelectScreen.class);
        startActivity(battle);
    }

    public void gotoSetting (View v){
        Intent setting = new Intent(this, SettingScreen.class);
        startActivity(setting);
    }


}