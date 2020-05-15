package com.example.beerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.SearchView;


import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public abstract class CatalogFragment extends Fragment {

    private FragmentListener activityCallBack; // Activity that this fragment is attached to
    private BeerArrayAdapter beerListArrayAdapter; // Array adapter for the beer list
    private AbsListView beerListView; // The view used to present the beers
    private int inflateResourceId; // The id of the XML file which the layout of the fragment is inflated
    private int listViewId; // The id of the list used for the beerListView
    private int itemViewId; // The id of the item used in the list view

    public CatalogFragment(){
        // Required empty public constructor in for onCreate(savedInstanceState) of the activity which has the fragment
    }

    CatalogFragment(int inflateResourceId, int listViewId, int itemViewId){
        this.inflateResourceId = inflateResourceId;
        this.listViewId = listViewId;
        this.itemViewId = itemViewId;
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
        View rootView = inflater.inflate(inflateResourceId, container, false);

        // Initializing the view used to present the beers
        beerListView = rootView.findViewById(listViewId);

        // If the fragment's layout used is the one for the tasted beers
        // then set the emptyTextView when the list is empty in order to show that the lists has no beers
        if (inflateResourceId == R.layout.fragment_tasted_beers)
            beerListView.setEmptyView(rootView.findViewById(R.id.emptyTextView));

        // Initializing Array adapter for the beer list
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
        // TODO Add x, when clicked in body, to close the searchView
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
        // If the request code is the code assigned to BeerDetailsActivity and the result code is RESULT_OK
        // then updating the list and the adapter since changes occurred
        if ((requestCode == 5) && (resultCode == RESULT_OK))
        {
            // Updates beer list and beers tasted list from the database after the changes
            activityCallBack.updateBeerLists();

            // Initializing Array adapter for the beer list
            initializeBeerListArrayAdapter();
        }
    }

    // Initializing Array adapter for the beer list
    private void initializeBeerListArrayAdapter()
    {
        // Get the beer list from main activity
        List<Beer> beerList;

        // If the fragment's layout used is the one for the tasted beers
        // then get only the tasted beers
        if (inflateResourceId == R.layout.fragment_tasted_beers)
            beerList = activityCallBack.getTastedBeerList();
        else
            beerList = activityCallBack.getBeerList();

        beerListArrayAdapter = new BeerArrayAdapter(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, beerList, itemViewId); // Array adapter for the beer list
        beerListView.setAdapter(beerListArrayAdapter);
        beerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                moveToBeerDetailsScreen(position);
            }
        });
    }

    // Method for creating intent and passing the Beer object to the new activity
    private void moveToBeerDetailsScreen(int position)
    {
        // Create the Intent to start new Activity
        Intent intent = new Intent(getContext(), BeerDetailsActivity.class);
        // Pass data to the Activity through the Intent
        intent.putExtra("selectedBeer", beerListArrayAdapter.getItem(position));
        // Ask Android to start the new Activity
        startActivityForResult(intent, 5);
    }

    // Used for refreshing the arrayAdapter when changing fragments
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Set empty text to the filter so the result will be all beers
        beerListArrayAdapter.getFilter().filter("");
    }
}