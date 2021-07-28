package com.example.moody.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moody.R;
import com.example.moody.adapters.EntriesAdapter;
import com.example.moody.databinding.FragmentAnonFeedBinding;
import com.example.moody.models.Entry;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class AnonFeedFragment extends Fragment {
    public static final String TAG = "AnonFeedFragment";

    private RecyclerView rvAnonPosts;
    //private TextView tvPost;
    protected EntriesAdapter adapter;
    protected List<Entry> allPosts;

    FragmentAnonFeedBinding binding;

    public AnonFeedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAnonFeedBinding.inflate(getLayoutInflater(), container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvAnonPosts = binding.rvAnonPosts;
        allPosts = new ArrayList<>();
        adapter = new EntriesAdapter(getContext(), allPosts);
        rvAnonPosts.setAdapter(adapter);
        rvAnonPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
    }
    protected void queryPosts() {
        // specify what type of data we to query -> Entry.class
        ParseQuery<Entry> query = ParseQuery.getQuery(Entry.class);
        query.addDescendingOrder("createdAt");
        query.setLimit(20);
        query.findInBackground(new FindCallback<Entry>() {
            @Override
            public void done(List<Entry> posts, ParseException e) {
                if (e != null) {
                    return;
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }

}