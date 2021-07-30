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

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    List<Movie> movieList;
    List<Movie> sadMovieList;
    List<Movie> happyMovieList;
    String emotion;
    public static final String TAG = "MovieRecommender";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String POPULAR_KEY
            = "movie/popular?api_key=eb094fc10e8fc702bfc06d84810d0728&language=en-US&page=";
    public static final String TOP_RATED_KEY
            = "movie/top_rated?api_key=eb094fc10e8fc702bfc06d84810d0728&language=en-US&page=";
    int max_pages = 100;

    public static final String HAPPY_KEY = "Happy";
    public static final String SAD_KEY = "SAD";

    AsyncHttpClient client = new AsyncHttpClient();
    String CALLED_URL;

//    public FetchMovies(String emotion) {
//        this.emotion = emotion;
//        if(emotion == null) {
//            this.emotion = "Happy";
//        }
//    }
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
    public List<Movie> loadInBackground() {
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
       return getMovieList(movieJSONArrayString);
    }
    public List<Movie> getMovieList(String data) {
        movieList = new ArrayList<>();
        sadMovieList = new ArrayList<>();
        happyMovieList = new ArrayList<>();
        Map<String, List<Movie>> movieMoodMap = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray moviesArray = jsonObject.getJSONArray("results");
            String title = null;
            String genre = null;
            //JSONObject jsonObject = json.jsonObject;
            for(int i = 0 ; i < moviesArray.length(); i++) {
                Movie newMovie = new Movie(moviesArray.getJSONObject(i));
                if (newMovie.getMood() == HAPPY_KEY) {
                    happyMovieList.add(newMovie);
                }
                if (newMovie.getMood() == SAD_KEY) {
                    sadMovieList.add(newMovie);
                }
            }
            movieMoodMap.put(HAPPY_KEY, happyMovieList);
            movieMoodMap.put(SAD_KEY, sadMovieList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(emotion == HAPPY_KEY) {
            return movieMoodMap.get(HAPPY_KEY);
        }
        return movieMoodMap.get(SAD_KEY);
        }
    }
