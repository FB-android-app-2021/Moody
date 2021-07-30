package com.example.moody.callbacks;

import com.example.moody.models.TVShow;

import java.util.List;
import java.util.Map;

public interface TVCallBackPresenter {
    public void success(Map<String, List<TVShow>> showMap);

    public void showError(String error);

    public void showLoader();

    public void hideLoader();
}
