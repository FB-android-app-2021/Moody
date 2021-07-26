package com.example.moody.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moody.callbacks.MovieCallBackPresenter;
import com.example.moody.adapters.MovieAdapter;
import com.example.moody.adapters.ShowAdapter;
import com.example.moody.callbacks.TVCallBackPresenter;
import com.example.moody.databinding.FragmentMediaBinding;
import com.example.moody.models.Movie;
import com.example.moody.models.MovieRecommender;
import com.example.moody.models.ShowRecommender;
import com.example.moody.models.TVShow;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import java.util.ArrayList;
import java.util.List;

public class MediaFragment extends Fragment {
    public static final String TAG = "MediaFragment";

    private TextView tvMovieCategory;
    private RecyclerView rvMovies;
    private TextView tvTVCategory;
    private RecyclerView rvTVShows;
    private TextView tvMusicCategory;
    private RecyclerView rvMusic;
    private View loadingBar;
    LoaderManager loaderManager;
    String emotion;

    ArrayList<Movie> movieRecs;
    MovieAdapter movieAdapter;
    MovieRecommender moodMovies;

    ArrayList<TVShow> showRecs;
    ShowAdapter showAdapter;
    ShowRecommender moodShows;


    FragmentMediaBinding binding;

    public MediaFragment(String emotion) {
        this.emotion = emotion;
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
        rvTVShows = binding.rvTVshows;
        tvTVCategory = binding.tvTVCategory;
        rvMusic = binding.rvMusic;
        tvTVCategory = binding.tvMusicCategory;
        loadingBar = binding.loadingBar;

        //movie recyclerview initialization
        movieRecs = new ArrayList<>();
        moodMovies = new MovieRecommender(emotion);
        movieAdapter = new MovieAdapter(getActivity(), movieRecs);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        moodMovies.fetchMovieData(new MovieCallBackPresenter() {
            @Override
            public void success(List<Movie> movies) {
                movieRecs.addAll(movies);
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void showError(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showLoader() {
                loadingBar.setVisibility(View.VISIBLE);
                rvMovies.setVisibility(View.GONE);
            }

            @Override
            public void hideLoader() {
                loadingBar.setVisibility(View.GONE);
                rvMovies.setVisibility(View.VISIBLE);
            }
        });

        //tv show recyclerview initialization
        showRecs = new ArrayList<>();
        moodShows = new ShowRecommender(emotion);
        showAdapter = new ShowAdapter(getActivity(), showRecs);
        rvTVShows.setAdapter(showAdapter);
        rvTVShows.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        moodShows.fetchTVData(new TVCallBackPresenter() {
            @Override
            public void success(List<TVShow> shows) {
                showRecs.addAll(shows);
                showAdapter.notifyDataSetChanged();
            }

            @Override
            public void showError(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showLoader() {
                loadingBar.setVisibility(View.VISIBLE);
                rvTVShows.setVisibility(View.GONE);
            }

            @Override
            public void hideLoader() {
                loadingBar.setVisibility(View.GONE);
                rvTVShows.setVisibility(View.VISIBLE);
            }
        });
    }
}