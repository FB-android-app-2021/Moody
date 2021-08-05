package com.example.moody.models;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.moody.callbacks.MovieCallBackPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;

import static com.example.moody.models.Movie.ANGRY;
import static com.example.moody.models.Movie.ANXIOUS;
import static com.example.moody.models.Movie.BASE_URL;
import static com.example.moody.models.Movie.EXCITED;
import static com.example.moody.models.Movie.HAPPY;
import static com.example.moody.models.Movie.POPULAR_KEY;
import static com.example.moody.models.Movie.RANDOM;
import static com.example.moody.models.Movie.SAD;
import static com.example.moody.models.Movie.ZEN;

public class FetchMovies {


    public static final String TAG = "MovieRecommender";
    List<Movie> sadMovieList;
    List<Movie> happyMovieList;
    List<Movie> anxiousMovieList;
    List<Movie> zenMovieList;
    List<Movie> angryMovieList;
    List<Movie> excitedMovieList;
    List<Movie> randomMovieList;
    Map<String, List<Movie>> movieMoodMap;
    String emotion;
    int max_pages = 100;

    AsyncHttpClient client = new AsyncHttpClient();
    String CALLED_URL;

    public FetchMovies() {
    }

    //makes client calls to endpoint urls and returns list of all unfiltered movie objects
    public void fetchMovieData(MovieCallBackPresenter movieCallback) {
        sadMovieList = new ArrayList<>();
        happyMovieList = new ArrayList<>();
        anxiousMovieList = new ArrayList<>();
        zenMovieList = new ArrayList<>();
        angryMovieList = new ArrayList<>();
        excitedMovieList = new ArrayList<>();
        randomMovieList = new ArrayList<>();
        movieMoodMap = new HashMap<>();
        for (int i = 1; i <= max_pages; i++) {
            CALLED_URL = BASE_URL + POPULAR_KEY + String.valueOf(i);
            client.get(CALLED_URL, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Headers headers, JSON json) {
                    movieCallback.showLoader();
                    JSONObject jsonObject = json.jsonObject;
                    try {
                        JSONArray results = jsonObject.getJSONArray("results");
                        for (int j = 0; j < results.length(); j++) {
                            Movie newMovie = new Movie(results.getJSONObject(j));
                            if (newMovie.getMood() == HAPPY) { happyMovieList.add(newMovie); }
                            if (newMovie.getMood() == SAD) { sadMovieList.add(newMovie); }
                            if (newMovie.getMood() == ANXIOUS) { anxiousMovieList.add(newMovie); }
                            if (newMovie.getMood() == ZEN) { zenMovieList.add(newMovie); }
                            if (newMovie.getMood() == ANGRY) { angryMovieList.add(newMovie); }
                            if (newMovie.getMood() == EXCITED) { excitedMovieList.add(newMovie); }
                            else if(newMovie.getMood() == RANDOM) { randomMovieList.add(newMovie); }
                        }
                        movieMoodMap.put(HAPPY, happyMovieList);
                        movieMoodMap.put(SAD, sadMovieList);
                        movieMoodMap.put(ANXIOUS, anxiousMovieList);
                        movieMoodMap.put(ZEN, zenMovieList);
                        movieMoodMap.put(ANGRY, angryMovieList);
                        movieMoodMap.put(EXCITED, excitedMovieList);
                        movieMoodMap.put(RANDOM, randomMovieList);
                        movieCallback.success(movieMoodMap);
                        movieCallback.hideLoader();
                    } catch (JSONException e) {
                        movieCallback.showError("Call could not be made");
                    }
                }

                @Override
                public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                }
            });
        }
    }
}