package com.moutamid.dantlicorp.Activities.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.dantlicorp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


public class VideoPlayActivity extends AppCompatActivity {
    String video_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        video_id = getIntent().getStringExtra("url");
        Log.d("data", video_id + "");
        video_id = video_id.substring(32, 43);
        Log.d("data", video_id + "");
        final YouTubePlayerView youTubePlayerView = findViewById(R.id.videoPlayer);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.getPlayerUiController();
//        youTubePlayerView.enterFullScreen();
//        youTubePlayerView.toggleFullScreen();
//        youTubePlayerView.getPlayerUiController();
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(video_id, 0);
            }

            @Override
            public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
                // this method is called if video has ended,
                super.onStateChange(youTubePlayer, state);

            }
        });
    }
}