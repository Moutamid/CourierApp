package com.moutamid.dantlicorp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.dantlicorp.Admin.Activities.InboxActivity;
import com.moutamid.dantlicorp.Admin.Activities.NotifcationsActivity;
import com.moutamid.dantlicorp.Admin.Video.AllVideo;
import com.moutamid.dantlicorp.R;

public class AdminPanel extends AppCompatActivity {
    Button add_vide_btn, inbox_btn, notification_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        add_vide_btn = findViewById(R.id.btn);
        inbox_btn = findViewById(R.id.inbox_btn);
        notification_btn = findViewById(R.id.notification_btn);
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