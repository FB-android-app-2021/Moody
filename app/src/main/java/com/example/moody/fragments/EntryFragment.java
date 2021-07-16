package com.example.moody.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moody.models.Entry;
import com.example.moody.MainActivity;
import com.example.moody.databinding.FragmentEntryBinding;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class EntryFragment extends Fragment {

    public static final String TAG = "EntryFragment";
    private TextView tvJournalQ;
    private EditText etEntry;
    private Button btnSave;
    private Button btnDelete;
    private String emotion;

    FragmentEntryBinding binding;

    public EntryFragment(String emotion) {
        this.emotion = emotion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate layout for fragment
       binding = FragmentEntryBinding.inflate(getLayoutInflater(), container, false);
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
                String journalEntry = etEntry.getText().toString();
                Log.i(TAG, "caption: " + journalEntry);
                if(journalEntry.isEmpty()) {
                    Toast.makeText(getContext(), "We can't save an empty entry. Write down a little of what's going on in your head.", Toast.LENGTH_LONG).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                Log.i(TAG, "currentUser: " + currentUser);
                saveEntry(journalEntry, currentUser, emotion);
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

    private void saveEntry(String caption, ParseUser currentUser, String emotion) {
        Entry entry = new Entry();
        entry.setCaption(caption);
        entry.setAuthor(currentUser);
        entry.setEmotion(emotion);
        entry.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Post save in Parse failed.", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post save was successful!");
                //clear entry box after submit
                etEntry.setText("");
            }
        });
    }


}