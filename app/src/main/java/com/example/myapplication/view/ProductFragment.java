package com.example.myapplication.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.ui.MainActivity;
import com.example.myapplication.viewmodel.CurrentProductViewModel;
import com.example.myapplication.viewmodel.DataArrayViewModel;

public class ProductFragment extends Fragment {
    private TextView resultTextView;
    private Button closeButton;
    private MainActivity mainActivity;
    private CurrentProductViewModel currentProductViewModel;
    private DataArrayViewModel dataArrayViewModel;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            currentProductViewModel = new ViewModelProvider(requireActivity()).get(CurrentProductViewModel.class);
            dataArrayViewModel = new ViewModelProvider(requireActivity()).get(DataArrayViewModel.class);
        } catch (Exception e) {
            Log.d("TAG", "onCreate: " + e.getMessage());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        resultTextView = view.findViewById(R.id.res);

        currentProductViewModel.getCurrentProductCode().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String currentProductCode) {

                Log.d("TAG", "onChanged: " + currentProductCode);
                retrieveItem(currentProductCode);
            }
        });

        if (getActivity() instanceof MainActivity) {
            mainActivity = (MainActivity) getActivity();
        }

        closeButton = view.findViewById(R.id.close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFragment();
            }
        });

        return view;
    }

    private void retrieveItem(String code) {
        try {
            if (code != null) {
                Item item = dataArrayViewModel.getSingleItem(code);
                if (item != null) {
                    resultTextView.setText(item.getBrand());
                } else {
                    resultTextView.setText("Item not found");
                }
            } else {
                resultTextView.setText("");
            }
        } catch (Exception e) {
            Log.d("TAG", "retrieveItem: " + e.getMessage());
        }
    }

    private void hideFragment() {
        if (mainActivity != null) {
            mainActivity.hideBottomSheet();
        }
    }
}
