package com.example.iat359_finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;

public class BattleScreen extends AppCompatActivity implements View.OnClickListener {
    Button attackButton,defenceButton,escapeButton,magicButton;
    ImageView enemyIV,characterIV,bgIV;
    View battleBG;
    ProgressBar enemyHPProgressBar, characterHPProgressBar;
    long stage;
    boolean isBright;

    EnemyDatabase edb;
    PlayerDatabase pdb;
    EnemyHelper eHelper;
    PlayerHelper pHelper;
    String currentPath;
    String eCurrentPath;
    File imgFile;
    File eImgFile;


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
            stage=(long) this.getIntent().getExtras().getInt("BATTLE");
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

        edb = new EnemyDatabase(this);
        eHelper = new EnemyHelper(this);
        pdb = new PlayerDatabase(this);
        pHelper = new PlayerHelper(this);

        //setting the image of the battle to the shared pref of current ID
        SharedPreferences sharedPrefs = getSharedPreferences("CaptureFightData", Context.MODE_PRIVATE);
        long currentID = sharedPrefs.getLong("currentID",0);
        String userInputType = String.valueOf(currentID);
//        Toast.makeText(this, "selection:"+ userInputType, Toast.LENGTH_SHORT).show();
        Cursor queryResults = pdb.getSelectedData(userInputType);

        int index1 = queryResults.getColumnIndex(PlayerConstants.FILE_PATH);

        queryResults.moveToFirst();
        while (!queryResults.isAfterLast()) {

            //            Toast.makeText(this,"index",Toast.LENGTH_LONG).show();
            currentPath = queryResults.getString(index1);
            queryResults.moveToNext();
        }

        if(currentPath!=null) {
            imgFile = new File(currentPath);

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                System.out.println("File Path = " + currentPath);
                characterIV.setImageBitmap(myBitmap);

            }
        }

        String enemyInput = String.valueOf(stage);
//        Toast.makeText(this, "selection:"+ userInputType, Toast.LENGTH_SHORT).show();
        Cursor enemyQueryResults = edb.getSelectedData(enemyInput);

        int enemyIndex1 = enemyQueryResults.getColumnIndex(EnemyConstants.FILE_PATH);

        enemyQueryResults.moveToFirst();
        while (!enemyQueryResults.isAfterLast()) {

            //            Toast.makeText(this,"index",Toast.LENGTH_LONG).show();
            eCurrentPath = queryResults.getString(enemyIndex1);
            enemyQueryResults.moveToNext();
        }

        if(eCurrentPath!=null) {
            eImgFile = new File(eCurrentPath);

            if (eImgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                System.out.println("File Path = " + eCurrentPath);
                enemyIV.setImageBitmap(myBitmap);

            }
        }
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