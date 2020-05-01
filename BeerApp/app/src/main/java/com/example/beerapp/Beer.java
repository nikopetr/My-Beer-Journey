package com.example.beerapp;

import java.io.Serializable;

// Class used to represent a beer
class Beer implements Serializable {
    private String beerName;
    private int beerImageId; // int used for the image of the specific beer (Going to change how we get images after)

    Beer(String beerName, int beerImageId) {
        this.beerName = beerName;
        this.beerImageId = beerImageId;
    }


    String getBeerName() {
        return beerName;
    }

//    public void setBeerName(String beerName) {
//        this.beerName = beerName;
//    }

    int getBeerImageId() {
        return beerImageId;
    }

//    public void setBeerImageId(int beerImageId) {
//        this.beerImageId = beerImageId;
//    }
}
