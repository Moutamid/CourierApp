package com.moutamid.dantlicorp.Admin.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.dantlicorp.Model.TimeSheetModel;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Constants;

public class ProfileFragment extends Fragment {
    ImageView profile_img;
    TextView name, dob, email, phone_number, cnic_number;
    TextView date, days, hours, pay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile2, container, false);
        profile_img = view.findViewById(R.id.profile_pic);
        name = view.findViewById(R.id.name);
        dob = view.findViewById(R.id.dob);
        email = view.findViewById(R.id.email);
        phone_number = view.findViewById(R.id.phone_number);
        cnic_number = view.findViewById(R.id.cnic_number);
        date = view.findViewById(R.id.date);
        days = view.findViewById(R.id.days);
        hours = view.findViewById(R.id.hours);
        pay = view.findViewById(R.id.pay);
        String userID = Stash.getString("userID");
        Log.d("user", "dtaa" + userID);

        Constants.UserReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userNew = snapshot.getValue(UserModel.class);
                Log.d("user", "dtaa" + snapshot.child("name").getValue().toString());
                name.setText(userNew.getName());
                dob.setText(userNew.dob);
                email.setText(userNew.email);
                phone_number.setText(userNew.phone_number);
                cnic_number.setText(userNew.cnic);
                Glide.with(getContext()).load(userNew.image_url).into(profile_img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Constants.UserReference.child(userID).child(Constants.TIME_SHEET).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    TimeSheetModel timeSheetModel = snapshot.getValue(TimeSheetModel.class);

                    date.setText(timeSheetModel.date);
                    days.setText(timeSheetModel.days);
                    hours.setText(timeSheetModel.hours);
                    pay.setText(timeSheetModel.pay);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}