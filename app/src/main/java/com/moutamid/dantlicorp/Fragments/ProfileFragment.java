package com.moutamid.dantlicorp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.google.firebase.auth.FirebaseAuth;
import com.moutamid.dantlicorp.Activities.Authentication.GetSocialLinksActivity;
import com.moutamid.dantlicorp.Activities.Authentication.LoginActivity;
import com.moutamid.dantlicorp.Model.SocialModel;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;


public class ProfileFragment extends Fragment {
    ImageView profile_img;
    TextView name, dob, email, phone_number, cnic_number;
    TextView facebook_url_txt, twitter_url_txt, instagram_url_txt, reddit_url_txt, pinterest_url_txt, linkedIn_url_txt;
    ImageView edit_lyt, logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//        profile_img = view.findViewById(R.id.profile_pic);
//        facebook_url_txt = view.findViewById(R.id.facebook);
//        twitter_url_txt = view.findViewById(R.id.twitter);
//        instagram_url_txt = view.findViewById(R.id.instagram);
//        reddit_url_txt = view.findViewById(R.id.reddit);
//        pinterest_url_txt = view.findViewById(R.id.pinterest);
//        linkedIn_url_txt = view.findViewById(R.id.linked_in);
//        edit_lyt = view.findViewById(R.id.edit_lyt);
//        name = view.findViewById(R.id.name);
//        dob = view.findViewById(R.id.dob);
//        email = view.findViewById(R.id.email);
//        phone_number = view.findViewById(R.id.phone_number);
//        cnic_number = view.findViewById(R.id.cnic_number);
//        logout = view.findViewById(R.id.logout);
//        UserModel userNew = (UserModel) Stash.getObject("UserDetails", UserModel.class);
//        SocialModel socialModel = (SocialModel) Stash.getObject("UserLinks", SocialModel.class);
//        name.setText(userNew.name);
//        dob.setText(userNew.dob);
//        email.setText(userNew.email);
//        phone_number.setText(userNew.phone_number);
//        cnic_number.setText(userNew.cnic);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                FirebaseAuth.getInstance().signOut();
//                Stash.clear("UserDetails");
//                startActivity(new Intent(getContext(), LoginActivity.class));
//                getActivity().finishAffinity();
//            }
//        });
//
//        if (socialModel != null) {
//            facebook_url_txt.setText(socialModel.facebook_url);
//            twitter_url_txt.setText(socialModel.twitter_url);
//            instagram_url_txt.setText(socialModel.instagram_url);
//            reddit_url_txt.setText(socialModel.reddit_url);
//            pinterest_url_txt.setText(socialModel.pinterest_url);
//            linkedIn_url_txt.setText(socialModel.linkedIn_url);
//        }
//        Glide.with(getContext()).load(userNew.image_url).into(profile_img);
//        edit_lyt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(), GetSocialLinksActivity.class));
//            }
//        });
        return view;
    }
}