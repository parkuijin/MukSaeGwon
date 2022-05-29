package com.cookandroid.muksaegwon;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class IntroActivity extends AppCompatActivity {
    public static Activity activity;
    ImageView image;
    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        image=findViewById(R.id.imageView);
        Glide.with(this).load(R.raw.intro_gif).into(image);

        activity = this;

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


    }
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            MainActivity mainActivity = (MainActivity) MainActivity.activity;
            mainActivity.finish();
            finish();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}