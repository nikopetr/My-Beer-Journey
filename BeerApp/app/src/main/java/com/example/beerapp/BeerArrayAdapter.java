package com.example.beerapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

// Class used as a custom adapter in order to have a beer object for each item, for the AbsListView of Beers
public class BeerArrayAdapter extends ArrayAdapter<Beer> {
    private List<Beer> beerList; // The list which contains all the beers given to the adapter
    private List<Beer> adaptedBeerList; // The list with the beers that are currently showed in the adapter
    private Activity context; // The activity that has the adapter
    private Filter beerNameFilter; // A filter which is used for searching a beer by it's name
    private int itemViewId; // The id item that is used for this adapter

    // Constructor of the class, initializing the variables used by the adapter
    BeerArrayAdapter(Activity context, int resource, List<Beer> beerList, int itemViewId) {
        super(context, resource, beerList);
        this.context = context;
        this.adaptedBeerList = beerList;
        this.beerList = new ArrayList<>();
        this.itemViewId = itemViewId;

        // Performing deep copy for each beer on a new beer list array,
        // to avoid losing the items used on the adapter when we use the search view
        for (Beer beer : beerList)
            this.beerList.add(new Beer(beer.get_id(), beer.getName(), beer.getManufacturer(),
                    beer.getCountry(), beer.getAbv(), beer.getType(), beer.isTasted(), beer.getBeerImageSerial()));

        setUpFilter();
    }

    // Sets up the filter which is used to search for a beer
    private void setUpFilter(){
        // Using a custom filter in order to be able to search for a beer and get the results based on the beerName
        beerNameFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Beer> beerSuggestions = new ArrayList<>();

                if (constraint == null || constraint.length() == 0)
                    beerSuggestions.addAll(BeerArrayAdapter.this.beerList);

                else
                {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Beer beer : BeerArrayAdapter.this.beerList)
                        if (beer.getName().toLowerCase().contains((filterPattern)))
                            beerSuggestions.add(beer);
                }

                results.values = beerSuggestions;
                results.count = beerSuggestions.size();

                return results;
            }

            // Adds the beers found to the results
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll( (List<Beer>) results.values);
                notifyDataSetChanged();
            }

            // Converting the result value to the beer name
            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return (((Beer)(resultValue)).getName());
            }
        };
    }

    @NonNull
    @Override
    // Returns the beer name Filter
    public Filter getFilter() {
        return beerNameFilter;
    }

    // Method called for drawing each item
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View beerItemView = convertView;
        LayoutInflater inflater = context.getLayoutInflater();

        if(convertView == null)
            beerItemView = inflater.inflate(itemViewId, parent, false);

        TextView beerNameTextView = beerItemView.findViewById(R.id.beetItemTextView);
        ImageView beerImageView = beerItemView.findViewById(R.id.beerItemImageView);
        beerNameTextView.setText(adaptedBeerList.get(position).getName());
        if (beerImageView != null && adaptedBeerList != null)
            beerImageView.setImageBitmap(adaptedBeerList.get(position).getBeerImage());
        return  beerItemView;
    }
}
