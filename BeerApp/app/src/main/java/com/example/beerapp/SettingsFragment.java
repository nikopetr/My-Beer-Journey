package com.example.beerapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

// Class used for the settings fragments.
// Shows useful information about the application and has reset data options
public class SettingsFragment extends Fragment {

    public SettingsFragment( ) {
        // Reburied empty constructor to call Fragment's constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize the root view
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize the buttons
        Button resetJourneyButton;
        // Find the Reset Journey button and set the onClick function to be called
        resetJourneyButton = rootView.findViewById(R.id.resetJourneyButton);
        resetJourneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user wants to reset everything
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(true);
                assert getFragmentManager() != null;
                confirmationDialog.show(getFragmentManager(), "confirmation dialog");
            }
        });

        Button resetStatsButton;
        // Find the Reset Stats button and set the onClick functions to be called
        resetStatsButton = rootView.findViewById(R.id.resetStatsButton);
        resetStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user wants to only reset the stats but not the different tasted beers
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(false);
                assert getFragmentManager() != null;
                confirmationDialog.show(getFragmentManager(), "confirmation dialog");
            }
        });

        return rootView;
    }

}