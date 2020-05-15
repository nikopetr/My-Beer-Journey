package com.example.beerapp;

import android.graphics.Bitmap;

import java.io.Serializable;

// Class used to represent a beer
class Beer implements Serializable {
    private int _id; // The id of the beer
    private String name;
    private String manufacturer;
    private String country;
    private float abv; // Alcohol by volume contained in the beer
    private String type; // Represents the type of the beer
    private boolean tasted; // boolean that represents if the user has tasted this beer or not
    private SerialBitmap beerImage; // bitmap for the image in list

    // Empty constructor of the class
    Beer()
    {

    }

    Beer(int _id, String name, String manufacturer, String country, float abv, String type,boolean tasted, SerialBitmap beerImage) {
        this._id = _id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.country = country;
        this.abv = abv;
        this.type = type;
        this.tasted =  tasted;
        this.beerImage = beerImage;
    }

    void set_id(int _id) {
        this._id = _id;
    }

    void setName(String name) {
        this.name = name;
    }

    void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    void setCountry(String country) {
        this.country = country;
    }

    void setAbv(float abv) {
        this.abv = abv;
    }

    void setType(String type) {
        this.type = type;
    }

    void setBeerImage(byte[] beerImageBytes) {this.beerImage = new SerialBitmap(beerImageBytes);}

    boolean isTasted() {
        return tasted;
    }

    void setTasted(boolean tasted) {
        this.tasted = tasted;
    }

    int get_id() {
        return _id;
    }

    String getName() {
        return name;
    }

    String getManufacturer() {
        return manufacturer;
    }

    String getCountry() {
        return country;
    }

    float getAbv() {
        return abv;
    }

    String getType() {
        return type;
    }

    // Returns the image of the beer in a bitmap
    Bitmap getBeerImage() {
        if (beerImage == null)
            return null;
        return beerImage.getBitmap();
    }

    // Returns the serial bit map of the image for serialized usage
    SerialBitmap getBeerImageSerial() {return beerImage;}
}
