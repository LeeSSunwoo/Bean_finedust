package com.example.beanfinedust.ui.add_device;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDeviceData {
    private double latitude;
    private double longtitude;

    public FirebaseDeviceData(){}

    public FirebaseDeviceData(LatLng position){
        this.latitude = position.latitude;
        this.longtitude = position.longitude;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("latitude", latitude);
        result.put("longtitude", longtitude);
        return result;
    }
}
