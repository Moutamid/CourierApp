package com.moutamid.dantlicorp.Admin.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.dantlicorp.Adapter.ChatListAdapter;
import com.moutamid.dantlicorp.Admin.Adapter.EmployeeListAdapter;
import com.moutamid.dantlicorp.Model.ChatListModel;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.ArrayList;

public class EmployeeActivity extends AppCompatActivity {
    ArrayList<UserModel> list;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(EmployeeActivity.this));
        recyclerView.setHasFixedSize(false);

        list = new ArrayList<>();

        getData();

    }
    private void getData() {
        Config.showProgressDialog(EmployeeActivity.this);
        Constants.UserReference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            list.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                UserModel model = dataSnapshot.getValue(UserModel.class);
                                list.add(model);
                                Log.d("listSize", "ee : "+ model.getId());

                            }


                        }
                        Config.dismissProgressDialog();
                        EmployeeListAdapter adapter = new EmployeeListAdapter(EmployeeActivity.this, list);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Config.dismissProgressDialog();
                        Toast.makeText(EmployeeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}