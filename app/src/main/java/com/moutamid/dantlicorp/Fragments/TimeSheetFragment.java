package com.moutamid.dantlicorp.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.dantlicorp.Activities.Home.TimeSheetActivity;
import com.moutamid.dantlicorp.Admin.Activities.AllTimeSheetsActivity;
import com.moutamid.dantlicorp.Admin.Adapter.InvoiceListAdapter;
import com.moutamid.dantlicorp.Model.TimeSheetModel;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.ArrayList;
import java.util.Objects;

public class TimeSheetFragment extends Fragment {
    ArrayList<TimeSheetModel> list;
    RecyclerView recyclerView;

    TextView no_text;
    TextView btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timesheet, container, false);
        recyclerView = view.findViewById(R.id.content_rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);

        list = new ArrayList<>();

        no_text = view.findViewById(R.id.no_text);
        btn = view.findViewById(R.id.btn);

//        if (Config.isNetworkAvailable(getContext())) {
//            getData();
//        } else {
//            Toast.makeText(getContext(), "No network connection available.", Toast.LENGTH_SHORT).show();
//        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://dantlicorp.com/forms/timesheet.php");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
//                startActivity(new Intent(getContext(), TimeSheetActivity.class));
            }
        });
        return view;
    }

    private void getData() {
        Dialog lodingbar = new Dialog(getContext());
        String userID = Stash.getString("userID");

        lodingbar.setContentView(R.layout.loading);
        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        lodingbar.setCancelable(false);
        lodingbar.show();
        Constants.UserReference.child(userID).child(Constants.TIME_SHEET)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            list.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                TimeSheetModel model = dataSnapshot.getValue(TimeSheetModel.class);

                                    list.add(model);
                                    Log.d("listSize", "ee : " + model.number);
                            }


                        }
                        if (list.size() < 1) {
                            no_text.setVisibility(View.GONE);
                        } else {
                            no_text.setVisibility(View.GONE);
                        }
                        lodingbar.dismiss();
                        InvoiceListAdapter adapter = new InvoiceListAdapter(getContext(), list);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        lodingbar.dismiss();
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}