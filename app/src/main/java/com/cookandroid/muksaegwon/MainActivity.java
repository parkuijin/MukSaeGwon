package com.cookandroid.muksaegwon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cookandroid.muksaegwon.controller.BackKeyHandler;

public class MainActivity extends AppCompatActivity {

    public static Activity activity;
    private BackKeyHandler backKeyHandler = new BackKeyHandler(this);

    // MapFragment
    MapFragment mapFragment;
    FragmentManager fragmentManager;

    // Button
    ImageView RegisterButton, MapButton, MypageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        // INTRO 실행
        Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mapFragment = new MapFragment();

                fragmentManager = getSupportFragmentManager();

                // Google Map 프래그먼트 출력
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.Container, mapFragment, null);
                fragmentTransaction.commit();

                // 카테고리 액티비티 열기
                /*CategoryButton = (ImageView)

                        findViewById(R.id.CategoryButton);
                CategoryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                        startActivity(intent);
                    }
                });*/


                // 마이페이지 액티비티 열기
                MypageButton = (ImageView) findViewById(R.id.MypageButton);
                MypageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                        startActivity(intent);
                    }
                });

                // 가게 등록 액티비티 열기
                RegisterButton = (ImageView) findViewById(R.id.RegisterButton);
                RegisterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(intent);
                    }
                });

                // 새로 고침 버튼
                MapButton = (ImageView) findViewById(R.id.MapButton);
                MapButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.detach(mapFragment).attach(mapFragment).commit();
                        Toast.makeText(MainActivity.this, "지도가 갱신 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, 1);

    } // onCreate

    @Override
    public void onBackPressed() {
        backKeyHandler.onBackPressed();
    }
}
