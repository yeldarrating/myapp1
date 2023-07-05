package com.example.myapplication;

import android.app.Application;

public class MyApplication extends Application {
    private Item[] items;


    public Item getSingleItem(String code) {
        if (items != null) {
            for (Item item : items) {
                if (item.getCode().equals(code)) {
                    return item;
                }
            }
        }
        return null;
    }


    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
