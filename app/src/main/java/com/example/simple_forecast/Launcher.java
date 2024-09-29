package com.example.simple_forecast;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Launcher extends AppCompatActivity {

    AnimationDrawable drawable;
    ImageView viewAnimation;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_launch);

        String intentText = getIntent().getStringExtra("text_reset");
        text = findViewById(R.id.launchText);
        text.setText(intentText == null || intentText.isEmpty() ? "" : intentText);

        viewAnimation = findViewById(R.id.viewAnimation);
        viewAnimation.setBackgroundResource(R.drawable.animation_sequence);
        drawable = (AnimationDrawable) viewAnimation.getBackground();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity( new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        }, 1600);

    }

    @Override
    protected void onStart() {
        super.onStart();

        drawable.start();
    }
}