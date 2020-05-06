package com.example.beerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_details);

        // Hides title bar from the Android Activity
        Objects.requireNonNull(this.getSupportActionBar()).hide();

        // Obtain references to objects
        TextView beerNameTextView = findViewById(R.id.beerNameTextView);
        TextView manufacturerTextView = findViewById(R.id.manufacturerTextView);
        TextView countryTextView = findViewById(R.id.countryTextView);
        TextView abvTextView = findViewById(R.id.abvTextView);
        TextView typeTextView = findViewById(R.id.typeTextView);
        ImageView beerImageView = findViewById(R.id.beerImageView);
        Button addTastedButton = findViewById(R.id.addTastedButton);

        // Get Bundle from the Intent
        Bundle extras = getIntent().getExtras();
        // If there are data passed in the Intent
        if (extras != null)
        {
            // Retrieve data passed in the Intent
            beerSelected = (Beer) extras.getSerializable("selectedBeer");


            // Updates the UI
           if (beerSelected != null) {
               beerNameTextView.setText(beerSelected.getName());
               manufacturerTextView.setText(String.format("%s %s", manufacturerTextView.getText(),beerSelected.getManufacturer()));
               countryTextView.setText(String.format("%s %s", countryTextView.getText(),beerSelected.getCountry()));
               abvTextView.setText(String.format("%s %s %%", abvTextView.getText(),String.valueOf(beerSelected.getAbv())));
               typeTextView.setText(String.format("%s %s", typeTextView.getText(),beerSelected.getType()));
               beerImageView.setImageResource(beerSelected.getBeerImageId());

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

    }

    // Reverses the current tasted variable of the beer and updates the buttons text's
    void updateBeerTasted(){
        Button addTastedButton = findViewById(R.id.addTastedButton);
        boolean tasted = beerSelected.isTasted();
        tasted = !tasted; // Reverse the taste state
        beerSelected.setTasted(tasted);

        // If the database was successfully updated, the button's text changes
        if (new DBHandler(this, null).updateBeerTasted(beerSelected)) {
            // Sets the text of the button according to the current state
            if (beerSelected.isTasted()) {
                if (toast!=null)
                {
                    toast.cancel();
                }
                toast = Toast.makeText(getApplicationContext(), "Beer added to Tasted list", Toast.LENGTH_SHORT); //add toast message for add beer
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 200);
                toast.show(); //show toast message
                addTastedButton.setText(R.string.remove_tasted);
            }
            else {
                if (toast!=null)
                {
                    toast.cancel();
                }
                toast = Toast.makeText(getApplicationContext(), "Beer removed from Tasted list",Toast.LENGTH_SHORT ); //add toast message for remove beer
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 200);
                toast.show(); //show toast message
                addTastedButton.setText(R.string.add_tasted);
            }
        }
        else
            Log.i("Database interaction", "Could not update beer tasted state");
    }
}
