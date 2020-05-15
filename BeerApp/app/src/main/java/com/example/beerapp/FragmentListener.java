package com.example.beerapp;

import java.util.List;

// Interface used to force classes to implement its methods which are used in the fragments
interface FragmentListener {

    // Returns a List including all beers
    List<Beer> getBeerList();

    // Returns a List including tasted beers
    List<Beer> getTastedBeerList();

    // Returns the DB handler
    DBHandler getDbHandler();

    // Updates beers list and beers tasted list with the from the database
    void updateBeerLists();
}
