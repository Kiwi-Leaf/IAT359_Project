package com.example.iat359_finalproject;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class BattleScreen extends AppCompatActivity implements View.OnClickListener {
    Button attackButton,defenceButton,escapeButton,magicButton;
    TextView enemyNameTV;
    ImageView enemyIV,characterIV,bgIV;
    View battleBG;
    ProgressBar enemyHPProgressBar, characterHPProgressBar;
    long stage;
    boolean isBright;
    int currentLevel;
    EnemyDatabase edb;
    PlayerDatabase pdb;
    EnemyHelper eHelper;
    PlayerHelper pHelper;
    String currentPath;
    String eCurrentPath;
    File imgFile;
    long currentID;


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

        enemyNameTV=findViewById(R.id.enemyNameTV);

        edb = new EnemyDatabase(this);
        eHelper = new EnemyHelper(this);
        pdb = new PlayerDatabase(this);
        pHelper = new PlayerHelper(this);

        SharedPreferences sharedPrefs = getSharedPreferences("CaptureFightData", Context.MODE_PRIVATE);

        currentID = sharedPrefs.getLong("currentID",0);
        if(currentID != 0) {
            int currentHP = sharedPrefs.getInt("currentHP", 0);
            int baseHP = sharedPrefs.getInt("currentMaxHP", 0);
            currentLevel = sharedPrefs.getInt("currentLV",0);
            int cHPPercent = (int) 100 * currentHP / baseHP;
            characterHPProgressBar.setProgress(cHPPercent);
        }
        //setting the image of the battle to the shared pref of current ID

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
        Toast.makeText(this, "Stage:"+ enemyInput, Toast.LENGTH_SHORT).show();
        Cursor enemyQueryResults = edb.getSelectedData(enemyInput);
        int enemyIndex1 = enemyQueryResults.getColumnIndex(EnemyConstants.FILE_PATH);
        int enemyIndex2 =  enemyQueryResults.getColumnIndex(EnemyConstants.NAME);
        int enemyIndex3 =  enemyQueryResults.getColumnIndex(EnemyConstants.ELEMENT);
        int enemyIndex4 =  enemyQueryResults.getColumnIndex(EnemyConstants.BASE_HP);
        int enemyIndex5 =  enemyQueryResults.getColumnIndex(EnemyConstants.ATTACK);
        int enemyIndex6 =  enemyQueryResults.getColumnIndex(EnemyConstants.DEFENSE);
        int enemyIndex7 =  enemyQueryResults.getColumnIndex(EnemyConstants.INTELLIGENCE);

        enemyQueryResults.getColumnIndex(EnemyConstants.FILE_PATH);
        enemyQueryResults.moveToFirst();
        while (!enemyQueryResults.isAfterLast()) {
            //            Toast.makeText(this,"index",Toast.LENGTH_LONG).show();
            eCurrentPath = enemyQueryResults.getString(enemyIndex1);
            String eName = enemyQueryResults.getString(enemyIndex2);
            String eType = enemyQueryResults.getString(enemyIndex3);
            int eBaseHP = (int) ( Integer.parseInt(enemyQueryResults.getString(enemyIndex4))*(currentLevel*1.01));
            int eCurrentHP = (int) (Integer.parseInt(enemyQueryResults.getString(enemyIndex4))*(currentLevel*1.01));
            int eAtk = (int) (Integer.parseInt(enemyQueryResults.getString(enemyIndex5))*(currentLevel*1.01));
            int eDef = (int) (Integer.parseInt(enemyQueryResults.getString(enemyIndex6))*(currentLevel*1.01));
            int eInt = (int) (Integer.parseInt(enemyQueryResults.getString(enemyIndex7))*(currentLevel*1.01));

            System.out.println("EnemyFile Path = " + eCurrentPath);
            enemyQueryResults.moveToNext();
            int enemyPath = getImageId(this, eCurrentPath);
            enemyIV.setImageResource(enemyPath);

            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("eCurrentType", eName);
            editor.putString("eCurrentType", eType);
            editor.putInt("eCurrentHP", eCurrentHP);
            editor.putInt("eCurrentMaxHP", eBaseHP);
            editor.putInt("eCurrentStr", eAtk);
            editor.putInt("eCurrentDef", eDef);
            editor.putInt("eCurrentInt", eInt);
            editor.commit();
            System.out.println("Enemy HP = " + eCurrentHP);
            enemyNameTV.setText(eName);
            int eHPPercent = (int) 100*eCurrentHP/eBaseHP;
            enemyHPProgressBar.setProgress(eHPPercent);
        }

    }
    //https://stackoverflow.com/questions/6783327/setimageresource-from-a-string
    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, null, context.getPackageName());
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
        SharedPreferences sharedPrefs = getSharedPreferences("CaptureFightData", Context.MODE_PRIVATE);
        int eAction = (int)(Math.random() * ((2 - 0) + 1));
        System.out.println("eAction: "+ eAction);
        switch (view.getId()){
            case    (R.id.attackButton):
                if(currentID!=0){
                    int currentHP = sharedPrefs.getInt("currentHP", 0);
                    int baseHP = sharedPrefs.getInt("currentMaxHP", 0);
                    int atk = sharedPrefs.getInt("currentStr",0);
                    int def = sharedPrefs.getInt("currentDef",0);
                    int level = sharedPrefs.getInt("currentLV",0);
                    int tempDamage = 1+atk*level;
                    int eDef = sharedPrefs.getInt("eCurrentDef",0);
                    int eAtk = sharedPrefs.getInt("eCurrentStr",0);
                    int eInt = sharedPrefs.getInt("eCurrentInt",0);
                    if(eDef <= 0){
                        eDef = 1;
                    }
                    int eTempDef = eDef;
                    int eDamage = 0;

                    if(eAction==0){
                        eDamage = (int) Math.ceil((eAtk)/def);
                    }
                    else if (eAction==1){
                        eDamage = (int) Math.ceil((eInt)/def);
                    }
                    else if (eAction==2){
                        eTempDef = eDef*2;
                    }
                    int damage = (int) Math.ceil(tempDamage/(eTempDef));

                    int eCurrentHP = sharedPrefs.getInt("eCurrentHP",0);
                    int eMaxHP = sharedPrefs.getInt("eCurrentMaxHP",0);
                    eCurrentHP -= damage;
                    currentHP -= eDamage;

                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putInt("eCurrentHP", eCurrentHP);
                    editor.putInt("currentHP", currentHP);
                    editor.commit();

                    int cHPPercent = (int) 100 * currentHP / baseHP;
                    characterHPProgressBar.setProgress(cHPPercent);
//                    System.out.println("eMaxHP = " + eMaxHP);
                    int eHPPercent = (int) 100 * eCurrentHP /(eMaxHP);
                    enemyHPProgressBar.setProgress(eHPPercent);
                    if(eCurrentHP <= 0){
                        Intent intent=new Intent(this, GameEndScreen.class);
                        intent.putExtra("WIN",TRUE);
                        startActivity(intent);
                    }
                    if(currentHP <= 0){
                        Intent intent=new Intent(this, GameEndScreen.class);
                        intent.putExtra("WIN",FALSE);
                        startActivity(intent);
                    }
                }
                break;
            case    (R.id.defenceButton):
                if(currentID!=0){
                    int currentHP = sharedPrefs.getInt("currentHP", 0);
                    int baseHP = sharedPrefs.getInt("currentMaxHP", 0);
                    int def = sharedPrefs.getInt("currentDef",0);
                    int level = sharedPrefs.getInt("currentLV",0);
                    int eAtk = sharedPrefs.getInt("eCurrentStr",0);
                    int eInt = sharedPrefs.getInt("eCurrentInt",0);
                    int eDamage = 0;
                    int tempDef = def*2;

                    if(eAction==0){
                        eDamage = (int) Math.ceil((eAtk)/tempDef);
                    }
                    else if (eAction==1){
                        eDamage = (int) Math.ceil((eInt)/tempDef);
                    }

                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    currentHP -= eDamage;

                    if(currentHP<baseHP){
                        currentHP += level;
                    }
//                    System.out.println("HP Now: "+ currentHP);
                    editor.putInt("currentHP", currentHP);
                    editor.commit();
//                    System.out.println("HP Now: "+ currentHP);
                    int cHPPercent = (int) 100 * currentHP / baseHP;
                    characterHPProgressBar.setProgress(cHPPercent);
                    if(currentHP <= 0){
                        Intent intent=new Intent(this, GameEndScreen.class);
                        intent.putExtra("WIN",FALSE);
                        startActivity(intent);
                    }
                }
                break;
            case    (R.id.magicButton):
                if(currentID!=0){
                    int currentHP = sharedPrefs.getInt("currentHP", 0);
                    int baseHP = sharedPrefs.getInt("currentMaxHP", 0);
                    int atk = sharedPrefs.getInt("currentInt",0);
                    int def = sharedPrefs.getInt("currentDef",0);
                    int level = sharedPrefs.getInt("currentLV",0);
                    int tempDamage = 1+atk*level;
                    int eDef = sharedPrefs.getInt("eCurrentDef",0);
                    int eAtk = sharedPrefs.getInt("eCurrentStr",0);
                    int eInt = sharedPrefs.getInt("eCurrentInt",0);
                    int type = sharedPrefs.getInt("currentType",0);
                    if(eDef <= 0){
                        eDef = 1;
                    }
                    int eTempDef = eDef;
                    int eDamage = 0;

                    if(eAction==0){
                        eDamage = (int) Math.ceil((1+eAtk)/def);
                    }
                    else if (eAction==1){
                        eDamage = (int) Math.ceil((1+eInt)/def);
                    }
                    else if (eAction==2){
                        eTempDef = eDef*2;
                    }
                    int damage = (int) Math.ceil(tempDamage/(eTempDef));

                    String eType = sharedPrefs.getString("eCurrentType", "DEFAULT");
                    if(type==0 && eType=="EARTH"){
                        damage = damage*2;
                    }
                    else if(type==0 && eType=="WATER"){
                        damage = (int) Math.ceil(damage*0.5);
                    }
                    else if(type==1 && eType=="FIRE"){
                        damage = damage*2;
                    }
                    else if(type==1 && eType=="ELECTRIC"){
                        damage = (int) Math.ceil(damage*0.5);
                    }
                    else if(type==2 && eType=="WATER"){
                        damage = damage*2;
                    }
                    else if(type==2 && eType=="WIND"){
                        damage = (int) Math.ceil(damage*0.5);
                    }
                    else if(type==3 && eType=="ELECTRIC"){
                        damage = damage*2;
                    }
                    else if(type==3 && eType=="EARTH"){
                        damage = (int) Math.ceil(damage*0.5);
                    }
                    else if(type==4 && eType=="WIND"){
                        damage = damage*2;
                    }
                    else if(type==4 && eType=="FIRE"){
                        damage = (int) Math.ceil(damage*0.5);
                    }

                    int eCurrentHP = sharedPrefs.getInt("eCurrentHP",0);
                    int eMaxHP = sharedPrefs.getInt("eCurrentMaxHP",0);
                    eCurrentHP -= damage;
                    currentHP -= eDamage;
                    System.out.println("HP Now: "+ currentHP);

                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putInt("eCurrentHP", eCurrentHP);

                    editor.putInt("currentHP", currentHP);
                    editor.commit();

                    int cHPPercent = (int) 100 * currentHP / baseHP;
                    characterHPProgressBar.setProgress(cHPPercent);
//                    System.out.println("eMaxHP = " + eMaxHP);
                    int eHPPercent = (int) 100 * eCurrentHP /(eMaxHP);
                    enemyHPProgressBar.setProgress(eHPPercent);
                    if(eCurrentHP <= 0){
                        Intent intent=new Intent(this, GameEndScreen.class);
                        intent.putExtra("WIN",TRUE);
                        startActivity(intent);
                    }
                    if(currentHP <= 0){
                        Intent intent=new Intent(this, GameEndScreen.class);
                        intent.putExtra("WIN",FALSE);
                        startActivity(intent);
                    }
                }


                break;
            case    (R.id.escapeButton):
                finish();
                break;
        }
    }
}