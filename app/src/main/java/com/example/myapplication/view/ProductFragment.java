package com.example.myapplication.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.db.history.History;
import com.example.myapplication.db.product.Product;
import com.example.myapplication.ui.MainActivity;
import com.example.myapplication.viewmodel.CurrentProductViewModel;
import com.example.myapplication.viewmodel.HistoryViewModel;
import com.example.myapplication.viewmodel.ProductArrayViewModel;

public class ProductFragment extends Fragment {
    private TextView tvCode;
    private TextView tvComposition;
    private TextView tvDescription;

    private TextView tvManufacturer;
    private TextView tvBrand;
    private TextView tvCountry;
    private TextView tvColor;
    private TextView tvWeight;
    private TextView tvVolume;
    private TextView tvBarcode;
    private TextView tvQuantity;

    private LinearLayout llCode;
    private LinearLayout llComposition;
    private LinearLayout llDescription;
    private LinearLayout llManufacturer;
    private LinearLayout llBrand;
    private LinearLayout llCountry;
    private LinearLayout llColor;
    private LinearLayout llWeight;
    private LinearLayout llVolume;
    private LinearLayout llBarcode;
    private LinearLayout llQuantity;

    private ImageView closeButton;
    private MainActivity mainActivity;
    private CurrentProductViewModel currentProductViewModel;
    private ProductArrayViewModel productArrayViewModel;

    private HistoryViewModel historyViewModel;

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
        historyViewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);

        tvCode = view.findViewById(R.id.product_code);
        tvComposition = view.findViewById(R.id.product_composition);
        tvDescription = view.findViewById(R.id.product_description);

        tvManufacturer = view.findViewById(R.id.product_manufacturer);
        tvBrand = view.findViewById(R.id.product_brand);
        tvCountry = view.findViewById(R.id.product_country);
        tvColor = view.findViewById(R.id.product_color);
        tvWeight = view.findViewById(R.id.product_weight);
        tvVolume = view.findViewById(R.id.product_volume);
        tvBarcode = view.findViewById(R.id.product_barcode);
        tvQuantity = view.findViewById(R.id.product_quantity);

        llCode = view.findViewById(R.id.code_layout);
        llComposition = view.findViewById(R.id.composition_layout);
        llDescription = view.findViewById(R.id.description_layout);
        llManufacturer = view.findViewById(R.id.manufacturer_layout);
        llBrand = view.findViewById(R.id.brand_layout);
        llCountry = view.findViewById(R.id.country_layout);
        llColor = view.findViewById(R.id.color_layout);
        llWeight = view.findViewById(R.id.weight_layout);
        llVolume = view.findViewById(R.id.volume_layout);
        llBarcode = view.findViewById(R.id.barcode_layout);
        llQuantity = view.findViewById(R.id.quantity_layout);

        currentProductViewModel.getCurrentProductCode().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                productArrayViewModel.getSingleProduct(s).observe(getViewLifecycleOwner(), new Observer<Product>() {
                    @Override
                    public void onChanged(Product product) {
                        if (product != null) {
                            Log.d("TAG", "onChanged: " + product.getBrand());
                            Toast.makeText(requireContext(), product.getBrand(), Toast.LENGTH_SHORT).show();

                            setTextOrNoValue(tvCode, product.getBarcode(), llCode);
                            setTextOrNoValue(tvComposition, product.getComposition(), llComposition);
                            setTextOrNoValue(tvDescription, product.getDescription(), llDescription);
                            setTextOrNoValue(tvManufacturer, product.getManufacturer(), llManufacturer);
                            setTextOrNoValue(tvBrand, product.getBrand(), llBrand);
                            setTextOrNoValue(tvCountry, product.getCountry(), llCountry);
                            setTextOrNoValue(tvColor, product.getColor(), llColor);
                            setTextOrNoValue(tvWeight, product.getWeight(), llWeight);
                            setTextOrNoValue(tvVolume, product.getVolume(), llVolume);
                            setTextOrNoValue(tvBarcode, product.getBarcode(), llBarcode); // Assuming this is intended
                            setTextOrNoValue(tvQuantity, String.valueOf(product.getQuantity()), llQuantity);

                            try {
                                String currentBarcode = product.getBarcode();
                                History currentHistory = new History(currentBarcode);
                                historyViewModel.insertHistory(currentHistory);
                            } catch (Exception e) {
                                Log.d("TAG", "onChanged: " + e.getMessage());
                            }

                        } else {
                            Toast.makeText(requireContext(), "Данного продукта не существует.", Toast.LENGTH_SHORT).show();
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

    private void setTextOrNoValue(TextView textView, String value, LinearLayout linearLayout) {
        if (value != null && !value.isEmpty()) {
            textView.setText(value);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }


    private void hideFragment() {
        if (mainActivity != null) {
            mainActivity.hideBottomSheet();
        }
    }
}
