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
import android.widget.Toast;

public class PlayerDatabase {
    private SQLiteDatabase pdb;
    private Context context;
    private final PlayerHelper helper;

    public PlayerDatabase (Context c){
        context = c;
        helper = new PlayerHelper(context);
    }

    public long insertData (String name, String elem, int lv, int hp, int atk,  int def,  int inte, int wins, String img)
    {
        pdb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PlayerConstants.NAME, name);
        contentValues.put(PlayerConstants.ELEMENT, elem);
        contentValues.put(PlayerConstants.BASE_HP, hp);
        contentValues.put(PlayerConstants.LEVEL, lv);
        contentValues.put(PlayerConstants.ATTACK, atk);
        contentValues.put(PlayerConstants.DEFENSE, def);
        contentValues.put(PlayerConstants.INTELLIGENCE, inte);
        contentValues.put(PlayerConstants.BATTLES_WON, wins);
        contentValues.put(PlayerConstants.FILE_PATH, img);


        long id = pdb.insert(PlayerConstants.TABLE_NAME, null, contentValues);
        return id;
    }


    public void updateData (int lv, int hp, int atk,  int def, int inte, int wins, long clause)
    {
        pdb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PlayerConstants.BASE_HP, hp);
        contentValues.put(PlayerConstants.LEVEL, lv);
        contentValues.put(PlayerConstants.ATTACK, atk);
        contentValues.put(PlayerConstants.DEFENSE, def);
        contentValues.put(PlayerConstants.INTELLIGENCE, inte);
        contentValues.put(PlayerConstants.BATTLES_WON, wins);

        String[] whereArgs = new String[] { String.valueOf(clause) };
        pdb.update(PlayerConstants.TABLE_NAME, contentValues, PlayerConstants.UID + "=?", whereArgs);
    }

    public Cursor getData()
    {
        SQLiteDatabase pdb = helper.getWritableDatabase();

        String[] columns = {PlayerConstants.UID, PlayerConstants.NAME, PlayerConstants.ELEMENT, PlayerConstants.LEVEL, PlayerConstants.BASE_HP,PlayerConstants.ATTACK, PlayerConstants.DEFENSE, PlayerConstants.INTELLIGENCE, PlayerConstants.BATTLES_WON, PlayerConstants.FILE_PATH};
        Cursor cursor = pdb.query(PlayerConstants.TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }


    public Cursor getSelectedData(String name)
    {
        SQLiteDatabase pdb = helper.getWritableDatabase();
        String[] columns = {PlayerConstants.UID, PlayerConstants.NAME, PlayerConstants.ELEMENT, PlayerConstants.LEVEL, PlayerConstants.BASE_HP,PlayerConstants.ATTACK, PlayerConstants.DEFENSE, PlayerConstants.INTELLIGENCE, PlayerConstants.BATTLES_WON, PlayerConstants.FILE_PATH};

        String selection = PlayerConstants.UID + "='" + name + "'";
        Cursor cursor = pdb.query(PlayerConstants.TABLE_NAME, columns, selection, null, null, null, null);
        return cursor;

    }

    public int deleteRow(int deleted){
        SQLiteDatabase pdb = helper.getWritableDatabase();
        String[] whereArgs = new String[] { String.valueOf(deleted) };
        int count = pdb.delete(PlayerConstants.TABLE_NAME, PlayerConstants.UID + "=?", whereArgs);
        return count;
    }


}
