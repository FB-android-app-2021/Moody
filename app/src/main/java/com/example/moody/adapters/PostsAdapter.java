package com.example.moody.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moody.R;
import com.example.moody.models.Entry;
import com.example.moody.models.Movie;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Entry> posts;

    public PostsAdapter(Context context, List<Entry> posts) {
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
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
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
        notifyItemInserted(0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPost;
        private TextView tvPostEmotion;
        private TextView tvPostTimeStamp;
        private ImageButton btnExclaim;
        private ImageButton btnHeart;
        private ImageButton btnComment;
        private TextView tvNumExclaims;
        private TextView tvNumLikes;
        private TextView tvNumComments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPost = itemView.findViewById(R.id.tvPost);
            tvPostTimeStamp = itemView.findViewById(R.id.tvPostTimeStamp);
            tvPostEmotion = itemView.findViewById(R.id.tvPostEmotion);
            btnExclaim = itemView.findViewById(R.id.btnExclaim);
            btnHeart = itemView.findViewById(R.id.btnHeart);
            btnComment = itemView.findViewById(R.id.btnComment);
            tvNumExclaims = itemView.findViewById(R.id.tvNumExclaims);
            tvNumLikes = itemView.findViewById(R.id.tvNumLikes);
            tvNumComments = itemView.findViewById(R.id.tvNumComments);
            btnExclaim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    int numExclaims;
                    if(pos != RecyclerView.NO_POSITION) {
                        Entry exclaimedPost = posts.get(pos);
                        numExclaims = exclaimedPost.getExclaims();
                        numExclaims++;
                        exclaimedPost.setExclaims(numExclaims);
                        tvNumExclaims.setText(String.valueOf(numExclaims));
                        exclaimedPost.saveInBackground();
                    }
                }
            });
            btnHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    int numLikes;
                    if(pos != RecyclerView.NO_POSITION) {
                        Entry heartedPost = posts.get(pos);
                        numLikes = heartedPost.getLikes();
                        numLikes++;
                        heartedPost.setLikes(numLikes);
                        tvNumLikes.setText(String.valueOf(numLikes));
                        heartedPost.saveInBackground();
                    }
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
            tvNumExclaims.setText(String.valueOf(post.getExclaims()));
            tvNumLikes.setText(String.valueOf(post.getLikes()));
            tvNumComments.setText("3");
            post.saveInBackground();
            String mood = post.getEmotion();
            if(mood.equals("Happy")) {
                tvPostEmotion.setText(":)");
            }
            else if(mood.equals("Sad")) {
                tvPostEmotion.setText(":(");
            }
        }
    }
    public void swapItems(List<Entry> newPosts) {
        // compute diffs
        final EntryDiffCallback diffCallback = new EntryDiffCallback(this.posts, posts);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        int oldContentSize = this.posts.size();//Get the data length before refresh
        notifyItemRangeRemoved(0,oldContentSize );
        this.posts.addAll(newPosts);//Add new data
        notifyItemRangeInserted(oldContentSize, EntryDiffCallback.mNewList.size() );

        diffResult.dispatchUpdatesTo(this); // calls adapter's notify methods after diff is computed
    }
    public static class EntryDiffCallback extends DiffUtil.Callback {

        private static List<Entry> mOldList;
        public static List<Entry> mNewList;

        public EntryDiffCallback(List<Entry> oldList, List<Entry> newList) {
            this.mOldList = oldList;
            this.mNewList = newList;
        }
        @Override
        public int getOldListSize() {
            return mOldList.size();
        }

        @Override
        public int getNewListSize() {
            return mNewList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            // add a unique ID property on Entry and expose a getId() method
            return mOldList.get(oldItemPosition).getId() == mNewList.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Entry oldEntry = mOldList.get(oldItemPosition);
            Entry newEntry = mNewList.get(newItemPosition);

            if (oldEntry.getCaption().equals(newEntry.getCaption())) {
                return true;
            }
            return false;
        }
    }
}
