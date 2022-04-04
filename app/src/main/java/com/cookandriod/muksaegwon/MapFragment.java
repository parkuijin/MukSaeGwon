package com.cookandriod.muksaegwon;

import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = MapFragment.class.getSimpleName();

    // weather
    ImageView weather;

    // EditText
    EditText searchBar;

    // GoogleMap
    GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        searchBar = (EditText) v.findViewById(R.id.SearchBar);

        // 지도 구현
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this::onMapReady);

        // 날씨 부분
        weather = (ImageView) v.findViewById(R.id.Weather);

        String url = "https://api.openweathermap.org/data/2.5/weather?lat=37.56&lon=126.97&appid=f2b522c6912209a728b3fd17a2982016";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jobj = new JSONObject(response);
                            JSONArray jsonArray = jobj.getJSONArray("weather");
                            JSONObject weatherArray = jsonArray.getJSONObject(0);
                            String weatherString = weatherArray.getString("main");
                            if (weatherString.equals("Clouds"))
                                weather.setImageResource(R.drawable.cloud);
                            else if (weatherString.equals("Clear"))
                                weather.setImageResource(R.drawable.sunny);
                            else if (weatherString.equals("Thunderstorm"))
                                weather.setImageResource(R.drawable.thunderstorm);
                            else if (weatherString.equals("Drizzle"))
                                weather.setImageResource(R.drawable.drizzle);
                            else if (weatherString.equals("Rain"))
                                weather.setImageResource(R.drawable.rain);
                            else if (weatherString.equals("Snow"))
                                weather.setImageResource(R.drawable.snow);
                            else if (weatherString.equals("Atmosphere"))
                                weather.setImageResource(R.drawable.foggy);
                            else weather.setImageResource(R.drawable.cloud);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        return;
                    }
                }
        );
        requestQueue.add(stringRequest);

        // EditText Enter EVENT 구현
        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:

                        String searchText = searchBar.getText().toString();

                        Geocoder geocoder = new Geocoder(getContext());
                        List<Address> addresses = null;

                        try {
                            addresses = geocoder.getFromLocationName(searchText, 3);
                            if (addresses != null && !addresses.equals(" ")) {
                                search(addresses);
                            }
                        } catch (Exception e) {
                        }
                        break;
                }
                return true;
            }
        });

        return v;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style_json));

            if (!success) {
                Toast.makeText(getContext(),"Style parsing failed.",Toast.LENGTH_LONG).show();
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

    }

    // 구글맵 주소 검색 메서드
    protected void search (List<Address> addresses) {
        Address address = addresses.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

        String addressText = String.format(
                "%s, %s",
                address.getMaxAddressLineIndex() > 0 ? address
                        .getAddressLine(0) : " ", address.getFeatureName());

        /*searchBar.setText("Latitude" + address.getLatitude() + "Longitude" + address.getLongitude() + "\n" + addressText);*/

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}
