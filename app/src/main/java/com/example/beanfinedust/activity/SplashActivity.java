package com.example.beanfinedust.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.beanfinedust.MapActivity;
import com.example.beanfinedust.R;
import com.example.beanfinedust.SaveSharedPreference;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String userData = SaveSharedPreference.getUserData(SplashActivity.this);
        Log.d("SaveShared userdata", userData);
        if (userData.length() == 1) {
            // call Login Activity
            Handler hd = new Handler();
            hd.postDelayed(new SplashHandler(), 1000);
        } else {
            String[] data = userData.split(",");
            FirebaseAuth.getInstance().signInWithEmailAndPassword(data[0], data[1])
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(SplashActivity.this, "성공", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MapActivity.class));
                        } else {
                            // 로그인 실패
                            Toast.makeText(SplashActivity.this, "실패", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), StartActivity.class));
                        }
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        SplashActivity.this.finish();
                    });
        }
    }

    private class SplashHandler implements Runnable {

        public void run() {
            startActivity(new Intent(getApplicationContext(), StartActivity.class));
        }
    }

}
