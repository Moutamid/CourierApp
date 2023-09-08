package com.moutamid.dantlicorp.Admin.Activities;

import static com.moutamid.dantlicorp.helper.Constants.NOTIFICATIONAPIURL;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.moutamid.dantlicorp.Model.NotificationModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class NotifcationsActivity extends AppCompatActivity {
    EditText edt_message;
    EditText call_url;
    TextView send_notification;
    RadioButton url;
    RadioButton simple;
    String type;
    String s;
    ProgressBar progress_bar;
    NotificationModel notificationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifcations);
        url = findViewById(R.id.url);
        simple = findViewById(R.id.simple);
        edt_message = findViewById(R.id.edt_message);
        call_url = findViewById(R.id.call_url);
        send_notification = findViewById(R.id.send_notification);
        progress_bar = findViewById(R.id.progress_bar);
        type = "simple";
        notificationModel = new NotificationModel();
        simple.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    call_url.setVisibility(View.GONE);
                    type = "simple";
                }
            }
        });
        url.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    call_url.setVisibility(View.VISIBLE);
                    type = "url";
                }
            }
        });


        send_notification.setOnClickListener(view -> {
            if (edt_message.getText().toString().isEmpty()) {
                edt_message.setError("Please enter message!");
            } else if (type.equals("url") ) {
                if (call_url.getText().toString().isEmpty()) {
                    call_url.setError("Please Enter");
                } else {
                    s = call_url.getText().toString();
                    sendFCMPush(edt_message.getText().toString() + " " + call_url.getText().toString());

                }
            } else {
                s = "";
                sendFCMPush(edt_message.getText().toString());

            }
        });
    }


    private void sendFCMPush(String message) {
        progress_bar.setVisibility(View.VISIBLE);
        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", "DANTLI CORP");
            notifcationBody.put("message", message);
            notifcationBody.put("type", type);
            notifcationBody.put("data", s);
            notification.put("to", "/topics/" + "general");
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, NOTIFICATIONAPIURL, notification,
                response -> {
                    Log.e("True", response + "");
                    Log.d("Responce", response.toString());
                    progress_bar.setVisibility(View.GONE);
                    Toast.makeText(NotifcationsActivity.this, "Successfully send a notification", Toast.LENGTH_SHORT).show();

                },
                error -> {
                    progress_bar.setVisibility(View.GONE);
                    Log.e("False", error.getMessage() + "  " + error.toString() + "");
                    Toast.makeText(NotifcationsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "key=" + Constants.ServerKey);
//                params.put("Content-Type", "application/json");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        int socketTimeout = 1000 * 60;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }
}