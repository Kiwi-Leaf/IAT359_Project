package com.example.iat359_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;

public class StageSelectScreen extends AppCompatActivity implements View.OnClickListener {
    Button selectStageButton1,selectStageButton2,selectStageButton3,characterButton;

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

        selectStageButton1.setOnClickListener(this);
        selectStageButton2.setOnClickListener(this);
        selectStageButton3.setOnClickListener(this);
        characterButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent ;
        switch (view.getId()){
            case (R.id.stage1Button):
                intent=new Intent(this,BattleScreen.class);
                intent.putExtra("BATTLE",1);
                startActivity(intent);
                break;

            case (R.id.stage2Button):
                intent=new Intent(this,BattleScreen.class);
                intent.putExtra("BATTLE",2);
                startActivity(intent);
                break;

            case (R.id.stage3Button):
                intent=new Intent(this,BattleScreen.class);
                intent.putExtra("BATTLE",3);
                startActivity(intent);
                break;

            case (R.id.characterButton):
                intent=new Intent(this,StatScreen.class);
                startActivity(intent);
                break;
                }


    }
}