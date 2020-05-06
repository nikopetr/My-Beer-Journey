package com.example.beerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Objects;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;


// Class used to display a beer's information and add it to user taste lists
public class BeerDetailsActivity extends AppCompatActivity {

    private Toast toast;

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

        // Get Bundle from the Intent
        Bundle extras = getIntent().getExtras();
        // If there are data passed in the Intent
        if (extras != null)
        {
            // Retrieve data passed in the Intent
            Beer beerSelected = (Beer) extras.getSerializable("selectedBeer");

//            // For debugging: print in the logcat (Debug level)
//            if (beerSelected != null) {
//                Log.d("BeerDetails.java", beerSelected.getName());
//            }
//            else{
//                Log.d("BeerDetails.java", "null Beer object");
//            }

            // Updates the UI
            assert beerSelected != null;
            beerNameTextView.setText(beerSelected.getName());
            manufacturerTextView.setText(String.format("%s %s", manufacturerTextView.getText(),beerSelected.getManufacturer()));
            countryTextView.setText(String.format("%s %s", countryTextView.getText(),beerSelected.getCountry()));
            abvTextView.setText(String.format("%s %s %%", abvTextView.getText(),String.valueOf(beerSelected.getAbv())));
            typeTextView.setText(String.format("%s %s", typeTextView.getText(),beerSelected.getType()));
            beerImageView.setImageResource(beerSelected.getBeerImageId());

        }

    }

    public void ShowMessage(View view)
    {
        Context context = getApplicationContext();
        CharSequence text = "Beer added to Tasted list";
        int duration = Toast.LENGTH_SHORT;
        if (toast == null || toast.getView().getWindowVisibility() != View.VISIBLE) {
                toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 200);
                toast.show();
            }

    }


}
