package com.cookandroid.muksaegwon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class MypageActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1000;

    ImageView backBtn;
    TextView nameTv, emailTv;
    LinearLayout heart,review;
    private GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        backBtn = (ImageView) findViewById(R.id.btn_back5);
        emailTv = (TextView) findViewById(R.id.EmailTv);
        nameTv = (TextView)findViewById(R.id.nameTv);
        review = (LinearLayout) findViewById(R.id.reviewBtn);
        heart = (LinearLayout) findViewById(R.id.heartBtn);

        preferences = getApplicationContext().getSharedPreferences("userInfo",MODE_PRIVATE);
        String name = preferences.getString("name","");
        String email = preferences.getString("email","");
        nameTv.setText(name);
        emailTv.setText(email);


        // 뒤로가기
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //찜 페이지 액티비티 열기
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                startActivity(intent);
            }
        });

        //리뷰 페이지 액티비티 열기
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                startActivity(intent);
            }
        });
    }
}