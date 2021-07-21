package com.example.moody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.moody.activities.LoginActivity;
import com.example.moody.databinding.ActivityMainBinding;
import com.example.moody.fragments.JournalFragment;
import com.example.moody.fragments.MediaFragment;
import com.example.moody.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private Button btnLogout;
    private BottomNavigationView navBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

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
                //keeps user from going back to main activity
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
                        fragment = new MediaFragment();
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment();
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
}