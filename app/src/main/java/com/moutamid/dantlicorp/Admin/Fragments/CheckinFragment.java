package com.moutamid.dantlicorp.Admin.Fragments;

import android.content.Intent;
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
import com.moutamid.dantlicorp.Activities.Home.MapsActivity;
import com.moutamid.dantlicorp.Admin.Adapter.ChecksAdapter;
import com.moutamid.dantlicorp.Model.ChecksModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.ArrayList;


public class CheckinFragment extends Fragment {

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        list = new ArrayList<>();
        list.clear();
        Stash.put("CheckIn", list);

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
        Config.showProgressDialog(getContext());
        Constants.UserReference.child(userID).child("check_in").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.exists()) {
                            ChecksModel model = dataSnapshot.getValue(ChecksModel.class);
                            list.add(model);
                        }
                    }


                    Stash.put("CheckIn", list);

                    Log.d("listSize", "ee : " + list.get(0).name);

                }
                adapter = new ChecksAdapter(getContext(), list);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
                Config.dismissProgressDialog();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Config.dismissProgressDialog();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
