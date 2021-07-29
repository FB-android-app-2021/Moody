package com.example.moody.callbacks;

import com.example.moody.models.Music;

import java.util.List;

public interface MusicCallBackPresenter {
    public void success(List<String> music);

    public void showError(String error);

    public void showLoader();

    public void hideLoader();
}
