package com.example.beanfinedust.ui.add_device;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.beanfinedust.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class QRcodeFragment extends Fragment {

    private QRViewPager viewPager ;
    private QRPageAdapter qrPageAdapter;
    private int position = 0;

    public QRcodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qrcode, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager = view.findViewById(R.id.QR_viewPager);
        qrPageAdapter = new QRPageAdapter(getContext());
        viewPager.setAdapter(qrPageAdapter);

        Button btn = view.findViewById(R.id.next_btn);
        btn.setOnClickListener(v -> {
            if(position == 0){
                viewPager.setCurrentItem(1);
                position=1;
            }

        });

        super.onViewCreated(view, savedInstanceState);
    }
}
