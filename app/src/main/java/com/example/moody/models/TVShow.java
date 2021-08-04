package com.example.moody.models;

import android.util.Log;

import com.example.moody.adapters.ShowAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import static com.example.moody.models.Movie.ANGRY;
import static com.example.moody.models.Movie.ANXIOUS;
import static com.example.moody.models.Movie.EXCITED;
import static com.example.moody.models.Movie.HAPPY;
import static com.example.moody.models.Movie.RANDOM;
import static com.example.moody.models.Movie.SAD;
import static com.example.moody.models.Movie.ZEN;

@Parcel
public class TVShow {
    String posterPath;
    String name;
    String genre;
    String overview;
    String mood;
    int id;

    String crimeId = "80";
    String realityId = "10764";
    String comedyId = "35";
    String familyId = "10751";
    String dramaId = "18";
    String sciFiFanId = "10765";
    String warPoliticId = "10768";
    String actionAdventureId = "10759";
    String animationId = "16";
    String mysteryId = "9648";
    String kidId = "10762";
    String docId = "99";


    List<TVShow> showList;
    String emotion;
    //ShowAdapter showAdapter;
    public static final String TAG = "ShowRecommender";
    public static final String POPULAR_KEY
            = "tv/popular?api_key=eb094fc10e8fc702bfc06d84810d0728&language=en-US&page=1";
    int max_pages = 100;

    public TVShow() {}

    //constructor takes in json object and create show
    public TVShow(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        name = jsonObject.getString("name");
        genre = jsonObject.getString("genre_ids");
        overview = jsonObject.getString("overview");
        id = jsonObject.getInt("id");

    }
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }
    public String getName() {
        return name;
    }
    public String getGenre() {
        return genre;
    }
    public String getOverview() { return overview;}
    public int getID() {
        return id;
    }
    public String getMood() {
        String recMood;
        String showGenre = getGenre();
        if (showGenre.contains(realityId) || showGenre.contains(kidId)) {
            recMood = HAPPY;
        } else if (showGenre.contains(dramaId) || showGenre.contains(warPoliticId)) {
            recMood = SAD;
        } else if (showGenre.contains(familyId) || showGenre.contains(sciFiFanId)) {
            recMood = ANXIOUS;
        } else if ( showGenre.contains(animationId) || showGenre.contains(docId)) {
            recMood = ZEN;
        } else if (showGenre.contains(actionAdventureId) || showGenre.contains(mysteryId)) {
            recMood = EXCITED;
        } else if (showGenre.contains(crimeId) || showGenre.contains(comedyId)) {
            recMood = ANGRY;
        }
        else {
            recMood = RANDOM;
        }
        return recMood;
    }
}
