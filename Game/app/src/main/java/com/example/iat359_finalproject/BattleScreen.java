package com.example.iat359_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class BattleScreen extends AppCompatActivity implements View.OnClickListener {
    Button attackButton,defenceButton,escapeButton,magicButton;
    ImageView enemyIV,characterIV;
    ProgressBar enemyHPProgressBar, characterHPProgressBar;
    int stage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_screen);

        if (this.getIntent().hasExtra("BATTLE")){
            stage=this.getIntent().getExtras().getInt("BATTLE");
            //and set up the stage with int number
        }

        //Hide the action bar on top
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        attackButton=findViewById(R.id.attackButton);
        attackButton.setOnClickListener(this);

        defenceButton=findViewById(R.id.defenceButton);
        defenceButton.setOnClickListener(this);

        escapeButton=findViewById(R.id.escapeButton);
        escapeButton.setOnClickListener(this);

        magicButton.findViewById(R.id.magicButton);
        magicButton.setOnClickListener(this);

        enemyIV.findViewById(R.id.enemyIV);
        characterIV.findViewById(R.id.characterBattleIV);

        enemyHPProgressBar=findViewById(R.id.enemyHPBar);
        characterHPProgressBar=findViewById(R.id.characterHPBar);

    }

    public void gotoSetting (View v){
        Intent setting = new Intent(this, SettingScreen.class);
        startActivity(setting);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case    (R.id.attackButton):

                break;
            case    (R.id.defenceButton):

                break;
            case    (R.id.magicButton):

                break;
            case    (R.id.escapeButton):

                break;
        }
    }
}