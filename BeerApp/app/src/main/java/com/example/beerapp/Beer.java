package com.example.beerapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.sql.Blob;

// Class used to represent a beer
class Beer implements Serializable {
    private int _id;
    private String name;
    private String manufacturer;
    private String country;
    private float abv;
    private String type; // String which represents the type of the beer
    private boolean tasted; // boolean that represents if the user has tasted this beer or not
    private SerialBitmap beerImage; // bitmap for the image in list
    private SerialBitmap beerImageHD; // bitmap for the image in details

    Beer()
    {

    }

    Beer(int _id, String name, String manufacturer, String country, float abv, String type,boolean tasted, SerialBitmap beerImage, SerialBitmap beerImageHD) {
        this._id = _id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.country = country;
        this.abv = abv;
        this.type = type;
        this.tasted =  tasted;
        this.beerImage = beerImage;
        this.beerImageHD = beerImageHD;
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

    void setBeerImageHD(byte[] beerImageHDBytes) {this.beerImageHD = new SerialBitmap(beerImageHDBytes);}

    public boolean isTasted() {
        return tasted;
    }

    public void setTasted(boolean tasted) {
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


    Bitmap getBeerImage() {
        if (beerImage == null)
            return null;
        return beerImage.getBitmap();
    }

    Bitmap getBeerImageHD() {
        if (beerImageHD == null)
            return null;
        return beerImageHD.getBitmap();
    }

    SerialBitmap getBeerImageSerial() {return beerImage;}

    SerialBitmap getBeerImageHDSerial() {return beerImageHD;}
}
