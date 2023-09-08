package com.moutamid.dantlicorp.Admin.Activities;

import static com.moutamid.dantlicorp.helper.Constants.ADMIN_UID;

import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.moutamid.dantlicorp.Activities.Adapter.ChatAdapter;
import com.moutamid.dantlicorp.Model.ChatModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Constants;

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
    String receiver_ID=ADMIN_UID;
    String name;
    String ID;


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
        name = Stash.getString("userName");
        chatName.setText(name);
        list = new ArrayList<>();
        back.setOnClickListener(v -> {
            finish();
        });


        recyler.setLayoutManager(new LinearLayoutManager(this));
        recyler.setHasFixedSize(false);

//        Constants.databaseReference().child(Constants.USER).child(receiver_ID)
//                .get().addOnSuccessListener(dataSnapshot -> {
//                    if (dataSnapshot.exists()){
//                        loginUser = dataSnapshot.getValue(UserModel.class);
//                    }
//                });

        getChat(ID);

        send.setOnClickListener(v -> {
            if (!message.getText().toString().isEmpty()) {
                long date = new Date().getTime();
                ChatModel conversation = new ChatModel(
                        message.getText().toString(),
                        receiver_ID,
                        ID,
                        date,
                        name
                );
                Constants.ChatReference.child(receiver_ID)
                        .child(ID)
                        .push()
                        .setValue(conversation)
                        .addOnSuccessListener(unused -> {
                            reciver(ID, date);
                        }).addOnFailureListener(e -> {

                        });
            }
        });

    }

    private void reciver(String ID, long date) {
        ChatModel conversation = new ChatModel(
                message.getText().toString(),
                receiver_ID,
                ID,
                date,
                "Admin"
        );
        Constants.ChatReference.child(ID)
                .child(receiver_ID)
                .push()
                .setValue(conversation)
                .addOnSuccessListener(unused -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", "Admin");
                    map.put("message", message.getText().toString());
                    map.put("timeStamp", date);
                    message.setText("");
                    Constants.ChatListReference.child(ID).child(receiver_ID).updateChildren(map)
                            .addOnSuccessListener(unused1 -> {
                                Constants.ChatListReference.child(receiver_ID).child(ID).updateChildren(map)
                                        .addOnSuccessListener(unused4 -> {
//                                            new FcmNotificationsSender(
//                                                    "/topics/" + ID, "Fiza",
//                                                    map.get("message").toString(), getApplicationContext(),
//                                                    ChatScreenActivity.this).SendNotifications();
                                        });
                            });
                }).addOnFailureListener(e -> {

                });
    }

    private void getChat(String id) {
        Constants.ChatReference.child(receiver_ID)
                .child(id)
                .addChildEventListener(new ChildEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (snapshot.exists()) {
                            ChatModel conversation = snapshot.getValue(ChatModel.class);
                            list.add(conversation);
                            list.sort(Comparator.comparing(ChatModel::getTimestamps));
                            ChatAdapter adapter = new ChatAdapter(ChatScreenActivity.this, list);
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
}