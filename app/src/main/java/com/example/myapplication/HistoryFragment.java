package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.Result;

public class HistoryFragment extends Fragment {

    String result;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView resultTextView;
    private Button closeButton;

    private MainActivity mainActivity;


    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(String result) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString("result", result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        resultTextView = view.findViewById(R.id.res);

        if (getArguments() != null) {
            result = getArguments().getString("result");
            MyApplication myApp = (MyApplication) requireActivity().getApplicationContext();
            Item item = myApp.getSingleItem(result);
            if (item != null) {
                resultTextView.setText(item.getBrand());
            } else {
                resultTextView.setText("Item not found");
            }
        } else {
            resultTextView.setText("");
        }

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


    private void hideFragment() {
        if (mainActivity != null) {
            mainActivity.hideBottomSheet();
        }
    }

}
