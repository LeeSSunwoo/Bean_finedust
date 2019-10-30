package com.bean.beanfinedust.ui.add_device;

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

    public int getERR() {
        return ERR;
    }

    public int getPM1() {
        return PM1;
    }

    public int getPM2_5() {
        return PM2_5;
    }

    public int getPM10() {
        return PM10;
    }

    private double latitude;
    private double longitude;
    private String device_name = "BeanDust Device";
    private String code;
    private double battery = 12.6;
    private double volt = 3.1;
    private int temp = 18;
    private int humi = 86;
    private String d_date;
    private boolean sharing_loc = true;
    private boolean sharing_data = true;
    private int ERR = 0;
    private int PM1 = -99999;
    private int PM2_5 = 10;
    private int PM10 = 20;
    private String UPDATED_TIME = ".";

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
                case "V_SYSTEM":
                    this.battery = (double) data.getValue();
                    break;
                case "V_SOLAR":
                    this.volt = (double) data.getValue();
                    break;
                case "TEMPURATURE":
                    this.temp = Integer.valueOf(String.valueOf(data.getValue()));
                    break;
                case "HUMIDITY":
                    this.humi = Integer.valueOf(String.valueOf(data.getValue()));
                    break;
                case "sharing_loc":
                    this.sharing_loc = (boolean) data.getValue();
                    break;
                case "sharing_data":
                    this.sharing_data = (boolean) data.getValue();
                    break;
                case "ERR":
                    this.ERR = Integer.valueOf(String.valueOf(data.getValue()));
                    break;
                case "PM1":
                    this.PM1 = Integer.valueOf(String.valueOf(data.getValue()));
                    break;
                case "PM2_5":
                    this.PM2_5 = Integer.valueOf(String.valueOf(data.getValue()));
                    break;
                case "PM10":
                    this.PM10 = Integer.valueOf(String.valueOf(data.getValue()));
                    break;
                case "UPDATED_TIME":
                    this.UPDATED_TIME = data.getValue().toString();
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
        result.put("V_SYSTEM", battery);
        result.put("V_SOLAR", volt);
        result.put("TEMPURATURE",temp);
        result.put("HUMIDITY", humi);
        result.put("d_date",d_date);
        result.put("sharing_loc",sharing_loc);
        result.put("sharing_data",sharing_data);
        result.put("PM1",PM1);
        result.put("PM2_5",PM2_5);
        result.put("PM10",PM10);
        result.put("ERR",ERR);
        result.put("UPDATED_TIME",UPDATED_TIME);
        return result;
    }
}
