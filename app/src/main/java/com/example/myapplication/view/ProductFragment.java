package com.example.myapplication.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.db.Product;
import com.example.myapplication.model.Item;
import com.example.myapplication.ui.MainActivity;
import com.example.myapplication.viewmodel.CurrentProductViewModel;
import com.example.myapplication.viewmodel.DataArrayViewModel;
import com.example.myapplication.viewmodel.ProductArrayViewModel;

public class ProductFragment extends Fragment {
    private TextView resultTextView;
    private ImageView closeButton;
    private MainActivity mainActivity;
    private CurrentProductViewModel currentProductViewModel;
    private ProductArrayViewModel productArrayViewModel;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        try {
        productArrayViewModel = new ViewModelProvider(this).get(ProductArrayViewModel.class);
        currentProductViewModel = new ViewModelProvider(this).get(CurrentProductViewModel.class);

        String currentBarcode = String.valueOf(currentProductViewModel.getCurrentProductCode());


            productArrayViewModel.getSingleProduct(currentBarcode).observe(getViewLifecycleOwner(), new Observer<Product>() {
                @Override
                public void onChanged(Product product) {
                    Log.d("TAG", "onChanged: " + product.getBrand());
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
        } catch (Exception e) {
            Log.d("TAG", "onCreateView: " + e.getMessage());
        }
        return view;
    }


    private void hideFragment() {
        if (mainActivity != null) {
            mainActivity.hideBottomSheet();
        }
    }
}
