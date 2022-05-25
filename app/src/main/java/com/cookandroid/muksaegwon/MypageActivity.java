package com.cookandroid.muksaegwon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MypageActivity extends AppCompatActivity {
    ImageView setting,heart,review;
    // 주석

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        // 셋팅 액티비티 열기
        /*
        setting = (ImageView) findViewById(R.id.settingBtn);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
        */

        //찜 페이지 액티비티 열기

        heart = (ImageView) findViewById(R.id.heartBtn);
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FavoriteActivity.class);
                startActivity(intent);
            }
        });



        //리뷰 페이지 액티비티 열기
        review = (ImageView) findViewById(R.id.reviewBtn);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                startActivity(intent);
            }
        });





    }
}