package com.example.beerapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class ConfirmationDialog extends DialogFragment {

    private FragmentListener activityCallBack; // Activity that this fragment is attached to
    private boolean resetWholeJourney;
    private Toast toast;

    // Constructor
    ConfirmationDialog(boolean resetWholeJourney) {
        this.resetWholeJourney = resetWholeJourney;
    }

    // Checks if the activity implements the interface otherwise throw exception
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            activityCallBack = (FragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement interface FragmentListener");
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        // Prevent the fragment from being destroyed on screen orientation change
        setRetainInstance(true);
        // Initialize the builder for the alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // If the user pressed the reset journey button
        if (resetWholeJourney)
        {
            builder.setTitle(R.string.dialog_title_journey_reset);
            builder.setMessage(R.string.dialog_message_journey_reset);
            // Button Start Over
            builder.setPositiveButton(R.string.dialog_positive_button_journey, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Resetting user stats and tasted beers on the db

                    if(activityCallBack.getDbHandler().resetUserData())
                    {
                        // Updates tasted beers list from the database after the changes
                        activityCallBack.updateBeerLists();

                        // Checking to make sure that the toast is null, in order to avoid showing multiple toasts at the same time
                        if (toast != null)
                            toast.cancel();

                        // Add toast message for Start over
                        toast = Toast.makeText(getContext(), "Starting Over", Toast.LENGTH_SHORT); //add toast message for Start over
                        // Show toast message about starting over
                        toast.show();
                    }
                    else
                        Log.i("Database Interaction", "Could not reset user data on databse");
                }
            });
            // Button Cancel
            builder.setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        } else {
            // If the user pressed the reset stats button
            builder.setTitle(R.string.dialog_title_stats_reset);
            builder.setMessage(R.string.dialog_message_stats_reset);
            // Button Reset Stats
            builder.setPositiveButton(R.string.dialog_positive_button_stats, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Resetting user stats on the db
                    if (activityCallBack.getDbHandler().resetUserStats())
                    {
                        // Checking to make sure that the toast is null, in order to avoid showing multiple toasts at the same time
                        if (toast != null)
                            toast.cancel();

                        // Add toast message for reset stats
                        toast = Toast.makeText(getContext(), "Resetting Stats", Toast.LENGTH_SHORT);
                        // Show toast message about resetting user stats
                        toast.show();
                    }
                    else
                        Log.i("Database Interaction", "Could not reset user data on database");
                }
            });
            // Button Cancel
            builder.setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }

        return builder.create();
    }

    //  Method called when the view is destroyed, in order to avoid dismissing the dialog on screen orientation change
    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

}
