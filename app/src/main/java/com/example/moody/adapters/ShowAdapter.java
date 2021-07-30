package com.example.moody.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moody.MainActivity;
import com.example.moody.R;
import com.example.moody.fragments.ShowDetailFragment;
import com.example.moody.models.TVShow;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder> {
    Context context;
    List<TVShow> showRecs;
    int pos;

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvShowTitle;
        ImageView ivShow;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvShowTitle = itemView.findViewById(R.id.tvShowTitle);
            ivShow = itemView.findViewById(R.id.ivShow);
            itemView.setOnClickListener(this);
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

        @Override
        public void onClick(View v) {
            pos = getAdapterPosition();
            if(pos != RecyclerView.NO_POSITION) {
                TVShow show = showRecs.get(pos);
                goTVDetailFragment(show);
            }
        }
    }
    private void goTVDetailFragment(TVShow show) {
        Fragment detailFragment = new ShowDetailFragment(showRecs, pos);
        Bundle bundle = new Bundle();
        bundle.putParcelable(TVShow.class.getSimpleName(), Parcels.wrap(show));
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
