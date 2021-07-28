package com.example.moody.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.moody.R;
import com.example.moody.databinding.FragmentMtvDetailBinding;
import com.example.moody.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class MovieDetailFragment extends Fragment {
    public static final String TAG = "MovieDetailFragment";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String VIDEO_URL = BASE_URL + "movie/%d/videos?api_key=eb094fc10e8fc702bfc06d84810d0728";
    Movie movie;


    TextView tvdTitle;
    TextView tvOverview;
    ImageView ivdPoster;
    Button btnTrailer;
    String youTubeKey;

    FragmentMtvDetailBinding binding;

    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMtvDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvdTitle = binding.tvdTitle;
        tvOverview = binding.tvOverview;
        ivdPoster = binding.ivdPoster;
        btnTrailer = binding.btnTrailer;
        btnTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(youTubeKey != null) {
                    onStop();
                    goTrailerFragment(youTubeKey);
                }
            }
        });

        //unwrap movie passed in via bundle using name as key
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            movie = Parcels.unwrap(bundle.getParcelable(Movie.class.getSimpleName()));
        }

        // set title and overview
        tvdTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        String posterUrl = movie.getPosterPath();
        int radius = 30; // corner radius, higher value = more rounded
        int margin = 10;
        Glide.with(this)
                .load(posterUrl)
                .transform(new RoundedCornersTransformation(radius, margin))
                .into(ivdPoster);

        AsyncHttpClient client = new AsyncHttpClient();
        String requestUrl = String.format(VIDEO_URL, movie.getID());
        client.get(requestUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    JSONObject video = results.getJSONObject(0);
                    youTubeKey = video.getString("key");
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
   }
    public void goTrailerFragment(String youTubeKey) {
        TrailerFragment trailerFragment = new TrailerFragment(youTubeKey);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main_placeholder, trailerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}