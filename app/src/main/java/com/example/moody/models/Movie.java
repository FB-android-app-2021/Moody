package com.example.moody.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class Movie {
    String posterPath;
    String title;
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
    public Movie() {}

    //constructor takes in json object and create Movie
    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        genre = jsonObject.getString("genre_ids");

    }
    //creates and returns list of movies that takes in data we got back
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray, String emotion) throws JSONException {
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
        List<Movie> movies = new ArrayList<>();
        //iterate through json array and construct a movie for each element in array
        for(int i = 0; i < movieJsonArray.length(); i++) {
            Movie newMovie = new Movie(movieJsonArray.getJSONObject(i));
            //if(emotion == "Happy") {
                if (newMovie.getGenre().contains(comedyId) || newMovie.getGenre().contains(familyId) || newMovie.getGenre().contains(romanceId)) {
                    movies.add(newMovie);
              //  }
            }
            //else {
                if(newMovie.getGenre().contains(dramaId) || newMovie.getGenre().contains(warId) || newMovie.getGenre().contains(fantasyId)) {
                    movies.add(newMovie);
              //  }
            }
        }

//
//        Set<Movie> happyMovies = null;
//        Set<Movie> sadMovies = null;
//        List<String> moods = Arrays.asList("happy", "sad");
//        List<Set<Movie>> movieMoods = Arrays.asList(happyMovies, sadMovies);
//        //String emotion key, set of movies value
//        ArrayList<HashMap<String, Set<Movie>>> movieData = new ArrayList<HashMap<String, Set<Movie>>>();
//        String genre_id;
//
//        for(int i = 0; i < movies.size(); i++) {
//            genre_id = movies.get(i).getGenre();
//            if(genre_id.contains(comedyId) || genre_id.contains(familyId)) {
//                happyMovies.add(movies.get(i));
//            }
//            else if(genre_id.contains(dramaId) || genre_id.contains(warId)) {
//                sadMovies.add(movies.get(i));
//            }
//        }
//        HashMap<String,Set<Movie>> emotionHashMap = new HashMap<String, Set<Movie>>();
//        for(int i = 0; i < moods.size(); i++) {
//            emotionHashMap.put(moods.get(i), movieMoods.get(i));
//            movieData.add(emotionHashMap);
//        }
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


