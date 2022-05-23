package com.cookandroid.muksaegwon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);



        reviewRecyclerView = (RecyclerView) findViewById(R.id.reviewRecyclerView);

        reviews = new ArrayList<Review>();
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        String url = "http://172.111.113.13:8080/MukSaeGwonServer/reviewFromMember.jsp?uId=1000";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
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
    }
}