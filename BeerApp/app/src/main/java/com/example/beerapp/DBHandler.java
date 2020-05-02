package com.example.beerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    // For users table
    private static final String TABLE_USER = "user";
    private static final String COLUMN_BEERS_TASTED = "tasted";
    private static final String COLUMN_LITRES_CONSUMED = "litres";
    private static final String COLUMN_TOTAL_TIME = "time";
    private static final String COLUMN_BEST_SESSION = "best_session";

    // Constructor
    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    // Creates the structure of the DB
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BEERS_TABLE = "CREATE TABLE " +
                TABLE_BEERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT," +
                COLUMN_MANUFACTURER + " TEXT," +
                COLUMN_COUNTRY + " TEXT," +
                COLUMN_ABV + " FLOAT," +
                COLUMN_TYPE + " TEXT" + ")";
        db.execSQL(CREATE_BEERS_TABLE);

        String CREATE_USER_TABLE = "CREATE TABLE " +
                TABLE_USER + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_BEERS_TASTED + " INTEGER," +
                COLUMN_LITRES_CONSUMED + " DOUBLE," +
                COLUMN_TOTAL_TIME + " BIGINT," +
                COLUMN_BEST_SESSION + " DOUBLE" + ")";
        db.execSQL(CREATE_USER_TABLE);

        ContentValues values = new ContentValues();
        values.put(COLUMN_BEERS_TASTED, 0);
        values.put(COLUMN_LITRES_CONSUMED, 0);
        values.put(COLUMN_TOTAL_TIME, 0);
        values.put(COLUMN_BEST_SESSION, 0);
        db.insert(TABLE_USER, null, values);

    }

    // Upgrades the DB, deleting recreating the DB (since we don't need the method to do anything special)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    // Method for adding a new Beer to the DB
    public void addBeer(Beer beer) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, beer.getBeerName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_BEERS, null, values);
//        db.close();
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
//        db.close();
        return beers;
    }

    // These functions are about the My Stats fragment
    // Add new tasted beer
    public boolean addTastedBeer() {
        // TO DO
        return true;
    }

    // Add session's litres to the DB
    public boolean addLitres(double litres) {
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            double alreadyDrankLitres = cursor.getDouble(2);
            Log.d("Already Drank Litres", String.format("%.2f L", alreadyDrankLitres));
            Log.d("Litres to add", String.format("%.2f L", litres));
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

    // Add session's time to the DB
    // The time is in seconds
    public boolean addSessionTime(long timeSpentDrinking) {
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            long alreadyTimeSpentDrinking = cursor.getLong(3);
            Log.d("Already Time", String.valueOf(alreadyTimeSpentDrinking));
            Log.d("Time to add", String.valueOf(timeSpentDrinking));
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

    // Add best session
    public boolean bestSession(double currentSession) {
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            double bestSession = cursor.getDouble(4);
            if (currentSession > bestSession) {
                ContentValues bestSessionUpdate = new ContentValues();
                bestSessionUpdate.put(COLUMN_BEST_SESSION, currentSession);
                db.update(TABLE_USER, bestSessionUpdate, null, null);
            }
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    // Return the total number of tasted beers
    public int getTotalTastedBeers() {
        // TO DO
        return 0;
    }

    // Return total litres that were consumed
    public double getTotalLitres() {
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
    public long getTotalTime() {
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

    // Return the best session
    public double getBestSession() {
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            double bestSession = cursor.getDouble(4);
            cursor.close();
            return bestSession;
        }
        cursor.close();
        return 0.0;
    }

}
