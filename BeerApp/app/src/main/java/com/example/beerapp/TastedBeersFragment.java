package com.example.beerapp;

public class TastedBeersFragment extends CatalogFragment {

    public TastedBeersFragment(){
        //  Gives the id of the layout that inflates the fragment, the grid view used for the tasted
        //  beer list and the item id used in the grid view to the super class constructor
        super(R.layout.fragment_tasted_beers, R.id.beerGridView, R.layout.grid_beer_item);
    }
}

