package com.example.moody.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moody.EntriesAdapter;
import com.example.moody.Entry;
import com.example.moody.databinding.FragmentJournalBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class JournalFragment extends Fragment {

    private RecyclerView rvEntries;
    FragmentJournalBinding binding;
    public static final String TAG = "JournalFragment";
    protected EntriesAdapter adapter;
    protected List<Entry> allEntries;

    public JournalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJournalBinding.inflate(getLayoutInflater(), container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvEntries = binding.rvEntries;

        allEntries = new ArrayList<>();
        adapter = new EntriesAdapter(getContext(), allEntries);
        rvEntries.setAdapter(adapter);
        rvEntries.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
    }
    protected void queryPosts() {
        // specify what type of data we want to query - Entry.class
        ParseQuery<Entry> query = ParseQuery.getQuery(Entry.class);
        query.include(Entry.KEY_AUTHOR);
        query.whereEqualTo(Entry.KEY_AUTHOR, ParseUser.getCurrentUser());
        query.setLimit(20);
        //query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Entry>() {
            @Override
            public void done(List<Entry> entries, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                // for debugging purposes let's print every post description to logcat
                for (Entry post : entries) {
                    Log.i(TAG, "Post: " + post.getCaption() + ", username: " + post.getAuthor().getUsername());
                }

                // save received posts to list and notify adapter of new data
                allEntries.addAll(entries);
                adapter.notifyDataSetChanged();
            }
        });
    }

}