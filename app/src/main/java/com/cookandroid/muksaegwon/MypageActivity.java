package com.cookandroid.muksaegwon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MypageActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1000;

    ImageView backBtn, profile;
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
        profile = (ImageView)findViewById(R.id.profile);

        preferences = getApplicationContext().getSharedPreferences("userInfo",MODE_PRIVATE);
        String name = preferences.getString("name","");
        String email = preferences.getString("email","");

        new DownloadImageTask(profile)
                .execute("https://lh3.googleusercontent.com/a-/AOh14Gg_QB13iJ8aYHge5SjaCTd9Jg3csmTcdDRf7DcX=s576-p-no");


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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            Glide.with(getApplicationContext()).load(result).circleCrop().into(bmImage);
//            bmImage.setImageBitmap(result);
        }
    }

}