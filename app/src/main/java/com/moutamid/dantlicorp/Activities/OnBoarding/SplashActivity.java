package com.moutamid.dantlicorp.Activities.OnBoarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.moutamid.dantlicorp.Activities.Authentication.LoginActivity;
import com.moutamid.dantlicorp.MainActivity;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Constants;


public class SplashActivity extends AppCompatActivity {
    int SPLASH_TIME = 2500;
    LinearLayout linearLayout;
    ImageView imageViewLogo;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        initViews();
        new Handler().postDelayed(() -> {
            SharedPreferences shared = getSharedPreferences("Record", MODE_PRIVATE);
            String boarding_view = (shared.getString("boarding_view", ""));
            if (!boarding_view.isEmpty()) {
//                UserModel userNew = (UserModel) Stash.getObject("UserDetails", UserModel.class);
//                if (Constants.auth().getUid()!= null) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                } else {
//                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//
//                }
            } else {
                startActivity(new Intent(SplashActivity.this, OnBoardingActivity.class));
            }
            finish();
        }, SPLASH_TIME);
    }

    private void initViews() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.enlarge_view);
        linearLayout = findViewById(R.id.ll);
        imageViewLogo = findViewById(R.id.imgLogo);
        linearLayout.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(() -> imageViewLogo.setVisibility(View.VISIBLE), 300);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}