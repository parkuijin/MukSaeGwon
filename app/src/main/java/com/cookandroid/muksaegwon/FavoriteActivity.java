package com.cookandroid.muksaegwon;

import android.content.Intent;
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
import com.cookandroid.muksaegwon.adapter.FavoriteAdapter;
import com.cookandroid.muksaegwon.controller.MsgXmlParser;
import com.cookandroid.muksaegwon.model.Favorite;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView favoriteRecyclerView;
    FavoriteAdapter favoriteAdapter;
    ArrayList<Favorite> favorites;
    MsgXmlParser msgXmlParser;

    SharedPreferences preferences;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = preferences.getString("userId","");

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        favoriteRecyclerView = (RecyclerView) findViewById(R.id.favoriteRecyclerView);
        loadFavorites();

        ImageView imageView = (ImageView) findViewById(R.id.btn_back5);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadFavorites();
    }

    public void loadFavorites(){
        favorites = new ArrayList<Favorite>();
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/favoriteFromMember.jsp?uId="+userId;
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

                        Intent intent = new Intent(getApplicationContext(), InfoStoreActivity.class);
                        favoriteAdapter = new FavoriteAdapter(favorites, intent);
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