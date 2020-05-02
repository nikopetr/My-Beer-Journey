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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    // Constant Strings used for the different action bar titles
    private static final String BEER_CATALOG_TITLE = "Beer Catalog";
    private static final String DRINK_SESSIONS_TITLE = "My Drink Sessions";
    private static final String SETTINGS_TITLE = "Settings";

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
                return fragmentTransition((item.getItemId()));
            }
        };

        actionBar = getSupportActionBar(); // Initializing toolbar
        BottomNavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        if (savedInstanceState != null){
           //Retrieve data from the Bundle and restore the dynamic state of the UI
//            currentFragment =  (SerializableFragment)savedInstanceState.getSerializable("fragmentSaved");
            actionBar.setTitle(savedInstanceState.getCharSequence("actonBarTitleSaved"));
            Fragment lastFragment = getSupportFragmentManager().getFragment(savedInstanceState,BEER_CATALOG_TITLE);
           // fragmentManager.beginTransaction().hide(Objects.requireNonNull(getCurrentFragment())).commit();

            getSupportFragmentManager().beginTransaction().show(Objects.requireNonNull(lastFragment)).commit();
        }
        else {
            //Initialize the UI
            actionBar.setTitle(BEER_CATALOG_TITLE); // Changes the title of the toolbar
            getSupportFragmentManager().beginTransaction().add(R.id.container, new BeerCatalogFragment(), BEER_CATALOG_TITLE).commit();
        }

    }

    // Method used to load the currently used fragment when the activity loads after an instance save
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save which fragment was loaded to the Bundle and the current title of the action bar
        //outState.putSerializable("fragmentSaved",(SerializableFragment)getCurrentFragment());
        // Save the fragments of the fragment manager to the Bundle
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments)
            if(fragment != null && fragment.getTag() != null)
                getSupportFragmentManager().putFragment(outState, fragment.getTag(), fragment);

        outState.putCharSequence("actonBarTitleSaved",actionBar.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    // Method called to load the given fragment
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Method used for transitioning from one fragment to another without recreating the previous fragment
    // by using tags to check if a fragment already exists in the fragment manager
    private boolean fragmentTransition (int selectedItemId) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Hiding the current visible fragment
        transaction.hide(Objects.requireNonNull(getCurrentFragment()));

        switch (selectedItemId) {
            case R.id.beerCatalogItem:
                if (fragmentManager.findFragmentByTag(BEER_CATALOG_TITLE) != null) {
                    // If a BeerCatalogFragment already exists, show it.
                    transaction.show(Objects.requireNonNull(fragmentManager.findFragmentByTag(BEER_CATALOG_TITLE)));
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else {
                    // If the fragment does not exist, add it to fragment manager.
                    transaction.add(R.id.container, new BeerCatalogFragment(), BEER_CATALOG_TITLE);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                actionBar.setTitle(BEER_CATALOG_TITLE);
                return true;
            case R.id.drinkSessionsItem:
                if (fragmentManager.findFragmentByTag(DRINK_SESSIONS_TITLE) != null) {
                    // If a DrinkSessionsFragment already exists, show it.
                    transaction.show(Objects.requireNonNull(fragmentManager.findFragmentByTag(DRINK_SESSIONS_TITLE)));
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else {
                    // If the fragment does not exist, add it to fragment manager.
                    transaction.add(R.id.container, new DrinkSessionsFragment(), DRINK_SESSIONS_TITLE);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                actionBar.setTitle(DRINK_SESSIONS_TITLE);
                return true;
            case R.id.settingsItem:
                if (fragmentManager.findFragmentByTag(SETTINGS_TITLE) != null) {
                    // If the fragment already exists, show it.
                    transaction.show(Objects.requireNonNull(fragmentManager.findFragmentByTag(SETTINGS_TITLE)));
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else {
                    // If the fragment does not exist, add it to fragment manager.
                    transaction.add(R.id.container, new SettingsFragment(), SETTINGS_TITLE);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                actionBar.setTitle(SETTINGS_TITLE);
                return true;
        }
        return false;
    }

    // Returns the current visible fragment
    private Fragment getCurrentFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments)
            if(fragment != null && fragment.isVisible())
                return fragment;
        return null;
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
