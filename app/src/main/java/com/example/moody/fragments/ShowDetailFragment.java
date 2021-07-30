package com.example.moody.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.moody.MainActivity;
import com.example.moody.R;
import com.example.moody.databinding.FragmentMtvDetailBinding;
import com.example.moody.models.TVShow;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class ShowDetailFragment extends Fragment implements GestureDetector.OnGestureListener{
        public static final String TAG = "ShowDetailFragment";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
        public static final String VIDEO_URL = BASE_URL + "tv/%d/videos?api_key=eb094fc10e8fc702bfc06d84810d0728";
        TVShow show;

        TextView tvdTitle;
        TextView tvOverview;
        ImageView ivdPoster;
        Button btnTrailer;
        String youTubeKey;

        List<TVShow> showRecs;
        int pos;

        FragmentMtvDetailBinding binding;

        public ShowDetailFragment(List<TVShow> showRecs, int pos) {
            this.showRecs = showRecs;
            this.pos = pos;
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
            View v = binding.mediaDetailFrag;
            final GestureDetector gesture = new GestureDetector(getActivity(),
                    new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onDown(MotionEvent e) {
                            return true;
                        }
                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                               float velocityY) {
                            Log.i(TAG, "onFling has been called!");
                            final int SWIPE_MIN_DISTANCE = 120;
                            final int SWIPE_MAX_OFF_PATH = 250;
                            final int SWIPE_THRESHOLD_VELOCITY = 200;
                            try {
                                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                    return false;
                                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                    changeDetailFragment(pos + 1);
                                    Log.i(TAG, "Right to Left");
                                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                    changeDetailFragment(pos - 1);
                                    Log.i(TAG, "Left to Right");
                                }
                            } catch (Exception e) {
                                // nothing
                            }
                            return super.onFling(e1, e2, velocityX, velocityY);
                        }
                    });

            v.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return gesture.onTouchEvent(event);
                }
            });

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
        AsyncHttpClient client = new AsyncHttpClient();
        String requestUrl = String.format(VIDEO_URL, show.getID());
        client.get(requestUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    JSONObject video = results.getJSONObject(0);
                    youTubeKey = video.getString("key");
                } catch (JSONException e) {
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
            }
        });
    }
    private void goTrailerFragment(String youTubeKey) {
        TrailerFragment trailerFragment = new TrailerFragment(youTubeKey);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main_placeholder, trailerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
    void changeDetailFragment(int pos) {
            TVShow newShow = null;
        if(pos != RecyclerView.NO_POSITION) {
            newShow = showRecs.get(pos);
            Fragment detailFragment = new ShowDetailFragment(showRecs, pos);
            Bundle bundle = new Bundle();
            bundle.putParcelable(TVShow.class.getSimpleName(), Parcels.wrap(newShow));
            detailFragment.setArguments(bundle);
            switchContent(R.id.fragment_main_placeholder, detailFragment);
        }
        else {
            goBackFragment(MainActivity.moodRecs);

        }
    }
    public void goBackFragment(Fragment mediaFragment) {
        String backStateName =  mediaFragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getActivity().getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.fragment_main_placeholder, mediaFragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        }

    }
    public void switchContent(int id, Fragment fragment) {
        if (getActivity() == null)
            return;
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag);
        }
    }
}