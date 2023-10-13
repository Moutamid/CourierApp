package com.moutamid.dantlicorp.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.dantlicorp.Model.TimeSheetModel;
import com.moutamid.dantlicorp.R;

import java.util.List;

public class TimesheetAdapter extends RecyclerView.Adapter<TimesheetAdapter.GalleryPhotosViewHolder> {


    Context ctx;
    List<TimeSheetModel> timeSheetModelList;

    public TimesheetAdapter(Context ctx, List<TimeSheetModel> timeSheetModelList) {
        this.ctx = ctx;
        this.timeSheetModelList = timeSheetModelList;
    }

    @NonNull
    @Override
    public GalleryPhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.timesheet_layout, parent, false);
        return new GalleryPhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryPhotosViewHolder holder, final int position) {
        TimeSheetModel timeSheetModel = timeSheetModelList.get(position);
//        holder.date.setText(timeSheetModel.date);
//        holder.days.setText(timeSheetModel.days);
//        holder.hours.setText(timeSheetModel.hours);
//        holder.pay.setText(timeSheetModel.pay);

    }

    @Override
    public int getItemCount() {
        return timeSheetModelList.size();
    }

    public class GalleryPhotosViewHolder extends RecyclerView.ViewHolder {

        TextView date, days, hours, pay;


        public GalleryPhotosViewHolder(@NonNull View view) {
            super(view);
            date = view.findViewById(R.id.date);
            days = view.findViewById(R.id.days);
            hours = view.findViewById(R.id.hours);
            pay = view.findViewById(R.id.pay);

        }
    }
}
