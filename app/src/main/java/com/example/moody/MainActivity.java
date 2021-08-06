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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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

import static com.example.moody.models.Movie.RANDOM;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private ImageButton btnLogout;
    private BottomNavigationView navBar;
    public static MediaFragment moodRecs;


    public String recMood;



    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        toolbar = binding.toolbar;
        btnLogout = binding.btnLogout;
        navBar = binding.navBar;
        //navBar.setItemTextColor(ColorStateList.valueOf(R.color.purple_200));
        //navBar.setBackgroundColor(R.color.white);
        //getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
        //navBar.setItemTextColor(ColorStateList.valueOf(R.color.purple_200));
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
                        recMood = getIntent().getStringExtra("emotion");
                        moodRecs = new MediaFragment(recMood);
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
        String intentFrag = getIntent().getStringExtra("frgToLoad");
        if(intentFrag != null) {
            switch (intentFrag) {
                case "media":
                    recMood = getIntent().getStringExtra("emotion");
                    fragmentManager.beginTransaction().replace(R.id.fragment_main_placeholder, new MediaFragment(recMood)).commit();
                    break;

            }
        }


    }
    public void switchContent(int id, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }
}