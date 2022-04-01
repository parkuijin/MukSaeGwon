package com.cookandriod.muksaegwon;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = MapFragment.class.getSimpleName();;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // 지도 구현
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this::onMapReady);


        return v;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        GoogleMap mMap = googleMap;

        // 지도 스타일 설정, 설정 파일 src/main/res/raw/style_json.json
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(getActivity(),R.raw.style_json);
        mMap.setMapStyle(style);

        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();         // 마커 생성
        markerOptions.position(SEOUL);
        markerOptions.title("서울");                         // 마커 제목
        markerOptions.snippet("한국의 수도");         // 마커 설명
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));                 // 초기 위치
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));                         // 줌의 정도

    }
}
