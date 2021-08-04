package com.example.moody.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moody.MainActivity;
import com.example.moody.R;
import com.example.moody.fragments.EntryFragment;
import com.example.moody.fragments.MovieDetailFragment;
import com.example.moody.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    public static List<Movie> movieRecs;
    Context context;
    int pos;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movieRecs = movies;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View movieView =  LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Movie movie = movieRecs.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movieRecs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvMovieTitle;
        ImageView ivMovie;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            ivMovie = itemView.findViewById(R.id.ivMovie);
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            tvMovieTitle.setText(movie.getTitle());
            String imageUrl;
            imageUrl = movie.getPosterPath();
            int radius = 30;
            int margin = 10;
            Glide.with(context)
                    .load(imageUrl)
                    .centerCrop()
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivMovie);
        }
        @Override
        public void onClick(View v) {
            pos = getAdapterPosition();
            if(pos != RecyclerView.NO_POSITION) {
                Movie movie = movieRecs.get(pos);
                goMovieDetailFragment(movie);
            }
        }
    }
    private void goMovieDetailFragment(Movie movie) {
        Fragment detailFragment = new MovieDetailFragment(movieRecs, pos);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Movie.class.getSimpleName(), Parcels.wrap(movie));
        detailFragment.setArguments(bundle);
        switchContent(R.id.fragment_main_placeholder, detailFragment);
    }
    public void switchContent(int id, Fragment fragment) {
        if (context == null)
            return;
        if (context instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) context;
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag);
        }

    }
}