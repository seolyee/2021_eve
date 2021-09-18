package com.example.HelluApp.MainFragment.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.HelluApp.DailyStamp.Post;
import com.example.HelluApp.R;

import java.util.ArrayList;

public class FeedAdapter_top extends RecyclerView.Adapter<FeedAdapter_top.FeedViewHolder> {

    private ArrayList<Post> arrayList;
    private feed_my_top_posts context;

    public FeedAdapter_top(ArrayList<Post> arrayList, feed_my_top_posts context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        FeedViewHolder holder = new FeedViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter_top.FeedViewHolder holder, int position) {
        Glide.with(holder.itemView).load(arrayList.get(position).uid).into(holder.iv_profile);
        Glide.with(holder.itemView).load(arrayList.get(position).image_path).into(holder.iv_image); //글 이미지
        //holder.tv_date.setText(arrayList.get(position).date); //제목
        holder.tv_author.setText(String.valueOf(arrayList.get(position).author)); //글 작성자
        holder.tv_title.setText(arrayList.get(position).title); //제목
        holder.tv_content.setText(String.valueOf(arrayList.get(position).content)); //내용 일부
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        ImageView iv_image;
        TextView tv_title;
        TextView tv_content;
        TextView tv_author;
        TextView tv_date;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.iv_image = itemView.findViewById(R.id.iv_image);

            this.tv_date = itemView.findViewById(R.id.tv_date);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_content = itemView.findViewById(R.id.tv_content);
            this.tv_author = itemView.findViewById(R.id.tv_author);
        }
    }
}
