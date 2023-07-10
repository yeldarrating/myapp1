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
        itemList = new ArrayList<>();

        itemList.add(new Item("Item 1", "Composition 1", "Manufacturer 1", "Brand 1",
                "Country 1", "Weight 1", "Volume 1", "460123456789"));
        itemList.add(new Item("Item 2", "Composition 2", "Manufacturer 2", "Brand 2",
                "Country 2", "Weight 2", "Volume 2", "460123456711"));
        itemList.add(new Item("Item 3", "Composition 3", "Manufacturer 3", "Brand 3",
                "Country 3", "Weight 3", "Volume 3", "97802013796"));
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
