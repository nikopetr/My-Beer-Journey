package com.example.beerapp;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BeerCatalogFragment extends SerializableFragment {

//    private BeerArrayAdapter beerListArrayAdapter; // Array adapter for the beer list
    private List<Beer> beerList;

    public BeerCatalogFragment() {
        Log.d("BeerCatalogFragment","BeerCatalogFragment object created");

        beerList = new ArrayList<>();
        beerList.add(new Beer("keo", R.drawable.ic_local_drink_black_24dp));
        beerList.add(new Beer("alfa", R.drawable.ic_local_drink_black_24dp));
        beerList.add(new Beer("corona", R.drawable.ic_local_drink_black_24dp));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i("BeerCatalogFragment","BeerCatalogFragment view created");

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_beer_catalog, container, false);

        ListView beerListView = fragmentView.findViewById(R.id.beerListView);
        final BeerArrayAdapter beerListArrayAdapter = new BeerArrayAdapter(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, beerList); // Array adapter for the beer list
        beerListView.setAdapter(beerListArrayAdapter);
        beerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seeBeerDetailsScreen(null);
            }
        });

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


    void seeBeerDetailsScreen(View view)
    {
        //Create the Intent to start the SayHelloNewScreen Activity
        Intent intent = new Intent(getContext(), BeerDetailsActivity.class);
        //Pass data to the SayHelloNewScreen Activity through the Intent
        Beer testBeer = new Beer("KEOTEST",R.drawable.ic_local_drink_black_24dp);
        intent.putExtra("selectedBeer", testBeer);
        //Ask Android to start the new Activity
        startActivity(intent);

    }

}