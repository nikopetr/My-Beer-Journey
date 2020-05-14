package com.example.beerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

// Class used to display a beer's information and add it to user taste lists
public class BeerDetailsActivity extends AppCompatActivity {

    private Toast toast;
    private Beer beerSelected; // Beer that was selected
    private DBHandler dbHandler; // DB Handler for the database interaction
    // Used to determine if the data was changed since the activity call, has RESULT_OK (-1) value if the taste taste was changed
    private int currentResultCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_details);

        // Shows the back button on top of action bar
        Objects.requireNonNull(getSupportActionBar()).setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtain references to objects
        TextView beerNameTextView = findViewById(R.id.beerNameTextView);
        TextView manufacturerTextView = findViewById(R.id.manufacturerTextView);
        TextView countryTextView = findViewById(R.id.countryTextView);
        TextView abvTextView = findViewById(R.id.abvTextView);
        TextView typeTextView = findViewById(R.id.typeTextView);
        ImageView beerImageView = findViewById(R.id.beerImageView);
        Button addTastedButton = findViewById(R.id.addTastedButton);

        dbHandler = new DBHandler(this, null);

        // Get the current variables values the saved instance state
        if (savedInstanceState != null)
        {
            beerSelected = (Beer)savedInstanceState.getSerializable("selectedBeer");
            currentResultCode = savedInstanceState.getInt("currentResultCode");
            // Makes an Intent with the new current result code
            makeReturnIntent();
        }

        else
        {
            // Set the result code to RESULT_CANCELED(0), since no changes occurred so far
            currentResultCode = RESULT_CANCELED;

            // Get Bundle from the Intent
            Bundle extras = getIntent().getExtras();

            // If there are data passed in the Intent retrieve the data
            if (extras != null)
                beerSelected = (Beer) extras.getSerializable("selectedBeer");

            // Create Intent to return the changed beer to the Main Activity
        }

        // Updates the UI
        if (beerSelected != null)
        {
            beerNameTextView.setText(beerSelected.getName());
            manufacturerTextView.setText(String.format("%s %s", manufacturerTextView.getText(),beerSelected.getManufacturer()));
            countryTextView.setText(String.format("%s %s", countryTextView.getText(),beerSelected.getCountry()));
            abvTextView.setText(String.format("%s %s %%", abvTextView.getText(),String.valueOf(beerSelected.getAbv())));
            typeTextView.setText(String.format("%s %s", typeTextView.getText(),beerSelected.getType()));
            byte[] beerImageBytes= dbHandler.getBeerImage(beerSelected.get_id(),true);
            Bitmap bitmap = BitmapFactory.decodeByteArray(beerImageBytes, 0, beerImageBytes.length);
            beerImageView.setImageBitmap(bitmap);

            // Sets the text of the button
            if (beerSelected.isTasted())
                addTastedButton.setText(R.string.remove_tasted);
            else
                addTastedButton.setText(R.string.add_tasted);

            addTastedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateBeerTasted();
                }
            });
            // Enables the button
            addTastedButton.setEnabled(true);
        }
    }

    //method for go-back button arrow
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    // Method used to load the currently used fragment when the activity loads after an instance save
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Saves which beer was loaded
        outState.putSerializable("selectedBeer", beerSelected);
        // Saves the current result code
        outState.putInt("currentResultCode", currentResultCode);
    }

    // Reverses the current tasted variable of the beer and updates the buttons text's
    void updateBeerTasted(){
        Button addTastedButton = findViewById(R.id.addTastedButton);
        boolean tasted = beerSelected.isTasted();
        tasted = !tasted; // Reverse the taste state
        beerSelected.setTasted(tasted);

        // If the database was successfully updated, the button's text changes
        if (dbHandler.updateBeerTasted(beerSelected))
        {
            // Sets the text of the button according to the current state
            if (beerSelected.isTasted())
            {
                if (toast != null)
                    toast.cancel();

                toast = Toast.makeText(getApplicationContext(), "Beer added to Tasted list", Toast.LENGTH_SHORT); //add toast message for add beer
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 200);
                toast.show(); //show toast message
                addTastedButton.setText(R.string.remove_tasted);
            }
            else {
                if (toast != null)
                    toast.cancel();

                toast = Toast.makeText(getApplicationContext(), "Beer removed from Tasted list",Toast.LENGTH_SHORT ); //add toast message for remove beer
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 200);
                toast.show(); //show toast message
                addTastedButton.setText(R.string.add_tasted);
            }

            // Changing the result code which the intent returns, according to if the taste state is changed.
            // If the current result code is equal to RESULT_CANCELED, then change it to RESULT_OK,
            // else change it to RESULT_CANCELED
            if(currentResultCode == RESULT_CANCELED)
                currentResultCode = RESULT_OK;
            else
                currentResultCode = RESULT_CANCELED;
            // Makes an Intent with the new current result code
            makeReturnIntent();
        }
        else
            Log.i("Database interaction", "Could not update beer tasted state");
    }

    // Makes an Intent with the current result code to return to the previous activity
    private void makeReturnIntent(){
        Intent returnData = new Intent();
        setResult(currentResultCode, returnData);
    }
}
