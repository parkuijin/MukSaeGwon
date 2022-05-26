package com.cookandroid.muksaegwon;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class IntroActivity extends AppCompatActivity {
    public static Activity activity;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        image=findViewById(R.id.imageView);
        Glide.with(this).load(R.raw.intro_gif).into(image);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        activity = this;
    }
}