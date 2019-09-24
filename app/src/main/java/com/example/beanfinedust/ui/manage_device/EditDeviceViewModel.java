package com.example.beanfinedust.ui.manage_device;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

public class EditDeviceViewModel extends ViewModel {
    private DatabaseReference databaseReference;

    public void initDatabase(String code){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("기기_데이터").child(code).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
