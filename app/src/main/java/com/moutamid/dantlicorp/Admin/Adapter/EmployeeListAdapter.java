package com.moutamid.dantlicorp.Admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.moutamid.dantlicorp.Admin.Activities.ChatScreenActivity;
import com.moutamid.dantlicorp.Admin.Activities.UserDetailsActivity;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ChatListVH> {
    Context context;
    ArrayList<UserModel> list;

    public EmployeeListAdapter(Context context, ArrayList<UserModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ChatListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatListVH(LayoutInflater.from(context).inflate(R.layout.employee_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListVH holder, int position) {
        UserModel model = list.get(holder.getAdapterPosition());
        Glide.with(context).load(model.image_url).placeholder(R.drawable.profile_icon).into(holder.imageView);
        holder.name.setText(model.getName());

        holder.itemView.setOnClickListener(v -> {

            Stash.put("userID", model.getId());
            Stash.put("userName", model.getName());
            context.startActivity(new Intent(context, UserDetailsActivity.class));
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ChatListVH extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView name;

        public ChatListVH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profile);
            name = itemView.findViewById(R.id.name);
         }
    }

}
