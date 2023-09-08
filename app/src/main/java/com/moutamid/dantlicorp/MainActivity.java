package com.moutamid.dantlicorp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;
import com.moutamid.dantlicorp.Fragments.ChatFragment;
import com.moutamid.dantlicorp.Fragments.HomeFragment;
import com.moutamid.dantlicorp.Fragments.ProfileFragment;
import com.moutamid.dantlicorp.Fragments.VideosFragment;
import com.moutamid.dantlicorp.helper.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {
    SmoothBottomBar bottomBar;
    String type, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomBar = findViewById(R.id.bottomBar);
        replaceFragment(new HomeFragment());

        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                if (i == 0) {
                    replaceFragment(new HomeFragment());

                } else if (i == 1) {
                    replaceFragment(new ChatFragment());

                } else if (i == 2) {
                    replaceFragment(new VideosFragment());

                } else if (i == 3) {
                    replaceFragment(new ProfileFragment());

                }
                return false;
            }
        });
        FirebaseMessaging.getInstance().subscribeToTopic("general");
        type = getIntent().getStringExtra("type");
        data = getIntent().getStringExtra("data");
        if (type != null) {
            if (type.equals("url")) {
                String url = data;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        }
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}