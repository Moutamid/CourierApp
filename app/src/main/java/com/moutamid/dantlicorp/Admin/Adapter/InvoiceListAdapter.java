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
import com.google.firebase.auth.FirebaseAuth;
import com.moutamid.dantlicorp.Admin.Activities.TimeSheetActivity;
import com.moutamid.dantlicorp.Model.TimeSheetModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class InvoiceListAdapter extends RecyclerView.Adapter<InvoiceListAdapter.ChatListVH> {
    Context context;
    ArrayList<TimeSheetModel> list;

    public InvoiceListAdapter(Context context, ArrayList<TimeSheetModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ChatListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatListVH(LayoutInflater.from(context).inflate(R.layout.invoice_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListVH holder, int position) {
        TimeSheetModel model = list.get(holder.getAdapterPosition());
        holder.name.setText(model.number);
        holder.status.setText(model.status);

        holder.itemView.setOnClickListener(v -> {
                    Stash.put(Constants.TIME_SHEET, model);
                    context.startActivity(new Intent(context, TimeSheetActivity.class));
                }
        );

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ChatListVH extends RecyclerView.ViewHolder {
        TextView name, status;

        public ChatListVH(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.status);
            name = itemView.findViewById(R.id.name);
        }
    }

}
