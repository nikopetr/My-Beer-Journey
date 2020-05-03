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

// Class used for the settings fragments.
// Shows useful information about the application and has reset data options
public class SettingsFragment extends Fragment {

    private List<String> listDataHeader; // List used for the data headers
    private HashMap<String, List<String>> listHash;  // Hash map used to pair the answers with the questions of the FAQ
    private DBHandler dbHandler; // Database Helper

    public SettingsFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize the root view
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize the db handler
        dbHandler = new DBHandler(Objects.requireNonNull(getActivity()), null);

        // Initialize the buttons
        Button resetJourneyButton;
        // Find the Reset Journey button and set the onClick function to be called
        resetJourneyButton = rootView.findViewById(R.id.resetJourneyButton);
        resetJourneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user wants to reset everything
                dbHandler.resetUserData();
            }
        });

        Button resetStatsButton;
        // Find the Reset Stats button and set the onClick functions to be called
        resetStatsButton = rootView.findViewById(R.id.resetStatsButton);
        resetStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user wants to only reset the stats but not the different tasted beers
                dbHandler.resetUserStats();
            }
        });

        // Initialize the expandable list view
        ExpandableListView listView = rootView.findViewById(R.id.expandableFAQList);
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