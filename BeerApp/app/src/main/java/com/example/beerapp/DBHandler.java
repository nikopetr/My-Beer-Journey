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
    // For user table
    private static final String TABLE_USER = "user";
    private static final String COLUMN_BEERS_TASTED = "tasted";
    private static final String COLUMN_LITRES_CONSUMED = "litres";
    private static final String COLUMN_TOTAL_TIME = "time";
    private static final String COLUMN_BEST_SESSION = "best_session";

    // Constructor
    DBHandler(Context context, SQLiteDatabase.CursorFactory factory){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);


        //final String DB_DESTINATION = "/data/data/com.example.beerapp/databases/beersDB.db";


        //This is the path where the database will be
        String outFileName = context.getDatabasePath(DATABASE_NAME).getPath();
        // Check if the database exists before copying
        boolean initialiseDatabase = (new File(outFileName)).exists();
        if (!initialiseDatabase) {
            try{
                InputStream mInput = context.getAssets().open(DATABASE_NAME);
                OutputStream mOutput = new FileOutputStream(outFileName);
                byte[] mBuffer = new byte[1024];
                int mLength;
                while ((mLength = mInput.read(mBuffer)) > 0) {
                    mOutput.write(mBuffer, 0, mLength);
                }
                mOutput.flush();
                mOutput.close();
                mInput.close();

            }catch (Exception e){
                Log.d("ERROR", "RE");
            }
        }
    }

    // Creates the structure of the DB
    @Override
    public void onCreate(SQLiteDatabase db) {
//        String CREATE_BEERS_TABLE = "CREATE TABLE " +
//                TABLE_BEERS + "(" +
//                COLUMN_ID + " INTEGER PRIMARY KEY," +
//                COLUMN_NAME + " TEXT," +
//                COLUMN_MANUFACTURER + " TEXT," +
//                COLUMN_COUNTRY + " TEXT," +
//                COLUMN_ABV + " FLOAT," +
//                COLUMN_TYPE + " TEXT" + ")";
//        db.execSQL(CREATE_BEERS_TABLE);
//
//
//        String CREATE_USER_TABLE = "CREATE TABLE " +
//                TABLE_USER + "(" +
//                COLUMN_ID + " INTEGER PRIMARY KEY," +
//                COLUMN_BEERS_TASTED + " INTEGER," +
//                COLUMN_LITRES_CONSUMED + " DOUBLE," +
//                COLUMN_TOTAL_TIME + " BIGINT," +
//                COLUMN_BEST_SESSION + " DOUBLE" + ")";
//        db.execSQL(CREATE_USER_TABLE);
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_BEERS_TASTED, 0);
//        values.put(COLUMN_LITRES_CONSUMED, 0);
//        values.put(COLUMN_TOTAL_TIME, 0);
//        values.put(COLUMN_BEST_SESSION, 0);
//        db.insert(TABLE_USER, null, values);
    }

    // Upgrades the DB, deleting recreating the DB (since we don't need the method to do anything special)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEERS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
//        onCreate(db);
    }

    // Method for adding a new Beer to the DB
    public void addBeer(Beer beer) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, beer.getBeerName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_BEERS, null, values);
    }


    //Μέθοδος για εύρεση προιόντος βάσει ονομασίας του
//    public Product findProduct(String productname) {
//        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " +
//                COLUMN_PRODUCTNAME + " = '" + productname + "'";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        Product product = new Product();
//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst();
//            product.setID(Integer.parseInt(cursor.getString(0)));
//            product.setProductName(cursor.getString(1));
//            product.setQuantity(Integer.parseInt(cursor.getString(2)));
//            cursor.close();
//        } else {
//            product = null;
//        }
//        db.close();
//        return product;
//    }

    //Μέθοδος για διαγραφή προιόντος βάσει ονομασίας του
//    public boolean deleteProduct(String productname) {
//        boolean result = false;
//        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " +
//                COLUMN_PRODUCTNAME + " = '" + productname + "'";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        Product product = new Product();
//        if (cursor.moveToFirst()) {
//            product.setID(Integer.parseInt(cursor.getString(0)));
//            db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?",
//                    new String[] { String.valueOf(product.getID()) });
//            cursor.close();
//            result = true;
//        }
//        db.close();
//        return result;
//    }

    // Returns all the Beer objects from the DB
    List<Beer> getAllBeers() {
        String query = "SELECT * FROM " + TABLE_BEERS ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Beer> beers = new ArrayList<>();
        while (cursor.moveToNext())
            beers.add(new Beer(cursor.getString(1), R.drawable.ic_local_drink_black_24dp)); // Column 1 contains the name of the beer, image is going to change
        cursor.close();
        return beers;
    }

    // Methods used in the My Stats fragment
    // Add new tasted beer
    // TODO
    public boolean addTastedBeer() {
        return true;
    }

    // Add session's litres to the DB
    // Returns true if the session's litres were successfully saved in the user DB
    boolean addLitres(double litres) {
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            double alreadyDrankLitres = cursor.getDouble(2);
            alreadyDrankLitres += litres;
            ContentValues litresUpdate = new ContentValues();
            litresUpdate.put(COLUMN_LITRES_CONSUMED, alreadyDrankLitres);
            db.update(TABLE_USER, litresUpdate, null, null);
            Log.d("LITRES","ADDED");
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
            long alreadyTimeSpentDrinking = cursor.getLong(3);
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
            bestSession = cursor.getDouble(4);
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
    // TODO
    int getTotalTastedBeers() {
        return 0;
    }

    // Return total litres that were consumed
    double getTotalLitres() {
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            double totalLitres = cursor.getDouble(2);
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
            long totalTime = cursor.getLong(3);
            cursor.close();
            return totalTime;
        }
        cursor.close();
        return 0;
    }

    // Reset the whole user's journey data from the db.
    // Including beers tasted, and user stats
    void resetUserData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BEERS_TASTED, 0);
        db.update(TABLE_USER, cv, null, null);
        resetUserStats();
    }

    // Reset user drinking session stats
    void resetUserStats() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LITRES_CONSUMED, 0);
        cv.put(COLUMN_TOTAL_TIME, 0);
        cv.put(COLUMN_BEST_SESSION, 0);
        db.update(TABLE_USER, cv, null, null);
    }

}