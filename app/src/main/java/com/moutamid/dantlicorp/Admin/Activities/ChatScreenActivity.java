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
import com.moutamid.dantlicorp.Admin.Adapter.AdminChatAdapter;
import com.moutamid.dantlicorp.Model.ChatModel;
import com.moutamid.dantlicorp.Model.UserModel;
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
    String ID, userName ;

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
}