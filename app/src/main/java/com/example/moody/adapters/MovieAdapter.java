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

    //member vars: context (where adapter is being constructed from/ inflate view) & movies (list of data)
    Context context;
    List<Movie> movieRecs;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movieRecs = movies;
    }

    //inflates a layout from XML and returns VH
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView =  LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    //populates data into item through holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        Movie movie = movieRecs.get(position);
        holder.bind(movie);
    }

    //returns num items in list
    @Override
    public int getItemCount() {
        return movieRecs.size();
    }

    //define ViewHolder class for representation of movie data
    public class ViewHolder extends RecyclerView.ViewHolder {
        //movie views
        TextView tvMovieTitle;
        ImageView ivMovie;

        //constructor defines where data for views is coming from
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            ivMovie = itemView.findViewById(R.id.ivMovie);
        }

        //define bind function using getters to fill in data
        public void bind(Movie movie) {
            tvMovieTitle.setText(movie.getTitle());
            String imageUrl;
            imageUrl = movie.getPosterPath();
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10;
            Glide.with(context)
                    .load(imageUrl)
                    .centerCrop()
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivMovie);
        }
    }
}