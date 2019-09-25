package com.example.beanfinedust.ui.add_device;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FirebaseDeviceData {
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDevice_name() {
        return device_name;
    }

    public String getCode() {
        return code;
    }

    public double getBattery() {
        return battery;
    }

    public double getVolt() {
        return volt;
    }

    public double getTemp() {
        return temp;
    }

    public double getHumi() {
        return humi;
    }

    public String getD_date() {
        return d_date;
    }

    public boolean isSharing_loc() {
        return sharing_loc;
    }

    public boolean isSharing_data() {
        return sharing_data;
    }

    private double latitude;
    private double longitude;
    private String device_name = "BeanDust Device";
    private String code;
    private double battery = 0.1;
    private double volt = 0.1;
    private double temp = 0.1;
    private double humi = 0.1;
    private String d_date;
    private boolean sharing_loc = true;
    private boolean sharing_data = true;

    public FirebaseDeviceData(){}

    public FirebaseDeviceData(DataSnapshot dataSnapshot){
        for(DataSnapshot data : dataSnapshot.getChildren()){
            switch (data.getKey()){
                case "serial_number":
                    this.code = data.getValue().toString();
                    break;
                case "name":
                    this.device_name = data.getValue().toString();
                    break;
                case "latitude":
                    this.latitude = (double) data.getValue();
                    break;
                case "longitude":
                    this.longitude = (double) data.getValue();
                    break;
                case "battery":
                    this.battery = (double) data.getValue();
                    break;
                case "volt":
                    this.volt = (double) data.getValue();
                    break;
                case "temp":
                    this.temp = (double) data.getValue();
                    break;
                case "humi":
                    this.humi = (double) data.getValue();
                    break;
                case "sharing_loc":
                    this.sharing_loc = (boolean) data.getValue();
                    break;
                case "sharing_data":
                    this.sharing_data = (boolean) data.getValue();
                    break;
            }
        }
    }

    public FirebaseDeviceData(LatLng position, String code){
        this.latitude = position.latitude;
        this.longitude = position.longitude;
        this.code = code;
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy. MM. dd", Locale.KOREA );
        Date currentTime = new Date ();
        this.d_date = mSimpleDateFormat.format ( currentTime );
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("serial_number", code);
        result.put("name", device_name);
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        result.put("battery", battery);
        result.put("volt", volt);
        result.put("temp",temp);
        result.put("humi", humi);
        result.put("d_date",d_date);
        result.put("sharing_loc",sharing_loc);
        result.put("sharing_data",sharing_data);
        return result;
    }
}
