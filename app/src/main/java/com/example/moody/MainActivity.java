package com.example.moody;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.moody.adapters.MovieAdapter;
import com.example.moody.databinding.ActivityMainBinding;
import com.example.moody.fragments.JournalFragment;
import com.example.moody.fragments.MediaFragment;
import com.example.moody.fragments.AnonFeedFragment;
import com.example.moody.models.Movie;
import com.example.moody.models.FetchMovies;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.Track;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.moody.fragments.MediaFragment.movieAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    public static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private Button btnLogout;
    private BottomNavigationView navBar;
    public static MediaFragment moodRecs;
    String emotion;

    private static final String CLIENT_ID = "b97cdb0553524708b15b3c1173fec698";
    private static final String REDIRECT_URI = "http://moody.com/callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    private static final int REQUEST_CODE = 2242;
    private static final String SCOPES = "playlist-read-private,app-remote-control,user-read-playback-state";
    //instance variables
    private static String mAccessToken;

    List<Movie> movieList;
    Map<String, List<Movie>> movieMoodMap;
    FetchMovies movieFetcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //authenticateSpotify();

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
                        moodRecs = new MediaFragment(emotion);
                        fragment = moodRecs;
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
        navBar.setSelectedItemId(R.id.action_feed);
       // getMovieInfo();
//        if(getSupportLoaderManager().getLoader(0)!=null){
//            getSupportLoaderManager().initLoader(0,null,this);
//        }

    }
    public void switchContent(int id, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }
    //call in OnCreate
    void getMovieInfo() {
        movieFetcher = new FetchMovies(MainActivity.this);
        movieFetcher.loadInBackground();
    }

    @NonNull
    @NotNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {
        return new FetchMovies(this);
    }

    @Override
    public void onLoadFinished(@NonNull @NotNull Loader<String> loader, String data) {
        MovieAdapter.movieRecs.addAll(movieFetcher.getMovieList(loader, data));
        movieAdapter.notifyDataSetChanged();



    }

    @Override
    public void onLoaderReset(@NonNull @NotNull Loader<String> loader) {

    }
}