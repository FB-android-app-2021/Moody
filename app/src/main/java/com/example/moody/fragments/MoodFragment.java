package com.example.moody.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.moody.R;
import com.example.moody.databinding.FragmentMoodBinding;


public class MoodFragment extends Fragment {

    private TextView tvMoodQ;
    private Button btnHappy;
    private Button btnSad;
    FragmentMoodBinding binding;
    String emotion;
    public static final String TAG = "MoodFragment";

    public MoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMoodBinding.inflate(getLayoutInflater(), container, false);
        View view = binding.getRoot();
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvMoodQ = binding.tvMoodQ;
        btnHappy = binding.btnHappy;
        btnSad = binding.btnSad;

        btnHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "Happy";
                goEntryFragment(emotion);
            }
        });
        btnSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "Sad";
                goEntryFragment(emotion);
            }
        });
    }
    private void goEntryFragment(String emotion) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, new EntryFragment(emotion));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}