package com.example.beerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // Constants used for the DB
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "beersDB.db";
    private static final String TABLE_BEERS = "beers";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MANUFACTURER = "manufacturer";
    private static final String COLUMN_COUNTRY = "country";
    private static final String COLUMN_ABV = "abv";
    private static final String COLUMN_TYPE = "type";

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
    }

    // Upgrades the DB, deleting recreating the DB (since we don't need the method to do anything special)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEERS);
        onCreate(db);
    }

    // Method for adding a new Beer to the DB
    public void addBeer(Beer beer) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, beer.getBeerName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_BEERS, null, values);
        db.close();
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
        db.close();
        return beers;
    }

}
