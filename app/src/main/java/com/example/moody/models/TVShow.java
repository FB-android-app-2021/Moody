package com.example.moody.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TVShow {
    String posterPath;
    String name;
    String genre;

    private static final String RESULTS_TAG = "results";

    public TVShow() {}

    //constructor takes in json object and create show
    public TVShow(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        name = jsonObject.getString("name");
        genre = jsonObject.getString("genre_ids");

    }
    //creates and returns list of filtered shows that takes in data we got back
    public static List<TVShow> fromJsonArray(JSONArray showJsonArray, String emotion) throws JSONException {
        String comedyId = "35";
        String familyId = "10751";
        String romanceId = "10749";
        String dramaId = "18";
        String fantasyId = "14";
        String warId = "10752";

        List<TVShow> shows = new ArrayList<>();
        for(int i = 0; i < showJsonArray.length(); i++) {
            TVShow newShow = new TVShow(showJsonArray.getJSONObject(i));
            if(emotion == "Happy") {
                if (newShow.getGenre().contains(comedyId) || newShow.getGenre().contains(familyId) || newShow.getGenre().contains(romanceId)) {
                    shows.add(newShow);
                }
            }
            else {
                if(newShow.getGenre().contains(dramaId) || newShow.getGenre().contains(warId) || newShow.getGenre().contains(fantasyId)) {
                    shows.add(newShow);
                }
            }
        }
        return shows;

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
}
