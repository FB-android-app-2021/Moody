package com.example.moody;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.moody.databinding.ActivityDailyBinding;
import com.example.moody.fragments.JournalFragment;
import com.example.moody.fragments.MoodFragment;

public class DailyActivity extends AppCompatActivity {

    public static final String TAG = "DailyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDailyBinding binding = ActivityDailyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_placeholder, new MoodFragment()).commit();
    }

}