package com.example.iat359_finalproject;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class GameEndScreen extends AppCompatActivity {
    Button retryBattleButton, backToStageButton ;
    int previousStage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end_screen);

        //Hide the action bar on top
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();




    }

    public void retryBattle(View v){
        Intent battle =new Intent(this, BattleScreen.class);
        //need a way to tell battle screen which stage it is in
        //battle.putExtra("BATTLE",previousStage);
        startActivity(battle);
    }

    public void backToStageSelect(View v){
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