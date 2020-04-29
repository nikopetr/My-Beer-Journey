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

    // The fragments used for the bottom navigation view
    private HomeFragment homeFragment;
    private DashBoardFragment dashBoardFragment;
    private SettingsFragment settingsFragment;


    private ActionBar actionBar; // A primary toolbar within the activity used to display the information of each fragment
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        // Method called when user selects to navigate to another fragment
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.homeItem:
                    actionBar.setTitle("Home");
                    loadFragment(homeFragment);
                    return true;

                case R.id.dashBoardItem:
                    actionBar.setTitle("Dashboard");
                    loadFragment(dashBoardFragment);
                    return true;

                case R.id.settingsItem:
                    actionBar.setTitle("Settings");
                    loadFragment(settingsFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initializing fragments
        homeFragment = new HomeFragment();
        dashBoardFragment = new DashBoardFragment();
        settingsFragment = new SettingsFragment();

        actionBar = getSupportActionBar(); // Initializing toolbar
        BottomNavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        actionBar.setTitle("Home"); // Changes the title of the toolbar
        loadFragment(homeFragment);
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
}
