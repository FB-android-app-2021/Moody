package com.example.moody.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moody.R;
import com.example.moody.databinding.FragmentShowDetailBinding;
import com.example.moody.models.TVShow;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ShowDetailFragment extends Fragment {
        public static final String TAG = "ShowDetailFragment";
        //public static final String VIDEO_URL = MainActivity.BASE_URL + "movie/%d/videos?api_key=eb094fc10e8fc702bfc06d84810d0728";
        TVShow show;

        TextView tvdTitle;
        TextView tvOverview;
        ImageView ivdPoster;
        // String youTubeKey;

        FragmentShowDetailBinding binding;

        public ShowDetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            binding = FragmentShowDetailBinding.inflate(getLayoutInflater());
            View view = binding.getRoot();
            return view;
        }

        @Override
        public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            tvdTitle = binding.tvdTitle;
            tvOverview = binding.tvOverview;
            ivdPoster = binding.ivdPoster;

            //unwrap movie passed in via bundle using name as key
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                show = Parcels.unwrap(bundle.getParcelable(TVShow.class.getSimpleName()));
            }

            // set title and overview
            tvdTitle.setText(show.getName());
            tvOverview.setText(show.getOverview());
            String posterUrl = show.getPosterPath();
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10;
            Glide.with(this)
                    .load(posterUrl)
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivdPoster);
//        AsyncHttpClient client = new AsyncHttpClient();
//        //get request on URL to get currently playing movies and save as constant
//        //movie api uses json so json response handler necessary
//        String requestUrl = String.format(VIDEO_URL, movie.getID());
//        client.get(requestUrl, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Headers headers, JSON json) {
//                Log.d(TAG, "onSuccess");
//                //get data we want from json obj
//                JSONObject jsonObject = json.jsonObject;
//                //key may not exist when parsing out json
//                try {
//                    JSONArray results = jsonObject.getJSONArray("results");
//                    //write to info level for debugging
//                    Log.i(TAG, "Results: " + results.toString());
//
//                    JSONObject video = results.getJSONObject(0);
//                    youTubeKey = video.getString("key");
//
//                } catch (JSONException e) {
//                    //log error
//                    Log.e(TAG, "Hit json exception", e);
//                }
//            }
//
//            @Override
//            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
//                Log.d(TAG, "onFailure");
//            }
//        });
        }
    }