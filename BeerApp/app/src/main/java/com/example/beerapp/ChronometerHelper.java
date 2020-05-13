package com.example.beerapp;

// Class that helps keeping the chronometer timers on activity or fragment change
class ChronometerHelper {

    private  Long startTime; // The start time of the chronometer

    // Returns the start time of the chronometer
    Long getStartTime() {
        return startTime;
    }

    // Sets the start time of the chronometer
    void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}