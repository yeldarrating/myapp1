package com.example.myapplication.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.myapplication.R;
import com.example.myapplication.view.GalleryFragment;
import com.example.myapplication.view.HistoryFragment;
import com.example.myapplication.view.ProductFragment;
import com.example.myapplication.viewmodel.CurrentProductViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.zxing.Result;

public class MainActivity extends AppCompatActivity {
    private CodeScanner codeScanner;
    private HistoryFragment historyFragment;
    private ProductFragment productFragment;
    private GalleryFragment galleryFragment;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    private View bottomSheetView;

    private Button galleryButton;

    private Button historyButton;

    private FragmentContainerView FCV;

    private CurrentProductViewModel currentProductViewModel;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int REQUEST_CAMERA_PERMISSION_SETTING = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historyButton = findViewById(R.id.history_button);
        galleryButton = findViewById(R.id.gallery_button);

        FCV = findViewById(R.id.container_bottom);

        currentProductViewModel = new ViewModelProvider(this).get(CurrentProductViewModel.class);

        // Check and request camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                // User has denied the permission previously, explain the situation
                showPermissionDeniedDialog();
            } else {
                // Request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_PERMISSION_REQUEST_CODE);
            }
        } else {
            // Permission already granted, initialize code scanner
            initializeCodeScanner();
        }

        // Get the bottom sheet view and set up the behavior
        bottomSheetView = findViewById(R.id.container_bottom);
        bottomSheetView.setVisibility(View.GONE);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        // Set the bottom sheet behavior callbacks
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                bottomSheetView.setClickable(false);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (slideOffset > 0.7f && bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    FCV.setBackgroundColor(getColor(android.R.color.white));
                } else if (slideOffset > 0.2f && slideOffset <= 0.7f && bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HALF_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    FCV.setBackground(getDrawable(R.drawable.rounded_corners));
                } else if (slideOffset <= 0.2f && bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    FCV.setBackground(getDrawable(R.drawable.rounded_corners));
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    FCV.setBackground(getDrawable(R.drawable.rounded_corners));
                }
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyFragment = new HistoryFragment();

                if (bottomSheetView.getVisibility() != View.VISIBLE) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

                bottomSheetView.setVisibility(View.VISIBLE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_bottom, historyFragment);
                ft.commitAllowingStateLoss();
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryFragment = new GalleryFragment();

                if (bottomSheetView.getVisibility() != View.VISIBLE) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

                bottomSheetView.setVisibility(View.VISIBLE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_bottom, galleryFragment);
                ft.commitAllowingStateLoss();
            }
        });
    }

    private void initializeCodeScanner() {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this, scannerView);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TAG", "run: " + result.getText());

                        Toast.makeText(MainActivity.this, result.getText(), Toast.LENGTH_SHORT).show();

                        currentProductViewModel.setCurrentProductCode(result.getText());
                        productFragment = new ProductFragment();


                        if (bottomSheetView.getVisibility() != View.VISIBLE) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }

                        bottomSheetView.setVisibility(View.VISIBLE);
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container_bottom, productFragment);
                        ft.commitAllowingStateLoss();
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });
    }

    private void showPermissionDeniedDialog() {
        // TODO: Show a dialog explaining the situation to the user
        Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
        // Optionally, provide a button to open the app settings

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openAppSettings();
            }
        }, 5000);
    }

    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CAMERA_PERMISSION_SETTING);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (codeScanner != null) {
            codeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        if (codeScanner != null) {
            codeScanner.releaseResources();
        }
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeCodeScanner();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA_PERMISSION_SETTING) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                initializeCodeScanner();
            } else {
                Toast.makeText(this, "Camera permission not provided", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public int getBottomSheetVisibility() {
        return bottomSheetView.getVisibility();
    }

    public void hideBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetView.setVisibility(View.GONE);
    }
}
