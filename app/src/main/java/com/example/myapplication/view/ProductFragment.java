package com.example.myapplication.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.db.product.Product;
import com.example.myapplication.ui.MainActivity;
import com.example.myapplication.viewmodel.CurrentProductViewModel;
import com.example.myapplication.viewmodel.ProductArrayViewModel;

public class ProductFragment extends Fragment {
    private TextView tvCode;
    private TextView tvComposition;
    private TextView tvDescription;

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productArrayViewModel = new ViewModelProvider(requireActivity()).get(ProductArrayViewModel.class);
        currentProductViewModel = new ViewModelProvider(requireActivity()).get(CurrentProductViewModel.class);

        TextView tvCode = view.findViewById(R.id.product_code);
        TextView tvComposition = view.findViewById(R.id.product_composition);
        TextView tvDescription = view.findViewById(R.id.product_description);

        currentProductViewModel.getCurrentProductCode().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                productArrayViewModel.getSingleProduct(s).observe(getViewLifecycleOwner(), new Observer<Product>() {
                    @Override
                    public void onChanged(Product product) {
                        if (product != null) {
                            Log.d("TAG", "onChanged: " + product.getBrand());
                            Toast.makeText(requireContext(), product.getBrand(), Toast.LENGTH_SHORT).show();

                            tvCode.setText(product.getBarcode());
                            tvComposition.setText(product.getComposition());
                            tvDescription.setText(product.getDescription());

                        } else {
                            Toast.makeText(requireContext(), "Product not found for the given code", Toast.LENGTH_SHORT).show();
                            hideFragment();
                        }
                    }
                });
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
    }

    private void hideFragment() {
        if (mainActivity != null) {
            mainActivity.hideBottomSheet();
        }
    }
}
