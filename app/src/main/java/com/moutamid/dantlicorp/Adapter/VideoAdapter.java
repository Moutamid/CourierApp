package com.moutamid.dantlicorp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moutamid.dantlicorp.Activities.Home.VideoPlayActivity;
import com.moutamid.dantlicorp.Model.VideoModel;
import com.moutamid.dantlicorp.R;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.GalleryPhotosViewHolder> {


    Context ctx;
    List<VideoModel> videoModelList;

    public VideoAdapter(Context ctx, List<VideoModel> videoModelList) {
        this.ctx = ctx;
        this.videoModelList = videoModelList;
    }

    @NonNull
    @Override
    public GalleryPhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.video, parent, false);
        return new GalleryPhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryPhotosViewHolder holder, final int position) {
        VideoModel videoModel=videoModelList.get(position);
        Glide.with(ctx).load(videoModel.getThumbnail()).into(holder.image);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(ctx, VideoPlayActivity.class);
            intent.putExtra("url", videoModel.getUrl());
            intent.putExtra("link", videoModel.getLink());
             ctx.startActivity(intent);
        });
        holder.remove_herb.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return videoModelList.size();
    }

    public class GalleryPhotosViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        ImageView remove_herb;

        public GalleryPhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            remove_herb = itemView.findViewById(R.id.remove_herb);

        }
    }
}
