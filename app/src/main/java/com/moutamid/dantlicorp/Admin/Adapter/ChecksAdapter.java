package com.moutamid.dantlicorp.Admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.moutamid.dantlicorp.Activities.Home.MapsActivity;
import com.moutamid.dantlicorp.Model.ChecksModel;
import com.moutamid.dantlicorp.R;

import java.util.ArrayList;

public class ChecksAdapter extends RecyclerView.Adapter<ChecksAdapter.ChatVH> {
    private Context context;
    private ArrayList<ChecksModel> list;

    public ChecksAdapter(Context context, ArrayList<ChecksModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ChatVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.checks_layout, parent, false);
        return new ChatVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatVH holder, int position) {
        ChecksModel model = list.get(holder.getAdapterPosition());
        holder.name.setText(model.name);
        holder.no_box.setText(model.box);
        holder.date.setText(model.date);
        holder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (model.lat > -90 && model.lat < 90 && model.lng > -180 && model.lng < 180) {
//                    Intent intent = new Intent(context, MapsActivity.class);
//                    intent.putExtra("lat", current_resturantModel.getLat());
//                    intent.putExtra("lng", current_resturantModel.getLng());
//                    intent.putExtra("name", current_resturantModel.getName());
//                    context.startActivity(intent);
//
//                } else {
//                    Toast.makeText(context, "Invalid Coordinates to show marker", Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ChatVH extends RecyclerView.ViewHolder {
        ImageView map;
        TextView name, no_box, date;

        public ChatVH(@NonNull View itemView) {
            super(itemView);
//
            map = itemView.findViewById(R.id.map);
            name = itemView.findViewById(R.id.name);
            no_box = itemView.findViewById(R.id.no_box);
            date = itemView.findViewById(R.id.date);

        }
    }

}
