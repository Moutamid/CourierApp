package com.moutamid.dantlicorp.Activities.Authentication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnSuccessListener;
import com.moutamid.dantlicorp.Activities.OnBoarding.SplashActivity;
import com.moutamid.dantlicorp.Dailogues.OpenAppDialogClass;
import com.moutamid.dantlicorp.Dailogues.WelcomeDialogClass;
import com.moutamid.dantlicorp.MainActivity;
import com.moutamid.dantlicorp.Model.SocialModel;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.Objects;

public class GetSocialLinksActivity extends AppCompatActivity {
    Button continue_btn;
    EditText facebook_url_edt, twitter_url_edt, instagram_url_edt, reddit_url_edt, pinterest_url_edt, linkedIn_url_edt;
    String facebook_url_str, twitter_url_str, instagram_url_str, reddit_url_str, pinterest_url_str, linkedIn_url_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_social_links);
        continue_btn = findViewById(R.id.next);
        facebook_url_edt = findViewById(R.id.facebook);
        twitter_url_edt = findViewById(R.id.twitter);
        instagram_url_edt = findViewById(R.id.instagram);
        reddit_url_edt = findViewById(R.id.reddit);
        pinterest_url_edt = findViewById(R.id.pinterest);
        linkedIn_url_edt = findViewById(R.id.linked_in);
        SocialModel socialModel = (SocialModel) Stash.getObject("UserLinks", SocialModel.class);
        if (socialModel != null) {
            facebook_url_edt.setText(socialModel.facebook_url);
            twitter_url_edt.setText(socialModel.twitter_url);
            instagram_url_edt.setText(socialModel.instagram_url);
            reddit_url_edt.setText(socialModel.reddit_url);
            pinterest_url_edt.setText(socialModel.pinterest_url);
            linkedIn_url_edt.setText(socialModel.linkedIn_url);
        }
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog lodingbar = new Dialog(GetSocialLinksActivity.this);
                lodingbar.setContentView(R.layout.loading);
                Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
                lodingbar.setCancelable(false);
                lodingbar.show();
                if (!facebook_url_edt.getText().toString().isEmpty()) {
                    facebook_url_str = facebook_url_edt.getText().toString();

                }
                if (!twitter_url_edt.getText().toString().isEmpty()) {
                    twitter_url_str = twitter_url_edt.getText().toString();

                }
                if (!instagram_url_edt.getText().toString().isEmpty()) {

                    instagram_url_str = instagram_url_edt.getText().toString();

                }
                if (!reddit_url_edt.getText().toString().isEmpty()) {
                    reddit_url_str = reddit_url_edt.getText().toString();

                }
                if (!pinterest_url_edt.getText().toString().isEmpty()) {
                    pinterest_url_str = pinterest_url_edt.getText().toString();

                }
                if (!linkedIn_url_edt.getText().toString().isEmpty()) {
                    linkedIn_url_str = linkedIn_url_edt.getText().toString();
                }

                SocialModel socialModel = new SocialModel();
                socialModel.facebook_url = facebook_url_str;
                socialModel.twitter_url = twitter_url_str;
                socialModel.instagram_url = instagram_url_str;
                socialModel.reddit_url = reddit_url_str;
                socialModel.pinterest_url = pinterest_url_str;
                socialModel.linkedIn_url = linkedIn_url_str;
                UserModel userModel = (UserModel) Stash.getObject("UserDetails", UserModel.class);

                Constants.UserReference.child(Constants.auth().getCurrentUser().getUid()).child("social_links").setValue(socialModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Stash.put("UserLinks", socialModel);
                        lodingbar.dismiss();
                        WelcomeDialogClass cdd = new WelcomeDialogClass(GetSocialLinksActivity.this);
                        cdd.show();
                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(GetSocialLinksActivity.this, LoginActivity.class));
        finishAffinity();
    }
}