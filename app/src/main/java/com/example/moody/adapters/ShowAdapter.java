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
import com.example.moody.models.TVShow;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder> {
    Context context;
    List<TVShow> showRecs;

    public ShowAdapter(Context context, List<TVShow> shows) {
        this.context = context;
        this.showRecs = shows;
    }

    //inflates a layout from XML and returns VH
    @NonNull
    @NotNull
    @Override
    public ShowAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("ShowAdapter", "onCreateViewHolder");
        View showView =  LayoutInflater.from(context).inflate(R.layout.item_tv, parent, false);
        return new ShowAdapter.ViewHolder(showView);
    }

    //populates data into item through holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ShowAdapter.ViewHolder holder, int position) {
        Log.d("ShowAdapter", "onBindViewHolder " + position);
        TVShow show = showRecs.get(position);
        holder.bind(show);
    }

    //returns num items in list
    @Override
    public int getItemCount() {
        return showRecs.size();
    }

    //define ViewHolder class for representation of show data
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvShowTitle;
        ImageView ivShow;

        //constructor defines where data for views is coming from
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvShowTitle = itemView.findViewById(R.id.tvShowTitle);
            ivShow = itemView.findViewById(R.id.ivShow);
        }

        //define bind function using getters to fill in data
        public void bind(TVShow show) {
            tvShowTitle.setText(show.getName());
            String imageUrl;
            imageUrl = show.getPosterPath();
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10;
            Glide.with(context)
                    .load(imageUrl)
                    .centerCrop()
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivShow);
        }
    }
}
