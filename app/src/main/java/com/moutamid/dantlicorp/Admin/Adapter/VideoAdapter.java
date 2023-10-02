package com.moutamid.dantlicorp.Admin.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.moutamid.dantlicorp.Activities.Home.VideoPlayActivity;
import com.moutamid.dantlicorp.Admin.Video.AddVideo;
import com.moutamid.dantlicorp.Model.VideoModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.List;
import java.util.Objects;

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
            Intent intent = new Intent(ctx, AddVideo.class);
            intent.putExtra("url", videoModel.getUrl());
            intent.putExtra("thumbnail", videoModel.getThumbnail());
            intent.putExtra("key", videoModel.getKey());
            ctx.startActivity(intent);
        });
        holder.remove_herb.setOnClickListener(view -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
            dialog.setTitle("Delete Video");
            dialog.setMessage("Are you sure to delete this video");
            dialog.setPositiveButton("Yes", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                Dialog lodingbar = new Dialog(ctx);
                lodingbar.setContentView(R.layout.loading);
                Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
                lodingbar.setCancelable(false);
                lodingbar.show();                Constants.VideosReference.child(videoModel.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
lodingbar.dismiss();//                        if (task.isSuccessful()){
//                            videoModelList.remove(position);
//                            notifyDataSetChanged();
//                        }
                    }
                }).addOnFailureListener(e -> {
lodingbar.dismiss();                    Toast.makeText(ctx, "Something went wrong.", Toast.LENGTH_SHORT).show();
                });
            }).setNegativeButton("No", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            dialog.show();
        });


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
