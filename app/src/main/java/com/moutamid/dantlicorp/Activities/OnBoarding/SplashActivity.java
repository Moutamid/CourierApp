package com.moutamid.dantlicorp.Activities.OnBoarding;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.moutamid.dantlicorp.Activities.Authentication.LoginActivity;
import com.moutamid.dantlicorp.Admin.AdminPanel;
import com.moutamid.dantlicorp.Dailogues.ChecksDialogClass;
import com.moutamid.dantlicorp.Dailogues.OpenAppDialogClass;
import com.moutamid.dantlicorp.MainActivity;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    ImageView imageViewLogo;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        if(!Stash.getString("language").isEmpty())
        {
            setLocale(Stash.getString("language"));
        }
        imageViewLogo = findViewById(R.id.imageView);

//        linearLayout = findViewById(R.id.ll);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageViewLogo, "y", -190);

        objectAnimator.setDuration(1500);

        AnimatorSet animatorSet=new AnimatorSet();

        animatorSet.playTogether(objectAnimator);

        objectAnimator.start();

        int splashInterval = 1700;

        new Handler().postDelayed(this::goToApp, splashInterval);


    }

    public void goToApp() {
        int adminLogin = Stash.getInt("admin_login");
        if (adminLogin != 1) {
            SharedPreferences shared = getSharedPreferences("Record", MODE_PRIVATE);
            String boarding_view = (shared.getString("boarding_view", ""));
            if (!boarding_view.isEmpty()) {
                UserModel userNew = (UserModel) Stash.getObject("UserDetails", UserModel.class);
                if (userNew != null) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            } else {
                OpenAppDialogClass cdd = new OpenAppDialogClass(SplashActivity.this);
                cdd.show();
            }
        } else {
            startActivity(new Intent(SplashActivity.this, AdminPanel.class));
            finish();
        }
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }

}