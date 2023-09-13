package com.moutamid.dantlicorp.Activities.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnSuccessListener;
import com.moutamid.dantlicorp.MainActivity;
import com.moutamid.dantlicorp.Model.SocialModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

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
                Config.showProgressDialog(GetSocialLinksActivity.this);
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
                Constants.UserReference.child(Constants.auth().getUid()).child("social_links").setValue(socialModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Stash.put("UserLinks", socialModel);
                        Config.dismissProgressDialog();
                        Intent intent = new Intent(GetSocialLinksActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });


            }
        });
    }
}