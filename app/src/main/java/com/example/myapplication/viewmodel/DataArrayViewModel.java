package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Item;

import java.util.ArrayList;
import java.util.List;

public class DataArrayViewModel extends ViewModel {
    private List<Item> itemList;

    public DataArrayViewModel() {
        initData();
    }

    public void initData() {

    }


    public List<Item> getItems() {
        return itemList;
    }

    public Item getSingleItem(String code) {
        if (itemList != null) {
            for (Item item : itemList) {
                if (item.getCode().equals(code)) {
                    return item;
                }
            }
        }
        return null;
    }
}
