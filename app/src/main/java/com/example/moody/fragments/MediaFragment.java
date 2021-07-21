package com.example.moody.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.moody.CustomAsyncTaskLoader;
import com.example.moody.adapters.MovieAdapter;
import com.example.moody.databinding.FragmentMediaBinding;
import com.example.moody.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MediaFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    List<Movie> movieRecs;
    private TextView tvMovieCategory;
    private RecyclerView rvMovies;
    private View loadingBar;
    MovieAdapter movieAdapter;
    LoaderManager loaderManager;

    //API Key b094fc10e8fc702bfc06d84810d0728
    public static final String TAG = "MediaFragment";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String POPULAR_KEY
            = "movie/popular?api_key=eb094fc10e8fc702bfc06d84810d0728";
    public static final String TOP_RATED_KEY
            = "movie/top_rated?api_key=eb094fc10e8fc702bfc06d84810d0728";


    FragmentMediaBinding binding;
    public MediaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMediaBinding.inflate(getLayoutInflater(), container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovies = binding.rvMovies;
        tvMovieCategory = binding.tvMovieCategory;
        loadingBar = binding.loadingBar;

        movieRecs = new ArrayList<>();
        movieAdapter = new MovieAdapter(getActivity(), movieRecs);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        getLoaderManager().initLoader(0, null, this);


       AsyncHttpClient client = new AsyncHttpClient();
       client.get(BASE_URL + TOP_RATED_KEY, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    movieRecs.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + movieRecs.size());
                } catch (JSONException e) {
                    //log error
                    Log.e(TAG, "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
        client.get(BASE_URL + POPULAR_KEY, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    movieRecs.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + movieRecs.size());
                } catch (JSONException e) {
                    //log error
                    Log.e(TAG, "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    @Override
    public CustomAsyncTaskLoader onCreateLoader(int id, Bundle args) {
        CustomAsyncTaskLoader asyncTaskLoader = new CustomAsyncTaskLoader(getActivity(),(ArrayList<Movie>) movieRecs);
        asyncTaskLoader.forceLoad();
        Log.i(TAG, "onCreateLoader");
        return asyncTaskLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        movieAdapter.notifyDataSetChanged();
        loadingBar.setVisibility(View.GONE);
        rvMovies.setVisibility(View.VISIBLE);
        Log.i(TAG, "onLoadFinish");
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        rvMovies.setAdapter(null);
        Log.i(TAG, "onLoaderReset");
    }

}