package com.example.beanfinedust.ui.manage_device;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beanfinedust.databinding.MyDeviceItemBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.MyViewHolder> {

    private List<MyDeviceList> myDeviceLists;

    DevicesAdapter() {
        this.myDeviceLists = new ArrayList<>();
    }

    public OnItemClickListener mOnItemClickListener = null;



    public interface OnItemClickListener {

        void onItemClick(View view, MyDeviceList myDeviceList);

    }



    public void setOnItemClickListener(OnItemClickListener listener) {

        mOnItemClickListener = listener;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyDeviceItemBinding binding = MyDeviceItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyDeviceList myDeviceList = myDeviceLists.get(position);
        holder.bind(myDeviceList);
        holder.binding.panel.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, myDeviceList));
    }

    void setItem(List<MyDeviceList> myDeviceLists){
        if(myDeviceLists == null) return;
        this.myDeviceLists = myDeviceLists;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return myDeviceLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MyDeviceItemBinding binding;

        MyViewHolder(MyDeviceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MyDeviceList deviceList) {
            binding.setDevice(deviceList);
        }
    }
}
