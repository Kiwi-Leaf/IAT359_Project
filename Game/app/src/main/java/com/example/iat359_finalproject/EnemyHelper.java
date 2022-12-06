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

    private static final String INITIALIZE_EDB = "insert into "+ EnemyConstants.TABLE_NAME +"("+EnemyConstants.NAME +", "+EnemyConstants.ELEMENT + ", "+EnemyConstants.BASE_HP+", "+EnemyConstants.ATTACK+", "+EnemyConstants.DEFENSE+", "+EnemyConstants.INTELLIGENCE+", "+EnemyConstants.FILE_PATH+") "+ "values (\"Cat\", \"FIRE\", 7, 9, 6, 10, \"drawable/cat_fire\"), (\"Cat\", \"WATER\", 7, 9, 6, 10, \"drawable/cat_water\"),(\"Cat\", \"ELECTRIC\", 7, 9, 6, 10, \"drawable/cat_electric\"),(\"Cat\", \"WIND\", 7, 9, 6, 10, \"drawable/cat_wind\"),(\"Cat\", \"EARTH\", 7, 9, 6, 10, \"drawable/cat_earth\"),(\"Dog\", \"FIRE\", 8, 12, 8, 8, \"drawable/dog_fire\"), (\"Dog\", \"WATER\", 8, 12, 8, 8, \"drawable/dog_water\"),(\"Dog\", \"ELECTRIC\", 8, 12, 8, 8, \"drawable/dog_electric\"),(\"Dog\", \"WIND\", 8, 12, 8, 8, \"drawable/dog_wind\"),(\"Dog\", \"EARTH\", 8, 12, 8, 8, \"drawable/dog_earth\"),(\"Dragon\", \"FIRE\", 12, 12, 12, 15, \"drawable/dragon_fire\"), (\"Dragon\", \"WATER\", 12, 12, 12, 15, \"drawable/dragon_water\"),(\"Dragon\", \"ELECTRIC\", 12, 12, 12, 15, \"drawable/dragon_electric\"),(\"Dragon\", \"WIND\", 12, 12, 12, 15, \"drawable/dragon_wind\"),(\"Dragon\", \"EARTH\", 12, 12, 12, 15, \"drawable/dragon_earth\"),(\"Fish\", \"FIRE\", 10, 8, 12, 10, \"drawable/fish_fire\"), (\"Fish\", \"WATER\", 10, 8, 12, 10, \"drawable/fish_water\"),(\"Fish\", \"ELECTRIC\", 10, 8, 12, 10, \"drawable/fish_electric\"),(\"Fish\", \"WIND\", 10, 8, 12, 10, \"drawable/fish_wind\"),(\"Fish\", \"EARTH\", 10, 8, 12, 10, \"drawable/fish_earth\"),(\"Mushroom\", \"FIRE\", 14, 5, 15, 8, \"drawable/mushroom_fire\"), (\"Mushroom\", \"WATER\", 14, 5, 15, 8, \"drawable/mushroom_water\"),(\"Mushroom\", \"ELECTRIC\", 14, 5, 15, 8, \"drawable/mushroom_electric\"),(\"Mushroom\", \"WIND\", 14, 5, 15, 8, \"drawable/mushroom_wind\"),(\"Mushroom\", \"EARTH\", 14, 5, 15, 8, \"drawable/mushroom_earth\"),(\"Owl\", \"FIRE\", 7, 9, 6, 14, \"drawable/owl_fire\"), (\"Owl\", \"WATER\", 7, 9, 6, 14, \"drawable/owl_water\"),(\"Owl\", \"ELECTRIC\", 7, 9, 6, 14, \"drawable/owl_electric\"),(\"Owl\", \"WIND\", 7, 9, 6, 14, \"drawable/owl_wind\"),(\"Owl\", \"EARTH\", 7, 9, 6, 14, \"drawable/owl_earth\"),(\"Skeleton\", \"FIRE\", 10, 10, 10, 8, \"drawable/skeleton_fire\"), (\"Skeleton\", \"WATER\", 10, 10, 10, 8, \"drawable/skeleton_water\"),(\"Skeleton\", \"ELECTRIC\", 10, 10, 10, 8, \"drawable/skeleton_electric\"),(\"Skeleton\", \"WIND\", 10, 10, 10, 8, \"drawable/skeleton_wind\"),(\"Skeleton\", \"EARTH\", 10, 10, 10, 8, \"drawable/skeleton_earth\");";

    public EnemyHelper(Context context){
        super (context, EnemyConstants.DATABASE_NAME, null, EnemyConstants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase edb) {
        try {
            edb.execSQL(CREATE_TABLE);
//            Toast.makeText(context, "onCreate() called", Toast.LENGTH_LONG).show();
            edb.execSQL(INITIALIZE_EDB);
//            Toast.makeText(context, "Intial data added", Toast.LENGTH_LONG).show();
            System.out.println("t'was inserted");
        } catch (SQLException e) {
            System.out.println("exception = " + e);
//            Toast.makeText(context, "exception onCreate() edb", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase edb, int oldVersion, int newVersion) {
        try {
            edb.execSQL(DROP_TABLE);
            onCreate(edb);
//            Toast.makeText(context, "onUpgrade called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
//            Toast.makeText(context, "exception onUpgrade() edb", Toast.LENGTH_LONG).show();
        }
    }

}
