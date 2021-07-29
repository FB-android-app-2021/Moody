package com.example.moody.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moody.R;
import com.example.moody.models.Entry;

import java.util.Date;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Entry> posts;

    public PostsAdapter(Context context , List<Entry> posts) {
        this.context = context;
        this.posts = posts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Entry post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Entry> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPost;
        private TextView tvPostEmotion;
        private TextView tvPostTimeStamp;
        private Button btnExclaim;
        private Button btnHeart;
        private Button btnComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPost = itemView.findViewById(R.id.tvPost);
            tvPostTimeStamp = itemView.findViewById(R.id.tvPostTimeStamp);
            tvPostEmotion = itemView.findViewById(R.id.tvPostEmotion);
            btnExclaim = itemView.findViewById((R.id.btnExclaim));
            btnHeart = itemView.findViewById((R.id.btnHeart));
            btnComment = itemView.findViewById((R.id.btnComment));
            btnExclaim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "I feel this", Toast.LENGTH_SHORT).show();
                }
            });
            btnHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "I love this", Toast.LENGTH_SHORT).show();
                }
            });
            btnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "I want to comment on that", Toast.LENGTH_SHORT).show();
                }
            });

        }

        public void bind(Entry post) {
            tvPost.setText(post.getCaption());
            Date createdAt = post.getCreatedAt();
            String timeAgo = post.calculateTimeAgo(createdAt);
            tvPostTimeStamp.setText(timeAgo);
            String mood = post.getEmotion();
            if(mood.equals("Happy")) {
                tvPostEmotion.setText(":)");
            }
            else if(mood.equals("Sad")) {
                tvPostEmotion.setText(":(");
            }
        }
    }
}
