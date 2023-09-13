package com.moutamid.dantlicorp.Fragments;

import static com.moutamid.dantlicorp.helper.Constants.ADMIN_UID;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.moutamid.dantlicorp.Adapter.ChatAdapter;
import com.moutamid.dantlicorp.Model.ChatModel;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatFragment extends Fragment {
    ArrayList<ChatModel> list;
    TextView chatName;
    RecyclerView recyler;
    ImageView back;
    ImageButton send;
    EditText message;
    String ID = ADMIN_UID;
UserModel userModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatName = view.findViewById(R.id.chatName);
        recyler = view.findViewById(R.id.recyler);
        back = view.findViewById(R.id.back);
        send = view.findViewById(R.id.send);
        message = view.findViewById(R.id.message);
        String name = "Chat with Admin";
        chatName.setText(name);
        userModel= (UserModel) Stash.getObject("UserDetails", UserModel.class);

        list = new ArrayList<>();


        recyler.setLayoutManager(new LinearLayoutManager(getContext()));
        recyler.setHasFixedSize(false);

//        Constants.databaseReference().child(Constants.USER).child(   Constants.auth().getUid())
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
                        Constants.auth().getUid(),
                        ID,
                        date,
                        userModel.name
                );
                Constants.ChatReference.child(Constants.auth().getUid())
                        .child(ID)
                        .push()
                        .setValue(conversation)
                        .addOnSuccessListener(unused -> {
                            reciver(ID, date, message_str);
                        }).addOnFailureListener(e -> {

                        });
            }
        });
        return view;
    }

    private void reciver(String ID, long date, String message_str) {
        ChatModel conversation = new ChatModel(
                message_str,
                   Constants.auth().getUid(),
                ID,
                date,
                "Admin"
        );
        Constants.ChatReference.child(ID)
                .child(   Constants.auth().getUid())
                .push()
                .setValue(conversation)
                .addOnSuccessListener(unused -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", userModel.name);
                    map.put("chat_id", userModel.id);
                    map.put("message", message_str);
                    map.put("timeStamp", date);
                    map.put("image_url", userModel.image_url);
                    map.put("token", Stash.getString("token"));
                    Constants.ChatListReference.child(ID).child(   Constants.auth().getUid()).updateChildren(map)
                            .addOnSuccessListener(unused1 -> {
                                Constants.ChatListReference.child(   Constants.auth().getUid()).child(ID).updateChildren(map)
                                        .addOnSuccessListener(unused4 -> {
//                                            new FcmNotificationsSender(
//                                                    "/topics/" + ID, userModel.name,
//                                                    map.get("message").toString(), getApplicationContext(),
//                                                    getActivity()).SendNotifications();
                                        });
                            });
                }).addOnFailureListener(e -> {

                });
    }

    private void getChat(String id) {
        Constants.ChatReference.child(   Constants.auth().getUid())
                .child(id)
                .addChildEventListener(new ChildEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (snapshot.exists()) {
                            ChatModel conversation = snapshot.getValue(ChatModel.class);
                            list.add(conversation);
                            list.sort(Comparator.comparing(ChatModel::getTimestamps));
                            ChatAdapter adapter = new ChatAdapter(getContext(), list);
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