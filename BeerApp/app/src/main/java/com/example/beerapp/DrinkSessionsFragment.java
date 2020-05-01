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

import java.util.concurrent.TimeUnit;


public class DrinkSessionsFragment extends SerializableFragment {

    private final double PINT_TO_LITRE = 0.5;
    private final double HALF_PINT_TO_LITRE = 0.3;

    // Initialize variables
    private double litresDrank;
    private boolean isDrinking;
    private long timePassed;
    // Components about the first half of the Drink Session
    private Button sessionButton;
    private TextView sessionText;
    private Chronometer sessionChronometer;
    // Components about the second half of the Drink Session
    private Button addPint;
    private Button addHalfPint;
    private TextView beerText;
    private TextView beerNumber;
    private double totalLitresDrank;
    // Stats components
    private TextView totalBeerConsumed;
    private TextView totalTime;


    public DrinkSessionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_drink_sessions, container, false);
        // Initialize variables
        isDrinking = false;
        totalLitresDrank = 0;
        litresDrank = 0;
        timePassed = 0;
        // Find the START/STOP SESSION button and set the onClick functions to be called
        sessionButton = rootView.findViewById(R.id.startSessionButton);
        sessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDrinking)
                    stopSession();
                else
                    startSession();
            }
        });
        // Find the chronometer
        sessionChronometer = rootView.findViewById(R.id.sessionChronometer);
        // Find the textView to get bold when session is started
        sessionText = rootView.findViewById(R.id.timeDrinkingTextView);

        // Find and initialize the ADD PINT button
        addPint = rootView.findViewById(R.id.addPintButton);
        addPint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBeer(false);
            }
        });
        // Find and initialize the ADD HALF PINT button
        addHalfPint = rootView.findViewById(R.id.addHalfPintButton);
        addHalfPint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBeer(true);
            }
        });
        // Find the text views for litres drank
        beerText = rootView.findViewById(R.id.beerDrankTextView);
        beerNumber = rootView.findViewById(R.id.litresDrankTextView);

        // Find the text views for the stats
        totalBeerConsumed = rootView.findViewById(R.id.beerConsumedNumberTextView);
        totalTime = rootView.findViewById(R.id.totalTimeSpentNumberTextView);


        // Inflate the layout for this fragment
        return rootView;
    }



    // This function is called when the button START SESSION is pressed to start the chronometer
    private void startSession() {
        // Reset the chronometer
        sessionChronometer.setBase(SystemClock.elapsedRealtime());
        // Start the chronometer
        sessionChronometer.start();
        // Enable the chronometer
        sessionChronometer.setEnabled(true);
        // Change the button to show "STOP SESSION" with red background
        sessionButton.setText(getResources().getString(R.string.stop_session));
        sessionButton.setBackgroundColor(Color.parseColor("#bd0000"));
        // Enable the "Time Drinking" text
        sessionText.setEnabled(true);
        // Enable the "Total beer drank" text
        beerText.setEnabled(true);
        // Enable the litres number text
        beerNumber.setEnabled(true);
        // Enable the add pint buttons
        addHalfPint.setEnabled(true);
        addPint.setEnabled(true);

        isDrinking = true;
    }

    // This function is called when the button STOP SESSION is pressed to stop the chronometer
    private void stopSession() {
        // Save the values to variables to be stored in the DB
        litresDrank += totalLitresDrank;

        long elapsedMillis = SystemClock.elapsedRealtime() - sessionChronometer.getBase();
        timePassed += elapsedMillis / 1000;
        // Convert time to days, hours, minutes, seconds
        int days = (int) TimeUnit.SECONDS.toDays(timePassed);
        long hours = TimeUnit.SECONDS.toHours(timePassed) - (days *24);
        long minutes = TimeUnit.SECONDS.toMinutes(timePassed) - (TimeUnit.SECONDS.toHours(timePassed)* 60);
        long seconds = TimeUnit.SECONDS.toSeconds(timePassed) - (TimeUnit.SECONDS.toMinutes(timePassed) *60);


        // Reset the variables
        sessionChronometer.setBase(SystemClock.elapsedRealtime());
        totalLitresDrank = 0;
        // Stop the chronometer
        sessionChronometer.stop();
        // Disable the chronometer
        sessionChronometer.setEnabled(false);
        // Change the button to show "START SESSION" with green background
        sessionButton.setText(getResources().getString(R.string.start_session));
        sessionButton.setBackgroundColor(Color.parseColor("#4caf50"));
        // Disable the "Time Drinking" text
        sessionText.setEnabled(false);
        // Disable the "Total beer drank" text
        beerText.setEnabled(false);
        // Disable and reset the litres number text
        beerNumber.setEnabled(false);
        beerNumber.setText("0 L");
        // Disable the add pint buttons
        addHalfPint.setEnabled(false);
        addPint.setEnabled(false);

        // Update the stats
        totalBeerConsumed.setText(String.format(" %.2f L", litresDrank));
        // Set the correct endings in word
        String totalTimeString = "";
        // For days
        if (days == 1)
            totalTimeString += days + " day ";
        else if (days > 1)
            totalTimeString += days + " days ";
        // For hours
        if (hours == 1)
            totalTimeString += hours + " hour ";
        else
            totalTimeString += hours + " hours ";
        // For minutes
        if (minutes == 1)
            totalTimeString += minutes + " minute ";
        else
            totalTimeString += minutes + " minutes ";
        // For seconds
        if (seconds == 1)
            totalTimeString += seconds + " second";
        else
            totalTimeString += seconds + " seconds";
        totalTime.setText(totalTimeString);

        isDrinking = false;
    }


    // This function is called to add half or a pint of beer
    private void addBeer(boolean isHalf) {
        if (isHalf)
            totalLitresDrank += HALF_PINT_TO_LITRE;
        else
            totalLitresDrank += PINT_TO_LITRE;
        beerNumber.setText(String.format("%.2f L", totalLitresDrank));

    }
}