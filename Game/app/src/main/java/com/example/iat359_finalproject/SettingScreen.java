package com.example.iat359_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner themeSpinner;
    ArrayAdapter<CharSequence> arrayAdapter;
    private final String DEBUG = "Setting_Debug";
    LinearLayout mainBackgroundLL,item1LL,item2LL,item3LL,item4LL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the action bar on top
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_setting_screen);
        themeSpinner = findViewById(R.id.themeSpinner);
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.theme_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(arrayAdapter);
        themeSpinner.setOnItemSelectedListener(this);
        mainBackgroundLL=findViewById(R.id.settingBackground);
        item1LL=findViewById(R.id.settingItem1);
        item2LL=findViewById(R.id.settingItem2);
        item3LL=findViewById(R.id.settingItem3);
        item4LL=findViewById(R.id.settingItem4);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //int i = UITool Theme value
            SharedPreferences sharedPrefs = getSharedPreferences("CaptureFightData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putInt("theme", i);
            editor.commit();

            UITool.setThemeColor(mainBackgroundLL,UITool.THEME_TYPE_TRANS,this);
            UITool.setThemeColor(item1LL,UITool.THEME_TYPE_SOLID,this);
            UITool.setThemeColor(item2LL,UITool.THEME_TYPE_SOLID,this);
            UITool.setThemeColor(item3LL,UITool.THEME_TYPE_SOLID,this);
            UITool.setThemeColor(item4LL,UITool.THEME_TYPE_SOLID,this);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}