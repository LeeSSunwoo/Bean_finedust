package com.example.beanfinedust.ui.manage_device;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beanfinedust.R;
import com.example.beanfinedust.databinding.EditDeviceFragmentBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class EditDeviceFragment extends Fragment {

    private EditDeviceViewModel mViewModel;
    private String code;

    public EditDeviceFragment(String code){
        this.code = code;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        EditDeviceFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.edit_device_fragment, container, false);
        // Inflate the layout for this fragment
        View view = binding.getRoot();

        mViewModel = ViewModelProviders.of(this).get(EditDeviceViewModel.class);

        return view;
    }

}
