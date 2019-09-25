package com.example.beanfinedust.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.beanfinedust.R;
import com.example.beanfinedust.ui.add_device.MyPositionData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private HomeViewModel homeViewModel;
    private GoogleMap googleMap;
    private ConstraintLayout mLayout;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    boolean needRequest = false;

    Map<String, Marker> markerMap = new HashMap<>();
    List<String> codeList;

    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private FusedLocationProviderClient mFusedLocationClient;
    private MyPositionData myPositionData;
    View marker_root_view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));

        marker_root_view = LayoutInflater.from(getContext()).inflate(R.layout.map_custom_infowindow, null);

        homeViewModel.addedData.observe(this, data -> {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(data.getLatitude(),data.getLongitude()));
            TextView dust = marker_root_view.findViewById(R.id.dust_text);
            TextView time = marker_root_view.findViewById(R.id.time_text);
            dust.setText(data.getDevice_name());
            time.setText(data.getCode());
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getContext(), marker_root_view)));
            markerOptions.title(data.getCode());

            Marker m = googleMap.addMarker(markerOptions);

            markerMap.put(data.getCode(),m);

        });

        homeViewModel.changedData.observe(this, data -> {
            Marker marker = markerMap.get(data.getCode());
            TextView dust = marker_root_view.findViewById(R.id.dust_text);
            TextView time = marker_root_view.findViewById(R.id.time_text);
            dust.setText(data.getDevice_name());
            time.setText(data.getCode());
            marker.setTitle(data.getCode());
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getContext(), marker_root_view)));
//            InfoWindowData info = new InfoWindowData();
//            info.setDust(data.getDevice_name());
//            info.setTime(data.getCode());
            //marker.setTag(info);
//            marker.hideInfoWindow();
//            marker.showInfoWindow();
        });

        homeViewModel.codeList.observe(this, list -> {
//            codeList = new ArrayList<>();
//            codeList.addAll(list);
//            if(markerMap.size() == codeList.size()){
//                for(String code:codeList){
//                    markerMap.get(code).showInfoWindow();
//                }
//            }
        });

        mLayout = root.findViewById(R.id.home_layout);

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        myPositionData = new MyPositionData(getActivity(), getContext(), googleMap, mFusedLocationClient, true);

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
        googleMap.setOnMarkerClickListener(this);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (myPositionData.checkLocationServicesStatus()) {
                    if (myPositionData.checkLocationServicesStatus()) {

                        Log.d("GPS CHECK", "onActivityResult : GPS 활성화 되있음");


                        needRequest = true;

                        return;
                    }
                }

                break;
        }
    }

    private Bitmap createDrawableFromView(Context context, View view) {

        view.setLayoutParams(new
                ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        view.measure(dm.widthPixels, dm.heightPixels);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(canvas);
        return bitmap;

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);

        googleMap.animateCamera(center, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                googleMap.animateCamera(zoom);
            }

            @Override
            public void onCancel() {

            }
        });

        //marker.get

        return true;
    }
}