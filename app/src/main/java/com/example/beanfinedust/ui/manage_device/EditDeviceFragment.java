package com.example.beanfinedust.ui.manage_device;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.beanfinedust.R;
import com.example.beanfinedust.databinding.EditDeviceFragmentBinding;
import com.example.beanfinedust.ui.add_device.MyPositionData;
import com.example.beanfinedust.ui.add_device.MyPositionFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class EditDeviceFragment extends Fragment {

    private EditDeviceViewModel mViewModel;
    private String code;
    private String d_name;

    public EditDeviceFragment(String code){
        this.code = code;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        EditDeviceFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.edit_device_fragment, container, false);
        // Inflate the layout for this fragment
        View view = binding.getRoot();
        MyPositionData myPositionData = new MyPositionData(getContext());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("기기_데이터");

        mViewModel = ViewModelProviders.of(this).get(EditDeviceViewModel.class);
        mViewModel.initDatabase(code);

        mViewModel.firebaseDeviceData.observe(this, firebaseDeviceData -> {
            d_name = firebaseDeviceData.getDevice_name();
            binding.nameText.setText(d_name);
            binding.serialText.setText(firebaseDeviceData.getCode());
            binding.positionText.setText(myPositionData.getCurrentAddress(new LatLng(firebaseDeviceData.getLatitude(), firebaseDeviceData.getLongitude())));
            binding.batteryText.setText(String.valueOf(firebaseDeviceData.getBattery()));
            binding.voltText.setText(firebaseDeviceData.getVolt()+"V");
            binding.tempHumiText.setText(firebaseDeviceData.getTemp()+" / "+firebaseDeviceData.getHumi());
            //binding.statusText.setText(firebaseDeviceData.getDevice_name());
            binding.dataSwitch.setChecked(firebaseDeviceData.isSharing_data());
            binding.locSwitch.setChecked(firebaseDeviceData.isSharing_loc());
        });

        binding.dataSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Map<String, Object> data = new HashMap<>();
            data.put("sharing_data", isChecked);
            databaseReference.child(code).updateChildren(data);
        });
        binding.locSwitch.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            Map<String, Object> data = new HashMap<>();
            data.put("sharing_loc", isChecked);
            databaseReference.child(code).updateChildren(data);
        }));

        binding.editNameBtn.setOnClickListener(v -> {
            if(binding.nameText.getText().toString().equals(d_name)){
                binding.nameText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put("name", binding.nameText.getText().toString());
                databaseReference.child(code).updateChildren(data);
                InputMethodManager immhide = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });

        binding.editLocationBtn.setOnClickListener(v -> {
            MyPositionFragment myPositionFragment = new MyPositionFragment(code, true);
            FragmentTransaction transaction1 = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            transaction1.replace(R.id.manage_container, myPositionFragment);
            transaction1.commit();
        });

        return view;
    }



}
