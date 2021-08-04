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
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String POPULAR_KEY
            = "movie/popular?api_key=eb094fc10e8fc702bfc06d84810d0728&language=en-US&page=1";
    public static final String TOP_RATED_KEY
            = "movie/top_rated?api_key=eb094fc10e8fc702bfc06d84810d0728&language=en-US&page=1";
    public static final String HAPPY = "happy";
    public static final String SAD = "sad";
    public static final String ANXIOUS = "anxious";
    public static final String ZEN = "zen";
    public static final String EXCITED = "excited";
    public static final String ANGRY = "angry";
    public static final String RANDOM = "random";
    String posterPath;
    String title;
    String genre;
    int id;
    String mood;
    String overview;

    String comedyId = "35";
    String familyId = "10751";
    String romanceId = "10749";
    String dramaId = "18";
    String fantasyId = "14";
    String warId = "10752";
    String musicalId = "10402";
    String horrorId = "27";
    String adventureId = "12";
    String actionId = "28";
    String historyId = "36";
    String animationId = "16";
    String mysteryId = "9648";
    String thrillerId = "53";
    String sciFiId = "878";
    String docId = "99";

    List<Movie> movieList;
    String emotion;
    int max_pages = 99;


    public Movie() {}

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        genre = jsonObject.getString("genre_ids");
        id = jsonObject.getInt("id");
        overview = jsonObject.getString("overview");
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
        String recMood;
        String movieGenre = getGenre();
        if (movieGenre.contains(sciFiId) || movieGenre.contains(romanceId)) {
            recMood = HAPPY;
        } else if (movieGenre.contains(dramaId) || movieGenre.contains(warId)) {
            recMood = SAD;
        } else if (movieGenre.contains(familyId) || movieGenre.contains(fantasyId) || movieGenre.contains(musicalId)) {
            recMood = ANXIOUS;
        } else if ( movieGenre.contains(animationId) || movieGenre.contains(docId)) {
            recMood = ZEN;
        } else if (movieGenre.contains(adventureId) || movieGenre.contains(thrillerId) || movieGenre.contains(mysteryId)) {
            recMood = EXCITED;
        } else if (movieGenre.contains(horrorId) || movieGenre.contains(actionId) || movieGenre.contains(comedyId)) {
            recMood = ANGRY;
        }
        else {
            recMood = RANDOM;
        }
        return recMood;
    }
}


