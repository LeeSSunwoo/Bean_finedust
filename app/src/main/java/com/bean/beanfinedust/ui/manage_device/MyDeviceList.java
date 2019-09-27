package com.bean.beanfinedust.ui.manage_device;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

public class MyDeviceList {
    private String d_name, d_date, status, code;

    public MyDeviceList(String name, String date, String status, String code) {
        this.d_name = name;
        this.d_date = date;
        this.status = status;
        this.code = code;
    }

    public MyDeviceList(DataSnapshot dataSnapshot){
        Log.d("getChildren", String.valueOf(dataSnapshot.getChildren()));
        for(DataSnapshot data : dataSnapshot.getChildren()){
            switch (data.getKey()){
                case "serial_number":
                    this.code = data.getValue().toString();
                    break;
                case "name":
                    this.d_name = data.getValue().toString();
                    break;
                case "battery":
                    this.status = data.getValue().toString();
                    break;
                case "d_date":
                    this.d_date = "등록일: "+data.getValue().toString();
                    break;
            }
        }
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String name) {
        this.d_date = name;
    }

    public String getD_date() {
        return d_date;
    }

    public void setD_date(String date) {
        this.d_date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
