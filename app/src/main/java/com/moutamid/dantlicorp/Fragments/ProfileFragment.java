package com.moutamid.dantlicorp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;


public class ProfileFragment extends Fragment {
    ImageView profile_img;
    TextView name, dob, email, phone_number, cnic_number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profile_img = view.findViewById(R.id.profile_pic);
        name = view.findViewById(R.id.name);
        dob = view.findViewById(R.id.dob);
        email = view.findViewById(R.id.email);
        phone_number = view.findViewById(R.id.phone_number);
        cnic_number = view.findViewById(R.id.cnic_number);
         UserModel userNew = (UserModel) Stash.getObject("UserDetails", UserModel.class);
        name.setText(userNew.name);
        dob.setText(userNew.dob);
        email.setText(userNew.email);
        phone_number.setText(userNew.phone_number);
        cnic_number.setText(userNew.cnic);
        Glide.with(getContext()).load(userNew.image_url).into(profile_img);
        return view;
    }
}