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

// Class used as a custom adapter in order to have an image for each item, for our ListVIew or GridView of Beers
public class BeerArrayAdapter extends ArrayAdapter<Beer> {
    private List<Beer> beerList;
    private List<Beer> adaptedBeerList;
    private Activity context;
    private Filter beerNameFilter;
    private int itemViewId; // The id item that is used for this adapter

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
                    beer.getCountry(), beer.getAbv(), beer.getType(), beer.isTasted(), beer.getBeerImageId()));

        // Using a custom filter in order to be able to search for a beer and get the results based on the beerName
        beerNameFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Beer> beerSuggestions = new ArrayList<>();

//                For debugging: print in the logcat (Debug level)
//                for (Beer beer : BeerArrayAdapter.this.beerList)
//                    Log.d("Beers in list",beer.getName());

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

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll( (List<Beer>) results.values);
                notifyDataSetChanged();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return (((Beer)(resultValue)).getName());
            }
        };
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return beerNameFilter;
    }

    // Method called for drawing each row.
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

//        For debugging: print in the logcat (Debug level)
//        Log.d("BeerItemCreated", String.valueOf(position));

        View beerItemView = convertView;
        LayoutInflater inflater = context.getLayoutInflater();

        if(convertView == null)
            beerItemView = inflater.inflate(itemViewId, parent, false);

        TextView beerNameTextView = beerItemView.findViewById(R.id.beetItemTextView);
        ImageView beerImageView = beerItemView.findViewById(R.id.beerItemImageView);
        beerNameTextView.setText(adaptedBeerList.get(position).getName());
        beerImageView.setImageResource(adaptedBeerList.get(position).getBeerImageId());
        return  beerItemView;
    }
}
