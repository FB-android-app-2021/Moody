package com.example.moody.callbacks;

import com.example.moody.models.TVShow;

import java.util.List;

public interface TVCallBackPresenter {
    public void success(List<TVShow> shows);

    public void showError(String error);

    public void showLoader();

    public void hideLoader();
}
