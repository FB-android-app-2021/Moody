package com.example.moody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.moody.databinding.ActivityMainBinding;
import com.example.moody.fragments.JournalFragment;
import com.example.moody.fragments.MediaFragment;
import com.example.moody.fragments.AnonFeedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.Track;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private Button btnLogout;
    private BottomNavigationView navBar;
    String emotion;

    private static final String CLIENT_ID = "b97cdb0553524708b15b3c1173fec698";
    private static final String REDIRECT_URI = "http://moody.com/callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    private static final int REQUEST_CODE = 2242;
    private static final String SCOPES = "playlist-read-private,app-remote-control,user-read-playback-state";
    //instance variables
    private static String mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        authenticateSpotify();

        emotion = getIntent().getStringExtra("emotion");
        if(emotion == null) {
            emotion = "Happy";
        }

        toolbar = binding.toolbar;
        btnLogout = binding.btnLogout;
        navBar = binding.navBar;
        navBar.setBackgroundColor(530);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        final FragmentManager fragmentManager = getSupportFragmentManager();
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_recs:
                        fragment = new MediaFragment(emotion, mAccessToken);
                        break;
                    case R.id.action_feed:
                        fragment = new AnonFeedFragment();
                        break;
                    case R.id.action_journal:
                    default:
                        fragment = new JournalFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.fragment_main_placeholder, fragment).commit();
                return true;
            }
        });

        //set default selection for navBar to home
        navBar.setSelectedItemId(R.id.action_recs);
    }
    @Override
    protected void onStart() {
        super.onStart();
        //spotify authorization and set up
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote
                        connected();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("MainActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    private void connected() {
        //play a playlist
        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");

        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d("MainActivity", track.name + " by " + track.artist.name);
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }
    public void switchContent(int id, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }

    private void authenticateSpotify() {
        //build request with correct scopes
        AuthorizationRequest.Builder builder = new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{SCOPES});
        AuthorizationRequest request = builder.build();
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);


        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:

                    //need token for any call
                    mAccessToken = response.getAccessToken();
                    Log.i("accessToken", mAccessToken);

                    break;

                // Auth flow returned an error
                case ERROR:
                    Log.i(TAG, "error when getting response");
                    break;

                // Most likely auth flow was cancelled
                default:
                    Log.i(TAG, "auth flow was cancelled");
                    // Handle other cases
            }
        }
    }
}