package com.example.moody.callbacks;

import com.example.moody.models.Movie;

import java.util.List;

public interface MovieCallBackPresenter {
    public void success(List<Movie> movies);

    public void showError(String error);

    public void showLoader();

    public void hideLoader();
}
