package com.moutamid.dantlicorp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.dantlicorp.Model.NotificationModel;
import com.moutamid.dantlicorp.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.GalleryPhotosViewHolder> {


    Context ctx;
    ArrayList<NotificationModel> notificationModelList;

    public NotificationAdapter(Context ctx, ArrayList<NotificationModel> notificationModelList) {
        this.ctx = ctx;
        this.notificationModelList = notificationModelList;
    }

    @NonNull
    @Override
    public GalleryPhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notifications_layout, parent, false);
        return new GalleryPhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryPhotosViewHolder holder, final int position) {
        NotificationModel notificationModel = notificationModelList.get(position);
        holder.notification_txt.setText(notificationModel.message+ notificationModel.title);
        holder.date.setText(notificationModel.date );

    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }

    public class GalleryPhotosViewHolder extends RecyclerView.ViewHolder {

        TextView notification_txt, date;

        public GalleryPhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            notification_txt = itemView.findViewById(R.id.notification_txt);
            date = itemView.findViewById(R.id.date);
        }
    }
}
