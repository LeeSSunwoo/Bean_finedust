package com.bean.beanfinedust.ui.add_device;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bean.beanfinedust.OnBackPressedListener;
import com.bean.beanfinedust.R;
import com.bean.beanfinedust.ui.home.HomeFragment;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment implements OnBackPressedListener {


    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.btn_device).setOnClickListener(v -> {
            QRcodeFragment qRcodeFragment = new QRcodeFragment();

            Log.d("first getActivity",getActivity().toString());
            FragmentTransaction transaction1 = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            transaction1.replace(R.id.container, qRcodeFragment);

            transaction1.commit();


        });
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onBackPressed() {
        HomeFragment homeFragment = new HomeFragment();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_const, homeFragment);

        transaction.commit();
    }
}
