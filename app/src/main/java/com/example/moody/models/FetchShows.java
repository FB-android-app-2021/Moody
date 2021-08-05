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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;

import static com.example.moody.models.Movie.ANGRY;
import static com.example.moody.models.Movie.ANXIOUS;
import static com.example.moody.models.Movie.BASE_URL;
import static com.example.moody.models.Movie.EXCITED;
import static com.example.moody.models.Movie.HAPPY;
import static com.example.moody.models.Movie.RANDOM;
import static com.example.moody.models.Movie.SAD;
import static com.example.moody.models.Movie.ZEN;
import static com.example.moody.models.TVShow.POPULAR_KEY;

public class FetchShows {
    public static final String TAG = "ShowRecommender";

    List<TVShow> sadShowList;
    List<TVShow> happyShowList;
    List<TVShow> zenShowList;
    List<TVShow> anxiousShowList;
    List<TVShow> angryShowList;
    List<TVShow> excitedShowList;
    List<TVShow> randomShowList;

    Map<String, List<TVShow>> showMoodMap;
    String emotion;
    ShowAdapter showAdapter;
    int max_pages = 100;

    AsyncHttpClient client = new AsyncHttpClient();
    String CALLED_URL;

    public FetchShows() {
    }

    //makes client calls to endpoint urls and returns list of all unfiltered tv show objects
    public void fetchTVData(TVCallBackPresenter showCallback) {
        happyShowList = new ArrayList<>();
        sadShowList = new ArrayList<>();
        zenShowList = new ArrayList<>();
        anxiousShowList = new ArrayList<>();
        angryShowList = new ArrayList<>();
        excitedShowList = new ArrayList<>();
        randomShowList = new ArrayList<>();
        showMoodMap = new HashMap<>();
        for (int i = 1; i <= max_pages; i++) {
            CALLED_URL = BASE_URL + POPULAR_KEY + String.valueOf(i);
            client.get(CALLED_URL, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Headers headers, JSON json) {
                    showCallback.showLoader();
                    JSONObject jsonObject = json.jsonObject;
                    try {
                        JSONArray results = jsonObject.getJSONArray("results");
                        for (int j = 0; j < results.length(); j++) {
                            TVShow newShow = new TVShow(results.getJSONObject(j));
                            if (newShow.getMood().equals(HAPPY)) { happyShowList.add(newShow); }
                            if (newShow.getMood().equals(SAD)) { sadShowList.add(newShow); }
                            if (newShow.getMood().equals(ANXIOUS)) { anxiousShowList.add(newShow); }
                            if (newShow.getMood().equals(ZEN)) { zenShowList.add(newShow); }
                            if (newShow.getMood().equals(ANGRY)) { angryShowList.add(newShow); }
                            if (newShow.getMood().equals(EXCITED)) { excitedShowList.add(newShow); }
                            if (newShow.getMood().equals(RANDOM)) { randomShowList.add(newShow); }
                        }
                        showMoodMap.put(HAPPY, happyShowList);
                        showMoodMap.put(SAD, sadShowList);
                        showMoodMap.put(ANXIOUS, anxiousShowList);
                        showMoodMap.put(ZEN, zenShowList);
                        showMoodMap.put(ANGRY, angryShowList);
                        showMoodMap.put(EXCITED, excitedShowList);
                        showMoodMap.put(RANDOM, randomShowList);
                        showCallback.success(showMoodMap);
                        showCallback.hideLoader();
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