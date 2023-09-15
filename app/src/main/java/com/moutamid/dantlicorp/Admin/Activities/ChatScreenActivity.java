package com.moutamid.dantlicorp.Admin.Activities;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fxn.stash.Stash;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.moutamid.dantlicorp.Admin.Adapter.AdminChatAdapter;
import com.moutamid.dantlicorp.Model.ChatModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ChatScreenActivity extends AppCompatActivity {
    ArrayList<ChatModel> list;
    TextView chatName;
    RecyclerView recyler;
    ImageView back;
    ImageButton send;
    EditText message;
    String ID, userName, usertoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        chatName = findViewById(R.id.chatName);
        recyler = findViewById(R.id.recyler);
        back = findViewById(R.id.back);
        send = findViewById(R.id.send);
        message = findViewById(R.id.message);
        ID = Stash.getString("userID");
        userName = Stash.getString("userName");
        usertoken = Stash.getString("usertoken");

        chatName.setText(userName);

        list = new ArrayList<>();


        recyler.setLayoutManager(new LinearLayoutManager(this));
        recyler.setHasFixedSize(false);

//        Constants.databaseReference().child(Constants.USER).child(   "admin123")
//                .get().addOnSuccessListener(dataSnapshot -> {
//                    if (dataSnapshot.exists()){
//                        loginUser = dataSnapshot.getValue(UserModel.class);
//                    }
//                });

        getChat(ID);

        send.setOnClickListener(v -> {
            if (!message.getText().toString().isEmpty()) {
                String message_str = message.getText().toString();
                message.setText("");
                long date = new Date().getTime();
                ChatModel conversation = new ChatModel(
                        message_str,
                        "admin123",
                        ID,
                        date,
                        userName
                );
                Constants.ChatReference.child("admin123")
                        .child(ID)
                        .push()
                        .setValue(conversation)
                        .addOnSuccessListener(unused -> {
                            reciver(ID, date, message_str);
                        }).addOnFailureListener(e -> {

                        });
            }
        });
        
    }

    private void reciver(String ID, long date, String message_str) {
        ChatModel conversation = new ChatModel(
                message_str,
                "admin123",
                ID,
                date,
                "Admin"
        );
        Constants.ChatReference.child(ID)
                .child("admin123")
                .push()
                .setValue(conversation)
                .addOnSuccessListener(unused -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", userName);
                    map.put("message", message_str);
                    map.put("timeStamp", date);
                    Constants.ChatListReference.child(ID).child("admin123").updateChildren(map)
                            .addOnSuccessListener(unused1 -> {
                                Constants.ChatListReference.child("admin123").child(ID).updateChildren(map)
                                        .addOnSuccessListener(unused4 -> {
                                            sendFCMPush(usertoken);
                                        });
                            });
                }).addOnFailureListener(e -> {

                });
    }

    private void getChat(String id) {
        Constants.ChatReference.child("admin123")
                .child(id)
                .addChildEventListener(new ChildEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (snapshot.exists()) {
                            ChatModel conversation = snapshot.getValue(ChatModel.class);
                            list.add(conversation);
                            list.sort(Comparator.comparing(ChatModel::getTimestamps));
                            AdminChatAdapter adapter = new AdminChatAdapter(getApplicationContext(), list);
                            recyler.setAdapter(adapter);
                            recyler.scrollToPosition(list.size() - 1);
                            adapter.notifyItemInserted(list.size() - 1);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (snapshot.exists()) {

                        }
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                        }
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (snapshot.exists()) {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void sendFCMPush(String token) {
        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", "Admin");
            notifcationBody.put("message", "Send a new message");
            notification.put("to", token);
            notification.put("data", notifcationBody);
            Log.e("DATAAAAAA", notification.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Constants.NOTIFICATIONAPIURL, notification,
                response -> {
                    Log.e("True", response + "");
//                    Toast.makeText(BookingActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("Responce", response.toString());
                },
                error -> {
                    Log.e("False", error + "");
                    Toast.makeText(ChatScreenActivity.this, "error", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "key=" + Constants.ServerKey);
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        int socketTimeout = 1000 * 60;// 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }

    public void backPress(View view) {
        onBackPressed();
    }
}