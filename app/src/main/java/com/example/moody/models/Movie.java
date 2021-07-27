package com.example.moody.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Parcel
public class Movie {
    String posterPath;
    String title;
    String genre;
    String id;
    String overview;
    private static final String RESULTS_TAG = "results";

    public Movie() {}

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        genre = jsonObject.getString("genre_ids");
       // id = jsonObject.getString("id");
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
            if(emotion == "Happy") {
                if (newMovie.getGenre().contains(comedyId) || newMovie.getGenre().contains(familyId) || newMovie.getGenre().contains(romanceId)) {
                    movies.add(newMovie);
                }
            }
            else {
                if(newMovie.getGenre().contains(dramaId) || newMovie.getGenre().contains(warId) || newMovie.getGenre().contains(fantasyId)) {
                    movies.add(newMovie);
                }
            }
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
//    public String getID() {
//        return id;
//    }
    public String getOverview() {
        return overview;
    }
}


