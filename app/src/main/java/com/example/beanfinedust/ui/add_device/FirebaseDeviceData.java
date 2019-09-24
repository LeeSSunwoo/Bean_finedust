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

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String name) {
        this.device_name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getBattery() {
        return battery;
    }

    public void setBattery(double battery) {
        this.battery = battery;
    }

    public double getVolt() {
        return volt;
    }

    public void setVolt(double volt) {
        this.volt = volt;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumi() {
        return humi;
    }

    public void setHumi(double humi) {
        this.humi = humi;
    }

    private double latitude;
    private double longitude;
    private String device_name = "BeanDust Device";
    private String code;
    private double battery = 0.0;
    private double volt = 0.0;
    private double temp = 0.0;
    private double humi = 0.0;
    private String d_date;

    public FirebaseDeviceData(){}

    public FirebaseDeviceData(DataSnapshot dataSnapshot){
        for(DataSnapshot data : dataSnapshot.getChildren()){
            switch (data.getKey()){
                case "serial_number":
                    this.code = data.child(data.getKey()).getValue().toString();
                    break;
                case "name":
                    this.device_name = data.child(data.getKey()).getValue().toString();
                    break;
                case "latitude":
                    this.latitude = (double) data.child(data.getKey()).getValue();
                    break;
                case "longitude":
                    this.longitude = (double) data.child(data.getKey()).getValue();
                    break;
                case "battery":
                    this.battery = (double) data.child(data.getKey()).getValue();
                    break;
                case "volt":
                    this.volt = (double) data.child(data.getKey()).getValue();
                    break;
                case "temp":
                    this.temp = (double) data.child(data.getKey()).getValue();
                    break;
                case "humi":
                    this.humi = (double) data.child(data.getKey()).getValue();
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
        return result;
    }
}
