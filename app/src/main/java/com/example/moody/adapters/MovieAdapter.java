package com.example.moody.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moody.R;
import com.example.moody.models.Movie;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movieRecs;

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle;
        ImageView ivMovie;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            ivMovie = itemView.findViewById(R.id.ivMovie);
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
    }
}