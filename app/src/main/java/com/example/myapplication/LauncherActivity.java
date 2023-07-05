package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setItems();


                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(intent);


//                finish();
            }
        }, 5000);
    }

    public void setItems() {
        try {

            Item[] items = new Item[3];

            Item item1 = new Item("Item 1", "Composition 1", "Manufacturer 1", "Brand 1",
                    "Country 1", "Weight 1", "Volume 1", "460123456789");
            items[0] = item1;

            Item item2 = new Item("Item 2", "Composition 2", "Manufacturer 2", "Brand 2",
                    "Country 2", "Weight 2", "Volume 2", "460123456711");
            items[1] = item2;

            Item item3 = new Item("Item 3", "Composition 3", "Manufacturer 3", "Brand 3",
                    "Country 3", "Weight 3", "Volume 3", "97802013796");
            items[2] = item3;

            Log.d("TAG", "setItems: " + items.length);

            MyApplication myApp = (MyApplication) getApplicationContext();
            myApp.setItems(items);

        } catch (Exception e) {
            Log.d("TAG", "setItems: " + e.getMessage());
        }
    }
}