package com.example.beerapp;

import java.util.List;

interface FragmentListener {

    // Returns a List including all beers
    List<Beer> getBeerList();

    // Returns a List including tasted beers
    List<Beer> getTastedBeerList();

    // Returns the DB handler
    DBHandler getDbHandler();
}
