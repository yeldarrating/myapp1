package com.example.myapplication.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.ui.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.db.Product;
import com.example.myapplication.db.ProductDao;
import com.example.myapplication.db.ProductDatabase;
import com.example.myapplication.ui.MainActivity;
import com.example.myapplication.viewmodel.ProductArrayViewModel;

import java.util.List;

public class HistoryFragment extends Fragment {
    private View rootView;
    private ProductArrayViewModel productArrayViewModel;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_history, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productArrayViewModel = new ViewModelProvider(this).get(ProductArrayViewModel.class);

        productArrayViewModel.getAllProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                Toast.makeText(getContext(), "onChanged", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onChanged: " + products.size());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), products.get(0).getBarcode(), Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });

        ImageView closeButton = view.findViewById(R.id.close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the fragment
                MainActivity activity = (MainActivity) requireActivity();
                if (activity != null) {
                    activity.hideBottomSheet();
                }
            }
        });
    }
}
