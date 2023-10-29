package com.moutamid.dantlicorp.Dailogues;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moutamid.dantlicorp.R;

public class SignDailogClass extends Dialog {

    public Context c;
    String name;
    ImageView signature, imgBack;

    public SignDailogClass(Context a, String name) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.name = name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.sign_dailogue);
        imgBack = findViewById(R.id.imgBack);
        signature = findViewById(R.id.signature);

        byte[] decodedString = Base64.decode(name, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
        signature.setImageBitmap(decodedByte);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


}