package com.example.beanfinedust.ui.analysis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.beanfinedust.OnBackPressedListener;
import com.example.beanfinedust.R;
import com.example.beanfinedust.ui.home.HomeFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class AnalysisFragment extends Fragment implements OnBackPressedListener {

    private AnalysisViewModel analysisViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        analysisViewModel =
                ViewModelProviders.of(this).get(AnalysisViewModel.class);
        View root = inflater.inflate(R.layout.fragment_analysis, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        analysisViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onBackPressed() {
        HomeFragment homeFragment = new HomeFragment();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_const, homeFragment);

        transaction.commit();
    }
}