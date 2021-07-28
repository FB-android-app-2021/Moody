package com.example.moody.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moody.R;
import com.example.moody.databinding.FragmentMovieTrailerBinding;
import com.example.moody.models.Movie;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

public class MovieTrailerFragment extends Fragment {

    FragmentMovieTrailerBinding binding;
    YouTubePlayerFragment youtubeFragment;
    String youtubeKey;

    public MovieTrailerFragment(String youTubeKey) {
        this.youtubeKey = youTubeKey;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieTrailerBinding.inflate(getLayoutInflater(), container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String videoId = youtubeKey;
         YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.youtubeFragment);
        youtubeFragment.initialize(getString(R.string.youtube_api),
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(videoId);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

}