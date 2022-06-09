package com.cookandroid.muksaegwon;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cookandroid.muksaegwon.adapter.ReviewAdapter;
import com.cookandroid.muksaegwon.controller.MsgXmlParser;
import com.cookandroid.muksaegwon.model.Review;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {
    RecyclerView reviewRecyclerView;
    ReviewAdapter reviewAdapter;
    ArrayList<Review> reviews;
    MsgXmlParser msgXmlParser;

    SharedPreferences preferences;
    String userId;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = preferences.getString("userId","");

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        reviewRecyclerView = (RecyclerView) findViewById(R.id.reviewRecyclerView);

        reviews = new ArrayList<Review>();
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/reviewFromMember.jsp?uId="+userId;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.i("RESPONSE: ",response);
                        msgXmlParser = new MsgXmlParser(response);
                        msgXmlParser.xmlParsingRFM(reviews);

                        reviewAdapter = new ReviewAdapter(reviews);
                        reviewRecyclerView.setAdapter(reviewAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);


        //뒤로가기 버튼 누르면 ReviewActivity전환
        ImageView imageView = (ImageView) findViewById(R.id.btn_back5);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        }
    }
