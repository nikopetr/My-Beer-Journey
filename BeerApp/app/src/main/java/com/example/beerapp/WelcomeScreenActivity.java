package com.example.beerapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class WelcomeScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        // Input Filter for the name edit text in the welcome screen to check the name length
        // Max allowed length is 15 characters. After 15, no other character is added
        EditText nameEditText = findViewById(R.id.nameEditText);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(15);
        nameEditText.setFilters(filterArray);

        // Hides the ActionBar
        Objects.requireNonNull(getSupportActionBar()).hide();
        // Initialize DB Handler
        DBHandler dbHandler = new DBHandler(this, null);

        // If the username is null, redirecting user to the welcome screen which is used on the first time the app launches to set his "name"
        if(dbHandler.getUserName() != null)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void startMainActivity(View view)
    {
        // Initialize DB Handler
        DBHandler dbHandler = new DBHandler(this, null);

        Intent intent = new Intent(this, MainActivity.class);
        String nameChosen =  ((EditText)findViewById(R.id.nameEditText)).getText().toString();

        if (dbHandler.updateUserName(nameChosen)){
            startActivity(intent);
            finish();
        }
        else
            Log.i("Database Interaction", "Could not save username to the database");
    }


}
