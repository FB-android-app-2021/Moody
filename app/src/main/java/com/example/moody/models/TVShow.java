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

    //genre IDs
    //happy
    //map<emotion, set>
    //emotion enum
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
    public TVShow() {}

    //constructor takes in json object and create show
    public TVShow(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        name = jsonObject.getString("name");
        genre = jsonObject.getString("genre_ids");

    }
    //creates and returns list of shows that takes in data we got back
    public static List<TVShow> fromJsonArray(JSONArray showJsonArray, String emotion) throws JSONException {
        String comedyId = "35";
        String familyId = "10751";
        String romanceId = "10749";
        String animatedId = "16";
        String dramaId = "18";
        String actionId = "28";
        String adventureId = "12";
        String thrillerId = "53";
        String fantasyId = "14";
        String horrorId = "27";
        String warId = "10752";
        String docId = "99";
        List<TVShow> shows = new ArrayList<>();
        //iterate through json array and construct a tvshow for each element in array
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

    // getters for each member var that creates full url
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
