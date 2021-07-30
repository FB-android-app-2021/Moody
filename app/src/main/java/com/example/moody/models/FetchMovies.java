package com.example.moody.models;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
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
import java.util.List;
import java.util.Map;

import okhttp3.Headers;

public class FetchMovies extends AsyncTaskLoader<String> {

    List<Movie> movieList;
    String emotion;
    public static final String TAG = "MovieRecommender";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String POPULAR_KEY
            = "movie/popular?api_key=eb094fc10e8fc702bfc06d84810d0728&language=en-US&page=";
    public static final String TOP_RATED_KEY
            = "movie/top_rated?api_key=eb094fc10e8fc702bfc06d84810d0728&language=en-US&page=";
    int max_pages = 100;
    Map<String, List<Movie>> movieMoodMap;

    AsyncHttpClient client = new AsyncHttpClient();
    String CALLED_URL;

//    public MovieRecommender(String emotion) {
//        this.emotion = emotion;
//        if(emotion == null) {
//            this.emotion = "Happy";
//        }
//    }
public FetchMovies(Context context) {
    super(context);
}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    //hashmap
    //filter method and code in loadInBackground
    //return movie list
    public String loadInBackground() {
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
                // Stream was empty. No point in parsing.
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
        return movieJSONArrayString;
    }
    public List<Movie> getMovieList(Loader<String> loader, String data) {
        movieList = new ArrayList<>();
       // movieMoodMap = new Map<String, List<Movie>>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray moviesArray = jsonObject.getJSONArray("results");
            int i = 0;
            String title = null;
            String genre = null;
            //JSONObject jsonObject = json.jsonObject;
            int numMovies = 0;
            while(numMovies < 10) {
                Movie newMovie = new Movie(moviesArray.getJSONObject(i));
                movieList.add(newMovie);
                //update numMovies when adding to list
                ++i;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }
}
