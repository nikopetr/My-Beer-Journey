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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements FragmentListener {

    // Constant Strings used for the different action bar titles
    private static final String BEER_CATALOG_TITLE = "Beer Catalog";
    private static final String DRINK_SESSIONS_TITLE = "My Drink Sessions";
    private static final String SETTINGS_TITLE = "Settings";
    private static final String TASTED_BEERS_TITLE = "Tasted Beers";

    // DB Handler for the database interaction
    private DBHandler dbHandler;
    // List including the all Beer objects
    private List<Beer> beerList;
    // List including the all tasted Beer objects
    private List<Beer> tastedBeerList;
    // The navigation view of the activity
    private BottomNavigationView navigationView;
    // Primary toolbar within the activity used to display the information of each fragment
    private ActionBar actionBar;
    private String currentFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize DB Handler
        dbHandler = new DBHandler(this, null);

        // Initialize beer list and beers tasted list from the database
        updateBeerLists();

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
        navigationView = findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        if (savedInstanceState != null)
        {
            // Retrieve data from the Bundle and restore the dynamic state of the UI
            currentFragmentTag = (String)savedInstanceState.getCharSequence("actionBarTitleSaved");
            actionBar.setTitle(currentFragmentTag);
        }
        else {
            currentFragmentTag = BEER_CATALOG_TITLE;
            // Initialize the UI with the BeerCatalogFragment fragment
            actionBar.setTitle(BEER_CATALOG_TITLE); // Changes the title of the toolbar
            getSupportFragmentManager().beginTransaction().add(R.id.container, new BeerCatalogFragment(), BEER_CATALOG_TITLE).commit();
        }

        // Change the title and selected navigation item when the fragment is changed when the back button is pressed
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // The active fragment
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
                assert fragment != null;
                if (fragment.getTag() != null) {
                    // Set the title based on the active fragment
                    actionBar.setTitle(fragment.getTag());
                    switch(fragment.getTag()) {
                        // If "Beer Catalog" fragment is active set the menu item to index 0
                        case BEER_CATALOG_TITLE:
                            navigationView.getMenu().getItem(0).setChecked(true);
                            break;
                        // If "My Beer List" fragment is active set the menu item to index 1
                        case TASTED_BEERS_TITLE:
                            navigationView.getMenu().getItem(1).setChecked(true);
                            break;
                        // If "My Drink Sessions" fragment is active set the menu item to index 2
                        case DRINK_SESSIONS_TITLE:
                            navigationView.getMenu().getItem(2).setChecked(true);
                            break;
                        // If "Settings" fragment is active set the menu item to index 3
                        case SETTINGS_TITLE:
                            navigationView.getMenu().getItem(3).setChecked(true);
                            break;
                    }
                }
            }
        });

    }

    // Method used to load the currently used fragment when the activity loads after an instance save
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Saves which fragment was loaded for title of the action bar after it loads  back
        outState.putCharSequence("actionBarTitleSaved",actionBar.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    // Returns a List including all beers
    public List<Beer> getBeerList(){
        return beerList;
    }

    // Returns a List including tasted beers
    public List<Beer> getTastedBeerList(){
        return tastedBeerList;
    }

//    // Returns the DB handler
    public DBHandler getDbHandler(){
        return dbHandler;
    }

    // Updates beers list and beers tasted list with the from the database
    public void updateBeerLists(){
        // Initialize beers list from the DB to apply the changes
        beerList = dbHandler.getAllBeers();
        // Sorting the beers by ascending order by name
        Collections.sort(this.beerList, new Comparator<Beer>() {
            @Override
            public int compare(Beer beer1, Beer beer2) {
                return beer1.getName().compareTo(beer2.getName());
            }
        });

        // Initialize beers list from the DB to apply the changes
        tastedBeerList = dbHandler.getTastedBeers();
        // Sorting the beers by ascending order by name
        Collections.sort(this.tastedBeerList, new Comparator<Beer>() {
            @Override
            public int compare(Beer beer1, Beer beer2) {
                return beer1.getName().compareTo(beer2.getName());
            }
        });
    }

    // Method called to show an existing fragment, found by it's tag and transact to it
    private void replaceFragment(String fragmentTag) {

        if (!currentFragmentTag.equals(fragmentTag))
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag(fragmentTag)), fragmentTag);
            transaction.addToBackStack(null);
            transaction.commit();
            actionBar.setTitle(fragmentTag);
            currentFragmentTag = fragmentTag;
        }
    }

    // Method called to add a fragment to the manager and transact to it
    private void replaceNewFragment(Fragment fragment, String fragmentTag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, fragmentTag);
        transaction.addToBackStack(null);
        transaction.commit();
        actionBar.setTitle(fragmentTag);
        currentFragmentTag = fragmentTag;
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
                    BeerCatalogFragment beerCatalogFragment = new BeerCatalogFragment();
                    // If the fragment does not exist, add it to fragment manager.
                    replaceNewFragment(beerCatalogFragment, BEER_CATALOG_TITLE);
                }
                return true;
            case R.id.drinkSessionsItem:
                if (fragmentManager.findFragmentByTag(DRINK_SESSIONS_TITLE) != null) {
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
            case R.id.tastedBeersItem:
                if (fragmentManager.findFragmentByTag(TASTED_BEERS_TITLE) != null) {
                    // If the fragment already exists, show it.
                    replaceFragment((TASTED_BEERS_TITLE));
                }
                else {
                    // If the fragment does not exist, add it to fragment manager.
                    replaceNewFragment(new TastedBeersFragment(), TASTED_BEERS_TITLE);
                }
                return true;
        }
        return false;
    }

    // Overwritten method in order to navigate to the beer catalog fragment
    // which is the default "home"
    @Override
    public void onBackPressed() {

        // if the current fragment is the beer catalog fragment finish the activity (exits application)
        if (currentFragmentTag.equals(BEER_CATALOG_TITLE))
            finish();
        else
            replaceFragment(BEER_CATALOG_TITLE);
    }
}
