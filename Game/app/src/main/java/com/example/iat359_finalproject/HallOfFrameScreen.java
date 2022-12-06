package com.example.iat359_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HallOfFrameScreen extends AppCompatActivity implements View.OnClickListener {
    EditText searchTermID;
    TextView queryUID, queryName, queryType;
    PlayerDatabase pdb;
    PlayerHelper pHelper;
    Button button, buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_of_frame_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        searchTermID = (EditText)findViewById(R.id.searchTermID);
        queryUID = (TextView)findViewById(R.id.queryUID);
        queryName = (TextView)findViewById(R.id.queryName);
        queryType = (TextView)findViewById(R.id.queryType);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
        buttonDelete = (Button)findViewById(R.id.buttonDelete);;
        buttonDelete.setOnClickListener(this);

        pdb = new PlayerDatabase(this);
        pHelper = new PlayerHelper(this);
    }

//    public void viewQueryResults (View view)
//    {
//        String userInputType = searchTermID.getText().toString();
//
////        Toast.makeText(this, "selection:"+ userInputType, Toast.LENGTH_SHORT).show();
//        Cursor queryResults = pdb.getSelectedData(userInputType);
//
//        int index1 = queryResults.getColumnIndex(PlayerConstants.UID);
//        int index2 = queryResults.getColumnIndex(PlayerConstants.NAME);
//        int index3 = queryResults.getColumnIndex(PlayerConstants.ELEMENT);
//
//
//        queryUID.setText(String.valueOf(index1));
//        queryName.setText(String.valueOf(index2));
//        queryType.setText(String.valueOf(index3));
//    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.button){
            String userInputType = searchTermID.getText().toString();

//        Toast.makeText(this, "selection:"+ userInputType, Toast.LENGTH_SHORT).show();
            Cursor queryResults = pdb.getSelectedData(userInputType);

            int index1 = queryResults.getColumnIndex(PlayerConstants.UID);
            int index2 = queryResults.getColumnIndex(PlayerConstants.NAME);
            int index3 = queryResults.getColumnIndex(PlayerConstants.ELEMENT);

            queryResults.moveToFirst();
            while (!queryResults.isAfterLast()) {

                //            Toast.makeText(this,"index",Toast.LENGTH_LONG).show();
                queryUID.setText(queryResults.getString(index1));
                queryName.setText(queryResults.getString(index2));
                queryType.setText(queryResults.getString(index3));
                queryResults.moveToNext();
            }
        }
        if(view.getId()==R.id.buttonDelete){
            String userInputType = searchTermID.getText().toString();
            int searchInt = Integer.parseInt(userInputType);
            searchInt = pdb.deleteRow(searchInt);
            Toast.makeText(this, "Deleted UID: " +userInputType, Toast.LENGTH_SHORT).show();
            queryUID.setText(" ");
            queryName.setText(" ");
            queryType.setText(" ");
        }
    }
}