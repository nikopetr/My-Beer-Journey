package com.example.beerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class TastedBeersFragment extends Fragment {

    private FragmentListener activityCallBack; // Activity that this fragment is attached to
    private BeerArrayAdapter beerListArrayAdapter; // Array adapter for the beer list
    private GridView beerListView; // The view used to present the tasted beers

    public TastedBeersFragment( ) {
        // Required empty public constructor in for onCreate(savedInstanceState) of the activity which has the fragment
    }

    // Checks if the activity implements the interface otherwise throw exception
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            activityCallBack = (FragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement interface FragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tasted_beers, container, false);

        // Initializing the view used to present the tasted beers
        beerListView = rootView.findViewById(R.id.beerGridView);

        // Sets the emptyTextView when the list is empty in order to show that the lists has no beers
        beerListView.setEmptyView(rootView.findViewById(R.id.emptyTextView));

        // Initializing Array adapter for the tasted beer list
        initializeBeerListArrayAdapter();

        // Initializing search view
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

    // After returning from BeerDetailsActivity,
    // updates the array adapter if changes occurred
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If this is the code assigned to BeerDetailsActivity and returning Intent succeeded
        if ((requestCode == 5) && (resultCode == RESULT_OK))
        {
            // Updates beer list and beers tasted list from the database after the changes
            activityCallBack.updateBeerLists();

            // Initializing Array adapter for the tasted beer list
            initializeBeerListArrayAdapter(); //TODO check if there is a better way doing this instead of making the array adapter from the start
        }
    }

    // Initializing Array adapter for the tasted beer list
    private void initializeBeerListArrayAdapter()
    {
        // Get the tasted beer list from main activity
        List<Beer> tastedBeerList = activityCallBack.getTastedBeerList();
        beerListArrayAdapter = new BeerArrayAdapter(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, tastedBeerList, R.layout.grid_beer_item); // Array adapter for the beer list
        beerListView.setAdapter(beerListArrayAdapter);
        beerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                moveToBeerDetailsScreen(position);
            }
        });
    }

    // Method for creating intent and passing the Beer object to the new activity
    private void moveToBeerDetailsScreen(int position )
    {
        // Create the Intent to start new Activity
        Intent intent = new Intent(getContext(), BeerDetailsActivity.class);
        // Pass data to the  Activity through the Intent
        intent.putExtra("selectedBeer", beerListArrayAdapter.getItem(position));
        // Ask Android to start the new Activity
        startActivityForResult(intent, 5);
    }
}