package com.moutamid.dantlicorp.Activities.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.dantlicorp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


public class VideoPlayActivity extends AppCompatActivity {
    String video_id = "Hce74cEAAaE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        final YouTubePlayerView youTubePlayerView = findViewById(R.id.videoPlayer);
//        youTubePlayerView.enterFullScreen();
//        youTubePlayerView.toggleFullScreen();
        getLifecycle().addObserver(youTubePlayerView);
//        youTubePlayerView.getPlayerUiController();
        youTubePlayerView.enterFullScreen();
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