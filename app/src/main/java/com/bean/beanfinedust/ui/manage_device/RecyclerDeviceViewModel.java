package com.bean.beanfinedust.ui.manage_device;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.bean.beanfinedust.SaveSharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecyclerDeviceViewModel extends ViewModel {

    private Map<String, MyDeviceList> list = new HashMap<>();
    MutableLiveData<Map<String, MyDeviceList>> liveDeviceData;
    MutableLiveData<ArrayList<String>> liveCodeData;

    DatabaseReference databaseReference;
    Context context;
    ArrayList<String> device_code_list = new ArrayList();
    public RecyclerDeviceViewModel() {
        liveDeviceData = new MutableLiveData<>();
    }

    public void initDatabase(Context context) {
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String Email = SaveSharedPreference.getUserData(context).split("@")[0];
        databaseReference.child("사용자_데이터").child(Email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    Log.d("Read data",messageData.child("serial_number").getValue().toString());
                    device_code_list.add(messageData.child("serial_number").getValue().toString());
                }
                //liveCodeData.setValue(device_code_list);

                for (String code : device_code_list){
                    databaseReference.child("기기데이터").child(code).addValueEventListener(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            MyDeviceList myDeviceList = new MyDeviceList(dataSnapshot);
                            if(!list.containsKey(code)) list.put(code,myDeviceList);
                            else list.replace(code, myDeviceList);
                            if(device_code_list.size() == list.size()) {
                                liveDeviceData.setValue(list);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}