package com.example.beanfinedust.ui.manage_device;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.beanfinedust.OnBackPressedListener;
import com.example.beanfinedust.R;
import com.example.beanfinedust.databinding.FragmentRecyclerBinding;
import com.example.beanfinedust.ui.home.HomeFragment;

import java.util.Objects;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerFragment extends Fragment implements DevicesAdapter.OnItemClickListener, OnBackPressedListener {

    private ObservableArrayList<MyDeviceList> deviceList;
    private DevicesAdapter devicesAdapter;
    private RecyclerDeviceViewModel viewModel;

    public RecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentRecyclerBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler, container, false);
        // Inflate the layout for this fragment
        View view = binding.getRoot();
        devicesAdapter = new DevicesAdapter();
        deviceList = new ObservableArrayList<>();
        binding.recyclerView.setAdapter(devicesAdapter);
        binding.setDeviceList(deviceList);
        devicesAdapter.setOnItemClickListener(this);

        viewModel = ViewModelProviders.of(this).get(RecyclerDeviceViewModel.class);
        viewModel.initDatabase(getContext());

        viewModel.liveDeviceData.observe(this, data ->{

            for(String code : viewModel.device_code_list){
                deviceList.add(data.get(code));
            }
        });

        return view;
    }

    @Override
    public void onItemClick(View view, MyDeviceList myDeviceList) {

        Toast.makeText(getContext(), myDeviceList.getCode(), Toast.LENGTH_SHORT).show();
        EditDeviceFragment editDeviceFragment = new EditDeviceFragment(myDeviceList.getCode());

        Log.d("first getActivity",getActivity().toString());
        FragmentTransaction transaction1 = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.manage_container, editDeviceFragment);
        transaction1.commit();

    }
    @Override
    public void onBackPressed() {
        HomeFragment homeFragment = new HomeFragment();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_const, homeFragment);

        transaction.commit();
    }
}
