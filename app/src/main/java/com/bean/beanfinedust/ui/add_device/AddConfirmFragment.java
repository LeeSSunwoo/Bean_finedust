package com.bean.beanfinedust.ui.add_device;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bean.beanfinedust.R;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddConfirmFragment extends Fragment {


    public AddConfirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_confirm, container, false);

        view.findViewById(R.id.end_button).setOnClickListener(v -> {
            AddDeviceFragment addDeviceFragment = new AddDeviceFragment();
            addDeviceFragment.finishFragment(getActivity());
            //fragmentManager.popBackStack();
        });

        return view;
    }

}
