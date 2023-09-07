package com.moutamid.dantlicorp;

import android.os.Bundle;
import android.widget.Toast;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {
    public String NOTIFICATIONAPIURL = "https://fcm.googleapis.com/fcm/send";
    SmoothBottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomBar = findViewById(R.id.bottomBar);
        replaceFragment(new HomeFragment());

        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                if(i==0)
                {
                    replaceFragment(new HomeFragment());

                }
                else if (i==1)
                {
                    replaceFragment(new ChatFragment());

                }else if (i==2)
                {
                    replaceFragment(new VideosFragment());

                } else if (i == 3) {
                    replaceFragment(new ProfileFragment());

                }
                return false;
            }
        });
        FirebaseMessaging.getInstance().subscribeToTopic("local");
        sendFCMPush();
    }


    private void sendFCMPush() {
        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", "DANTLI CORP");
            notifcationBody.put("message", "Welcome to the Team");
            notification.put("to", "/topics/" + "local");
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, NOTIFICATIONAPIURL, notification,
                response -> {
                },
                error -> {
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "key=" + "AAAAzzvbhX8:APA91bGavDjgYZn9tdcqZCSxPEZtmvOxUSRbNxSrpakLAvMAZ8uZ5pmaqBxo4AVmpued6aKR-Nwkj8pngfV_yhNvdAytaTh_8wuGcZ-ueTYe90LFF_zgwzVXtEyYLQv42JJae9SWdHC9");
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        int socketTimeout = 1000 * 60;// 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}