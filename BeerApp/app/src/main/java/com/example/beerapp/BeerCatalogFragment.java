package com.example.beerapp;

public class BeerCatalogFragment extends CatalogFragment {

    public BeerCatalogFragment(){
        //  Gives the id of the layout that inflates the fragment, the list view used for the beer
        // catalog and the item id used in the list view to the super class constructor
        super(R.layout.fragment_beer_catalog, R.id.beerListView, R.layout.row_beer_item);
    }
}