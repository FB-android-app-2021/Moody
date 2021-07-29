package com.example.moody.models;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.moody.callbacks.MovieCallBackPresenter;
import com.example.moody.callbacks.MusicCallBackPresenter;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MusicRecommender {
    public static final String TAG = "MusicRecommender";
    public static final String MOOD_PLAYLISTS_URL = "https://api.spotify.com/v1/browse/categories/mood/playlists?access_token=";
    public static final String ENCODED_URI = "http%3A%2F%2Fmoody.com%2Fcallback";
    public static final String  AUTH_URL = "https://accounts.spotify.com/en/authorize?client_id=b97cdb0553524708b15b3c1173fec698&response_type=code&redirect_uri=http:%2F%2Fmoody.com%2Fcallback&scope=playlist-read-private%20app-remote-control";
    private static final String CLIENT_ID = "b97cdb0553524708b15b3c1173fec698";
    private static final String REDIRECT_URI = "http://moody.com/callback";
    private static final int REQUEST_CODE = 2242;
    private static final String SCOPES = "playlist-read-private,app-remote-control,user-read-playback-state";
    private static String accessToken;

    List<String> musicList;
    String emotion;

    AsyncHttpClient client = new AsyncHttpClient();

    public MusicRecommender(String emotion, String accessToken) {
        this.emotion = emotion;
        if(emotion == null) {
            this.emotion = "Happy";
        }
        this.accessToken = accessToken;
    }


    //makes client calls to endpoint urls and returns list of all unfiltered movie objects
    public void fetchMusicData(MusicCallBackPresenter movieCallback) {
        musicList = new ArrayList<>();
        client.get(MOOD_PLAYLISTS_URL + accessToken, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                //movieCallback.showLoader();
                JSONObject jsonObject = json.jsonObject;
                try {
                        JSONArray results = jsonObject.getJSONArray("items");
                    for (int j = 0; j < 20; j++) {
                        Music playlist = new Music();
                        String playlistName = playlist.getPlaylist();
                        musicList.add(playlistName);
                    }
//                    movieCallback.hideLoader();
//                    movieCallback.success(movieList);
                    } catch(JSONException e){
                        // movieCallback.showError("Call could not be made");
                    }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
            }
        });
    }

}
