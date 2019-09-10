package com.example.beanfinedust;

import android.os.Bundle;
import android.view.View;

import com.example.beanfinedust.databinding.ActivityStartBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class StartActivity extends AppCompatActivity {
    private ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_start);

        binding.startBtn.setOnClickListener( v -> {
            binding.startBtn.setVisibility(View.GONE);
            binding.loginBtn.setVisibility(View.VISIBLE);
            binding.registerBtn.setVisibility(View.VISIBLE);
        });
    }
}
