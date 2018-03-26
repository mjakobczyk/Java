package lab6.jakobczyk.michal.java.polsl.pl.morseapp.activity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import lab6.jakobczyk.michal.java.polsl.pl.morseapp.controller.ContextHolder;
import lab6.jakobczyk.michal.java.polsl.pl.morseapp.controller.Converter;
import lab6.jakobczyk.michal.java.polsl.pl.morseapp.database.DatabaseHelper;
import lab6.jakobczyk.michal.java.polsl.pl.morseapp.exception.IncorrectConversionTypeException;

/**
 * Main Activity of the application
 *
 * @author Michał Jakóbczyk
 * @version 6.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Converter object for translations
     */
    private Converter converter;

    /**
     * SQLite database object for handling transactions and keep history
     */
    private SQLiteDatabase database;

    /**
     * Database helper object to maintain database
     */
    private DatabaseHelper databaseHelper;

    /**
     * Initialization of the MainActivity object
     * @param savedInstanceState if there was any instance before
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Context Holder with current application context
        ContextHolder.setCurrentContext(getApplicationContext());

        // Initialize conveter controller
        this.converter = new Converter();

        // Initialize database with Database Helper
        this.databaseHelper = new DatabaseHelper(ContextHolder.getCurrectContext());
        this.database = databaseHelper.getWritableDatabase();

        // Initialize Webview with premade html page
        WebView page = new WebView(ContextHolder.getCurrectContext());
        WebSettings webSettings = page.getSettings();
        webSettings.setJavaScriptEnabled(true);
        page.addJavascriptInterface(this,"activity");
        page.loadUrl("file:///android_asset/index.html");

        // View the content to the user
        setContentView(page);
    }

    /**
     * Javascript function to translate text to morse
     * @param toConvert text passed from html
     * @return translated text
     * @throws IOException when i/o is corrupted
     * @throws IncorrectConversionTypeException when conversion is not recognised
     */
    @JavascriptInterface
    public String translateToMorse(String toConvert) throws IOException, IncorrectConversionTypeException {
        try {
            String temp = converter.performConversion(toConvert, "l2m");

            // Get the current data and time
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());

            try {
                this.database.execSQL("INSERT INTO Morse VALUES ("
                        + null + ", '"
                        + "l2m" + "', '"
                        + timeStamp + "')");
            }
            catch (SQLException e) {
                Toast.makeText(this, "SQL Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            return temp;
        }
        catch (IOException | IncorrectConversionTypeException e) {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show();
        }

        return "Try again...";
    }

    /**
     * Javascript function to translate text to latin
     * @param toConvert text passed from html
     * @return translated text
     * @throws IOException when i/o is corrupted
     * @throws IncorrectConversionTypeException when conversion is not recognised
     */
    @JavascriptInterface
    public String translateToLatin(String toConvert) throws IOException, IncorrectConversionTypeException {
        try {
            String temp = converter.performConversion(toConvert, "m2l");

            // Get the current data and time
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());

            try {
                this.database.execSQL("INSERT INTO Morse VALUES ("
                        + null + ", '"
                        + "m2l" + "', '"
                        + timeStamp + "')");
            }
            catch (SQLException e) {
                Toast.makeText(this, "SQL Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            return temp;
        }
        catch (IOException | IncorrectConversionTypeException e) {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show();
        }

        return "Try again...";
    }

    /**
     * Javascript function to get the translations history
     * @return history of translations
     */
    @JavascriptInterface
    public String history() {
        Cursor cursor = database.rawQuery("SELECT * FROM Morse", null);

        ArrayList<String> dataFromDatabase = new ArrayList<>();

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();

            do {
                Integer currentId = cursor.getInt(cursor.getColumnIndex("id")) + 1;
                dataFromDatabase.add(currentId.toString());
                dataFromDatabase.add(".   ");
                dataFromDatabase.add(cursor.getString(cursor.getColumnIndex("type")));
                dataFromDatabase.add("   ");
                dataFromDatabase.add(cursor.getString(cursor.getColumnIndex("data")));
                dataFromDatabase.add("\n");
            } while(cursor.moveToNext());
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String temp : dataFromDatabase) {
            stringBuilder.append(temp);
        }

        return stringBuilder.toString();
    }
}
