package com.example.beerapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

// Class used when the app launches in order to welcome user when he enters the app for the first time
// and set his name
public class WelcomeScreenActivity extends AppCompatActivity {

    private EditText nameEditText; // EditText used for the input of the user's name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        // Initialize DB Handler
        DBHandler dbHandler = new DBHandler(this, null);

        // If the user name is already set, redirecting user to the MainActivity
        if(dbHandler.getUserName() != null)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // If the username is null, user has to sets his "name"

        // Input Filter for the name edit text in the welcome screen to check the name length
        // Max allowed length is 15 characters. After 15, no other character is added
        nameEditText = findViewById(R.id.nameEditText);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(15);
        nameEditText.setFilters(filterArray);

        // Hides the ActionBar
        Objects.requireNonNull(getSupportActionBar()).hide();
    }


    // Method used to save the name to the database and redirect user to the MainActivity
    public void startMainActivity(View view)
    {
        // Initialize DB Handler
        DBHandler dbHandler = new DBHandler(this, null);

        Intent intent = new Intent(this, MainActivity.class);

        nameEditText = findViewById(R.id.nameEditText);
        String nameChosen =  nameEditText.getText().toString();

        // If the name is empty show an error and don't proceed to main activity
        if (nameChosen.trim().isEmpty()) {
            TextInputLayout textInputLayout = findViewById(R.id.nameEditLayout);
            textInputLayout.setError("You need to enter a name");
            nameEditText.requestFocus();
        }
        // If the name is valid, add the name to the database and proceed to main activity
        else
        {
            if (dbHandler.updateUserName(nameChosen)) {
                startActivity(intent);
                finish();
            } else
                Log.i("Database Interaction", "Could not save username to the database");
        }
    }
}
