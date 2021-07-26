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

public class ShowRecommender {

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

    public ShowRecommender(String emotion) {
        this.emotion = emotion;
        if(emotion == null) {
            this.emotion = "Happy";
        }
    }

    public void fetchTVData(TVCallBackPresenter showCallback) {
        showList = new ArrayList<>();
        for (int i = 1; i <= max_pages; i++) {
            CALLED_URL = BASE_URL + TOP_RATED_KEY + String.valueOf(i);
            client.get(CALLED_URL, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Headers headers, JSON json) {
                    showCallback.showLoader();
                    Log.d(TAG, "onSuccess");
                    JSONObject jsonObject = json.jsonObject;
                    try {
                        JSONArray results = jsonObject.getJSONArray("results");
                        showList.addAll(TVShow.fromJsonArray(results, emotion));
                        Log.i(TAG, "showList: " + showList.toString());
                        showCallback.hideLoader();
                        showCallback.success(showList);
                        //set to list
                        //scan over movieRecs set to see if there is duplication, if there is don't add to new list
                        Log.i(TAG, "shows: " + showList.size());
                    } catch (JSONException e) {
                        showCallback.showError("Call could not be made");
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
                    showCallback.showLoader();
                    Log.d(TAG, "onSuccess");
                    JSONObject jsonObject = json.jsonObject;
                    try {
                        JSONArray results = jsonObject.getJSONArray("results");
                        showList.addAll(TVShow.fromJsonArray(results, emotion));
                        Log.i(TAG, "showList: " + showList.toString());
                        showCallback.hideLoader();
                        showCallback.success(showList);
                        //set to list
                        //scan over movieRecs set to see if there is duplication, if there is don't add to new list
                        Log.i(TAG, "shows: " + showList.size());
                    } catch (JSONException e) {
                        showCallback.showError("Call could not be made");
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