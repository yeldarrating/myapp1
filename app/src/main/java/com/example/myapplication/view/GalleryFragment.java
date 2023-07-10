package com.example.myapplication.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private static final int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST = 1;

    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;
    private List<String> imagePaths;
    private View rootView;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = rootView.findViewById(R.id.gallery_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

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

        // Request the read external storage permission if not granted
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_PERMISSION_REQUEST);
        } else {
            loadGalleryImages();
        }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadGalleryImages();
            } else {
                // Permission denied
                // Handle the case when the user denies the permission
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
