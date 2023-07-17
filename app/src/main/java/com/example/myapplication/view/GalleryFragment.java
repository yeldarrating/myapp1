package com.example.myapplication.view;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.GalleryAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private static final int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST = 1;

    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;
    private List<String> imagePaths;
    private View rootView;

    private static final int REQUEST_SELECT_IMAGE = 1;

    private OnActivityResultListener onActivityResultListener;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = rootView.findViewById(R.id.gallery_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // Request the read external storage permission if not granted
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_PERMISSION_REQUEST);

            openGallery();
        } else {
            loadGalleryImages();
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        ImageView closeButton = view.findViewById(R.id.close_btn);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the fragment
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    activity.hideBottomSheet();
                }
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }

    private void loadGalleryImages() {
        imagePaths = getGalleryImages();

        galleryAdapter = new GalleryAdapter(getContext());
        galleryAdapter.setImagePaths(imagePaths);

        recyclerView.setAdapter(galleryAdapter);
    }

    private List<String> getGalleryImages() {
        List<String> paths = new ArrayList<>();
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                String imagePath = cursor.getString(columnIndex);
                paths.add(imagePath);
            }
            cursor.close();
        }

        return paths;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            // Image selected successfully
            // You can get the selected image URI from the data intent
            Uri imageUri = data.getData();

            // Create a Vision API BarcodeDetector
            BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(requireContext())
                    .setBarcodeFormats(Barcode.ALL_FORMATS)
                    .build();

            // Load the image using a Bitmap
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Failed to load image", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a Frame object from the bitmap
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();

            // Detect barcodes from the frame using the BarcodeDetector
            SparseArray<Barcode> barcodes = barcodeDetector.detect(frame);

            if (barcodes.size() > 0) {
                // Barcode detected
                Barcode barcode = barcodes.valueAt(0);
                String barcodeText = barcode.displayValue;

                // Do something with the decoded barcode text
                if (onActivityResultListener != null) {
                    onActivityResultListener.onGalleryActivityResult(barcodeText);
                }
            } else {
                // No barcodes detected
                Toast.makeText(getActivity(), "Штрих-код не был распознан", Toast.LENGTH_SHORT).show();
            }

            // Release resources
            barcodeDetector.release();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadGalleryImages();
            } else {
                // Permission denied
                // Handle the case when the user denies the permission
                // Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface OnActivityResultListener {
        void onGalleryActivityResult(String imageUri);
    }

    public void setOnActivityResultListener(OnActivityResultListener listener) {
        onActivityResultListener = listener;
    }
}
