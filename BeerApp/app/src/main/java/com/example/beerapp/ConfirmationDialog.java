package com.example.beerapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class ConfirmationDialog extends DialogFragment {

    private boolean resetWholeJourney;
    private DBHandler dbHandler;
    private Toast toast;

    // Constructor
    ConfirmationDialog(boolean resetWholeJourney, DBHandler dbHandler) {
        this.resetWholeJourney = resetWholeJourney;
        this.dbHandler = dbHandler;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        // Prevent the fragment from being destroyed on screen orientation change
        setRetainInstance(true);
        // Initialize the builder for the alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // If the user pressed the reset journey button
        if (resetWholeJourney) {
            builder.setTitle(R.string.dialog_title_journey_reset);
            builder.setMessage(R.string.dialog_message_journey_reset);
            // Button Start Over
            builder.setPositiveButton(R.string.dialog_positive_button_journey, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbHandler.resetUserData();
                    if (toast != null)
                        toast.cancel();
                    toast = Toast.makeText(getContext(), "Starting Over", Toast.LENGTH_SHORT); //add toast message for Start over
                    toast.show(); //show toast message
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
                    if (toast != null)
                        toast.cancel();
                    toast = Toast.makeText(getContext(), "Resetting Stats", Toast.LENGTH_SHORT); //add toast message for Start over
                    toast.show(); //show toast message
                    dbHandler.resetUserStats();
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

    // Do not dismiss the dialog on screen orientation change
    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

}