package com.example.moody;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.moody.fragments.MediaFragment;
import com.example.moody.models.Movie;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    private ArrayList<Movie> movies;
    private final static String TAG = CustomAsyncTaskLoader.class.getSimpleName();

    public CustomAsyncTaskLoader(Context context, ArrayList<Movie> movies) {
        super(context);
        this.movies = movies;
        Log.i(TAG, "init Asynctask Loader");
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        //load data
            try {
        synchronized (this) {
            wait(3000);
            setMoviesData(movies);
            Log.i(TAG, "load in background");
            }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        return movies;
    }
    @Override
    public void deliverResult(ArrayList<Movie> data) {
        super.deliverResult(data);
        Log.i(TAG, "deliver Result");
    }

    //hash filter movies
    public static void setMoviesData(ArrayList<Movie> Movies) {

    }
}
