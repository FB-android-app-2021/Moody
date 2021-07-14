package com.example.moody.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moody.DailyActivity;
import com.example.moody.LoginActivity;
import com.example.moody.MainActivity;
import com.example.moody.databinding.FragmentJournalBinding;

public class JournalFragment extends Fragment {

    public static final String TAG = "JournalFragment";
    private TextView tvJournalQ;
    private EditText etEntry;
    private Button btnSave;
    private Button btnDelete;

    FragmentJournalBinding binding;

    public JournalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate layout for fragment
       binding = FragmentJournalBinding.inflate(getLayoutInflater(), container, false);
       View view = binding.getRoot();
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvJournalQ = binding.tvJournalQ;
        etEntry = binding.etEntry;
        btnSave = binding.btnSave;
        btnDelete = binding.btnDelete;

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainActivity();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainActivity();
            }
        });
    }
    public void goMainActivity() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
    }


}