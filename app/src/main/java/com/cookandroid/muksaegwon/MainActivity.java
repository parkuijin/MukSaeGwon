package com.cookandroid.muksaegwon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    // MapFragment
    MapFragment mapFragment;
    FragmentManager fragmentManager;

    // Button
    ImageView CategoryButton, RegisterButton, MapButton, MypageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // ActionBar hide
                ActionBar actionBar = getSupportActionBar();
                actionBar.hide();

                mapFragment = new

                        MapFragment();

                fragmentManager =

                        getSupportFragmentManager();

                // Google Map 프래그먼트 출력
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.Container, mapFragment, null);
                fragmentTransaction.commit();

                // 카테고리 액티비티 열기
                CategoryButton = (ImageView)

                        findViewById(R.id.CategoryButton);
                CategoryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                        startActivity(intent);
                    }
                });

                // 로그인 액티비티 열기
                MypageButton = (ImageView)

                        findViewById(R.id.MypageButton);
                MypageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                        startActivity(intent);
                    }
                });

                // 가게 등록 액티비티 열기
                RegisterButton = (ImageView)

                        findViewById(R.id.RegisterButton);
                RegisterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(intent);
                    }
                });

                // 테스트용 가게 정보 액티비티 열기
                MapButton = (ImageView)

                        findViewById(R.id.MapButton);
                MapButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), InfoStoreActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }, 1);
    }
}
