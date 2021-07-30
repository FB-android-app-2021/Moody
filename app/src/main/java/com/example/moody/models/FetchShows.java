package com.example.moody.models;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.moody.callbacks.MovieCallBackPresenter;
import com.example.moody.adapters.ShowAdapter;
import com.example.moody.callbacks.TVCallBackPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class FetchShows {

    List<TVShow> showList;
    String emotion;
    ShowAdapter showAdapter;
    public static final String TAG = "ShowRecommender";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String POPULAR_KEY
            = "tv/popular?api_key=eb094fc10e8fc702bfc06d84810d0728&language=en-US&page=";
    public static final String TOP_RATED_KEY
            = "tv/top_rated?api_key=eb094fc10e8fc702bfc06d84810d0728&language=en-US&page=";
    int max_pages = 100;

    AsyncHttpClient client = new AsyncHttpClient();
    String CALLED_URL;

    public FetchShows(String emotion) {
        this.emotion = emotion;
        if(emotion == null) {
            this.emotion = "Happy";
        }
    }

    //makes client calls to endpoint urls and returns list of all unfiltered tv show objects
    public void fetchTVData(TVCallBackPresenter showCallback) {
        showList = new ArrayList<>();
        for (int i = 1; i <= max_pages; i++) {
            CALLED_URL = BASE_URL + TOP_RATED_KEY + String.valueOf(i);
            client.get(CALLED_URL, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Headers headers, JSON json) {
                    showCallback.showLoader();
                    JSONObject jsonObject = json.jsonObject;
                    try {
                        JSONArray results = jsonObject.getJSONArray("results");
                        showList.addAll(TVShow.fromJsonArray(results, emotion));
                        showCallback.hideLoader();
                        showCallback.success(showList);
                    } catch (JSONException e) {
                        showCallback.showError("Call could not be made");
                    }
                }

                @Override
                public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                }
            });
            CALLED_URL = BASE_URL + POPULAR_KEY + String.valueOf(i);
            client.get(CALLED_URL, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Headers headers, JSON json) {
                    showCallback.showLoader();
                    JSONObject jsonObject = json.jsonObject;
                    try {
                        JSONArray results = jsonObject.getJSONArray("results");
                        showList.addAll(TVShow.fromJsonArray(results, emotion));
                        showCallback.hideLoader();
                        showCallback.success(showList);
                    } catch (JSONException e) {
                        showCallback.showError("Call could not be made");
                    }
                }

                @Override
                public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                }
            });
        }
    }
}