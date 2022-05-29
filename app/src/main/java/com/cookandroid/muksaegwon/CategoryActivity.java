package com.cookandroid.muksaegwon;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CategoryActivity extends AppCompatActivity {
    ImageView corn, fish, red, ggochi, sweet, sand,octopus, wa, sso;
    Intent intent;
    String cateogry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        double lat = getIntent().getDoubleExtra("lat",0);
        double lng = getIntent().getDoubleExtra("lng",0);

        corn = (ImageView) findViewById(R.id.corn);
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

        fish = (ImageView) findViewById(R.id.fish);
        fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        red = (ImageView) findViewById(R.id.red);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ggochi = (ImageView) findViewById(R.id.ggochi);
        ggochi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sweet = (ImageView) findViewById(R.id.sweet);
        sweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sand = (ImageView) findViewById(R.id.sand);
        sand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        octopus = (ImageView) findViewById(R.id.octopus);
        octopus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        wa = (ImageView) findViewById(R.id.wa);
        wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sso = (ImageView) findViewById(R.id.sso);
        sso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}