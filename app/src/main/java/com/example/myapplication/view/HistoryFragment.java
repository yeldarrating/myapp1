package com.example.myapplication.view;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.adapter.HistoryAdapter;
import com.example.myapplication.db.history.History;
import com.example.myapplication.ui.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.db.product.Product;
import com.example.myapplication.viewmodel.CurrentProductViewModel;
import com.example.myapplication.viewmodel.HistoryViewModel;
import com.example.myapplication.viewmodel.ProductArrayViewModel;

import java.util.List;

public class HistoryFragment extends Fragment {
    private View rootView;
    private ProductArrayViewModel productArrayViewModel;
    private CurrentProductViewModel currentProductViewModel;

    private HistoryAdapter historyAdapter;

    private HistoryViewModel historyViewModel;

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

        try {
            historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

            historyAdapter = new HistoryAdapter();

            // Set the adapter for the RecyclerView
            RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setAdapter(historyAdapter);

            historyViewModel.getAllHistory().observe(getViewLifecycleOwner(), new Observer<List<History>>() {
                @Override
                public void onChanged(List<History> histories) {
                    if (histories.size() > 0) {
                        historyAdapter.setData(histories);
                    }
                }
            });
        } catch (Exception e) {
            Log.d("TAG", "onViewCreated: " + e.getMessage());
        }

//
//        productArrayViewModel = new ViewModelProvider(this).get(ProductArrayViewModel.class);
//        currentProductViewModel = new ViewModelProvider(this).get(CurrentProductViewModel.class);
//
//        currentProductViewModel.setCurrentProductCode("4870207313717");
//
//        String currentBarcode = currentProductViewModel.getCurrentProductCode().getValue();
//



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
