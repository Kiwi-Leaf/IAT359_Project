package com.example.iat359_finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EnemyDatabase {
    private SQLiteDatabase edb;
    private Context context;
    private final EnemyHelper helper;

    public EnemyDatabase (Context c){
        context = c;
        helper = new EnemyHelper(context);
    }

    public long insertData (String name, String elem, int hp, int atk,  int def,  int inte, int spd, String cycle, String img)
    {
        edb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EnemyConstants.NAME, name);
        contentValues.put(EnemyConstants.ELEMENT, elem);
        contentValues.put(EnemyConstants.BASE_HP, hp);
        contentValues.put(EnemyConstants.ATTACK, atk);
        contentValues.put(EnemyConstants.DEFENSE, def);
        contentValues.put(EnemyConstants.INTELLIGENCE, inte);
        contentValues.put(EnemyConstants.SPEED, spd);
        contentValues.put(EnemyConstants.TIME_CYCLE, cycle);
        contentValues.put(EnemyConstants.FILE_PATH, img);

        long id = edb.insert(EnemyConstants.TABLE_NAME, null, contentValues);
        return id;
    }

    public Cursor getData()
    {
        SQLiteDatabase edb = helper.getWritableDatabase();

        String[] columns = {EnemyConstants.UID, EnemyConstants.NAME, EnemyConstants.ELEMENT, EnemyConstants.BASE_HP,EnemyConstants.ATTACK, EnemyConstants.DEFENSE, EnemyConstants.INTELLIGENCE, EnemyConstants.SPEED, EnemyConstants.TIME_CYCLE, EnemyConstants.FILE_PATH};
        Cursor cursor = edb.query(EnemyConstants.TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }


    public Cursor getSelectedData(String name, String cycle)
    {
        SQLiteDatabase edb = helper.getWritableDatabase();
        String[] columns = {EnemyConstants.UID, EnemyConstants.NAME, EnemyConstants.ELEMENT, EnemyConstants.BASE_HP,EnemyConstants.ATTACK, EnemyConstants.DEFENSE, EnemyConstants.INTELLIGENCE, EnemyConstants.SPEED, EnemyConstants.TIME_CYCLE, EnemyConstants.FILE_PATH};

        String selection = EnemyConstants.NAME + "='" +name+ "' AND " + EnemyConstants.TIME_CYCLE + "='" +cycle+ "'";
        Cursor cursor = edb.query(EnemyConstants.TABLE_NAME, columns, selection, null, null, null, null);
        return cursor;

    }


}
