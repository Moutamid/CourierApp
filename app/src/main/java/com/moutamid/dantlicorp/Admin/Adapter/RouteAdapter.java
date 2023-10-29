package com.moutamid.dantlicorp.Admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.moutamid.dantlicorp.Admin.Activities.ChecksDetailsActivity;
import com.moutamid.dantlicorp.Model.ChecksModel;
import com.moutamid.dantlicorp.R;

import java.util.ArrayList;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ChatVH> {
    private Context context;
    private ArrayList<ChecksModel> list;

    public RouteAdapter(Context context, ArrayList<ChecksModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ChatVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.route_layout, parent, false);
        return new ChatVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatVH holder, int position) {
        ChecksModel model = list.get(holder.getAdapterPosition());
        holder.no_box.setText(model.email);
        holder.date.setText(model.route_name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stash.put("key_route", model.key);
                context.startActivity(new Intent(context, ChecksDetailsActivity.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ChatVH extends RecyclerView.ViewHolder {
        TextView no_box, date;

        public ChatVH(@NonNull View itemView) {
            super(itemView);
//
            no_box = itemView.findViewById(R.id.no_box);
            date = itemView.findViewById(R.id.date);

        }
    }

}
