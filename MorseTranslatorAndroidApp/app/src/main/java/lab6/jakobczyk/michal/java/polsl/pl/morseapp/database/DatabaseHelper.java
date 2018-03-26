package lab6.jakobczyk.michal.java.polsl.pl.morseapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class responsible for handling database
 *
 * @author Michał Jakóbczyk
 * @version 6.0
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * Constructor of the DatabaseHelper object
     * @param context in which DB works
     */
    public DatabaseHelper(Context context) {
        super(context, "Morse", null, 1);
    }

    /**
     * Method popping upon creation of the object
     * @param sqLiteDatabase for initialization
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(
                    "create table Morse(" +
                            "id integer PRIMARY KEY AUTOINCREMENT, " +
                            "type varchar(5)," +
                            "data varchar(30));");
        } catch(SQLiteException e) {
            System.out.println("Something went wrong: " +e.getMessage());
        }
    }

    /**
     * Method popping upon upgrading database to the newer build
     * @param database to initialize
     * @param i older version of the build
     * @param i1 newer version of the build
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + "Morse" );
        onCreate(database);
    }
}
