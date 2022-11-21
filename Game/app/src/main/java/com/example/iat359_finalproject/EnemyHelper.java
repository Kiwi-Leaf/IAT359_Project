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
                    EnemyConstants.SPEED + " TEXT, " +
                    EnemyConstants.TIME_CYCLE + " TEXT, " +
                    EnemyConstants.FILE_PATH + " TEXT);" ;

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + EnemyConstants.TABLE_NAME;

    private static final String INITIALIZE_EDB = "insert into "+ EnemyConstants.DATABASE_NAME +"(NAME, ELEMENT, BASE_HP, ATTACK, DEFENSE, INTELLIGENCE, SPEED, TIME_CYCLE, FILE_PATH) "+ "values (Cat, Electric, 7, 9, 6, 10, 16, Day, drawable/cat_electric.png), (Cat, Electric, 7, 9, 5, 12, 17, Night, drawable/cat_electric.png) " ;

    public EnemyHelper(Context context){
        super (context, EnemyConstants.DATABASE_NAME, null, EnemyConstants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context, "onCreate() called", Toast.LENGTH_LONG).show();
            db.execSQL(INITIALIZE_EDB);
            Toast.makeText(context, "Intial data added", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onCreate() db", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
            Toast.makeText(context, "onUpgrade called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onUpgrade() db", Toast.LENGTH_LONG).show();
        }
    }

}
