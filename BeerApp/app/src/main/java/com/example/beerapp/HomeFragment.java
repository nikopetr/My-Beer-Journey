package com.example.beerapp;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment {

    private ListView beerListView;
    private BeerArrayAdapter beerListArrayAdapter; // Array adapter for the beer list
    private List<String> beerList;

    public HomeFragment() {
        Log.i("HomeFragment","HomeFragment object created");

        beerList = new ArrayList<>();
        beerList.add("keo"); beerList.add("alfa"); beerList.add("corona");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i("HomeFragment","HomeFragment view created");

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);

        beerListView = fragmentView.findViewById(R.id.beerListView);
        beerListArrayAdapter = new BeerArrayAdapter(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, beerList, null); // Array adapter for the beer list
        beerListView.setAdapter(beerListArrayAdapter);

        SearchView searchView = fragmentView.findViewById(R.id.searchView); // Initializes the search item
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
        return fragmentView;
    }

}