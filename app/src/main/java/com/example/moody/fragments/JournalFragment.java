package com.example.moody.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.moody.ComposeActivity;
import com.example.moody.adapters.EntriesAdapter;
import com.example.moody.models.Entry;
import com.example.moody.databinding.FragmentJournalBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


public class JournalFragment extends Fragment {

    public static final String TAG = "JournalFragment";
    private RecyclerView rvEntries;
    private TextView tvJournalTitle;
    private Button btnCompose;
    private TextView tvIdea;

    FragmentJournalBinding binding;
    protected EntriesAdapter adapter;
    protected List<Entry> allEntries;

    AsyncHttpClient client = new AsyncHttpClient();
    public static final String IDEA_URL = "http://www.boredapi.com/api/activity/";

    public JournalFragment() {
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
        tvJournalTitle = binding.tvJournalTitle;
        btnCompose = binding.btnCompose;
        tvIdea = binding.tvIdea;

        allEntries = new ArrayList<>();
        adapter = new EntriesAdapter(getContext(), allEntries);
        rvEntries.setAdapter(adapter);
        rvEntries.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();

        btnCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               goMoodFragment();
            }
        });

        client.get(IDEA_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    String idea = jsonObject.getString("activity");
                    tvIdea.setText(idea);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });

    }
    protected void queryPosts() {
        // specify what type of data we to query -> Entry.class
        ParseQuery<Entry> query = ParseQuery.getQuery(Entry.class);
        query.include(Entry.KEY_AUTHOR);
        query.whereEqualTo(Entry.KEY_AUTHOR, ParseUser.getCurrentUser());
        query.addDescendingOrder("createdAt");
        query.setLimit(20);
        query.findInBackground(new FindCallback<Entry>() {
            @Override
            public void done(List<Entry> entries, ParseException e) {
                if (e != null) {
                    return;
                }
                allEntries.addAll(entries);
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void goMoodFragment() {
        Intent i = new Intent(getActivity(), ComposeActivity.class);
        startActivity(i);
    }

}