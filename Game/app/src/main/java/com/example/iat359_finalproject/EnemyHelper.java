package com.example.iat359_finalproject;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class EnemyHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String CREATE_TABLE =
            "CREATE TABLE "+
                    EnemyConstants.TABLE_NAME + " (" +
                    EnemyConstants.UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EnemyConstants.NAME + " TEXT, " +
                    EnemyConstants.ELEMENT + " TEXT, " +
                    EnemyConstants.BASE_HP + " TEXT, " +
                    EnemyConstants.ATTACK + " TEXT, " +
                    EnemyConstants.DEFENSE + " TEXT, " +
                    EnemyConstants.INTELLIGENCE + " TEXT, " +
                    EnemyConstants.FILE_PATH + " TEXT);" ;

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + EnemyConstants.TABLE_NAME;

    private static final String INITIALIZE_EDB = "insert into "+ EnemyConstants.TABLE_NAME +"("+EnemyConstants.NAME +", "+EnemyConstants.ELEMENT + ", "+EnemyConstants.BASE_HP+", "+EnemyConstants.ATTACK+", "+EnemyConstants.DEFENSE+", "+EnemyConstants.INTELLIGENCE+", "+EnemyConstants.FILE_PATH+") "+ "values (\"Cat\", \"FIRE\", 7, 9, 6, 10, \"drawable/cat_fire\"), (\"Cat\", \"WATER\", 7, 9, 6, 10, \"drawable/cat_water\"),(\"Cat\", \"ELECTRIC\", 7, 9, 6, 10, \"drawable/cat_electric\"),(\"Cat\", \"WIND\", 7, 9, 6, 10, \"drawable/cat_wind\"),(\"Cat\", \"EARTH\", 7, 9, 6, 10, \"drawable/cat_earth\");";

    public EnemyHelper(Context context){
        super (context, EnemyConstants.DATABASE_NAME, null, EnemyConstants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase edb) {
        try {
            edb.execSQL(CREATE_TABLE);
            Toast.makeText(context, "onCreate() called", Toast.LENGTH_LONG).show();
            edb.execSQL(INITIALIZE_EDB);
            Toast.makeText(context, "Intial data added", Toast.LENGTH_LONG).show();
            System.out.println("t'was inserted");
        } catch (SQLException e) {
            System.out.println("exception = " + e);
            Toast.makeText(context, "exception onCreate() edb", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase edb, int oldVersion, int newVersion) {
        try {
            edb.execSQL(DROP_TABLE);
            onCreate(edb);
            Toast.makeText(context, "onUpgrade called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onUpgrade() edb", Toast.LENGTH_LONG).show();
        }
    }

}
