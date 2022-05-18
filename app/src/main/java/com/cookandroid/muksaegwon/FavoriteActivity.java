package com.cookandroid.muksaegwon;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.muksaegwon.adapter.FavoriteAdapter;
import com.cookandroid.muksaegwon.model.Favorite;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView favoriteRecyclerView;
    FavoriteAdapter favoriteAdapter;
    ArrayList<Favorite> favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        favoriteRecyclerView = (RecyclerView) findViewById(R.id.favoriteRecyclerView);

        favorites = new ArrayList<Favorite>();
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}