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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.moody.databinding.ActivityMainBinding;
import com.example.moody.fragments.JournalFragment;
import com.example.moody.fragments.MediaFragment;
import com.example.moody.fragments.AnonFeedFragment;
import com.example.moody.models.Movie;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private Button btnLogout;
    private BottomNavigationView navBar;
    public static MediaFragment moodRecs;

    String emotion;
    List<Movie> movieList;
    Map<String, List<Movie>> movieMoodMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
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

        navBar.setSelectedItemId(R.id.action_feed);
       // getMovieInfo();

    }
    public void switchContent(int id, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }
}