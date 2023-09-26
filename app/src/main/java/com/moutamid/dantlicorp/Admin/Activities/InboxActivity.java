package com.moutamid.dantlicorp.Admin.Activities;

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
import com.moutamid.dantlicorp.Adapter.ChatListAdapter;
import com.moutamid.dantlicorp.Model.ChatListModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.ArrayList;

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
        Config.showProgressDialog(InboxActivity.this);
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
                        Config.dismissProgressDialog();
                        ChatListAdapter adapter = new ChatListAdapter(InboxActivity.this, list);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Config.dismissProgressDialog();
                        Toast.makeText(InboxActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}