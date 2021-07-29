package com.example.moody.models;

import com.spotify.android.appremote.api.SpotifyAppRemote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Music {
    //String albumCover;
    String playlist;
    String playlistTracks;
    String track;

    public static final String MOOD_URL = "https://api.spotify.com/v1/browse/categories/mood/playlists";
    public static final String ENCODED_URI = "http%3A%2F%2Fmoody.com%2Fcallback";
    public static final String  AUTH_URL = "https://accounts.spotify.com/en/authorize?client_id=b97cdb0553524708b15b3c1173fec698&response_type=code&redirect_uri=http:%2F%2Fmoody.com%2Fcallback&scope=playlist-read-private%20app-remote-control";
    private static final String CLIENT_ID = "b97cdb0553524708b15b3c1173fec698";
    private static final String REDIRECT_URI = "http://moody.com/callback";
    private SpotifyAppRemote mSpotifyAppRemote;

    public Music() {}

    public Music(JSONObject jsonObject) throws JSONException {
       // albumCover = jsonObject.getString("poster_path");
        playlist = jsonObject.getString("name");
        playlistTracks = jsonObject.getString("name");
        track = jsonObject.getJSONArray("tracks").getString(Integer.parseInt("href"));

    }

    //creates and returns list of filtered music that takes in data we got back
    public static List<Music> fromJsonArray(JSONArray musicJsonArray, String emotion) throws JSONException {
        String comedyId = "35";
        String familyId = "10751";
        String romanceId = "10749";
        String dramaId = "18";
        String fantasyId = "14";
        String warId = "10752";
        List<Music> listMusic = new ArrayList<>();
//        for(int i = 0; i < musicJsonArray.length(); i++) {
//            Music newMusic = new Music(musicJsonArray.getJSONObject(i));
//            if(emotion == "Happy") {
//                if (newMusic.getGenre().contains(comedyId) || newMusic.getGenre().contains(familyId) || newMusic.getGenre().contains(romanceId)) {
//                    listMusic.add(newMusic);
//                }
//            }
//            else {
//                if(newMusic.getGenre().contains(dramaId) || newMusic.getGenre().contains(warId) || newMusic.getGenre().contains(fantasyId)) {
//                    listMusic.add(newMusic);
//                }
//            }
//        }
        return listMusic;

    }
    public String getPlaylist() {
        return track;
    }

    public String getTrack() {
        return track;
    }

    public String getPlaylistTracks() {
        return playlistTracks;
    }
}
