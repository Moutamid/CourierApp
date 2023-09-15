package com.moutamid.dantlicorp.Fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.fxn.stash.Stash;
import com.google.firebase.auth.FirebaseAuth;
import com.moutamid.dantlicorp.Activities.Authentication.LoginActivity;
import com.moutamid.dantlicorp.Activities.Home.EditProfileActivity;
import com.moutamid.dantlicorp.Activities.Home.WebViewActivity;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;


public class ProfileFragment extends Fragment {
       RelativeLayout profile;
    TextView name_txt, name_latter, textView7;
    RelativeLayout rlShare, rlRate, delete_data, privacy;
    UserModel userModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        userModel = (UserModel) Stash.getObject("UserDetails", UserModel.class);

        textView7 = view.findViewById(R.id.textView7);
        name_txt = view.findViewById(R.id.textView6);
        rlShare = view.findViewById(R.id.rlShare);
        rlRate = view.findViewById(R.id.rlRate);
        privacy = view.findViewById(R.id.privacy);
        delete_data = view.findViewById(R.id.delete_data);
        name_latter = view.findViewById(R.id.textView5);
        profile = view.findViewById(R.id.profile);
        name_txt.setText(userModel.name);

        textView7.setText(userModel.email);
        char c = userModel.name.charAt(0);
        name_latter.setText(c + "");
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });
        delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Stash.clear("UserDetails");
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finishAffinity();
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), WebViewActivity.class));
            }
        });
//
//        edit_lyt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(), GetSocialLinksActivity.class));
//            }
//        });

        rlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    String shareMessage = userModel.name + " found a helpful application, Here is the link\n\n";


                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName() + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });
        rlRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    getActivity().startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }
            }
        });
        return view;
    }
}