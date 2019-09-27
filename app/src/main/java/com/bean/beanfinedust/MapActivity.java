package com.bean.beanfinedust;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.beanfinedust.activity.LoginActivity;
import com.bean.beanfinedust.ui.add_device.AddDeviceFragment;
import com.bean.beanfinedust.ui.add_device.QRcodeFragment;
import com.bean.beanfinedust.ui.analysis.AnalysisFragment;
import com.bean.beanfinedust.ui.home.HomeFragment;
import com.bean.beanfinedust.ui.manage_device.ManageDeviceFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

public class MapActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    HomeFragment homeFragment = new HomeFragment();
                    transaction1.replace(R.id.main_const, homeFragment);
                    break;
                case R.id.nav_analysis:
                    AnalysisFragment analysisFragment = new AnalysisFragment();
                    transaction1.replace(R.id.main_const, analysisFragment);
                    break;
                case R.id.nav_add_device:
                    AddDeviceFragment addDeviceFragment = new AddDeviceFragment();
                    transaction1.replace(R.id.main_const, addDeviceFragment);
                    break;
                case R.id.nav_manage_device:
                    ManageDeviceFragment manageDeviceFragment = new ManageDeviceFragment();
                    transaction1.replace(R.id.main_const, manageDeviceFragment);
                    break;
            }
            drawer.closeDrawer(Gravity.LEFT);
            transaction1.commit();

            return true;
        });

        View headerView = navigationView.getHeaderView(0);
        TextView emailTextView = headerView.findViewById(R.id.nav_header_email);
        emailTextView.setText(SaveSharedPreference.getUserData(this).split(",")[0]);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_analysis, R.id.nav_add_device,
//                R.id.nav_manage_device)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        transaction.replace(R.id.main_const, homeFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);

//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.activity_map_drawer,menu);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawer.openDrawer(navigationView);
                break;
            case R.id.action_settings:
                SaveSharedPreference.clearUserData(getApplication());
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();
                // todo
            } else {
                QRcodeFragment qRcodeFragment = new QRcodeFragment();
                qRcodeFragment.onQRCodeRead(this,result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null) {
            //TODO: Perform your logic to pass back press here
            for(Fragment fragment : fragmentList){
                Log.d("fragment_name_check",fragment.getClass().toString());
                if(fragment instanceof OnBackPressedListener){
                    ((OnBackPressedListener)fragment).onBackPressed();
                }
            }
        }
    }
}
