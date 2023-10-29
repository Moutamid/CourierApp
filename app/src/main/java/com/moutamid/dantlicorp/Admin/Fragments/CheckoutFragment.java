package com.moutamid.dantlicorp.Admin.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.dantlicorp.Admin.Activities.MapsActivity;
import com.moutamid.dantlicorp.Admin.Adapter.ChecksAdapter;
import com.moutamid.dantlicorp.Model.ChecksModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.ArrayList;
import java.util.Objects;


public class CheckoutFragment extends Fragment {

    ArrayList<ChecksModel> list;
    RecyclerView recyclerView;
    String userID;
    ChecksAdapter adapter;
    Button all_check_in;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check_in, container, false);
        userID = Stash.getString("userID");

        recyclerView = view.findViewById(R.id.recycler);
        all_check_in = view.findViewById(R.id.all_check_in);
        all_check_in.setText(getString(R.string.all_checkout));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        list = new ArrayList<>();
        list.clear();
        Stash.put("CheckOut", list);

        getData();
        all_check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MapsActivity.class));

            }
        });
        return view;


    }
    private void getData() {
        Dialog lodingbar = new Dialog(getContext());
        lodingbar.setContentView(R.layout.loading);
        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        lodingbar.setCancelable(false);
        lodingbar.show();
        Constants.UserReference.child(userID).child("Routes").child(Stash.getString("route_key")).child("check_out").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.exists()) {
                            ChecksModel checksModel = new ChecksModel();
                            checksModel.name = dataSnapshot.child("name").getValue().toString();
                            checksModel.picked_up = dataSnapshot.child("picked_up").getValue().toString();
                            checksModel.drop_off = dataSnapshot.child("drop_off").getValue().toString();
                            checksModel.lat = Double.parseDouble(dataSnapshot.child("lat").getValue().toString());
                            checksModel.lng = Double.parseDouble(dataSnapshot.child("lng").getValue().toString());
                            checksModel.date = dataSnapshot.child("date").getValue().toString();
                            checksModel.sign = dataSnapshot.child("sign").getValue().toString();

                            list.add(checksModel);
                        }
                    }


                    Stash.put("CheckIn", list);

                    Log.d("listSize", "ee : " + list.get(0).picked_up);

                }
                adapter = new ChecksAdapter(getContext(), list);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
                lodingbar.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                lodingbar.dismiss();                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
