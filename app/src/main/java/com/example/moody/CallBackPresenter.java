package com.example.moody;


import android.content.Context;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

import com.example.moody.models.Movie;

import java.util.ArrayList;
import java.util.List;

public interface CallBackPresenter {
    public void success(List<Movie> movies);

    public void showError(String error);

    public void showLoader();

    public void hideLoader();
}
