package com.example.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CurrentProductViewModel extends ViewModel {
    private MutableLiveData<String> currentProductCode = new MutableLiveData<>();

    public void setCurrentProductCode(String value) {
        currentProductCode.setValue(value);
    }

    public LiveData<String> getCurrentProductCode() {
        return currentProductCode;
    }
}
