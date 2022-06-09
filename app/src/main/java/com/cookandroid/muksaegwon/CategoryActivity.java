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

    double left;
    double bottom;
    double right;
    double top;
    private double lat;
    private double lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        cateBackBtn = (ImageView) findViewById(R.id.btn_back5);
        fish = (ImageView) findViewById(R.id.fish);
        red = (ImageView) findViewById(R.id.red);
        ggochi = (ImageView) findViewById(R.id.ggochi);
        sweet = (ImageView) findViewById(R.id.sweet);
        sand = (ImageView) findViewById(R.id.sand);
        wa = (ImageView) findViewById(R.id.wa);
        octopus = (ImageView) findViewById(R.id.octopus);
        sso = (ImageView) findViewById(R.id.sso);
        corn = (ImageView) findViewById(R.id.corn);

        lat = getIntent().getDoubleExtra("lat",0);
        lng = getIntent().getDoubleExtra("lng",0);
        left = getIntent().getDoubleExtra("left",0);
        bottom = getIntent().getDoubleExtra("bottom",0);
        right  = getIntent().getDoubleExtra("right",0);
        top = getIntent().getDoubleExtra("top",0);


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
                openCategoryMap(intent,"corn");
            }
        });

        fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                openCategoryMap(intent,"fish");
            }
        });

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                openCategoryMap(intent,"topokki");
            }
        });

        ggochi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                openCategoryMap(intent,"eomuk");
            }
        });

        sweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                openCategoryMap(intent,"sweetpotato");
            }
        });

        sand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                intent.putExtra("category", "toast");
                openCategoryMap(intent,"toast");
            }
        });

        octopus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                openCategoryMap(intent,"takoyaki");
            }
        });

        wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                openCategoryMap(intent,"waffle");
            }
        });

        sso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),CategoryMapActivity.class);
                openCategoryMap(intent,"dakggochi");
            }
        });
    }

    public void openCategoryMap(Intent intent, String category){
        intent.putExtra("category", category);
        intent.putExtra("lat",lat);
        intent.putExtra("lng",lng);
        intent.putExtra("left",left);
        intent.putExtra("bottom",bottom);
        intent.putExtra("right",right);
        intent.putExtra("top",top);
        startActivity(intent);
    }
}