package com.moutamid.dantlicorp.Dailogues;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.moutamid.dantlicorp.Activities.OnBoarding.OnBoardingActivity;
import com.moutamid.dantlicorp.R;

public class OpenAppDialogClass extends Dialog {

    public Activity c;
    Button accept;
    Button deny;


    public OpenAppDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.open_app_dailogue);
        accept = findViewById(R.id.accept);
        deny = findViewById(R.id.deny);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.startActivity(new Intent(c, OnBoardingActivity.class));
                dismiss();
            }
        });
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.finishAffinity();
            }
        });
    }


}