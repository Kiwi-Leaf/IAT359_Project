package com.example.iat359_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner themeSpinner;
    ArrayAdapter <CharSequence> arrayAdapter;
    private final String DEBUG="Setting_Debug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);
        themeSpinner=findViewById(R.id.themeSpinner);
        arrayAdapter= ArrayAdapter.createFromResource(this, R.array.theme_array,android.R.layout.simple_spinner_item );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(arrayAdapter);
        themeSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(view== themeSpinner){
            Log.i(DEBUG, "int returned: "+i+", long returned: "+l);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}