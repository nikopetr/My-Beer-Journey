package com.example.beerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Objects;


public class MyBeerListFragment extends Fragment {

    private BeerArrayAdapter beerListArrayAdapter; // Array adapter for the beer list
    private List<Beer> beerList; // List including the Beer objects

    public MyBeerListFragment( ) {
        // Required empty constructor to call Fragment's constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_beer_list, container, false);

        // Gets the dbHandler from the main activity
        DBHandler dbHandler = ((MainActivity) Objects.requireNonNull(getActivity())).getDbHandler();
        this.beerList = dbHandler.getTastedBeers();

        GridView beerListView = rootView.findViewById(R.id.beerGridView);
        // Initializing Array adapter for the beer list
        beerListArrayAdapter = new BeerArrayAdapter(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, beerList, R.layout.grid_beer_item); // Array adapter for the beer list
        beerListView.setAdapter(beerListArrayAdapter);
        beerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seeBeerDetailsScreen(beerList.get(position));
            }
        });

        SearchView searchView = rootView.findViewById(R.id.searchView); // Initializes the search item
        searchView.setQueryHint("Search for a beer");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                beerListArrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SearchView) view).onActionViewExpanded();
            }
        });
        return rootView;
    }


    // Method for creating intent and passing the Beer object to the new activity
    private void seeBeerDetailsScreen(Beer beerSelected)
    {
        // Create the Intent to start new Activity
        Intent intent = new Intent(getContext(), BeerDetailsActivity.class);
        // Pass data to the  Activity through the Intent
        intent.putExtra("selectedBeer", beerSelected);
        // Ask Android to start the new Activity
        startActivity(intent);
    }

}