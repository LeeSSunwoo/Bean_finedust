package com.bean.beanfinedust.ui.manage_device;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterBindings {
    @BindingAdapter("bind:item")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<MyDeviceList> myDeviceLists) {
        DevicesAdapter adapter = (DevicesAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItem(myDeviceLists);
        }
    }
}
