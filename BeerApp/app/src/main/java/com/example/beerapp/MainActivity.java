package com.example.beerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    // Constant Strings used for the different action bar titles
    private final String BEER_CATALOG_TITLE = "Beer Catalog";
    private final String DRINK_SESSIONS_TITLE = "My Drink Sessions";
    private final String SETTINGS_TITLE = "Settings";

    private SerializableFragment currentFragment; // The fragment that is currently active

    private ActionBar actionBar; // A primary toolbar within the activity used to display the information of each fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Navigation item listener used for the bottom navigation view
        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            // Method called when user selects to navigate to another fragment
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.beerCatalogItem:
                        actionBar.setTitle(BEER_CATALOG_TITLE);
                        currentFragment = new BeerCatalogFragment();
                        loadFragment(currentFragment);
                        return true;

                    case R.id.drinkSessionsItem:
                        actionBar.setTitle(DRINK_SESSIONS_TITLE);
                        currentFragment = new DrinkSessionsFragment();
                        loadFragment(currentFragment);
                        return true;

                    case R.id.settingsItem:
                        actionBar.setTitle(SETTINGS_TITLE);
                        currentFragment = new SettingsFragment();
                        loadFragment(currentFragment);
                        return true;
                }
                return false;
            }
        };

        actionBar = getSupportActionBar(); // Initializing toolbar
        BottomNavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        if (savedInstanceState != null){
            //Retrieve data from the Bundle and restore the dynamic state of the UI
            currentFragment =  (SerializableFragment)savedInstanceState.getSerializable("fragmentSaved");
            actionBar.setTitle(savedInstanceState.getCharSequence("actonBarTitleSaved"));
        }
        else {
            //Initialize the UI
            actionBar.setTitle("Home"); // Changes the title of the toolbar
            currentFragment = new BeerCatalogFragment();
            loadFragment(currentFragment);
        }

    }

    // Used to load the currently used fragment when the activity loads after an instance save
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save which fragment was loaded to the Bundle and the current title of the aciton bar
        outState.putSerializable("fragmentSaved",currentFragment);
        outState.putCharSequence("actonBarTitleSaved",actionBar.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    // Function called to load the given fragment
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Overwritten function in oder to finish activity if back is pressed and the fragment stack has only 1 fragment
    @Override
    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount() == 1)
            finish();
        else
            super.onBackPressed();
    }


}
