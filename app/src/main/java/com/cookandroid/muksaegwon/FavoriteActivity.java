package com.cookandroid.muksaegwon;

import android.os.Bundle;
import android.util.Log;

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

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        favoriteRecyclerView = (RecyclerView) findViewById(R.id.favoriteRecyclerView);
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        favorites = new ArrayList<>();

        // 집 192.168.0.22
        // 학교 172.111.113.13
        String url = "http://172.111.113.13:8080/MukSaeGwonServer/favoriteFromMember.jsp?uId=1000";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE: ",response);
                        result = response;
                        msgXmlParser = new MsgXmlParser(result);
                        msgXmlParser.xmlParsingFFM(favorites);

                        favoriteAdapter = new FavoriteAdapter(favorites);
                        favoriteAdapter.notifyDataSetChanged();

                        favoriteRecyclerView.setAdapter(favoriteAdapter);
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