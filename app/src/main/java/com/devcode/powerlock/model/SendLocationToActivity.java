package com.devcode.powerlock.model;

import android.location.Location;

public class SendLocationToActivity {
    private Location location;
    public SendLocationToActivity(Location location){
        this.location=location;
    }
    public Location getLocation(){
        return location;
    }

}
