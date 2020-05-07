package com.example.beerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    // Constant Strings used for the different action bar titles
    private static final String BEER_CATALOG_TITLE = "Beer Catalog";
    private static final String DRINK_SESSIONS_TITLE = "My Drink Sessions";
    private static final String SETTINGS_TITLE = "Settings";
    private static final String MY_BEER_LIST = "My Beer List";

    // DB Handler for all Database Stuff
    private DBHandler dbHandler;
    private ActionBar actionBar; // A primary toolbar within the activity used to display the information of each fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DB Handler
        dbHandler = new DBHandler(this, null);

        // Navigation item listener used for the bottom navigation view
        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            // Method called when user selects to navigate to another fragment
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return fragmentTransition((item.getItemId()));
            }
        };

        actionBar = getSupportActionBar(); // Initializing toolbar
        BottomNavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        if (savedInstanceState != null){
            // Retrieve data from the Bundle and restore the dynamic state of the UI
            actionBar.setTitle(savedInstanceState.getCharSequence("actonBarTitleSaved"));
        }
        else {
            // Initialize the UI
            actionBar.setTitle(BEER_CATALOG_TITLE); // Changes the title of the toolbar
            getSupportFragmentManager().beginTransaction().add(R.id.container, new BeerCatalogFragment(), BEER_CATALOG_TITLE).commit();
        }

    }

    // Method used to load the currently used fragment when the activity loads after an instance save
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Saves which fragment was loaded  for title of the action bar after it loads  back
        outState.putCharSequence("actonBarTitleSaved",actionBar.getTitle());
        //outState.putSerializable("asd",dbHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    // Method called to show an existing fragment, found by it's tag and transact to it
    private void replaceFragment(String fragmentTag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag(fragmentTag)), fragmentTag);
        transaction.addToBackStack(null);
        transaction.commit();
        actionBar.setTitle(fragmentTag);
    }

    // Method called to add a fragment to the manager and transact to it
    private void replaceNewFragment(Fragment fragment, String fragmentTag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, fragmentTag);
        transaction.addToBackStack(null);
        transaction.commit();
        actionBar.setTitle(fragmentTag);
    }

    // Method used for transitioning from one fragment to another without recreating the previous fragment
    // by using tags to check if a fragment already exists in the fragment manager
    private boolean fragmentTransition (int selectedItemId) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (selectedItemId) {
            case R.id.beerCatalogItem:
                if (fragmentManager.findFragmentByTag(BEER_CATALOG_TITLE) != null) {
                    // If a BeerCatalogFragment already exists, show it.
                    replaceFragment(BEER_CATALOG_TITLE);
                }
                else {
                    // If the fragment does not exist, add it to fragment manager.
                    replaceNewFragment(new BeerCatalogFragment(), BEER_CATALOG_TITLE);
                }
                return true;
            case R.id.drinkSessionsItem:
                if (fragmentManager.findFragmentByTag(DRINK_SESSIONS_TITLE) != null) {
                    // Before you show the DrinkSessionsFragment update it's text values in case there was a reset from the user
                    DrinkSessionsFragment fragment = (DrinkSessionsFragment) getSupportFragmentManager().findFragmentByTag(DRINK_SESSIONS_TITLE);
                    if (fragment != null) fragment.updateStats();
                    // If a DrinkSessionsFragment already exists, show it.
                    replaceFragment(DRINK_SESSIONS_TITLE);
                }
                else {
                    // If the fragment does not exist, add it to fragment manager.
                    replaceNewFragment(new DrinkSessionsFragment(), DRINK_SESSIONS_TITLE);
                }
                return true;
            case R.id.settingsItem:
                if (fragmentManager.findFragmentByTag(SETTINGS_TITLE) != null) {
                    // If the fragment already exists, show it.
                    replaceFragment((SETTINGS_TITLE));
                }
                else {
                    // If the fragment does not exist, add it to fragment manager.
                    replaceNewFragment(new SettingsFragment(), SETTINGS_TITLE);
                }
                return true;
            case R.id.myBeerListItem:
                if (fragmentManager.findFragmentByTag(MY_BEER_LIST) != null) {
                    // If the fragment already exists, show it.
                    replaceFragment((MY_BEER_LIST));
                }
                else {
                    // If the fragment does not exist, add it to fragment manager.
                    replaceNewFragment(new MyBeerListFragment(), MY_BEER_LIST);
                }
                return true;
        }
        return false;
    }

    DBHandler getDbHandler()
    {
        return dbHandler;
    }


}
