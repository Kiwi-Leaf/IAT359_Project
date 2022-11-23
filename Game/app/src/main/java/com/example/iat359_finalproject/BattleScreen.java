package com.example.iat359_finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class BattleScreen extends AppCompatActivity implements View.OnClickListener {
    Button attackButton,defenceButton,escapeButton,magicButton;
    ImageView enemyIV,characterIV,bgIV;
    View battleBG;
    ProgressBar enemyHPProgressBar, characterHPProgressBar;
    int stage;
    boolean isBright;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the action bar on top
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_battle_screen);

        bgIV=findViewById(R.id.battleBackgroundIV);

        if (this.getIntent().hasExtra("BATTLE")){
            stage=this.getIntent().getExtras().getInt("BATTLE");
            //and set up the stage with int number
        }
        if (this.getIntent().hasExtra("BRIGHT")){
            isBright=this.getIntent().getExtras().getBoolean("BRIGHT");
            Log.i("BATTLE_DEBUG","There is BRIGHT:" + isBright);
            if (isBright){
                bgIV.setImageResource(R.drawable.background_bright);
            }
            else{
                bgIV.setImageResource(R.drawable.background_dark);
            }
        }

        battleBG=findViewById(R.id.rect_overlay_battleBG);

        attackButton=findViewById(R.id.attackButton);
        attackButton.setOnClickListener(this);

        defenceButton=findViewById(R.id.defenceButton);
        defenceButton.setOnClickListener(this);

        escapeButton=findViewById(R.id.escapeButton);
        escapeButton.setOnClickListener(this);

        magicButton=findViewById(R.id.magicButton);
        magicButton.setOnClickListener(this);

        enemyIV=findViewById(R.id.enemyIV);
        characterIV=findViewById(R.id.characterBattleIV);

        enemyHPProgressBar=findViewById(R.id.enemyHPBar);
        characterHPProgressBar=findViewById(R.id.characterHPBar);

        UITool.setThemeColor(battleBG,UITool.THEME_TYPE_TRANS,this);
        UITool.setButtonThemeColor(attackButton,UITool.THEME_TYPE_SOLID,this);
        UITool.setButtonThemeColor(defenceButton,UITool.THEME_TYPE_SOLID,this);
        UITool.setButtonThemeColor(escapeButton,UITool.THEME_TYPE_SOLID,this);
        UITool.setButtonThemeColor(magicButton,UITool.THEME_TYPE_SOLID,this);

    }

    public void gotoSetting (View v){
        Intent setting = new Intent(this, SettingScreen.class);
        startActivity(setting);
    }
    @Override
    public void onRestart() {
        super.onRestart();
        UITool.setThemeColor(battleBG,UITool.THEME_TYPE_TRANS,this);
        UITool.setButtonThemeColor(attackButton,UITool.THEME_TYPE_SOLID,this);
        UITool.setButtonThemeColor(defenceButton,UITool.THEME_TYPE_SOLID,this);
        UITool.setButtonThemeColor(escapeButton,UITool.THEME_TYPE_SOLID,this);
        UITool.setButtonThemeColor(magicButton,UITool.THEME_TYPE_SOLID,this);
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