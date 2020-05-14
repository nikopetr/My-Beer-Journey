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

public class DBHandler extends SQLiteOpenHelper {
    // Constants used for the DB
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "beersDB.db";
    // For beers table
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
    // For user table
    private static final String TABLE_USER = "user";
    private static final String COLUMN_LITRES_CONSUMED = "litres";
    private static final String COLUMN_TOTAL_TIME = "time";
    private static final String COLUMN_BEST_SESSION = "best_session";

    // Constructor
    DBHandler(Context context, SQLiteDatabase.CursorFactory factory){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

        // Path where the database will be
        String outFileName = context.getDatabasePath(DATABASE_NAME).getPath();
        // Check if the database exists before copying
        boolean initialiseDatabase = (new File(outFileName)).exists();
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

    // Creates the structure of the DB
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    // Upgrades the DB, deleting recreating the DB (since we don't need the method to do anything special)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    // Returns all the Beer objects from the DB
    List<Beer> getAllBeers() {
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_MANUFACTURER + ", " + COLUMN_COUNTRY + ", " + COLUMN_ABV + ", " +
            COLUMN_TYPE + ", " + COLUMN_TASTED + " FROM " + TABLE_BEERS ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Beer> beers = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Beer beer = new Beer();
            beer.set_id(cursor.getInt(0));
            beer.setName(cursor.getString(1));
            beer.setManufacturer(cursor.getString(2));
            beer.setCountry(cursor.getString(3));
            beer.setAbv(cursor.getFloat(4));
            beer.setType(cursor.getString(5));
            beer.setTasted(cursor.getInt(6) == 1);
            beer.setBeerImage(getBeerImage(cursor.getInt(0),false));
            // Add the beer to the beers list
            beers.add(beer);
        }
        cursor.close();

        return beers;
    }

    // Returns the tasted Beer objects from the DB
    List<Beer> getTastedBeers() {
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_MANUFACTURER + ", " + COLUMN_COUNTRY + ", " + COLUMN_ABV + ", " +
                COLUMN_TYPE + ", " + COLUMN_TASTED + " FROM " + TABLE_BEERS + " WHERE " + COLUMN_TASTED + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[] {"1"});
        List<Beer> beers = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Beer beer = new Beer();
            beer.set_id(cursor.getInt(0));
            beer.setName(cursor.getString(1));
            beer.setManufacturer(cursor.getString(2));
            beer.setCountry(cursor.getString(3));
            beer.setAbv(cursor.getFloat(4));
            beer.setType(cursor.getString(5));
            beer.setTasted(cursor.getInt(6) == 1);
            beer.setBeerImage(getBeerImage(cursor.getInt(0),false));
//            beer.setBeerImageHD(getBeerImageHD(cursor.getInt(0)));
            // Add the beer to the beers list
            beers.add(beer);
        }
        cursor.close();
        return beers;
    }

    // Changes the value of tasted the specific beer as given,
    // returns true if a row from the table was affected
    boolean updateBeerTasted(Beer beer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASTED, beer.isTasted());

        // If a row was affected return true
        return (db.update(TABLE_BEERS, cv, COLUMN_ID + "=" + beer.get_id(), null) >= 1);
    }

    // Returns the image of the beerId to the beer in byte in array
    // if the boolean parameter inHd, is true, then the image is returned in hd
    byte[] getBeerImage(int beerId, boolean inHd) {
        SQLiteDatabase db = this.getWritableDatabase();

        // if the parameter inHd is true, select the hd version of the image
        String resolution = COLUMN_IMAGE;
        if (inHd)
            resolution = COLUMN_IMAGE_HD;

        String query = "SELECT " + resolution + " FROM " + TABLE_BEERS + " WHERE " + COLUMN_ID + " = " + beerId;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            byte[] imgByte = cursor.getBlob(0);
            cursor.close();
            if (imgByte != null)
                return imgByte;
        }
        cursor.close();
        return null;
    }

    // Add session's litres to the DB
    // Returns true if the session's litres were successfully saved in the user DB
    boolean addLitres(double litres) {
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            double alreadyDrankLitres = cursor.getDouble(1);
            alreadyDrankLitres += litres;
            ContentValues litresUpdate = new ContentValues();
            litresUpdate.put(COLUMN_LITRES_CONSUMED, alreadyDrankLitres);
            db.update(TABLE_USER, litresUpdate, null, null);
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    // Add session's time in seconds to the DB
    // Returns true if the session's time was successfully saved in the user DB
    boolean addSessionTime(long timeSpentDrinking) {
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            long alreadyTimeSpentDrinking = cursor.getLong(2);
            alreadyTimeSpentDrinking += timeSpentDrinking;
            ContentValues timeUpdate = new ContentValues();
            timeUpdate.put(COLUMN_TOTAL_TIME, alreadyTimeSpentDrinking);
            db.update(TABLE_USER, timeUpdate, null, null);
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    // Return the most amount of beer drank by the user in time
    double getBestSession() {
        double bestSession = 0.0;
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            bestSession = cursor.getDouble(3);
        cursor.close();
        return bestSession;
    }

    // Updates the best session
    boolean updateBestSession(double currentSession) {
        String query = "SELECT * FROM " + TABLE_USER;
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

    // Return the total number of tasted beers
    int getTotalTastedBeers() {
        String query = "SELECT COUNT(*) FROM " + TABLE_BEERS + " WHERE " + COLUMN_TASTED + " = 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int totalBeersTasted = 0;
        if (cursor.moveToFirst())
            totalBeersTasted = cursor.getInt(0);
        cursor.close();
        return totalBeersTasted;
    }

    // Return total litres that were consumed
    double getTotalLitres() {
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            double totalLitres = cursor.getDouble(1);
            cursor.close();
            return totalLitres;
        }
        cursor.close();
        return 0.0;
    }

    // Return the total time spent drinking in seconds
    long getTotalTime() {
        String query = "SELECT * FROM " + TABLE_USER;
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

    // Return the username of the user
    String getUserName(){
        String query = "SELECT * FROM " + TABLE_USER;
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

    // Updates the user's name in user table
    // returns true if a row from the table was affected
    boolean updateUserName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        return (db.update(TABLE_USER, cv, null, null) >=1);
    }

    // Reset the whole user's journey data from the db.
    // Including beers tasted, and user stats
    // returns true if a row from the table was affected
    boolean resetUserData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASTED, 0);
        return (db.update(TABLE_BEERS, cv, null, null) >=1 && resetUserStats());
    }

    // Reset user drinking session stats,
    // returns true if a row from the table was affected
    boolean  resetUserStats() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LITRES_CONSUMED, 0);
        cv.put(COLUMN_TOTAL_TIME, 0);
        cv.put(COLUMN_BEST_SESSION, 0);
        return db.update(TABLE_USER, cv, null, null) >= 1;
    }

}