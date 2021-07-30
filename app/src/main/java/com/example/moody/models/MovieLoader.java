package com.example.moody.models;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.moody.R;
import com.example.moody.callbacks.MovieCallBackPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;

import static com.example.moody.models.Movie.BASE_URL;
import static com.example.moody.models.Movie.HAPPY_KEY;
import static com.example.moody.models.Movie.SAD_KEY;
import static com.example.moody.models.Movie.TOP_RATED_KEY;

public class MovieLoader extends AsyncTaskLoader<Map<String, List<Movie>>> {

    List<Movie> movieList;
    List<Movie> sadMovieList;
    List<Movie> happyMovieList;
    Map<String, List<Movie>> movieMoodMap;
    String emotion;
    public static final String TAG = "MovieRecommender";
    int max_pages = 100;


    AsyncHttpClient client = new AsyncHttpClient();
    String CALLED_URL;

    public MovieLoader(Context context) {
        super(context);
    }
//hashmap
    //filter method and code in loadInBackground
    //return movie list

    @Override
    public void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public Map<String, List<Movie>> loadInBackground() {
        movieList = new ArrayList<>();
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String movieJSONArrayString = null;
        URL requestURL = null;
        try {
            requestURL = new URL(BASE_URL + TOP_RATED_KEY);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            if (builder.length() == 0) {
                return null;
            }
            movieJSONArrayString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        getMovieMap(movieJSONArrayString);
        return movieMoodMap;
    }

    public void getMovieMap(String data) {
        movieList = new ArrayList<>();
        sadMovieList = new ArrayList<>();
        happyMovieList = new ArrayList<>();
        movieMoodMap = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray moviesArray = jsonObject.getJSONArray("results");
            String title = null;
            String genre = null;
            //JSONObject jsonObject = json.jsonObject;
            for (int i = 0; i < moviesArray.length(); i++) {
                Movie newMovie = new Movie(moviesArray.getJSONObject(i));
                if (newMovie.getMood().equals((HAPPY_KEY))) {
                    happyMovieList.add(newMovie);
                }
                if (newMovie.getMood().equals(SAD_KEY)) {
                    sadMovieList.add(newMovie);
                }
            }
            movieMoodMap.put(HAPPY_KEY, happyMovieList);
            movieMoodMap.put(SAD_KEY, sadMovieList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
