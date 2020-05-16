package com.example.beerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

// Class responsible for all the transactions between the application and the database
public class DBHandler extends SQLiteOpenHelper {
    // Constants about the database information
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "beersDB.db";
    // Constants about the Beers Table inside the database
    private static final String TABLE_BEERS = "beers";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MANUFACTURER = "manufacturer";
    private static final String COLUMN_COUNTRY = "country";
    private static final String COLUMN_ABV = "abv";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_IMAGE_HD = "imagehd";
    private static final String COLUMN_TASTED = "tasted";
    // Constants about the User Table inside the database
    private static final String TABLE_USER = "user";
    private static final String COLUMN_LITRES_CONSUMED = "litres";
    private static final String COLUMN_TOTAL_TIME = "time";
    private static final String COLUMN_BEST_SESSION = "best_session";

    // Constructor of the DBHandler
    DBHandler(Context context, SQLiteDatabase.CursorFactory factory){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

        // Path where the database will be copied
        String outFileName = context.getDatabasePath(DATABASE_NAME).getPath();
        // Check if the database exists before copying
        boolean initialiseDatabase = (new File(outFileName)).exists();
        // If the database doesn't exist, copy it to the specified path directory
        if (!initialiseDatabase) {
            try{
                InputStream mInput = context.getAssets().open("databases/"+DATABASE_NAME);
                OutputStream mOutput = new FileOutputStream(outFileName);
                byte[] mBuffer = new byte[1024];
                int mLength;
                while ((mLength = mInput.read(mBuffer)) > 0) {
                    mOutput.write(mBuffer, 0, mLength);
                }
                mOutput.flush();
                mOutput.close();
                mInput.close();

            }
            catch (IOException e){
                Log.e("Initialise DB Error", "Error copying the database from assets to local files");
            }
        }
    }

