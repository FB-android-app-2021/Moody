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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moody.adapters.MovieAdapter;
import com.example.moody.adapters.ShowAdapter;
import com.example.moody.callbacks.MovieCallBackPresenter;
import com.example.moody.callbacks.TVCallBackPresenter;
import com.example.moody.databinding.FragmentMediaBinding;
import com.example.moody.models.Movie;
import com.example.moody.models.FetchShows;
import com.example.moody.models.FetchMovies;
import com.example.moody.models.TVShow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.moody.models.Movie.HAPPY_KEY;
import static com.example.moody.models.Movie.SAD_KEY;

public class MediaFragment extends Fragment {
    public static final String TAG = "MediaFragment";
    public static View loadingBar;
    public static RecyclerView rvMovies;
    public static String emotion;

    private TextView tvMovieCategory;
    private TextView tvTVCategory;
    private RecyclerView rvTVShows;

    public static ArrayList<TVShow> showRecs;
    public static ShowAdapter showAdapter;
    public static FetchShows moodShows;

    public static ArrayList<Movie> movieRecs;
    public static MovieAdapter movieAdapter;
    public static FetchMovies moodMovies;

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
        loadingBar = binding.loadingBar;

        //movie recyclerview initialization
        movieRecs = new ArrayList<>();
        moodMovies = new FetchMovies(emotion);
        movieAdapter = new MovieAdapter(getActivity(), movieRecs);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        moodMovies.fetchMovieData(new MovieCallBackPresenter() {
            @Override
            public void success(Map<String, List<Movie>> movieMap) {
                List<Movie> sortedMovies = new ArrayList<>();
                if(emotion == HAPPY_KEY) {
                    sortedMovies.addAll(movieMap.get(HAPPY_KEY));
                }
                else {
                    sortedMovies.addAll(movieMap.get(SAD_KEY));
                }
                movieRecs.addAll(sortedMovies);
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
        moodShows = new FetchShows(emotion);
        showAdapter = new ShowAdapter(getActivity(), showRecs);
        rvTVShows.setAdapter(showAdapter);
        rvTVShows.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        moodShows.fetchTVData(new TVCallBackPresenter() {
            @Override
            public void success(Map<String, List<TVShow>> showMap) {
                List<TVShow> sortedShows = new ArrayList<>();
                if(emotion == HAPPY_KEY) {
                    sortedShows.addAll(showMap.get(HAPPY_KEY));
                }
                else {
                    sortedShows.addAll(showMap.get(SAD_KEY));
                }
                showRecs.addAll(sortedShows);
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