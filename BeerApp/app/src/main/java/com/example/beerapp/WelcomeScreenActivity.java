package com.example.beerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_beer_item);

        // Initialize DB Handler
        DBHandler dbHandler = new DBHandler(this, null);

        // If the username is null, redirecting user to the welcome screen which is used on the first time the app launches to set his "name"
        if(dbHandler.getUserName() == null)
        {
            Intent intent = new Intent(this, MainActivity.class);
            String nameChosen =  "NIKOPEDROOO"; //TODO takes userinput
            dbHandler.updateUserName(nameChosen);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(this, MainActivity.class);;
            startActivity(intent);
            finish();
        }


    }
}
