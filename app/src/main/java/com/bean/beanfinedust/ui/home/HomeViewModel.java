package com.bean.beanfinedust.ui.home;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.bean.beanfinedust.SaveSharedPreference;
import com.bean.beanfinedust.ui.add_device.FirebaseDeviceData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    public MutableLiveData<FirebaseDeviceData> addedData;
    public MutableLiveData<FirebaseDeviceData> changedData;
    public MutableLiveData<FirebaseDeviceData> deletedData;
    MutableLiveData<Map<String, FirebaseDeviceData>> allData;
    private DatabaseReference databaseReference;
    private DatabaseReference userReference;
    long size;
    String id;

    MutableLiveData<List<String>> codeList;

    public HomeViewModel() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userReference = FirebaseDatabase.getInstance().getReference("사용자_데이터");
        addedData = new MutableLiveData<>();
        changedData = new MutableLiveData<>();
        codeList = new MutableLiveData<>();
        allData = new MutableLiveData<>();
        deletedData = new MutableLiveData<>();

        //observableArrayList = new ObservableArrayList<>();
        //mutableLiveData = new MutableLiveData<>();


    }
    public void initDatabase(Context context){
        databaseReference.child("기기데이터").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, FirebaseDeviceData> map = new HashMap<>();
                Log.e("first Count CHECK", String.valueOf(dataSnapshot.getChildrenCount()));
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    FirebaseDeviceData temp = new FirebaseDeviceData(dataSnapshot1);


                    map.put(temp.getCode(), temp);
                }

                allData.setValue(map);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        id = SaveSharedPreference.getUserData(context).split("@")[0];
        databaseReference.child("사용자_데이터").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> code_list = new ArrayList<>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    code_list.add(dataSnapshot1.getValue().toString());
                    Log.d("code CHECK", dataSnapshot1.getValue().toString());
                }
                codeList.setValue(code_list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("기기데이터").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("onChildAdded", String.valueOf(dataSnapshot.getChildrenCount()));
                addedData.setValue(new FirebaseDeviceData(dataSnapshot));
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("onChildChanged", dataSnapshot.getKey());
                changedData.setValue(new FirebaseDeviceData(dataSnapshot));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                deletedData.setValue(new FirebaseDeviceData(dataSnapshot));
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}