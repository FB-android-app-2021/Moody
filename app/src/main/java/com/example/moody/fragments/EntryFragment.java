package com.example.moody.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moody.R;
import com.example.moody.models.Entry;
import com.example.moody.MainActivity;
import com.example.moody.databinding.FragmentEntryBinding;
import com.example.moody.models.Movie;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

public class EntryFragment extends Fragment {

    public static final String TAG = "EntryFragment";
    private TextView tvJournalQ;
    private EditText etEntry;
    private Button btnSave;
    private Button btnDelete;
    private String recEmotion;


    FragmentEntryBinding binding;

    public EntryFragment(String emotion) {
        this.recEmotion = emotion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        binding.entryScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getWindow()
                        .getCurrentFocus().getWindowToken(), 0);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String journalEntry = etEntry.getText().toString();
                if(journalEntry.isEmpty()) {
                    Toast.makeText(getContext(), "We can't save an empty entry. Write down a little of what's going on in your head.", Toast.LENGTH_LONG).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                saveEntry(journalEntry, currentUser, recEmotion);
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
        i.putExtra("emotion", recEmotion);
        i.putExtra("frgToLoad", "media");
        startActivity(i);
    }

    private void saveEntry(String caption, ParseUser currentUser, String emotion) {
        Entry entry = new Entry();
        entry.setCaption(caption);
        entry.setAuthor(currentUser);
        entry.setEmotion(emotion);
        entry.setLikes(0);
        entry.setExclaims(0);
        entry.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null) {
                    Toast.makeText(getContext(), "Post save in Parse failed.", Toast.LENGTH_SHORT).show();
                }
                //clear entry box after submit
                etEntry.setText("");
            }
        });
    }



}