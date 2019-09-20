package com.example.beanfinedust.ui.manage_device;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManageDeviceViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ManageDeviceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}