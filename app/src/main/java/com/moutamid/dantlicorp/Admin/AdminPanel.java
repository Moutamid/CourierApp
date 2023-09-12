package com.moutamid.dantlicorp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.dantlicorp.Activities.Home.AllUserLocationActivity;
import com.moutamid.dantlicorp.Admin.Activities.EmployeeActivity;
import com.moutamid.dantlicorp.Admin.Activities.InboxActivity;
import com.moutamid.dantlicorp.Admin.Activities.NotifcationsActivity;
import com.moutamid.dantlicorp.Admin.Video.AllVideo;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.ArrayList;

public class AdminPanel extends AppCompatActivity {
    Button add_vide_btn, inbox_btn, notification_btn, employee_btn, show_map;
    ArrayList<UserModel> userArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        employee_btn = findViewById(R.id.employee_btn);
        add_vide_btn = findViewById(R.id.btn);
        inbox_btn = findViewById(R.id.inbox_btn);
        notification_btn = findViewById(R.id.notification_btn);
        show_map = findViewById(R.id.show_map);
        show_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.showProgressDialog(AdminPanel.this);
                Constants.LocationReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            UserModel userModel = ds.getValue(UserModel.class);
                            userArrayList.add(new UserModel(userModel.lat, userModel.lng, userModel.image_url, userModel.name));
                        }
                        Stash.put("AllUserLocation", userArrayList);
                        startActivity(new Intent(AdminPanel.this, AllUserLocationActivity.class));
                        Config.dismissProgressDialog();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        employee_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this, EmployeeActivity.class));
            }
        });

        add_vide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this, AllVideo.class));
            }
        });
        inbox_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this, InboxActivity.class));
            }
        });
        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this, NotifcationsActivity.class));
            }
        });
    }
}