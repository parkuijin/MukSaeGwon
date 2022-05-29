package com.cookandroid.muksaegwon;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

public class CategoryMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = MapFragment.class.getSimpleName();

    GoogleMap mMap;

    ImageView categoryMapFinBtn;

    String category;
    double lat, lng;
    private CameraPosition cameraPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_map);

        category = getIntent().getStringExtra("category");
        Log.i("CATEGORY: ", category);
        lat = getIntent().getDoubleExtra("lat",0);
        lng = getIntent().getDoubleExtra("lng",0);
        Log.i("LATLNG: ", lat+" "+lng);

        categoryMapFinBtn = (ImageView) findViewById(R.id.CategoryMapFinBtn);

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 지도 구현
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this::onMapReady);

        categoryMapFinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.style_json));

            if (!success) {
                Toast.makeText(getApplicationContext(), "Style parsing failed.", Toast.LENGTH_LONG).show();
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        } // try - catch
        cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat,lng))
                .zoom(17.0f)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}