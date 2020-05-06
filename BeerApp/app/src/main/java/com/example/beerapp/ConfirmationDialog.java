package com.example.beerapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class ConfirmationDialog extends DialogFragment {

    private boolean resetWholeJourney;
    private DBHandler dbHandler;

    ConfirmationDialog(boolean resetWholeJourney, DBHandler dbHandler) {
        this.resetWholeJourney = resetWholeJourney;
        this.dbHandler = dbHandler;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        // Prevent the fragment from being destroyed on screen orientation change
        setRetainInstance(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (resetWholeJourney) {
            builder.setTitle(R.string.dialog_title_journey_reset);
            builder.setMessage(R.string.dialog_message_journey_reset);
            builder.setPositiveButton(R.string.dialog_positive_button_journey, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbHandler.resetUserData();
                }
            });
            builder.setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        } else {
            builder.setTitle(R.string.dialog_title_stats_reset);
            builder.setMessage(R.string.dialog_message_stats_reset);
            builder.setPositiveButton(R.string.dialog_positive_button_stats, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbHandler.resetUserStats();
                }
            });
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
