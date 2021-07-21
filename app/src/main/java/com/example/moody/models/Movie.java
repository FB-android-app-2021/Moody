package com.example.moody.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Movie {
    String posterPath;
    String title;
    String genre;

    //genre IDs
    //happy
    String comedyId = "35";
    String familyId = "10751";
    String romanceId = "10749";

    //sad
    String dramaId = "18";

    //excited
    String actionId = "28";
    String adventureId = "12";
    String thrillerId = "53";

    //JSON data
    private static final String RESULTS_TAG = "results";


    //default constructor for parcel
    public Movie() {}

    //constructor takes in json object and create Movie
    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        genre = jsonObject.getString("genre_ids");

    }
    //creates and returns list of movies that takes in data we got back
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        //iterate through json array and construct a movie for each element in array
        for(int i = 0; i < movieJsonArray.length(); i++) {
           Movie newMovie = new Movie(movieJsonArray.getJSONObject(i));
           if(newMovie.getGenre().contains(newMovie.comedyId) || newMovie.getGenre().contains(newMovie.familyId) || newMovie.getGenre().contains(newMovie.romanceId)) {
               movies.add(newMovie);
           }
        }
        return movies;

    }

    // getters for each member var that creates full url
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }
}


