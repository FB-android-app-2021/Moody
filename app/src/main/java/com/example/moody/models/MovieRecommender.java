package com.example.moody.models;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.moody.CallBackPresenter;
import com.example.moody.MainActivity;
import com.example.moody.adapters.MovieAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import okhttp3.Headers;

public class MovieRecommender {

    List<Movie> movieList;
    String emotion;
    MovieAdapter movieAdapter;
    public static final String TAG = "MovieRecommender";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String POPULAR_KEY
            = "movie/popular?api_key=eb094fc10e8fc702bfc06d84810d0728&language=en-US&page=";
    public static final String TOP_RATED_KEY
            = "movie/top_rated?api_key=eb094fc10e8fc702bfc06d84810d0728&language=en-US&page=";
    int max_pages = 100;

    AsyncHttpClient client = new AsyncHttpClient();
    String CALLED_URL;

    public MovieRecommender(String emotion) {
        this.emotion = emotion;
        if(emotion == null) {
            this.emotion = "Happy";
        }
    }

    public void fetchData(CallBackPresenter movieCallback) {
        movieList = new ArrayList<>();
        for (int i = 1; i <= max_pages; i++) {
            CALLED_URL = BASE_URL + TOP_RATED_KEY + String.valueOf(i);
            client.get(CALLED_URL, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Headers headers, JSON json) {
                    movieCallback.showLoader();
                    Log.d(TAG, "onSuccess");
                    JSONObject jsonObject = json.jsonObject;
                    try {
                        JSONArray results = jsonObject.getJSONArray("results");
                        movieList.addAll(Movie.fromJsonArray(results, emotion));
                        Log.i(TAG, "movieList: " + movieList.toString());
                        movieCallback.hideLoader();
                        movieCallback.success(movieList);
                        //set to list
                        //scan over movieRecs set to see if there is duplication, if there is don't add to new list
                        Log.i(TAG, "Movies: " + movieList.size());
                    } catch (JSONException e) {
                        movieCallback.showError("Call could not be made");
                        Log.e(TAG, "Hit json exception", e);
                    }
                }

                @Override
                public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                    Log.d(TAG, "onFailure");
                }
            });
            CALLED_URL = BASE_URL + POPULAR_KEY + String.valueOf(i);
            client.get(CALLED_URL, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Headers headers, JSON json) {
                    movieCallback.showLoader();
                    Log.d(TAG, "onSuccess");
                    JSONObject jsonObject = json.jsonObject;
                    try {
                        JSONArray results = jsonObject.getJSONArray("results");
                        movieList.addAll(Movie.fromJsonArray(results, emotion));
                        Log.i(TAG, "movieList: " + movieList.toString());
                        movieCallback.hideLoader();
                        movieCallback.success(movieList);
                        //set to list
                        //scan over movieRecs set to see if there is duplication, if there is don't add to new list
                        Log.i(TAG, "Movies: " + movieList.size());
                    } catch (JSONException e) {
                        movieCallback.showError("Call could not be made");
                        Log.e(TAG, "Hit json exception", e);
                    }
                }

                @Override
                public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                    Log.d(TAG, "onFailure");
                }
            });
        }
    }
}
