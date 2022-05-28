package com.cookandroid.muksaegwon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cookandroid.muksaegwon.adapter.FavoriteAdapter;
import com.cookandroid.muksaegwon.controller.MsgXmlParser;
import com.cookandroid.muksaegwon.model.Favorite;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView favoriteRecyclerView;
    FavoriteAdapter favoriteAdapter;
    ArrayList<Favorite> favorites;
    MsgXmlParser msgXmlParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        favoriteRecyclerView = (RecyclerView) findViewById(R.id.favoriteRecyclerView);

        favorites = new ArrayList<Favorite>();
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String url = "http://172.111.113.13:8080/MukSaeGwonServer/reviewFromMember.jsp?uId=1000";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.i("RESPONSE: ",response);
                        msgXmlParser = new MsgXmlParser(response);
                        msgXmlParser.xmlParsingFFM(favorites);

                        favoriteAdapter = new FavoriteAdapter(favorites);
                        favoriteRecyclerView.setAdapter(favoriteAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);




        ImageView imageView = (ImageView) findViewById(R.id.btn_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                startActivity(intent);
            }
        });

    }
}