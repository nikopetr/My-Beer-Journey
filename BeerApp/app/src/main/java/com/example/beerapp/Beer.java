package com.example.beerapp;

import java.io.Serializable;

// Class used to represent a beer
class Beer implements Serializable {
    private int _id;
    private String name;
    private String manufacturer;
    private String country;
    private float abv;
    private String type; // String which represents the type of the beer
    private int beerImageId; // int used for the image of the specific beer
    private boolean tasted; // boolean that represents if the user has tasted this beer or not

    Beer()
    {

    }

    Beer(int _id, String name, String manufacturer, String country, float abv, String type,boolean tasted, int beerImageId) {
        this._id = _id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.country = country;
        this.abv = abv;
        this.type = type;
        this.beerImageId = beerImageId;
        this.tasted =  tasted;
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

    void setBeerImageId(int beerImageId) {
        this.beerImageId = beerImageId;
    }

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

    int getBeerImageId() {
        return beerImageId;
    }
}