    // Empty method, the database is in the app package and is copied on the local directory
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Empty
    }

    // TODO Commenting
    // Upgrades the DB, deleting recreating the DB (since we don't need the method to do anything special)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    // Method used to return all the beers from the database in a list of Beer objects
    List<Beer> getAllBeers() {
        // Query for selecting the necessary information for each beer from the Beer Table of the database
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_MANUFACTURER + ", " + COLUMN_COUNTRY + ", " + COLUMN_ABV + ", " +
            COLUMN_TYPE + ", " + COLUMN_TASTED + " FROM " + TABLE_BEERS ;

        // Initialize the database
        SQLiteDatabase db = this.getWritableDatabase();
        // Execute the query and put the result inside a cursor
        Cursor cursor = db.rawQuery(query, null);
        // Initialize an empty ArrayList to store the result of the query
        List<Beer> beers = new ArrayList<>();
        // Initialize each beer and add it to the ArrayList
        while (cursor.moveToNext())
        {
            Beer beer = new Beer();
            beer.set_id(cursor.getInt(0)); // Beer ID
            beer.setName(cursor.getString(1)); // Beer Name
            beer.setManufacturer(cursor.getString(2)); // Beer Manufacturer
            beer.setCountry(cursor.getString(3)); // Beer Country
            beer.setAbv(cursor.getFloat(4)); // Beer ABV (Alcohol By Volume)
            beer.setType(cursor.getString(5)); // Beer Type
            beer.setTasted(cursor.getInt(6) == 1); // Beer Tasted
            beer.setBeerImage(getBeerImage(cursor.getInt(0),false)); // Beer Image (in byte[] format)
            // Add the beer to the beers list
            beers.add(beer);
        }
        cursor.close();
        // Return the list containing all the beers
        return beers;
    }

    // Method used to return all the tasted beers from the database in a list of Beer objects
    List<Beer> getTastedBeers() {
        // Query for selecting the necessary information for each beer from the Beer Table of the database
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_MANUFACTURER + ", " + COLUMN_COUNTRY + ", " + COLUMN_ABV + ", " +
                COLUMN_TYPE + ", " + COLUMN_TASTED + " FROM " + TABLE_BEERS + " WHERE " + COLUMN_TASTED + "=?";
        // Initialize the database
        SQLiteDatabase db = this.getWritableDatabase();
        // Execute the query and put the result inside a cursor
        Cursor cursor = db.rawQuery(query, new String[] {"1"});
        // Initialize an empty ArrayList to store the result of the query
        List<Beer> beers = new ArrayList<>();
        // Initialize each beer and add it to the ArrayList
        while (cursor.moveToNext())
        {
            Beer beer = new Beer();
            beer.set_id(cursor.getInt(0)); // Beer ID
            beer.setName(cursor.getString(1)); // Beer Name
            beer.setManufacturer(cursor.getString(2)); // Beer Manufacturer
            beer.setCountry(cursor.getString(3)); // Beer Country
            beer.setAbv(cursor.getFloat(4)); // Beer ABV (Alcohol By Volume)
            beer.setType(cursor.getString(5)); // Beer Type
            beer.setTasted(cursor.getInt(6) == 1); // Beer Tasted
            beer.setBeerImage(getBeerImage(cursor.getInt(0),false)); // Beer Image (in byte[] format)
            // Add the beer to the beers list
            beers.add(beer);
        }
        cursor.close();
        // Return the list containing all the tasted beers
        return beers;
    }

    // Method used to change the value of tasted field of the beer given
    // Returns true if a row from the Beer Table was affected
    boolean updateBeerTasted(Beer beer) {
        // Initialize the database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASTED, beer.isTasted());

        // If a row was affected return true
        return (db.update(TABLE_BEERS, cv, COLUMN_ID + "=" + beer.get_id(), null) >= 1);
    }

    // Method used to return the image of a beer in array of bytes
    // If the boolean parameter inHd is true, then return the HD version of the image
    byte[] getBeerImage(int beerId, boolean inHd) {
        // Initialize the database
        SQLiteDatabase db = this.getWritableDatabase();

        // If the parameter inHd is true, select the hd version of the image
        String resolution = COLUMN_IMAGE;
        if (inHd)
            resolution = COLUMN_IMAGE_HD;
        // Query for selecting the correct image according to the beerId
        String query = "SELECT " + resolution + " FROM " + TABLE_BEERS + " WHERE " + COLUMN_ID + " = " + beerId;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            // Get the Blob from the database as an array of bytes
            byte[] imgByte = cursor.getBlob(0);
            cursor.close();
            if (imgByte != null)
                return imgByte;
        }
        cursor.close();
        // If the image was not found return null
        return null;
    }

    // Method used to add session's litres to the database
    // Returns true if the session's litres were successfully saved in the User Table
    boolean addLitres(double litres) {
        String query = "SELECT * FROM " + TABLE_USER;
        // Initialise the database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            // Get the litres that are saved in the database
            double alreadyDrankLitres = cursor.getDouble(1);
            alreadyDrankLitres += litres; // Add the session's litres
            // Update the total litres in the database with the new value
            ContentValues litresUpdate = new ContentValues();
            litresUpdate.put(COLUMN_LITRES_CONSUMED, alreadyDrankLitres);
            db.update(TABLE_USER, litresUpdate, null, null);
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    // Method used to add session's time in seconds to the database
    // Returns true if the session's time was successfully saved in the User Table
    boolean addSessionTime(long timeSpentDrinking) {
        String query = "SELECT * FROM " + TABLE_USER;
        // Initialise the database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            // Get the time (in seconds) that are saved in the database
            long alreadyTimeSpentDrinking = cursor.getLong(2);
            alreadyTimeSpentDrinking += timeSpentDrinking; // Add the session's time
            // Update the total time in the database with the new value
            ContentValues timeUpdate = new ContentValues();
            timeUpdate.put(COLUMN_TOTAL_TIME, alreadyTimeSpentDrinking);
            db.update(TABLE_USER, timeUpdate, null, null);
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    // Method used to get the most litres consumed in a single session
    double getBestSession() {
        // Initialise the variable that is returned
        double bestSession = 0.0;
        String query = "SELECT * FROM " + TABLE_USER;
        // Initialise the database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            bestSession = cursor.getDouble(3); // Get the most litres that are saved in the database
        cursor.close();
        return bestSession;
    }

    // Method used to update the best session (most litres consumed in a single session)
    // The method is called if the current session's litres are more than the best session's litres
    boolean updateBestSession(double currentSession) {
        String query = "SELECT * FROM " + TABLE_USER;
        // Initialise the database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            ContentValues bestSessionUpdate = new ContentValues();
            bestSessionUpdate.put(COLUMN_BEST_SESSION, currentSession);
            db.update(TABLE_USER, bestSessionUpdate, null, null);
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    // Method used to get the total number of tasted beers
    int getTotalTastedBeers() {
        String query = "SELECT COUNT(*) FROM " + TABLE_BEERS + " WHERE " + COLUMN_TASTED + " = 1";
        // Initialise the database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // Initialise the variable that is returned
        int totalBeersTasted = 0;
        if (cursor.moveToFirst())
            totalBeersTasted = cursor.getInt(0);
        cursor.close();
        return totalBeersTasted;
    }

    // Method used to get the total litres consumed from all sessions
    double getTotalLitres() {
        String query = "SELECT * FROM " + TABLE_USER;
        // Initialise the database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            double totalLitres = cursor.getDouble(1);
            cursor.close();
            return totalLitres;
        }
        cursor.close();
        // If there was an error with the database return 0
        return 0.0;
    }

    // Method used to get the total time drinking (in seconds) from all sessions
    long getTotalTime() {
        String query = "SELECT * FROM " + TABLE_USER;
        // Initialise the database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            long totalTime = cursor.getLong(2);
            cursor.close();
            return totalTime;
        }
        cursor.close();
        return 0;
    }

    // Method used to return the name of the user
    String getUserName(){
        String query = "SELECT * FROM " + TABLE_USER;
        // Initialise the database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            String userName = cursor.getString(4);
            cursor.close();
            return userName;
        }
        cursor.close();
        return null;
    }

    // Method used to update the user's name in the User Table
    // Returns true if a row from the table was affected
    boolean updateUserName(String name) {
        // Initialise the database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        return (db.update(TABLE_USER, cv, null, null) >=1);
    }

    // Method used to reset the whole user's journey data from the database
    // Reset beers tasted, user's name used in application and user stats
    // Returns true if a row from the table was affected
    boolean resetUserData() {
        // Initialise the database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvBeersTable = new ContentValues();
        ContentValues cvUserTable = new ContentValues();
        cvBeersTable.put(COLUMN_TASTED, 0); // In order to reset the beers tasted
        cvUserTable.putNull(COLUMN_NAME); // In order to remove user's name
        return (db.update(TABLE_BEERS, cvBeersTable, null, null) >=1 &&
                db.update(TABLE_USER, cvUserTable, null, null) >=1 && resetUserStats());
    }

    // Method used to reset user drinking session stats (best session, total time drinking and total litres consumed)
    // Tasted Beers are not affected by this method
    // returns true if a row from the table was affected
    boolean  resetUserStats() {
        // Initialise the database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LITRES_CONSUMED, 0);
        cv.put(COLUMN_TOTAL_TIME, 0);
        cv.put(COLUMN_BEST_SESSION, 0);
        return db.update(TABLE_USER, cv, null, null) >= 1;
    }

}