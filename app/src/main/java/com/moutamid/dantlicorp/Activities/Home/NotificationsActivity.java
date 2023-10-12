package com.moutamid.dantlicorp.Activities.Home;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.moutamid.dantlicorp.Adapter.NotificationAdapter;
import com.moutamid.dantlicorp.Model.NotificationModel;
import com.moutamid.dantlicorp.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    RecyclerView content_rcv;
    public List<NotificationModel> notificationModelList = new ArrayList<>();
    NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notifications);
        content_rcv = findViewById(R.id.content_rcv);
        content_rcv.setLayoutManager(new LinearLayoutManager(this));

//       ArrayList<NotificationModel> notification =  Stash.getArrayList("Notification", NotificationModel.class);
//        Toast.makeText(this, "data "+notification.toString(), Toast.LENGTH_SHORT).show();     notificationAdapter = new NotificationAdapter(this, notification);
//        content_rcv.setAdapter(notificationAdapter);
//
    }

}