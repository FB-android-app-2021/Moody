package com.example.moody;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseFile;

import java.util.Date;
import java.util.List;

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.ViewHolder> {
    private Context context;
    private List<Entry> entries;

    //constructor
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

    // Clean all elements of the recycler
    public void clear() {
        entries.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Entry> list) {
        entries.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvEntryPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEntryPost = itemView.findViewById(R.id.tvEntryPost);
        }

        public void bind(Entry entry) {
            // Bind the post data to the view elements
            tvEntryPost.setText(entry.getCaption());
        }
//        public void onClick(View v) {
//            //get item position
//            int pos = getAdapterPosition();
//            //make sure pos exists in view
//            if(pos != RecyclerView.NO_POSITION) {
//                Entry entry = entries.get(pos);
//                Intent intent = new Intent(context, ImageDetailActivity.class);
//                //serialize pass data to new intent
//                intent.putExtra("Caption", entry.getDescription());
//                Date createdAt = entry.getCreatedAt();
//                String timeAgo = entry.calculateTimeAgo(createdAt);
//                intent.putExtra("Timestamp", timeAgo);
//                intent.putExtra("User", entry.getUser().getUsername());
//                //show activity
//                context.startActivity(intent);
//            }
//        }
    }
}

