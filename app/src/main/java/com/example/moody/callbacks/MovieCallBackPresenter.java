package com.example.moody.callbacks;

import com.example.moody.models.Movie;

import java.util.List;
import java.util.Map;

public interface MovieCallBackPresenter {
    public void success(Map<String, List<Movie>> movieMap);

    public void showError(String error);

    public void showLoader();

    public void hideLoader();
}
