package com.example.beanfinedust;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.beanfinedust.databinding.ActivityStartBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class StartActivity extends AppCompatActivity {
    private ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start);

        Animation fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        binding.startBtn.setOnClickListener(v -> {
            binding.startBtn.startAnimation(fade_out);
            binding.loginBtn.startAnimation(fade_in);
            binding.registerBtn.startAnimation(fade_in);
        });

        binding.loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        binding.registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

    }

}
