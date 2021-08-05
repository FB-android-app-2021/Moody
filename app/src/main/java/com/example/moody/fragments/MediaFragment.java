package com.example.moody.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moody.adapters.MovieAdapter;
import com.example.moody.adapters.ShowAdapter;
import com.example.moody.callbacks.MovieCallBackPresenter;
import com.example.moody.callbacks.TVCallBackPresenter;
import com.example.moody.databinding.FragmentMediaBinding;
import com.example.moody.models.FetchMovies;
import com.example.moody.models.FetchShows;
import com.example.moody.models.Movie;
import com.example.moody.models.TVShow;
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.moody.models.Movie.ANGRY;
import static com.example.moody.models.Movie.ANXIOUS;
import static com.example.moody.models.Movie.EXCITED;
import static com.example.moody.models.Movie.HAPPY;
import static com.example.moody.models.Movie.RANDOM;
import static com.example.moody.models.Movie.SAD;
import static com.example.moody.models.Movie.ZEN;

public class MediaFragment extends Fragment {
    public static final String TAG = "MediaFragment";
    public static View loadingBar;
    public static String recEmotion;

    private TextView tvMovieCategory;
    public static CarouselRecyclerview rvMovies;
    private TextView tvTVCategory;
    public static CarouselRecyclerview rvTVShows;

    public static ArrayList<TVShow> showRecs;
    public static ShowAdapter showAdapter;
    public static FetchShows moodShows;

    public static ArrayList<Movie> movieRecs;
    public static MovieAdapter movieAdapter;
    public static FetchMovies moodMovies;

    FragmentMediaBinding binding;
    private RecyclerView.LayoutManager CarouselLayoutManager;

    public MediaFragment(String recMood) {
        if(recMood == null) {
            this.recEmotion = RANDOM;
        } else{
        this.recEmotion = recMood;}
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
        moodMovies = new FetchMovies();
        Log.d("MediaFragment", recEmotion);
        movieAdapter = new MovieAdapter(getActivity(), movieRecs);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setInfinite(true);
        rvMovies.setLayoutManager(rvMovies.getCarouselLayoutManager());
        //rvMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        moodMovies.fetchMovieData(new MovieCallBackPresenter() {
            @Override
            public void success(Map<String, List<Movie>> movieMap) {
                List<Movie> sortedMovies = new ArrayList<>();
                if(recEmotion.equals(HAPPY)) {
                    sortedMovies.addAll(movieMap.get(HAPPY));
                }
                else if(recEmotion.equals(SAD)){
                    sortedMovies.addAll(movieMap.get(SAD));
                }
                else if(recEmotion.equals(ANXIOUS)){
                    sortedMovies.addAll(movieMap.get(ANXIOUS));
                }
                else if(recEmotion.equals(ZEN)){
                    sortedMovies.addAll(movieMap.get(ZEN));
                }
                else if(recEmotion.equals(ANGRY)){
                    sortedMovies.addAll(movieMap.get(ANGRY));
                }
                else if(recEmotion.equals(EXCITED)){
                    sortedMovies.addAll(movieMap.get(EXCITED));
                }
                else if(recEmotion.equals(RANDOM)){
                    sortedMovies.addAll(movieMap.get(RANDOM));
                }
                addUniqueMovies(movieRecs, sortedMovies);
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
        moodShows = new FetchShows();
        showAdapter = new ShowAdapter(getActivity(), showRecs);
        rvTVShows.setAdapter(showAdapter);
        rvTVShows.setInfinite(true);
        rvTVShows.setLayoutManager(rvTVShows.getCarouselLayoutManager());
        //rvTVShows.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        moodShows.fetchTVData(new TVCallBackPresenter() {
            @Override
            public void success(Map<String, List<TVShow>> showMap) {
                List<TVShow> sortedShows = new ArrayList<>();
                if(recEmotion.equals(HAPPY)) {
                    sortedShows.addAll(showMap.get(HAPPY));
                }
                else if(recEmotion.equals(SAD)) {
                    sortedShows.addAll(showMap.get(SAD));
                }
                else if(recEmotion.equals(ANXIOUS)){
                    sortedShows.addAll(showMap.get(ANXIOUS));
                }
                else if(recEmotion.equals(ZEN)){
                    sortedShows.addAll(showMap.get(ZEN));
                }
                else if(recEmotion.equals(ANGRY)){
                    sortedShows.addAll(showMap.get(ANGRY));
                }
                else if(recEmotion.equals(EXCITED)){
                    sortedShows.addAll(showMap.get(EXCITED));
                }
                else if(recEmotion.equals(RANDOM)){
                    sortedShows.addAll(showMap.get(RANDOM));
                }
                addUniqueShows(showRecs, sortedShows);
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
    void addUniqueMovies(List<Movie> movieRecs, List<Movie> newMovies) {
        int movieCount = 0;
        if(movieRecs != null) {
                for (int i = 0; i < newMovies.size(); i++) {
                    movieCount = 0;
                    for (int j = 0; j < movieRecs.size(); j++) {
                        if (newMovies.get(i).getID() == movieRecs.get(j).getID()) {
                            movieCount++;
                        }
                    }
                    if (movieCount == 0) {
                        movieRecs.add(newMovies.get(i));
                    }
                }
        }
        else { movieRecs.addAll(newMovies);}
    }
    void addUniqueShows(List<TVShow> showRecs, List<TVShow> newShows) {
        int movieCount = 0;
        if(showRecs != null) {
            for (int i = 0; i < newShows.size(); i++) {
                movieCount = 0;
                for (int j = 0; j < showRecs.size(); j++) {
                    if (newShows.get(i).getID() == showRecs.get(j).getID()) {
                        movieCount++;
                    }
                }
                if (movieCount == 0) {
                    showRecs.add(newShows.get(i));
                }
            }
        }
        else { showRecs.addAll(newShows);}
    }
}