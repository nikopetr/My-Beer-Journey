package com.example.beerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SettingsFragment extends Fragment {

    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize the view
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize the db
        final DBHandler dbHandler = new DBHandler(Objects.requireNonNull(getActivity()), null);

        // Initialize the button variables
        Button resetJourneyButton;
        Button resetStatsButton;
        // Find the Reset Journey button and set the onClick function to be called
        resetJourneyButton = rootView.findViewById(R.id.resetJourneyButton);
        resetJourneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user wants to reset everything
                dbHandler.resetJourney();
            }
        });

        // Find the Reset Stats button and set the onClick functions to be called
        resetStatsButton = rootView.findViewById(R.id.resetStatsButton);
        resetStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user wants to only reset the stats but not the different tasted beers
                dbHandler.resetStats();
            }
        });

        // Initialize the expandable list view
        ExpandableListView listView = (ExpandableListView)rootView.findViewById(R.id.expandableFAQList);
        initData();
        ExpandableListAdapter listAdapter = new ExpandableListAdapter(rootView.getContext(), listDataHeader, listHash);
        listView.setAdapter(listAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }

    private void initData() {
        // Initialize the ArrayList and the HashMap
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        // Add the three questions to the listDataHeader
        // The three questions will be the header of each section of the view
        listDataHeader.add("1) What is the point of the application?");
        listDataHeader.add("2) How do I start my beer journey?");
        listDataHeader.add("3) Can I reset my stats?");

        // Initialize the answer of the first question to the list
        List<String> firstAnswer = new ArrayList<>();
        firstAnswer.add("The application is made for all the beer lovers who want to explore different beers from all across the globe.");

        // Initialize the answer of the second question to the list
        List<String> secondQuestion = new ArrayList<>();
        secondQuestion.add("Just head to the Drink Session section and when you start drinking beer press the START SESSION button and select the beer you are drinking." +
                "When you are done drinking press the STOP SESSION button to save your drinking session.");

        // Initialize the answer of the third question
        List<String> thirdQuestion = new ArrayList<>();
        thirdQuestion.add("If you want to reset only your stats but not your tasted beers count press on the RESET STATS button in Settings section." +
                "If you want to start your beer journey from the beginning press on the START OVER button.");

        // Put the question-answer pairs in the hashmap
        listHash.put(listDataHeader.get(0), firstAnswer);
        listHash.put(listDataHeader.get(1), secondQuestion);
        listHash.put(listDataHeader.get(2), thirdQuestion);
    }

}