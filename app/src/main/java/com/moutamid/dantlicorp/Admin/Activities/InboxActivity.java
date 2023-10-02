package com.moutamid.dantlicorp.Admin.Activities;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.dantlicorp.Activities.Home.EditProfileActivity;
import com.moutamid.dantlicorp.Adapter.ChatListAdapter;
import com.moutamid.dantlicorp.Model.ChatListModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.ArrayList;
import java.util.Objects;

public class InboxActivity extends AppCompatActivity {
    ArrayList<ChatListModel> list;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(InboxActivity.this));
        recyclerView.setHasFixedSize(false);

        list = new ArrayList<>();

        getData();

    }
    private void getData() {
        Dialog lodingbar = new Dialog(InboxActivity.this);
        lodingbar.setContentView(R.layout.loading);
        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        lodingbar.setCancelable(false);
        lodingbar.show();

        Constants.ChatListReference.child("admin123")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            list.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                ChatListModel model = dataSnapshot.getValue(ChatListModel.class);
                                list.add(model);
                            }

                            Log.d("listSize", "ee : "+ snapshot.getChildrenCount());

                        }
lodingbar.dismiss();                        ChatListAdapter adapter = new ChatListAdapter(InboxActivity.this, list);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
lodingbar.dismiss();                        Toast.makeText(InboxActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}