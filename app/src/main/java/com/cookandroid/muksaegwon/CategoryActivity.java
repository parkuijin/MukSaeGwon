package com.cookandroid.muksaegwon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {

    ImageView corn, fish, red, ggochi, sweet, sand,octopus, wa, sso;
    Intent intent;
    ImageView cateBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        cateBackBtn = (ImageView) findViewById(R.id.btn_back4);
        fish = (ImageView) findViewById(R.id.fish);
        red = (ImageView) findViewById(R.id.red);
        ggochi = (ImageView) findViewById(R.id.ggochi);
        sweet = (ImageView) findViewById(R.id.sweet);
        sand = (ImageView) findViewById(R.id.sand);
        wa = (ImageView) findViewById(R.id.wa);
        octopus = (ImageView) findViewById(R.id.octopus);
        sso = (ImageView) findViewById(R.id.sso);
        corn = (ImageView) findViewById(R.id.corn);

        double lat = getIntent().getDoubleExtra("lat",0);
        double lng = getIntent().getDoubleExtra("lng",0);

        cateBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        corn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                intent.putExtra("category", "corn");
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                startActivity(intent);
            }
        });

        fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                intent.putExtra("category", "fish");
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                startActivity(intent);
            }
        });

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                intent.putExtra("category", "topokki");
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                startActivity(intent);
            }
        });

        ggochi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                intent.putExtra("category", "eomuk");
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                startActivity(intent);
            }
        });

        sweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                intent.putExtra("category", "sweetpotato");
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                startActivity(intent);
            }
        });

        sand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                intent.putExtra("category", "toast");
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                startActivity(intent);
            }
        });

        octopus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                intent.putExtra("category", "takoyaki");
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                startActivity(intent);
            }
        });

        wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                intent.putExtra("category", "waffle");
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                startActivity(intent);
            }
        });

        sso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                intent.putExtra("category", "dakggochi");
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                startActivity(intent);
            }
        });
    }
}