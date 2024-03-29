package com.bean.beanfinedust.ui.add_device;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bean.beanfinedust.R;
import com.bean.beanfinedust.SaveSharedPreference;
import com.bean.beanfinedust.ui.manage_device.EditDeviceFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPositionFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;

    private String device;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;

    private static LatLng currentLatlng;

    private static final String TAG = "googlemap_example";

    private static final int PERMISSIONS_REQUEST_CODE = 100;
    boolean needRequest = false;

    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소

    ConstraintLayout mLayout;
    private MyPositionData myPositionData;

    private FusedLocationProviderClient mFusedLocationClient;

    private DatabaseReference databaseReference;

    private int clicked = 0;
    private String Email;
    private boolean update;

    public MyPositionFragment() {
        // Required empty public constructor
    }

    public MyPositionFragment(String device, boolean update) {
        this.update = update;
        this.device = device;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_position, container, false);

        mLayout = view.findViewById(R.id.position_layout);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.my_map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        String userData = SaveSharedPreference.getUserData(getActivity());
        Email = userData.split(",")[0];
        if(update){
            view.findViewById(R.id.before_image).setVisibility(View.GONE);
            view.findViewById(R.id.before_text).setVisibility(View.GONE);
            view.findViewById(R.id.before_const).setVisibility(View.GONE);
            view.findViewById(R.id.my_map).setVisibility(View.VISIBLE);
            Button btn = view.findViewById(R.id.add_position_btn);
            btn.setText("위치 변경하기");
        }

        view.findViewById(R.id.add_position_btn).setOnClickListener(v -> {
            if (update) {

                updateLocationData();
            } else {
                clicked++;
                if (clicked == 1) {
                    view.findViewById(R.id.before_image).setVisibility(View.GONE);
                    view.findViewById(R.id.before_text).setVisibility(View.GONE);
                    view.findViewById(R.id.before_const).setVisibility(View.GONE);
                    view.findViewById(R.id.my_map).setVisibility(View.VISIBLE);
                } else {
                    postFirebaseDatabase(true, false);
                    postFirebaseDatabase(true, true);
                }
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        myPositionData = new MyPositionData(getActivity(), getContext(), googleMap, mFusedLocationClient, false);

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            myPositionData.startLocationUpdates(); // 3. 위치 업데이트 시작

        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Snackbar.make(mLayout, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.",
                        Snackbar.LENGTH_INDEFINITE).setAction("확인", view -> {

                    // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                    ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                            PERMISSIONS_REQUEST_CODE);
                }).show();


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }


        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(() -> {
            myPositionData.startLocationUpdates();
            currentLatlng = myPositionData.currentPosition;
            return false;
        });
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.setOnMapClickListener(latLng -> {
            String markerTitle = myPositionData.getCurrentAddress(latLng);
            String markerSnippet = "위도:" + latLng.latitude
                    + " 경도:" + latLng.longitude;
            currentLatlng = myPositionData.setCurrentLocation(latLng, markerTitle, markerSnippet);

        });

        currentLatlng = myPositionData.currentPosition;
    }

    @Override
    public void onStop() {

        super.onStop();

        if (mFusedLocationClient != null) {

            Log.d(TAG, "onStop : call stopLocationUpdates");
            mFusedLocationClient.removeLocationUpdates(myPositionData.locationCallback);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (myPositionData.checkLocationServicesStatus()) {
                    if (myPositionData.checkLocationServicesStatus()) {

                        Log.d(TAG, "onActivityResult : GPS 활성화 되있음");


                        needRequest = true;

                        return;
                    }
                }

                break;
        }
    }

    public void postFirebaseDatabase(boolean add, boolean user_data) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        String location;
        if (add && !user_data) {
            location = "/기기데이터/" + device;
            FirebaseDeviceData post = new FirebaseDeviceData(currentLatlng, device);
            postValues = post.toMap();
            childUpdates.put(location, postValues);
            databaseReference.updateChildren(childUpdates);
        } else if (add && user_data) {
            Map<String, Object> user_device = new HashMap<>();
            user_device.put("serial_number", device);
            databaseReference.child("사용자_데이터").child(Email.split("@")[0]).child(device).updateChildren(user_device).addOnCompleteListener(getActivity(), task -> {
                if (task.isSuccessful()) {
                    // 기기 등록 성공
                    if (user_data) {
                        AddConfirmFragment addConfirmFragment = new AddConfirmFragment();

                        Log.d("first getActivity", getActivity().toString());
                        FragmentTransaction transaction1 = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                        transaction1.replace(R.id.container, addConfirmFragment);

                        transaction1.commit();
                    }
                } else {
                    // 실패
                    Toast.makeText(getContext(), "실패", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void updateLocationData() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> data = new HashMap<>();
        data.put("latitude", currentLatlng.latitude);
        data.put("longitude", currentLatlng.longitude);
        databaseReference.child("기기데이터").child(device).updateChildren(data).addOnCompleteListener(getActivity(), task -> {
            if (task.isSuccessful()) {
                EditDeviceFragment editDeviceFragment = new EditDeviceFragment(device);

                FragmentTransaction transaction1 = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.manage_container, editDeviceFragment);

                transaction1.commit();
            } else {
                // 실패
                Toast.makeText(getContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    static void setCurrentLatlng(LatLng latlng) {
        currentLatlng = latlng;
    }
}
