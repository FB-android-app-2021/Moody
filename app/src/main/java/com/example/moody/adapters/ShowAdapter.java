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

    @NonNull
    @NotNull
    @Override
    public ShowAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View showView =  LayoutInflater.from(context).inflate(R.layout.item_tv, parent, false);
        return new ShowAdapter.ViewHolder(showView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShowAdapter.ViewHolder holder, int position) {
        TVShow show = showRecs.get(position);
        holder.bind(show);
    }

    @Override
    public int getItemCount() {
        return showRecs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvShowTitle;
        ImageView ivShow;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvShowTitle = itemView.findViewById(R.id.tvShowTitle);
            ivShow = itemView.findViewById(R.id.ivShow);
        }

        public void bind(TVShow show) {
            tvShowTitle.setText(show.getName());
            String imageUrl;
            imageUrl = show.getPosterPath();
            int radius = 30;
            int margin = 10;
            Glide.with(context)
                    .load(imageUrl)
                    .centerCrop()
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivShow);
        }
    }
}
