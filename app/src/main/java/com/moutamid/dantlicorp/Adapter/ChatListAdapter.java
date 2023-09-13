package com.moutamid.dantlicorp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.moutamid.dantlicorp.Admin.Activities.ChatScreenActivity;
import com.moutamid.dantlicorp.Model.ChatListModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListVH> {
    Context context;
    ArrayList<ChatListModel> list;

    public ChatListAdapter(Context context, ArrayList<ChatListModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ChatListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatListVH(LayoutInflater.from(context).inflate(R.layout.message_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListVH holder, int position) {
        ChatListModel model = list.get(holder.getAdapterPosition());
        Glide.with(context).load(model.getImage_url()).placeholder(R.drawable.profile_icon).into(holder.imageView);
        holder.name.setText(model.getName());
        holder.message.setText(model.getMessage());
        String today = Config.getFormatedDate(new Date().getTime());
        String message = Config.getFormatedDate(model.getTimeStamp());
        String time = Config.getFormatedTime(new Date().getTime());
        String ttt = Config.getFormatedTime(model.getTimeStamp());

        if (time.equals(ttt)) {
            holder.date.setText("Now");
        } else if (today.equals(message)) {
            holder.date.setText("Today");
        } else {
            holder.date.setText(Config.getDate(model.getTimeStamp()));
        }
        Log.d("listSize", "ee : "+model.getToken());


        holder.itemView.setOnClickListener(v -> {
            Stash.put("userID", model.getChat_id());
            Stash.put("userName", model.getName());
            Stash.put("usertoken", model.getToken());
            context.startActivity(new Intent(context, ChatScreenActivity.class));
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ChatListVH extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView name, message, date;

        public ChatListVH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profile);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.date);
        }
    }

}
