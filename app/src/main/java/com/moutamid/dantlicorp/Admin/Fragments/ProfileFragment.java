package com.moutamid.dantlicorp.Admin.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.dantlicorp.Admin.Adapter.TimesheetAdapter;
import com.moutamid.dantlicorp.Model.SocialModel;
import com.moutamid.dantlicorp.Model.TimeSheetModel;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    ImageView profile_img;
    TextView name, dob, email, phone_number, cnic_number;
    RecyclerView content_rcv;
    public List<TimeSheetModel> productModelList = new ArrayList<>();
    TimesheetAdapter timesheetAdapter;
    String userID;
    TextView no_text;
    TextView facebook_url_txt, twitter_url_txt, instagram_url_txt, reddit_url_txt, pinterest_url_txt, linkedIn_url_txt;

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
        userID = Stash.getString("userID");
        facebook_url_txt = view.findViewById(R.id.facebook);
        twitter_url_txt = view.findViewById(R.id.twitter);
        instagram_url_txt = view.findViewById(R.id.instagram);
        reddit_url_txt = view.findViewById(R.id.reddit);
        pinterest_url_txt = view.findViewById(R.id.pinterest);
        linkedIn_url_txt = view.findViewById(R.id.linked_in);
        no_text = view.findViewById(R.id.no_text);

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
        Constants.UserReference.child(userID).child("social_links").addListenerForSingleValueEvent(new ValueEventListener() {
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

        content_rcv = view.findViewById(R.id.content_rcv);
        content_rcv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        timesheetAdapter = new TimesheetAdapter(getContext(), productModelList);
        content_rcv.setAdapter(timesheetAdapter);

        if (Config.isNetworkAvailable(getContext())) {
            getProducts();
        } else {
            Toast.makeText(getContext(), "No network connection available.", Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    private void getProducts() {
//        Config.showProgressDialog(getContext());
        Constants.UserReference.child(userID).child(Constants.TIME_SHEET).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productModelList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    TimeSheetModel herbsModel = ds.getValue(TimeSheetModel.class);
                    productModelList.add(herbsModel);
                }
                if(productModelList.size()<1)
                {
                    no_text.setVisibility(View.VISIBLE);
                }
                else
                {
                    no_text.setVisibility(View.GONE);
                }
                timesheetAdapter.notifyDataSetChanged();
//                Config.dismissProgressDialog();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Config.dismissProgressDialog();

            }


        });
    }

}