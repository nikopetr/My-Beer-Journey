package com.example.beerapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

// Fragment used for displaying user's drinking stats, and for having a new drink session
public class DrinkSessionsFragment extends Fragment {

    private static final double PINT_TO_LITRE = 0.5;
    private static final double HALF_PINT_TO_LITRE = 0.3;

    // Initialize variables
    private DBHandler dbHandler;  // Database
    private View rootView; // The root view of the fragment, used to get the rest view components
    private Chronometer sessionChronometer; // Chronometer used to count the time in a session
    private boolean isDrinking; // Represents if the user is currently in a drink session
    private double totalLitresDrank; // The total amount of beer the user has consumed during a drink session
    private TextView differentBeersTextView; // For showing the different beers tasted (GOING TO ADD IT LATER)

    public DrinkSessionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_drink_sessions, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialize the db
        this.dbHandler = new DBHandler(Objects.requireNonNull(getActivity()), null);
        // Initialize the chronometer
        this.sessionChronometer = rootView.findViewById(R.id.sessionChronometer);

        // Find the START/STOP SESSION button and set the onClick methods to be called
        Button sessionButton = rootView.findViewById(R.id.startSessionButton);
        sessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user is drinking then we stop the session, otherwise a session starts
                if (isDrinking)
                    stopDrinkSession();
                else
                    startDrinkSession(SystemClock.elapsedRealtime());
            }
        });

        // Set on click method to the ADD PINT button
        Button addPintButton = rootView.findViewById(R.id.addPintButton);
        addPintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBeerPint(false);
            }
        });
        // Set on click method to the ADD HALF PINT button
        Button addHalfPintButton = rootView.findViewById(R.id.addHalfPintButton);
        addHalfPintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBeerPint(true);
            }
        });

        // Initialize the text views for the stats from the db data
        // Initialize different beers consumed text from the db data
        ((TextView)rootView.findViewById(R.id.totalBeersTastedTextView)).setText(String.valueOf(dbHandler.getTotalTastedBeers()));
        // Initialize total beer consumed text
        ((TextView)rootView.findViewById(R.id.beerConsumedNumberTextView)).setText(String.format(getString(R.string.litres_format_string), dbHandler.getTotalLitres()));
        // Initialize the time text
        ((TextView)rootView.findViewById(R.id.totalTimeSpentNumberTextView)).setText(getConvertedTime());
        // Initialize the best session text
        ((TextView)rootView.findViewById(R.id.bestSessionTextView)).setText(String.format(getString(R.string.litres_format_string), dbHandler.getBestSession()));

        if (savedInstanceState != null) {
            this.isDrinking = savedInstanceState.getBoolean("isDrinking");
            this.totalLitresDrank = savedInstanceState.getDouble("totalLitresDrank");
            if (isDrinking)
            {
                // Starting the drinking session continuing from the previous base
                startDrinkSession(savedInstanceState.getLong("chronometerBase"));
                ((TextView)rootView.findViewById(R.id.litresDrankTextView)).setText(String.format(getString(R.string.litres_format_string), totalLitresDrank));
            }
            else
            {
                disableSessionComponents();
            }
        }
        else
        {
            this.isDrinking = false;
            this.totalLitresDrank = 0;
        }
    }

    // Used to save the on going session's information
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isDrinking",isDrinking);
        outState.putLong("chronometerBase",sessionChronometer.getBase());
        outState.putDouble("totalLitresDrank",totalLitresDrank);
    }


    // Method called when the button START SESSION is pressed to start the chronometer at the given base time and enable the view components
    private void startDrinkSession(long baseTime) {
        // Reset the chronometer
        sessionChronometer.setBase(baseTime);
        // Start the chronometer
        sessionChronometer.start();
        // Enable the chronometer
        sessionChronometer.setEnabled(true);
        // Change the button to show "STOP SESSION" with red background
        Button sessionButton = rootView.findViewById(R.id.startSessionButton);
        sessionButton.setText(getResources().getString(R.string.stop_session));
        sessionButton.setBackgroundColor(Color.parseColor("#bd0000"));
        // Enable the "Time Drinking" text
        rootView.findViewById(R.id.timeDrinkingTextView).setEnabled(true);
        // Enable the "Total beer drank" text
        rootView.findViewById(R.id.beerDrankTextView).setEnabled(true);
        // Enable the litres number text
        rootView.findViewById(R.id.litresDrankTextView).setEnabled(true);
        // Enable the add pint buttons
        rootView.findViewById(R.id.addHalfPintButton).setEnabled(true);
        rootView.findViewById(R.id.addPintButton).setEnabled(true);

        isDrinking = true;
    }

    // Method for disabling the view components used in a drink session
    private void disableSessionComponents()
    {
        // Disable the chronometer
        sessionChronometer.setEnabled(false);
        // Change the button to show "START SESSION" with green background
        Button sessionButton = rootView.findViewById(R.id.startSessionButton);
        sessionButton.setText(getResources().getString(R.string.start_session));
        sessionButton.setBackgroundColor(Color.parseColor("#4caf50"));
        // Disable the "Time Drinking" text
        rootView.findViewById(R.id.timeDrinkingTextView).setEnabled(false);
        // Disable the "Total beer drank" text
        rootView.findViewById(R.id.beerDrankTextView).setEnabled(false);
        // Disable and reset the litres number text
        rootView.findViewById(R.id.litresDrankTextView).setEnabled(false);
        ((TextView)rootView.findViewById(R.id.litresDrankTextView)).setText("0 L");
        // Disable the add pint buttons
        rootView.findViewById(R.id.addHalfPintButton).setEnabled(false);
        rootView.findViewById(R.id.addPintButton).setEnabled(false);
    }

    // Method called when the button STOP SESSION is pressed to stop the chronometer,
    // save(update) the current's drink session outcomes and disable the view components
    private void stopDrinkSession() {
        // Save the values to variables to be stored in the DB
        dbHandler.addLitres(totalLitresDrank);

        // Time elapsed in millis
        long elapsedMillis = SystemClock.elapsedRealtime() - sessionChronometer.getBase();
        // Save the time of the session (in seconds) to the DB
        dbHandler.addSessionTime(elapsedMillis / 1000);

        // Add the session's litres in the DB if it is the best session
        dbHandler.bestSession(totalLitresDrank);

        // Reset the variables' values
        sessionChronometer.setBase(SystemClock.elapsedRealtime());
        totalLitresDrank = 0;
        // Stop the chronometer
        sessionChronometer.stop();

        disableSessionComponents();

        // Update the stats
        // Update total litres text
        ((TextView)rootView.findViewById(R.id.beerConsumedNumberTextView)).setText(String.format(getString(R.string.litres_format_string), dbHandler.getTotalLitres()));
        // Update the time text
        ((TextView)rootView.findViewById(R.id.totalTimeSpentNumberTextView)).setText(getConvertedTime());
        // Update best session text
        ((TextView)rootView.findViewById(R.id.bestSessionTextView)).setText(String.format(getString(R.string.litres_format_string), dbHandler.getBestSession()));

        this.isDrinking = false;
    }

    // Method called to add half or a pint of beer
    private void addBeerPint(boolean isHalf) {
        if (isHalf)
            totalLitresDrank += HALF_PINT_TO_LITRE;
        else
            totalLitresDrank += PINT_TO_LITRE;
        ((TextView)rootView.findViewById(R.id.litresDrankTextView)).setText(String.format(getString(R.string.litres_format_string), totalLitresDrank));

    }

    // Method used to convert time from seconds to days, hours, minutes and seconds
    // The time that is converted is the total time from the DB
    // Return a string with the correct format of the time
    private String getConvertedTime() {
        // Initialize the string
        String timeString = "";
        // Get the total time in seconds
        long totalTimePassed = dbHandler.getTotalTime();
        // Convert total time to days, hours, minutes and seconds
        int days = (int) TimeUnit.SECONDS.toDays(totalTimePassed);
        long hours = TimeUnit.SECONDS.toHours(totalTimePassed) - (days *24);
        long minutes = TimeUnit.SECONDS.toMinutes(totalTimePassed) - (TimeUnit.SECONDS.toHours(totalTimePassed)* 60);
        long seconds = TimeUnit.SECONDS.toSeconds(totalTimePassed) - (TimeUnit.SECONDS.toMinutes(totalTimePassed) *60);
        // Create the string
        // For days
        if (days == 1)
            timeString += days + " day ";
        else if (days > 1)
            timeString += days + " days ";
        // For hours
        if (hours == 1)
            timeString += hours + " hour ";
        else
            timeString += hours + " hours ";
        // For minutes
        if (minutes == 1)
            timeString += minutes + " minute ";
        else
            timeString += minutes + " minutes ";
        // For seconds
        if (seconds == 1)
            timeString += seconds + " second";
        else
            timeString += seconds + " seconds";

        return timeString;
    }
}