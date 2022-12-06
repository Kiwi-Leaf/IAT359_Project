package com.example.iat359_finalproject;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class GameEndScreen extends AppCompatActivity {
    Button levelUpButton, heallButton ;
    View background;
    Boolean winCon;
    long currentID;
    PlayerDatabase pdb;
    PlayerHelper pHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Hide the action bar on top
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_game_end_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        levelUpButton=findViewById(R.id.levelUpButton);
        heallButton=findViewById(R.id.heallButton);
        background=findViewById(R.id.rect_overlay_gameEnd);

        UITool.setThemeColor(background,UITool.THEME_TYPE_TRANS,this);
        UITool.setButtonThemeColor(levelUpButton,UITool.THEME_TYPE_SOLID,this);
        UITool.setButtonThemeColor(heallButton,UITool.THEME_TYPE_SOLID,this);
        pdb = new PlayerDatabase(this);
        pHelper = new PlayerHelper(this);

        if (this.getIntent().hasExtra("WIN")){
            winCon=this.getIntent().getExtras().getBoolean("WIN");
            if (winCon){
                levelUpButton.setVisibility(View.VISIBLE);
            }
            else{
                levelUpButton.setVisibility(View.INVISIBLE);
            }
        }


    }
    //update query
//https://stackoverflow.com/questions/9798473/sqlite-in-android-how-to-update-a-specific-row
    public void levelUpButton(View v){
        SharedPreferences sharedPrefs = getSharedPreferences("CaptureFightData", Context.MODE_PRIVATE);

        currentID = sharedPrefs.getLong("currentID",0);
        if(currentID != 0) {

            String userInputType = String.valueOf(currentID);

            Cursor queryResults = pdb.getSelectedData(userInputType);

            int index1 = queryResults.getColumnIndex(PlayerConstants.BASE_HP);
            int index2 = queryResults.getColumnIndex(PlayerConstants.LEVEL);
            int index3 = queryResults.getColumnIndex(PlayerConstants.ATTACK);
            int index4 = queryResults.getColumnIndex(PlayerConstants.DEFENSE);
            int index5 = queryResults.getColumnIndex(PlayerConstants.INTELLIGENCE);
            int index6 = queryResults.getColumnIndex(PlayerConstants.BATTLES_WON);

            int hp=0;
            int level=0;
            int str=0;
            int def=0;
            int inte=0;
            int bats=0;

            queryResults.moveToFirst();
            while (!queryResults.isAfterLast()) {
                hp = queryResults.getInt(index1) +1;
                level = queryResults.getInt(index2) +1;
                str = queryResults.getInt(index3) +1;
                def = queryResults.getInt(index4) +1;
                inte = queryResults.getInt(index5) +1;
                bats = queryResults.getInt(index6) +1;

                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putInt("currentHP", hp);
                editor.putInt("currentMaxHP", hp);
                editor.putInt("currentLV", level);
                editor.putInt("currentStr", str);
                editor.putInt("currentDef", def);
                editor.putInt("currentInt", inte);
                editor.commit();
                queryResults.moveToNext();
            }
            pdb.updateData(hp,level,str,def,inte,bats, currentID);
        }
        Intent stage=new Intent(this, StageSelectScreen.class);
        startActivity(stage);
    }

    public void heallButton(View v){
        SharedPreferences sharedPrefs = getSharedPreferences("CaptureFightData", Context.MODE_PRIVATE);

        currentID = sharedPrefs.getLong("currentID",0);
        if(currentID != 0) {
            int baseHP = sharedPrefs.getInt("currentMaxHP", 0);
            int currentHP = baseHP;
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putInt("currentHP", baseHP);
            editor.commit();
        }
        Toast.makeText(this, "Level Up", Toast.LENGTH_SHORT).show();
        Intent stage=new Intent(this, StageSelectScreen.class);
        startActivity(stage);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) )
        {
            Intent stage=new Intent(this, StageSelectScreen.class);
            startActivity(stage);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}