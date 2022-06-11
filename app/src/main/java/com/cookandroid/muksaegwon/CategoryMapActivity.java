package com.cookandroid.muksaegwon;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

import java.util.ArrayList;
import java.util.List;

public class CategoryMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String TAG = MapFragment.class.getSimpleName();

    GoogleMap mMap;

    ImageView categoryMapFinBtn;

    String category;
    double lat, lng;
    private CameraPosition cameraPosition;

    Marker marker;
    ArrayList<Marker> markers = new ArrayList<>();
    private double left=0;
    private double bottom;
    private double right;
    private double top;

    private Marker currentMarker=null;
    private int UPDATE_INTERVAL_MS = 1000;
    private int FASTEST_UPDATE_INTERVAL_MS = 500;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest.Builder builder;

    TextView categoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_map);

        categoryTitle = (TextView)findViewById(R.id.categoryTitle);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

        builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);

        category = getIntent().getStringExtra("category");
        setCatrogyrTitle(categoryTitle, category);

        Log.i("CATEGORY: ", category);
        lat = getIntent().getDoubleExtra("lat",0);
        lng = getIntent().getDoubleExtra("lng",0);
        left = getIntent().getDoubleExtra("left",0);
        bottom = getIntent().getDoubleExtra("bottom",0);
        right = getIntent().getDoubleExtra("right",0);
        top = getIntent().getDoubleExtra("top",0);
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

    private void setCatrogyrTitle(TextView categoryTitle, String category) {
        if (category.equals("corn"))
            categoryTitle.setText("옥수수");
        else if ( category.equals("fish"))
            categoryTitle.setText("붕어빵");
        else if ( category.equals("topokki"))
            categoryTitle.setText("떡볶이");
        else if ( category.equals("eomuk"))
            categoryTitle.setText("어묵");
        else if ( category.equals("sweetpotato"))
            categoryTitle.setText("고구마");
        else if ( category.equals("toast"))
            categoryTitle.setText("토스트");
        else if ( category.equals("takoyaki"))
            categoryTitle.setText("타코야끼");
        else if ( category.equals("waffle"))
            categoryTitle.setText("와플");
        else if ( category.equals("dakggochi"))
            categoryTitle.setText("닭꼬치");
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

        markCurrentLocation(new LatLng(lat,lng));

//        startLocationUpdates();

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

        placesByCategory(category);

        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        Log.i("FF","startlocationUpdates Started");
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    private Location myLocation;

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            List<Location> locationList = locationResult.getLocations();
                myLocation = locationList.get(locationList.size() - 1);
                Log.d("CURRENT LOCATION: ", myLocation.getLatitude() + " " + myLocation.getLongitude());

                // Marker 생성 필요
                String makerTitle = "내 위치";
                String markerSnippet = "위도:" + String.valueOf(myLocation.getLatitude())
                        + " 경도:" + String.valueOf(myLocation.getLongitude());
                setCurrentLocation(myLocation, makerTitle, markerSnippet);
        }
    };

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
    }

    public void markCurrentLocation(LatLng latLng){
        if (currentMarker != null) currentMarker.remove();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.draggable(true);

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_dot);
        if (bitmapdraw != null) {
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        }
        currentMarker = mMap.addMarker(markerOptions);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        String storeId = marker.getTag().toString();


        Intent intent = new Intent(getApplicationContext(), InfoStoreActivity.class);
        intent.putExtra("storeId", storeId);
        startActivity(intent);

        return false;
    }

    public void placesByCategory(String category){

        Log.i("LBRT: ",left+" "+bottom+" "+right+" "+top);
        String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/placesByCategory.jsp?left="+left+"&bottom="+bottom+"&right="+right+"&top="+top+"&category="+category;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE: ", response);
                        nearPlaces(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    public void nearPlaces(String response){
        ArrayList<Store> stores= new ArrayList<>();
//        String test = "<store><storeName>"+"test"+"</storeName><lat>"+ 37.58330 +"</lat><lng>"+ 126.92509 +"</lng></store>"
//                + "<store><storeName>"+"test"+"</storeName><lat>"+ 37.58225 +"</lat><lng>"+ 126.92612 +"</lng></store>";
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

    @Override
    protected void onStop() {
        super.onStop();
        if (mFusedLocationClient != null) {
            Log.d(TAG, "onStop : call stopLocationUpdates");
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }
}