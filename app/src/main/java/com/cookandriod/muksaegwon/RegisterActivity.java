package com.cookandriod.muksaegwon;

import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class RegisterActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = MapFragment.class.getSimpleName();

    // GoogleMap
    GoogleMap mMap;
    Button ftRegisterBtn;
    ImageView regFinBtn;
    TextView currentLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ftRegisterBtn = (Button) findViewById(R.id.FoodtruckRegister);
        regFinBtn = (ImageView) findViewById(R.id.RegFinBtn);

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 지도 구현
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this::onMapReady);

        ftRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodTruckInfoActivity.class);
                startActivity(intent);
            }
        });

        regFinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        currentLoc = (TextView) findViewById(R.id.CurrentLocation);
        Geocoder geocoder = new Geocoder(getApplicationContext());

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
        }

        LatLng SEOUL = new LatLng(37.56, 126.97);
        MarkerOptions markerOptions = new MarkerOptions(); // 마커 생성
        markerOptions.position(SEOUL);
        markerOptions.title("서울"); // 마커 제목
        markerOptions.snippet("한국의 수도"); // 마커 설명
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL)); // 초기 위치
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15)); // 줌의 정도

        // 맵 터치 이벤트 구현
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                mMap.clear();
                MarkerOptions foodTruck = new MarkerOptions();
                // 마커 타이틀
                foodTruck.title("마커 좌표");
                Double latitude = point.latitude; // 위도
                Double longitude = point.longitude; // 경도

                // 마커의 스니펫(간단한 텍스트) 설정
                foodTruck.snippet(latitude.toString() + ", " + longitude.toString());

                // LatLng: 위도 경도 쌍을 나타냄
                foodTruck.position(new LatLng(latitude, longitude));

                // 마커 추가
                mMap.addMarker(foodTruck);

                // 현재 위치 설정
                currentLoc.setText(latitude + " " + longitude);

                // 역지오코딩
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 10);
                } catch (Exception e) {
                }
                if (addresses != null) {
                    if (addresses.size() == 0) {
                        Toast.makeText(getApplicationContext(), "지명이 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        String city = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        /*Log.d("찾은 주소",addresses.get(0).toString());*/
                        /*currentLoc.setText(addresses.get(0).getAddressLine(0));*/
                    }
                }
            }
        });
    }
}