package com.moutamid.dantlicorp.Activities.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.dantlicorp.MainActivity;
import com.moutamid.dantlicorp.R;

public class GetSocialLinksActivity extends AppCompatActivity {
    Button continue_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_social_links);
        continue_btn = findViewById(R.id.next);
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetSocialLinksActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}