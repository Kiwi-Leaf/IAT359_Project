package com.example.iat359_finalproject;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class PlayerHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String CREATE_TABLE =
            "CREATE TABLE "+
                    PlayerConstants.TABLE_NAME + " (" +
                    PlayerConstants.UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PlayerConstants.NAME + " TEXT, " +
                    PlayerConstants.ELEMENT + " TEXT, " +
                    PlayerConstants.LEVEL + " TEXT, " +
                    PlayerConstants.BASE_HP + " TEXT, " +
                    PlayerConstants.ATTACK + " TEXT, " +
                    PlayerConstants.DEFENSE + " TEXT, " +
                    PlayerConstants.INTELLIGENCE + " TEXT, " +
                    PlayerConstants.FILE_PATH + " TEXT, " +
                    PlayerConstants.BATTLES_WON + " TEXT);" ;

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + PlayerConstants.TABLE_NAME;

    public PlayerHelper(Context context){
        super (context, PlayerConstants.DATABASE_NAME, null, PlayerConstants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase pdb) {
        try {
            pdb.execSQL(CREATE_TABLE);
            Toast.makeText(context, "onCreate() called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onCreate() pdb", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase pdb, int oldVersion, int newVersion) {
        try {
            pdb.execSQL(DROP_TABLE);
            onCreate(pdb);
            Toast.makeText(context, "onUpgrade called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onUpgrade() db", Toast.LENGTH_LONG).show();
        }
    }
}
