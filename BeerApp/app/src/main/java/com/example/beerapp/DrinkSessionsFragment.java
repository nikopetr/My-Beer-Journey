package com.example.beerapp;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.TimeUnit;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

// Fragment used for displaying user's drinking stats, and for having a new drink session
public class DrinkSessionsFragment extends Fragment {

    private static final double PINT_TO_LITRE = 0.5;
    private static final double HALF_PINT_TO_LITRE = 0.3;

    // Initialize variables
    private NameMustChange activityCallBack; // Activity that this fragment is attached to
   // private DBHandler dbHandler;  // Database Helper
    private View rootView; // The root view of the fragment, used to get the rest view components
    private boolean isDrinking; // Represents if the user is currently in a drink session
    private double totalLitresDrank; // The total amount of beer the user has consumed during a drink session
    private Toast stopSessionToast; // Toast used for showing message when the session is stopped
    private Chronometer sessionChronometer; // Chronometer used to count the time in a session
    private ChronometerHelper chronometerHelper; //

     public DrinkSessionsFragment( ) {
         // Reburied empty constructor to call Fragment's constructor
    }

    // Checks if the activity implements the interface otherwise throw exception
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            activityCallBack = (NameMustChange) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement interface<<INTERFACE NAME>>"); //TODO CHANGE NAME
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.chronometerHelper = new ChronometerHelper();
        this.isDrinking = false;
        this.totalLitresDrank = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_drink_sessions, container, false);

        // Initialize the session's chronometer
        sessionChronometer = rootView.findViewById(R.id.sessionChronometer);

        // Get the dbHandler from the main activity which is used for the database interaction
        DBHandler dbHandler = activityCallBack.getDbHandler();

        // Find the START/STOP SESSION button and set the onClick methods
        Button sessionButton = rootView.findViewById(R.id.startSessionButton);
        sessionButton.setBackgroundResource(R.drawable.custom_start_button);
        sessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user is drinking then we stop the session, otherwise a session starts
                if (isDrinking){
                    // Call the method for showing toasts that the session has stopped
                    addStopMessageToast();
                    // Stop the on going session
                    stopDrinkSession();
                }
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


        // If the user is currently drinking, continuing the drink session from where it was left
        if (isDrinking)
        {
            // set the chronometer's base to the previous base.
            startDrinkSession(chronometerHelper.getStartTime());
            ((TextView)rootView.findViewById(R.id.litresDrankTextView)).setText(String.format(getString(R.string.litres_format_string), totalLitresDrank));

        }

        return rootView;
    }

    // Used to continue the on going session
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            this.isDrinking = savedInstanceState.getBoolean("isDrinking");
            this.totalLitresDrank = savedInstanceState.getDouble("totalLitresDrank");
            if (isDrinking)
            {
                // Starting the drinking session continuing from the previous base
                startDrinkSession(savedInstanceState.getLong("chronometerBase"));
                ((TextView)rootView.findViewById(R.id.litresDrankTextView)).setText(String.format(getString(R.string.litres_format_string), totalLitresDrank));
            }
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
        // Sets the start time for the chronometer helper
        chronometerHelper.setStartTime(baseTime);
        // Enable the chronometer
        sessionChronometer.setEnabled(true);
        // Change the button to show "STOP SESSION" with red background
        Button sessionButton = rootView.findViewById(R.id.startSessionButton);
        sessionButton.setText(getResources().getString(R.string.stop_session));
        sessionButton.setBackgroundResource(R.drawable.custom_stop_button);
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
        sessionButton.setBackgroundResource(R.drawable.custom_start_button);
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
        String logMessageTag = "Database Interaction";
        DBHandler dbHandler = activityCallBack.getDbHandler();
        // Save the values to variables to be stored in the DB
        if (!dbHandler.addLitres(totalLitresDrank))
            Log.i(logMessageTag, "Could not save session's litres to the DB");
        // Time elapsed in millis
        long elapsedMillis = SystemClock.elapsedRealtime() - sessionChronometer.getBase();
        // Save the time of the session (in seconds) to the DB
        if (!dbHandler.addSessionTime(elapsedMillis / 1000))
            Log.i(logMessageTag, "Could not save session's time to the DB");
        // Save the session's litres in the DB if it is the best session
        if(totalLitresDrank > dbHandler.getBestSession())
            if(!dbHandler.updateBestSession(totalLitresDrank))
                Log.i(logMessageTag, "Could not save best session to the DB");

        // Reset the variables' values
        sessionChronometer.setBase(SystemClock.elapsedRealtime());
        chronometerHelper.setStartTime(SystemClock.elapsedRealtime());
        totalLitresDrank = 0;
        // Stop the chronometer
        sessionChronometer.stop();
        // Disable the session components
        disableSessionComponents();
        // Update the stats with the new values
        updateStats();
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
        DBHandler dbHandler = activityCallBack.getDbHandler();
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

    // Method for resetting the text values of the session's section
    // Might need to call this function when resetting the stats or the journey from settings section
    private void updateStats() {
        DBHandler dbHandler = activityCallBack.getDbHandler();
        // Update the stats
        // Update total litres text
        ((TextView) rootView.findViewById(R.id.beerConsumedNumberTextView)).setText(String.format(getString(R.string.litres_format_string), dbHandler.getTotalLitres()));
        // Update the time text
        ((TextView) rootView.findViewById(R.id.totalTimeSpentNumberTextView)).setText(getConvertedTime());
        // Update best session text
        ((TextView) rootView.findViewById(R.id.bestSessionTextView)).setText(String.format(getString(R.string.litres_format_string), dbHandler.getBestSession()));
    }


    // Method which adds a stopSessionToast with a message that shows the session has stopped
    private void addStopMessageToast(){
        Button button = rootView.findViewById(R.id.addPintButton);
        int heightOffset = button.getHeight();

        // When the orientation is portrait, the gravity of the stopSessionToast is at the bottom with
        // with bigger offset to the add pint button
        if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT)
        {
            // Checking to make sure that the stopSessionToast is null, in order to avoid showing multiple toasts at the same time
            if (stopSessionToast == null || stopSessionToast.getView().getWindowVisibility() != View.VISIBLE)
            {
                stopSessionToast = Toast.makeText(getContext(), "Session Stopped", Toast.LENGTH_SHORT);
                stopSessionToast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, heightOffset * 2);
                stopSessionToast.show(); // Show stopSessionToast message
            }
        }
        else
        {
            // When the orientation is landscape, the gravity of the stopSessionToast is at the bottom with
            // with smaller offset to the add pint button

            // Checking to make sure that the stopSessionToast is null, in order to avoid showing multiple toasts at the same time
            if (stopSessionToast == null || stopSessionToast.getView().getWindowVisibility() != View.VISIBLE) {
                stopSessionToast = Toast.makeText(getContext(), "Session Stopped", Toast.LENGTH_SHORT);
                stopSessionToast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, heightOffset);
                // Show stopSessionToast message
                stopSessionToast.show();
            }
        }
    }
}