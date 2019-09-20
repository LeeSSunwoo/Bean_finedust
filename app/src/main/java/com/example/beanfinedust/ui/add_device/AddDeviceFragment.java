package com.example.beanfinedust.ui.add_device;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.beanfinedust.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class AddDeviceFragment extends Fragment {

    private AddDeviceViewModel addDeviceViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addDeviceViewModel =
                ViewModelProviders.of(this).get(AddDeviceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_device, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        addDeviceViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}