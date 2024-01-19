package com.moutamid.dantlicorp.Admin;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.moutamid.dantlicorp.Activities.Authentication.LoginActivity;
import com.moutamid.dantlicorp.Activities.Home.AllUserLocationActivity;
import com.moutamid.dantlicorp.Admin.Activities.EmployeeActivity;
import com.moutamid.dantlicorp.Admin.Activities.InboxActivity;
import com.moutamid.dantlicorp.Admin.Activities.NotifcationsActivity;
import com.moutamid.dantlicorp.Admin.Video.AllVideo;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.ArrayList;
import java.util.Objects;

public class AdminPanel extends AppCompatActivity {
    CardView add_vide_btn, inbox_btn, notification_btn, show_map;
    ArrayList<UserModel> userArrayList = new ArrayList<>();
    CardView employee_btn, logout;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        employee_btn = findViewById(R.id.employee_btn);
        add_vide_btn = findViewById(R.id.btn);
        inbox_btn = findViewById(R.id.inbox_btn);
        notification_btn = findViewById(R.id.notification_btn);
        show_map = findViewById(R.id.show_map);
        logout = findViewById(R.id.logout);
        mDatabase = FirebaseDatabase.getInstance().getReference("DantliCorp").child("Admin");

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                String result = task.getResult();
                mDatabase.child("token").setValue(result);

            }
        });
        show_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog lodingbar = new Dialog(AdminPanel.this);
                lodingbar.setContentView(R.layout.loading);
                Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
                lodingbar.setCancelable(false);
                lodingbar.show();                Constants.LocationReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            UserModel userModel = ds.getValue(UserModel.class);
                            userArrayList.add(new UserModel(ds.getKey(), userModel.lat, userModel.lng, userModel.image_url, userModel.name));
                        }
                        Stash.put("AllUserLocation", userArrayList);
                        startActivity(new Intent(AdminPanel.this, AllUserLocationActivity.class));
lodingbar.dismiss();                    }

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
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stash.put("admin_login", 0);
                startActivity(new Intent(AdminPanel.this, LoginActivity.class));
                finishAffinity();
            }
        });
    }
}