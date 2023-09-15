package com.moutamid.dantlicorp.Admin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.dantlicorp.Model.SocialModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Constants;

public class SocialFragment extends Fragment {

    TextView facebook_url_txt, twitter_url_txt, instagram_url_txt, reddit_url_txt, pinterest_url_txt, linkedIn_url_txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_social, container, false);
        facebook_url_txt = view.findViewById(R.id.facebook);
        twitter_url_txt = view.findViewById(R.id.twitter);
        instagram_url_txt = view.findViewById(R.id.instagram);
        reddit_url_txt = view.findViewById(R.id.raddit);
        pinterest_url_txt = view.findViewById(R.id.pinterest);
        linkedIn_url_txt = view.findViewById(R.id.linked_in);

        Constants.UserReference.child(Stash.getString("userID")).child("social_links").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    SocialModel socialModel = snapshot.getValue(SocialModel.class);
                    facebook_url_txt.setText(socialModel.facebook_url);
                    twitter_url_txt.setText(socialModel.twitter_url);
                    instagram_url_txt.setText(socialModel.instagram_url);
                    reddit_url_txt.setText(socialModel.reddit_url);
                    pinterest_url_txt.setText(socialModel.pinterest_url);
                    linkedIn_url_txt.setText(socialModel.linkedIn_url);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }


}