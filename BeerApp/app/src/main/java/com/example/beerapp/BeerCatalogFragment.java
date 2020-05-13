package com.example.beerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class BeerCatalogFragment extends Fragment {

    private NameMustChange activityCallBack; // Activity that this fragment is attached to
    private BeerArrayAdapter beerListArrayAdapter; // Array adapter for the beer list
    private List<Beer> beerList; // List including the Beer objects
    private Beer beerSelected; // The beer that the user selects to see it's details



    public BeerCatalogFragment(){
        // Required empty public constructor in for onCreate(savedInstanceState) of the activity which has the fragment
    }

    // Checks if the activity implements the interface otherwise throw exception
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            activityCallBack = (NameMustChange) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement interface<<INTERFACE NAME>>"); //TODO CHANGE NAME
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_beer_catalog, container, false);

        if(savedInstanceState != null)
            beerSelected = (Beer)savedInstanceState.getSerializable("beerSelected");
        else
            beerSelected = null;

        // Initializing from main activity
        this.beerList = activityCallBack.getBeerList();

        ListView beerListView = rootView.findViewById(R.id.beerListView);
        // Initializing Array adapter for the beer list
        beerListArrayAdapter = new BeerArrayAdapter(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, beerList, R.layout.row_beer_item); // Array adapter for the beer list
        beerListView.setAdapter(beerListArrayAdapter);
        beerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                moveToBeerDetailsScreen(position);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("beerSelected",beerSelected);
    }

    // After returning from BeerDetailsActivity,
    // updates the array adapter if changes occurred
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If this is the code assigned to BeerDetailsActivity and returning Intent succeeded
        if ((requestCode == 5) && (resultCode == RESULT_OK))
        {
            // Get saved beer's taste state from the returning Intent
            boolean tasted = Objects.requireNonNull(data.getExtras()).getBoolean("tasted");
            beerSelected.setTasted(tasted);
        }
    }

    // Method for creating intent and passing the Beer object to the new activity
    private void moveToBeerDetailsScreen(int position)
    {
        beerSelected = beerList.get(position);
        // Create the Intent to start new Activity
        Intent intent = new Intent(getContext(), BeerDetailsActivity.class);
        // Pass data to the Activity through the Intent
        intent.putExtra("selectedBeer", beerSelected);
        // Ask Android to start the new Activity
        startActivityForResult(intent, 5);
    }

}