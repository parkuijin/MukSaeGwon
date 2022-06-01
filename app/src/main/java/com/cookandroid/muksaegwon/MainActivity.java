package com.cookandroid.muksaegwon;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity{

    public static Activity activity;

    // MapFragment
    MapFragment mapFragment;
    FragmentManager fragmentManager;

    // Button
    ImageView RegisterButton, MapButton, MypageButton;

    ActivityResultLauncher<Intent> startActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                    }
                }
            });

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
        startActivityLauncher.launch(intent);

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
//                CategoryButton = (ImageView)
//
//                        findViewById(R.id.CategoryButton);
//                CategoryButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
//                        startActivity(intent);
//                    }
//                });

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
