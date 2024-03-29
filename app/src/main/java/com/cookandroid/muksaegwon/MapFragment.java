package com.cookandroid.muksaegwon;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cookandroid.muksaegwon.controller.MsgXmlParser;
import com.cookandroid.muksaegwon.model.Store;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, IntroActivity.LoginCallback {
    // LOGIN
    IntroActivity introActivity = new IntroActivity();
    public static MapFragment mapFragment;


    private static final String TAG = MapFragment.class.getSimpleName();
    // weather
    ImageView weather;

    // EditText
    EditText searchBar;

    // GoogleMap
    GoogleMap mMap;

    Location myLocation;

    private View mLayout;

    // 현재 위치를 위한 변수 선언부
    LocationRequest locationRequest;
    LocationSettingsRequest.Builder builder;
    FusedLocationProviderClient mFusedLocationClient;

    private Location location;

    private Marker currentMarker = null;
    private int UPDATE_INTERVAL_MS = 5000; //5초
    private int FASTEST_UPDATE_INTERVAL_MS = 2500; // 2.5초

    ArrayList<Marker> markers = new ArrayList<>();
    Marker marker = null;

    private VisibleRegion vr;
    double left;
    double bottom;
    double right;
    double top;

    Location currentLocation;
    LatLng currentPosition;   // Latitude & Longitude

    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = this;

        searchBar = (EditText) v.findViewById(R.id.SearchBar);
        // 지도 구현
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

        builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
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
        /*searchBar.setOnKeyListener(new View.OnKeyListener() {
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
                                locSearch(addresses);
                            }
                        } catch (Exception e) {
                        }
                        break;
                }
                return true;
            }
        });*/

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this::onMapReady);

        return v;
    }

    //LOGIN
    public void startLogin(boolean b){
        introActivity.setLoginCallback(this);
        introActivity.LogOn(b);
    }

    @Override
    public void logined(boolean  b) {
        if (b){
            startLocationUpdates();
        } else {
            Toast.makeText(getContext(),"위치 권한이 필요합니다",Toast.LENGTH_LONG).show();
            loadComplete();
        }
    }

    protected void startLocationUpdates() {
        Log.i("FF","startlocationUpdates Started");
        if (!checkLocationServicesStatus()) {
            Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting");
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.d("startLocationUpdates: ", "call mFusedLocationClient.requestLocationUpdates");
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mFusedLocationClient != null) {
            Log.d(TAG, "onStop : call stopLocationUpdates");
            startLocationUpdates();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mFusedLocationClient != null) {
            Log.d(TAG, "onStop : call stopLocationUpdates");
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {
        if (currentMarker != null) currentMarker.remove();
        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_dot);
        if (bitmapdraw != null) {
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        }
        currentMarker = mMap.addMarker(markerOptions);
        loadComplete();
    }


    public void loadComplete(){
        IntroActivity introActivity = (IntroActivity) IntroActivity.activity;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                introActivity.finish();
            }
        }, 2000);


        vr = mMap.getProjection().getVisibleRegion();
        left = vr.latLngBounds.southwest.longitude;
        bottom = vr.latLngBounds.southwest.latitude;
        right = vr.latLngBounds.northeast.longitude;
        top = vr.latLngBounds.northeast.latitude;
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        try {
            String storeId = marker.getTag().toString();

            Intent intent = new Intent(getContext(), InfoStoreActivity.class);
            intent.putExtra("storeId", storeId);
            startActivity(intent);
            return false;
        } catch (NullPointerException e){
            return false;
        }
    }

    public void nearPlaces(){
        Log.i("lbrt: ", left + " " + bottom + " " + right + " " + top);
        String markerUrl = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/markerCluster.jsp?left=" + left + "&right=" + right + "&top=" + top + "&bottom=" + bottom;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                markerUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("MARKER:", response);
                        ArrayList<Store> stores= new ArrayList<>();
                        MsgXmlParser msgXmlParser = new MsgXmlParser(response);
                        msgXmlParser.xmlNearByPlaces(stores);

                        LatLng latLng;
                        MarkerOptions markerOptions = new MarkerOptions();

                        // 모든 마커 지우기
                        if(marker != null){
                            for(int i=0; i<markers.size();i++){
                                markers.get(i).remove();
                            }
                        }

                        for(int i=0;i<stores.size();i++){
                            latLng = new LatLng(stores.get(i).getLat(),stores.get(i).getLng());
                            markerOptions.position(latLng);
                            markerOptions.title(stores.get(i).getStoreName());
                            markerOptions.draggable(true);
                            marker = mMap.addMarker(markerOptions);
                            marker.setTag(stores.get(i).getStoreId());
                            markers.add(marker);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        Log.i("MAP LOADED: ","true");

        try {
            // Customise the styling of the bas
            // style map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style_json));

            if (!success) {
                Toast.makeText(getContext(), "Style parsing failed.", Toast.LENGTH_LONG).show();
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        mMap.setOnMarkerClickListener(this);
        // Marker Cluster (영역에 보이는 마커 찍기)
    }

    private boolean initFlag = true;
    CameraPosition cameraPosition;
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                myLocation = locationList.get(locationList.size() - 1);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (initFlag) {
                                    cameraPosition = new CameraPosition.Builder()
                                            .target(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()))
                                            .zoom(17.0f)
                                            .build();

                                    ImageView categoryBtn = getActivity().findViewById(R.id.CategoryButton);

                                    categoryBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(getContext(), CategoryActivity.class);
                                            intent.putExtra("lat", myLocation.getLatitude());
                                            intent.putExtra("lng", myLocation.getLongitude());
                                            intent.putExtra("left", left);
                                            intent.putExtra("bottom", bottom);
                                            intent.putExtra("right", right);
                                            intent.putExtra("top", top);
                                            startActivity(intent);
                                        }
                                    });

                                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                    if (left != 0) {
                                        nearPlaces();
                                        initFlag = false;
                                    }
                                }
                            }
                        });
                    }
                }).start();

                Log.d("CURRENT LOCATION: ", myLocation.getLatitude() + " " + myLocation.getLongitude());

                // Marker 생성 필요
                String makerTitle = "내 위치";
                String markerSnippet = "위도:" + String.valueOf(myLocation.getLatitude())
                        + " 경도:" + String.valueOf(myLocation.getLongitude());
                setCurrentLocation(myLocation, makerTitle, markerSnippet);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        nearPlaces();
    }

    // 구글맵 주소 검색 메서드
    protected void locSearch(List<Address> addresses) {
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
