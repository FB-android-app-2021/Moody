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

import static com.example.moody.models.Movie.BASE_URL;
import static com.example.moody.models.Movie.HAPPY_KEY;
import static com.example.moody.models.Movie.POPULAR_KEY;
import static com.example.moody.models.Movie.SAD_KEY;
import static com.example.moody.models.Movie.TOP_RATED_KEY;

public class FetchMovies {


    public static final String TAG = "MovieRecommender";
    List<Movie> sadMovieList;
    List<Movie> happyMovieList;
    Map<String, List<Movie>> movieMoodMap;
    String emotion;
    int max_pages = 100;

    AsyncHttpClient client = new AsyncHttpClient();
    String CALLED_URL;

    public FetchMovies(String emotion) {
        this.emotion = emotion;
        if(emotion == null) {
            this.emotion = "HAPPY_KEY";
        }
    }

    //makes client calls to endpoint urls and returns list of all unfiltered movie objects
    public void fetchMovieData(MovieCallBackPresenter movieCallback) {
        sadMovieList = new ArrayList<>();
        happyMovieList = new ArrayList<>();
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
                            if (newMovie.getMood().equals((HAPPY_KEY))) {
                                happyMovieList.add(newMovie);
                            }
                            if (newMovie.getMood().equals(SAD_KEY)) {
                                sadMovieList.add(newMovie);
                            }
                        }
                        movieMoodMap.put(HAPPY_KEY, happyMovieList);
                        movieMoodMap.put(SAD_KEY, sadMovieList);
                        movieCallback.hideLoader();
                        movieCallback.success(movieMoodMap);
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