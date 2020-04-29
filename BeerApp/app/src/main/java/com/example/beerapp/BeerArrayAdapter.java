package com.example.beerapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

//This class is used to populating data for ListVIew.
public class BeerArrayAdapter extends ArrayAdapter<String> {
    private List<String> beerNames;
    private List<Integer> imageId;
    private Activity context;
    private Filter beerNameFitler;

    public BeerArrayAdapter(Activity context, int resource, final List<String> beerNames, List<Integer> imageId) {
        super(context, resource, beerNames);
        this.context = context;
        this.beerNames = beerNames;
        this.imageId = imageId;

        beerNameFitler = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<String> beerNameSuggestions = new ArrayList<>();

                if (constraint == null || constraint.length() == 0)
                    beerNameSuggestions.addAll(beerNames);
                else
                {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (String beerName : beerNames)
                    {
                        if (beerName.toLowerCase().contains((filterPattern)))
                            beerNameSuggestions.add(beerName);

                    }
                }

                results.values = beerNameSuggestions;
                results.count = beerNameSuggestions.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((List)results.values);
                notifyDataSetChanged();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return (resultValue.toString());
            }
        };
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return beerNameFitler;
    }

    // getView method is get called for drawing each row.
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        Log.i("BeerItemCreated", String.valueOf(position));

        View beerItemView = convertView;
        LayoutInflater inflater = context.getLayoutInflater();

        if(convertView == null)
            beerItemView = inflater.inflate(R.layout.row_beer_item, parent, false);

        TextView beerNameTextView = beerItemView.findViewById(R.id.beetItemTextView);
        ImageView beerImageView = beerItemView.findViewById(R.id.beerItemImageView);
        beerNameTextView.setText(beerNames.get(position));
        beerImageView.setImageResource(R.drawable.ic_local_drink_black_24dp);
        return  beerItemView;
    }
}
