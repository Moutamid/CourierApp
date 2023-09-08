package com.moutamid.dantlicorp.Admin.Video;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.dantlicorp.Admin.Adapter.VideoAdapter;
import com.moutamid.dantlicorp.Model.VideoModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.ArrayList;
import java.util.List;

public class AllVideo extends AppCompatActivity {

    RecyclerView content_rcv;
    public List<VideoModel> videoModelList = new ArrayList<>();
    VideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_videos);
        content_rcv = findViewById(R.id.content_rcv);
        content_rcv.setLayoutManager(new LinearLayoutManager(this));
        videoAdapter = new VideoAdapter(this, videoModelList);
        content_rcv.setAdapter(videoAdapter);
        if (Config.isNetworkAvailable(AllVideo.this)) {
            getHerbs();
        } else {
            Toast.makeText(AllVideo.this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }

    }

    public void add_details(View view) {
        Intent intent = new Intent(this, AddVideo.class);
        intent.putExtra("url", "");
        intent.putExtra("thumbnail", "");
        intent.putExtra("key", "");
        startActivity(intent);
    }

    private void getHerbs() {
//        Config.showProgressDialog(AllVideo.this);
        Constants.VideosReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                videoModelList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    VideoModel videoModel = ds.getValue(VideoModel.class);
                    videoModelList.add(videoModel);
                }
                videoAdapter.notifyDataSetChanged();
//                Config.dismissProgressDialog();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Config.dismissProgressDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Config.isNetworkAvailable(AllVideo.this)) {
            getHerbs();
        } else {
            Toast.makeText(AllVideo.this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
    }
}