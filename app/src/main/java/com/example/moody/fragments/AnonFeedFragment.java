package com.example.moody.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moody.R;
import com.example.moody.adapters.EntriesAdapter;
import com.example.moody.adapters.PostsAdapter;
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
    protected PostsAdapter adapter;
    protected List<Entry> allPosts;

    private SwipeRefreshLayout swipeContainer;

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

        swipeContainer = binding.swiperefresh;
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);
        rvAnonPosts.setAdapter(adapter);
        rvAnonPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_purple,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark);
    }
    protected void queryPosts() {
        // specify what type of data we to query -> Entry.class
        ParseQuery<Entry> query = ParseQuery.getQuery(Entry.class);
        query.addDescendingOrder(Entry.KEY_CREATED_AT);
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
    public void fetchTimelineAsync(int page) {
        ParseQuery<Entry> query = ParseQuery.getQuery(Entry.class);
        query.include(Entry.KEY_AUTHOR);
        query.addDescendingOrder(Entry.KEY_CREATED_AT);
        query.setLimit(20);
        query.findInBackground(new FindCallback<Entry>() {
            @Override
            public void done(List<Entry> posts, ParseException e) {
                if (e != null) {
                    return;
                }
                adapter.clear();
                adapter.addAll(posts);
                swipeContainer.setRefreshing(false);
            }
        });
    }

}