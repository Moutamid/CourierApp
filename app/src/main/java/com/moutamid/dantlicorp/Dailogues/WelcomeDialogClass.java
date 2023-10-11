package com.moutamid.dantlicorp.Dailogues;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.moutamid.dantlicorp.Activities.Authentication.GetSocialLinksActivity;
import com.moutamid.dantlicorp.Activities.OnBoarding.OnBoardingActivity;
import com.moutamid.dantlicorp.MainActivity;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;

import de.hdodenhof.circleimageview.CircleImageView;

public class WelcomeDialogClass extends Dialog {

    public Activity c;
    TextView name_person;
    Button welcome;
    UserModel userModel;

    CircleImageView profile_img;

    public WelcomeDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.welcome_dailogue);
        profile_img = findViewById(R.id.profile_img);
        name_person = findViewById(R.id.name_person);
        welcome = findViewById(R.id.welcome);
        userModel = (UserModel) Stash.getObject("UserDetails", UserModel.class);
        name_person.setText(userModel.getName() + " " + userModel.city + ", " + userModel.state);
        Glide.with(c).load(userModel.image_url).into(profile_img);
        welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.sendFCMPush(c);
                Intent intent = new Intent(c, MainActivity.class);
                c.startActivity(intent);
                c.finish();
            }
        });

    }


}