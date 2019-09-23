package com.example.beanfinedust.ui.add_device;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.beanfinedust.R;
import com.google.zxing.integration.android.IntentIntegrator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class QRcodeFragment extends Fragment {

    private QRViewPager viewPager ;
    private QRPageAdapter qrPageAdapter;
    private int position = 0;
    //private FragmentActivity fragmentActivity;

    private IntentIntegrator qrScan;

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

        //fragmentActivity = getActivity();

        qrScan = new IntentIntegrator(getActivity());
        qrScan.setOrientationLocked(false); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됩니다.
        qrScan.setPrompt("기기의 QR코드를 인식해 주세요.");


        Button btn = view.findViewById(R.id.next_btn);
        btn.setOnClickListener(v -> {
            if(position == 0){
                viewPager.setCurrentItem(1);

            }
            position++;
            if(position >= 2){
                qrScan.initiateScan();
            }

        });



        super.onViewCreated(view, savedInstanceState);
    }

    public void onQRCodeRead(FragmentActivity fragmentActivity, String s){
        Log.d("QR",s);
        MyPositionFragment myPositionFragment = new MyPositionFragment(s);
        Log.d("second getActivity",fragmentActivity.toString());
        FragmentTransaction transaction1 = fragmentActivity.getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.container, myPositionFragment);

        transaction1.commitAllowingStateLoss();
    }
}
