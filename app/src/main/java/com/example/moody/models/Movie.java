package com.example.moody.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Parcel
public class Movie {
    String posterPath;
    String title;
    String genre;
    int id;
    String overview;

    List<Movie> movieList;
    String emotion;
    String mood;
    public static final String TAG = "MovieRecommender";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String POPULAR_KEY
            = "movie/popular?api_key=eb094fc10e8fc702bfc06d84810d0728&language=en-US&page=";
    public static final String TOP_RATED_KEY
            = "movie/top_rated?api_key=eb094fc10e8fc702bfc06d84810d0728&language=en-US&page=";
    int max_pages = 100;


    public Movie() {}

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        genre = jsonObject.getString("genre_ids");
        id = jsonObject.getInt("id");
        overview = jsonObject.getString("overview");


    }

    //creates and returns list of filtered movies that takes in data we got back
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray, String emotion) throws JSONException {
        String comedyId = "35";
        String familyId = "10751";
        String romanceId = "10749";
        String dramaId = "18";
        String fantasyId = "14";
        String warId = "10752";
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++) {
            Movie newMovie = new Movie(movieJsonArray.getJSONObject(i));
            movies.add(newMovie);
        }
        return movies;

    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }
    public String getGenre() {
        return genre;
    }
    public int getID() { return id; }
    public String getOverview() {
        return overview;
    }
    public String getMood() {
        String comedyId = "35";
        String familyId = "10751";
        String romanceId = "10749";
        String dramaId = "18";
        String fantasyId = "14";
        String warId = "10752";
        if(getGenre().contains(comedyId)|| getGenre().contains(familyId) || getGenre().contains(romanceId)) {
            mood = "Happy";
        }
        else if(getGenre().contains(dramaId)|| getGenre().contains(warId) || getGenre().contains(fantasyId)) {
            mood = "Sad";
        }
        else {
            mood = "random";
        }
        return mood;
    }
}


