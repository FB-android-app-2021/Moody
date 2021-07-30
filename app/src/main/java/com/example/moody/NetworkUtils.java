package com.example.moody;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
//import com.example.moody.models.Movie;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.List;
//
//import okhttp3.Headers;
//
//public class NetworkUtils {
//    private static final String TAG =
//            NetworkUtils.class.getSimpleName();
//    public static final String BASE_URL = "https://api.themoviedb.org/3/";
//    public static final String POPULAR_KEY
//            = "movie/popular?api_key=eb094fc10e8fc702bfc06d84810d0728&language=en-US&page=1";
//    public static final String TOP_RATED_KEY
//            = "movie/top_rated?api_key=eb094fc10e8fc702bfc06d84810d0728&language=en-US&page=";
//    int max_pages = 100;
//    List<Movie> movieList;
//
//    static List<Movie> getMovieInfo() {
//////        HttpURLConnection urlConnection = null;
//////        BufferedReader reader = null;
////        final String[] movieJSONArray = {null};
////        AsyncHttpClient client = new AsyncHttpClient();
////        String requestURL = BASE_URL + POPULAR_KEY;
////        client.get(requestURL, new JsonHttpResponseHandler() {
////            @Override
////            public void onSuccess(int i, Headers headers, JSON json) {
////                // movieCallback.showLoader();
////                JSONObject jsonObject = json.jsonObject;
////                try {
////                    JSONArray results = jsonObject.getJSONArray("results");
////                    movieList.addAll(Movie.fromJsonArray(results, emotion));
////
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
////            }
////            @Override
////            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
////
////            }
////        }
//       return movieJSONArray[0];
//   }
//}
