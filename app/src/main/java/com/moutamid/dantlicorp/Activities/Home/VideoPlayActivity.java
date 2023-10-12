package com.moutamid.dantlicorp.Activities.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
//                if (state.equals(state))
//                {
//                    Uri webpage = Uri.parse("https://docs.google.com/document/d/1DJz-8OCkQUuxLtROm-l7hF2M0Bbmqrx4WQaKs8K4a_4/edit?usp=sharing");
//                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
////                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
//                    startActivity(intent);
////                 Toast.makeText(VideoPlayActivity.this, "yes", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}