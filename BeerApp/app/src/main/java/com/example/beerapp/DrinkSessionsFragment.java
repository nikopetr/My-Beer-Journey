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

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

// Fragment used for displaying user's drinking stats, and for having a new drink session
public class DrinkSessionsFragment extends SerializableFragment {

    private static final double PINT_TO_LITRE = 0.5;
    private static final double HALF_PINT_TO_LITRE = 0.3;

    //Initialize the view
    private View rootView;
    // Database
    private DBHandler dbHandler;
    // Initialize variables
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
    private TextView differentBeersTextView;
    private TextView totalBeerConsumedTextView;
    private TextView totalTimeTextView;
    private TextView bestSessionTextView;


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
        dbHandler = new DBHandler(Objects.requireNonNull(getActivity()), null);
        // Initialize variables
        this.isDrinking = false;
        this.totalLitresDrank = 0;
        this.timePassed = 0;
        // Find the START/STOP SESSION button and set the onClick functions to be called
        sessionButton = rootView.findViewById(R.id.startSessionButton);
        sessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user is drinking then we stop the session, otherwise a session starts
                if (isDrinking)
                    stopSession();
                else
                    startSession();
            }
        });
        // Initialize the chronometer
        sessionChronometer = rootView.findViewById(R.id.sessionChronometer);
        // Find the textView, in order to get bold when session is started
        sessionText = rootView.findViewById(R.id.timeDrinkingTextView);

        // Initialize and set on click method to the ADD PINT button
        addPint = rootView.findViewById(R.id.addPintButton);
        addPint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBeer(false);
            }
        });
        // Initialize and set on click method to the ADD HALF PINT button
        addHalfPint = rootView.findViewById(R.id.addHalfPintButton);
        addHalfPint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBeer(true);
            }
        });
        // Initialize the text views for litres drank
        beerText = rootView.findViewById(R.id.beerDrankTextView);
        beerNumber = rootView.findViewById(R.id.litresDrankTextView);

        // Initialize the text views for the stats
        // Initialize different beers consumed text
        differentBeersTextView = rootView.findViewById(R.id.totalBeersTastedTextView);
        differentBeersTextView.setText(String.valueOf(dbHandler.getTotalTastedBeers()));
        // Initialize total beer consumed text
        totalBeerConsumedTextView = rootView.findViewById(R.id.beerConsumedNumberTextView);
        totalBeerConsumedTextView.setText(String.format(" %.2f L", dbHandler.getTotalLitres()));
        // Initialize the time text
        totalTimeTextView = rootView.findViewById(R.id.totalTimeSpentNumberTextView);
        totalTimeTextView.setText(timeConvert());
        // Initialize the best session text
        bestSessionTextView = rootView.findViewById(R.id.bestSessionTextView);
        bestSessionTextView.setText(String.format(" %.2f L", dbHandler.getBestSession()));

    }

    // Method called when the button START SESSION is pressed to start the chronometer and enable the view components
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

    // Method called when the button STOP SESSION is pressed to stop the chronometer,
    // save(update) the current's drink session outcomes and disable the view components
    private void stopSession() {
        // Save the values to variables to be stored in the DB
        dbHandler.addLitres(totalLitresDrank);

        // Time elapsed in seconds
        long elapsedMillis = SystemClock.elapsedRealtime() - sessionChronometer.getBase();
        timePassed = elapsedMillis / 1000;
        // Add the time to the DB
        dbHandler.addSessionTime(timePassed);

        // Add the session's litres in the DB if it is the best session
        dbHandler.bestSession(totalLitresDrank);

        // Reset the variables' values
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
        // Update total litres text
        totalBeerConsumedTextView.setText(String.format(" %.2f L", dbHandler.getTotalLitres()));
        // Update the time text
        totalTimeTextView.setText(timeConvert());
        // Update best session text
        bestSessionTextView.setText(String.format(" %.2f L", dbHandler.getBestSession()));

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

    // This function is used to convert time from seconds to days, hours, minutes and seconds
    // The time that is converted is the total time from the DB
    // Return a string with the correct format of the time
    private String timeConvert() {
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