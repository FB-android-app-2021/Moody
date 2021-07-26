package com.example.moody.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moody.R;
import com.example.moody.models.Entry;

import java.util.Date;
import java.util.List;

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.ViewHolder> {
    private Context context;
    private List<Entry> entries;

    public EntriesAdapter(Context context , List<Entry> entries) {
        this.context = context;
        this.entries = entries;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Entry entry = entries.get(position);
        holder.bind(entry);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public void clear() {
        entries.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Entry> list) {
        entries.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvEntryPost;
        private TextView tvEmotion;
        private TextView tvTimeStamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEntryPost = itemView.findViewById(R.id.tvEntryPost);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            tvEmotion = itemView.findViewById(R.id.tvEmotion);
        }

        public void bind(Entry entry) {
            tvEntryPost.setText(entry.getCaption());
            Date createdAt = entry.getCreatedAt();
            String timeAgo = entry.calculateTimeAgo(createdAt);
            tvTimeStamp.setText(timeAgo);
        }
    }
}